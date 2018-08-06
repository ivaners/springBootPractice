package com.code.demo.modules.auth.service.impl;

import com.code.demo.dao.master.AuthOperationLogMapper;
import com.code.demo.domain.bo.AuthOperationLog;
import com.code.demo.modules.auth.service.OperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/* *
 *
 * @Description 
 * @Date 9:34 2018/4/22
 */
@Service
public class OperationLogServiceImpl implements OperationLogService {

    @Autowired
    AuthOperationLogMapper authOperationLogMapper;

    @Override
    public List<AuthOperationLog> getOperationList() {
        return authOperationLogMapper.selectOperationLogList();
    }
}
