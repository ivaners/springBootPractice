package com.code.demo.modules.shop.controllers;

import com.code.demo.common.BasicAction;
import com.code.demo.common.util.Result;
import com.code.demo.modules.shop.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class ShopController extends BasicAction {
    @Autowired
    ShopService shopService;

    @PostMapping("seckill")
    public Result seckill(HttpServletRequest request) throws Exception {
        Map<String, String> params = getRequestBody(request);
        shopService.seckill(params);
        return Result.ok();
    }

    @PostMapping("order")
    public Result order(HttpServletRequest request) throws Exception {
        Map<String, String> params = getRequestBody(request);
        shopService.orderByOptimistic(params);
        return Result.ok();
    }
}
