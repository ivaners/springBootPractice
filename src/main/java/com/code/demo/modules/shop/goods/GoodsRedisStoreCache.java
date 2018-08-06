package com.code.demo.modules.shop.goods;

import com.code.demo.common.constant.CommonConstant;
import com.code.demo.domain.bo.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class GoodsRedisStoreCache {
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsRedisStoreCache.class);

    @Autowired
    private StringRedisTemplate redis;

    public void doinit(Goods goods){
        String key = MessageFormat.format(CommonConstant.RedisKey.REDIS_GOODS_STORE,
                new Object[] { goods.getRandomName() });
        if(!redis.hasKey(key)){
            long now = System.currentTimeMillis();
            redis.opsForValue().set(key, String.valueOf(goods.getStore()),
                    (goods.getEndTime().getTime()-now)/1000,TimeUnit.SECONDS);
        }
    }


    /**
     * 加减库存,正数加库存，负数减库存
     * @param goodsRandomName
     * @return
     */
    public boolean incrStore(String goodsRandomName, Integer num){
        return redis.opsForValue().increment(
                MessageFormat.format(CommonConstant.RedisKey.REDIS_GOODS_STORE, new Object[] { goodsRandomName }),
                num) == null ? false : true;
    }
}
