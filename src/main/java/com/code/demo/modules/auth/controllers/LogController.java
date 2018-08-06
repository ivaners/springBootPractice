package com.code.demo.modules.auth.controllers;

import com.code.demo.common.BasicAction;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.code.demo.common.util.Result;
import com.code.demo.domain.bo.AuthAccountLog;
import com.code.demo.domain.bo.AuthOperationLog;
import com.code.demo.modules.auth.service.AccountLogService;
import com.code.demo.modules.auth.service.OperationLogService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/* *
 * @Description
 * @Date 12:20 2018/4/22
 */
@RestController
@RequestMapping("/log")
public class LogController extends BasicAction {

    @Autowired
    AccountLogService accountLogService;

    @Autowired
    OperationLogService operationLogService;

    @SuppressWarnings("unchecked")
    @ApiOperation(value = "获取日志记录", httpMethod = "GET")
    @RequestMapping("/accountLog/{currentPage}/{pageSize}")
    public Result getAccountLogList(@PathVariable Integer currentPage, @PathVariable Integer pageSize ) {
        PageHelper.startPage(currentPage, pageSize);
        List<AuthAccountLog> accountLogs = accountLogService.getAccountLogList();
        PageInfo pageInfo = new PageInfo(accountLogs);
        return Result.ok("return accountLogs success").put("data",pageInfo);
    }

    @SuppressWarnings("unchecked")
    @ApiOperation(value = "获取用户操作api日志列表", httpMethod = "GET")
    @RequestMapping("/operationLog/{currentPage}/{pageSize}")
    public Result getOperationLogList(@PathVariable Integer currentPage, @PathVariable Integer pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<AuthOperationLog> authOperationLogs = operationLogService.getOperationList();
        PageInfo pageInfo = new PageInfo(authOperationLogs);
        return Result.ok("return operationLogList success").put("data", pageInfo);
    }
}
