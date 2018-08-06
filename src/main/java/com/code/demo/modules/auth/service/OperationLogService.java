package com.code.demo.modules.auth.service;

import com.code.demo.domain.bo.AuthOperationLog;

import java.util.List;

/* *
 *
 * @Description 
 * @Date 9:30 2018/4/22
 */
public interface OperationLogService {

    List<AuthOperationLog> getOperationList();
}
