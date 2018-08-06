package com.code.demo.common.constant;

public class CommonConstant {
    public interface RedisKey{
        // redis库存
        String REDIS_GOODS_STORE = "GOODS_RANDOM_NAME:{0}";

        // 秒杀处理列表
        String SECKILL_HANDLE_LIST = "S_H_LIST:{0}:{1}";

        String SECKILL_HANDLE_LIST_PREFIX = "S_H_LIST:*";
    }
}
