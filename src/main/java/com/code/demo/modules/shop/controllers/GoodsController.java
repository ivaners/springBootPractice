package com.code.demo.modules.shop.controllers;

import com.code.demo.common.BasicAction;
import com.code.demo.common.util.Result;
import com.code.demo.modules.shop.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GoodsController extends BasicAction {
    @Autowired
    ShopService shopService;

    @GetMapping("/goods")
    public Result List(@RequestParam(value = "name", defaultValue = "") String name) {
        return Result.ok().put("data", shopService.findAll(name));
    }

    @GetMapping("/goods/{id}")
    public Result detail(@PathVariable Integer id){
        return Result.ok().put("data", shopService.goodsDetail(id));
    }

    @GetMapping("seckill_link/{id}")
    public Result seckillLink(@PathVariable Integer id){
        return Result.ok().put("data", shopService.getGoodsRandomName(id));
    }
}
