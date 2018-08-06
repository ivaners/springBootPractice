package com.code.demo.modules.auth.service;

import com.code.demo.domain.bo.AuthAccountLog;

import java.util.List;

/* *
 *
 * @Description 
 * @Date 9:30 2018/4/22
 */
public interface AccountLogService {

    List<AuthAccountLog> getAccountLogList();
}
