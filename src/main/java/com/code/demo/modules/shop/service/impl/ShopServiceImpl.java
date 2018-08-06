package com.code.demo.modules.shop.service.impl;

import com.code.demo.common.constant.CommonConstant;
import com.code.demo.common.lock.Locks;
import com.code.demo.dao.cluster.GoodsMapper;
import com.code.demo.dao.cluster.OrderMapper;
import com.code.demo.domain.bo.Goods;
import com.code.demo.domain.bo.Order;
import com.code.demo.modules.shop.goods.GoodsRedisStoreCache;
import com.code.demo.modules.shop.service.ShopService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class ShopServiceImpl implements ShopService {
    private Lock lock = new ReentrantLock(true);//互斥锁 参数默认false，不公平锁

    @Autowired
    StringRedisTemplate redis;

    @Autowired
    GoodsMapper goodsMapper;

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    GoodsRedisStoreCache goodsRedisStoreCache;

    @Override
    public List<Goods> findAll(String name) {
        return goodsMapper.findAll(name);
    }

    @Override
    public Goods goodsDetail(Integer id) {
        return goodsMapper.selectByPrimaryKey(id);
    }

    @Override
    public String getGoodsRandomName(Integer id) {
        Goods goods = goodsDetail(id);
        long now = System.currentTimeMillis();

        // 已经开始了活动，则输出抢购链接
        if (goods.getStartTime().getTime() < now && now < goods.getEndTime().getTime())
        {
            return goods.getRandomName();
        }

        return StringUtils.EMPTY;
    }

    @Override
    @Locks
    public void seckill(Map data) throws Exception {
        try {
            Integer number = Integer.parseInt(data.get("num").toString());
            if (number <= 0) {
                throw new Exception("购买数量不合法!");
            }
            Integer goodsStore = Integer.parseInt(redis.opsForValue().get(MessageFormat.format(
                    CommonConstant.RedisKey.REDIS_GOODS_STORE, data.get("goods_random_name"))));
            if (goodsStore < number || goodsStore <= 0) {
                throw new Exception("库存不足！");
            }
            String key = MessageFormat.format(CommonConstant.RedisKey.SECKILL_HANDLE_LIST,
                    data.get("goods_id"), data.get("mobile"));
            if (redis.hasKey(key)) {
                throw new Exception("您已经提交过抢购，请在一个小时内支付，否则取消资格...");
            }
            Map<String, String> map = new HashMap<>();
            map.put("mobile", data.get("mobile").toString());
            map.put("num", data.get("num").toString());
            map.put("goods", data.get("goods_random_name").toString());
            map.put("add_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            redis.opsForHash().putAll(key, map);
            goodsRedisStoreCache.incrStore(data.get("goods_random_name").toString(), number * -1);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }


    /**
     * 排他锁，悲观锁实现
     * @param data
     * @throws Exception
     */
    @Override
    @Transactional
    public void order(Map data) throws Exception {
        String key = MessageFormat.format(CommonConstant.RedisKey.SECKILL_HANDLE_LIST,
                data.get("goods_id"), data.get("mobile"));
        List<Object> result = redis.opsForHash().multiGet(key, Arrays.asList("num", "goods", "mobile"));
        if( result.get(1) == null ){
            throw new Exception("没有秒杀记录...");
        }
        try{
            Order order = new Order();
            Integer goodsId = Integer.parseInt(data.get("goods_id").toString());
            order.setGoodsId(goodsId);
            order.setMobile(result.get(2).toString());
            order.setNum(Integer.parseInt(result.get(0).toString()));
            order.setCreateTime(new Date());
            order.setDelFlag(true);
            orderMapper.insert(order);
            Map<String,Object> goods = new HashMap<>();
            goods.put("id", goodsId);
            goods.put("num", result.get(0));
            goodsMapper.getLock(goodsId);
            goodsMapper.updateStoreById(goods);
            redis.delete(key);
        }catch (Exception e){
            throw new Exception("下单异常!");
        }
    }

    @Override
    @Locks
    @Transactional
    public void orderByOptimistic(Map data) throws Exception{
        try{
            String key = MessageFormat.format(CommonConstant.RedisKey.SECKILL_HANDLE_LIST,
                    data.get("goods_id"), data.get("mobile"));
            List<Object> result = redis.opsForHash().multiGet(key, Arrays.asList("num", "goods", "mobile"));
            if( result.get(1) == null ){
                throw new Exception("没有秒杀记录...");
            }
            Order order = new Order();
            Integer goodsId = Integer.parseInt(data.get("goods_id").toString());
            order.setGoodsId(goodsId);
            order.setMobile(result.get(2).toString());
            order.setNum(Integer.parseInt(result.get(0).toString()));
            order.setCreateTime(new Date());
            order.setDelFlag(true);
            orderMapper.insert(order);
            Goods goodsInfo = goodsMapper.selectByPrimaryKey(goodsId);
            Map<String,Object> goods = new HashMap<>();
            goods.put("id", goodsInfo.getId());
            goods.put("num", result.get(0));
            goods.put("version", goodsInfo.getVersion());

            int res = goodsMapper.updateStore(goods);
            if(res<=0){
                throw new Exception("下单失败，请重试!");
            }
            redis.delete(key);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
