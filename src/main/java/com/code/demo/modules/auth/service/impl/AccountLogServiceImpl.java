package com.code.demo.modules.auth.service.impl;

import com.code.demo.dao.master.AuthAccountLogMapper;
import com.code.demo.domain.bo.AuthAccountLog;
import com.code.demo.modules.auth.service.AccountLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/* *
 *
 * @Description 
 * @Date 9:32 2018/4/22
 */
@Service
public class AccountLogServiceImpl implements AccountLogService {

    @Autowired
    AuthAccountLogMapper authAccountLogMapper;

    @Override
    public List<AuthAccountLog> getAccountLogList() {
        return authAccountLogMapper.selectAccountLogList();
    }
}
