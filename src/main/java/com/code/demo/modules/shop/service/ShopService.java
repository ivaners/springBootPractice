package com.code.demo.modules.shop.service;

import com.code.demo.domain.bo.Goods;

import java.util.List;
import java.util.Map;

public interface ShopService {
    List<Goods> findAll(String name);

    Goods goodsDetail(Integer id);

    void seckill(Map data) throws Exception;

    String getGoodsRandomName(Integer id);

    void order(Map data) throws Exception;

    void orderByOptimistic(Map data) throws Exception;
}
