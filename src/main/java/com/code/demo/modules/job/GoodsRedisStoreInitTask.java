package com.code.demo.modules.job;

import com.code.demo.common.constant.CommonConstant;
import com.code.demo.dao.cluster.GoodsMapper;
import com.code.demo.domain.bo.Goods;
import com.code.demo.modules.shop.goods.GoodsRedisStoreCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class GoodsRedisStoreInitTask {
    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private GoodsRedisStoreCache goodsRedisStore;

    @Autowired
    private StringRedisTemplate redis;

    /**
     * 每隔1分钟触发一次
     */
    @Scheduled(cron = "0 0/1 * * * ? ")
    public void doInit()
    {
        Date now = new Date();
        List<Goods> goods = goodsMapper.findByTime(now, now);
        for (Goods item : goods)
        {
            goodsRedisStore.doinit(item);
        }
    }

    @Scheduled(cron = "0 0/1 * * * ? ")
    public void doClear() throws ParseException {
        Set<String> keys = redis.keys(CommonConstant.RedisKey.SECKILL_HANDLE_LIST_PREFIX);

        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(String key : keys){
            //验证token是否过期，过期了释放redis库存
            List<Object> result = redis.opsForHash().multiGet(key, Arrays.asList("goods", "num", "add_time"));
            Date time = date.parse(result.get(2).toString());
            return;
        }
    }
}
