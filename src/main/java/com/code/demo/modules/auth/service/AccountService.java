package com.code.demo.modules.auth.service;

import com.code.demo.domain.bo.AuthUser;
import com.code.demo.domain.vo.Account;

/* *
 *
 * @Description 
 * @Date 22:02 2018/3/7
 */
public interface AccountService {

    Account loadAccount(String appId);
    boolean isAccountExistByUid(String uid);
    boolean registerAccount(AuthUser account);
    String loadAccountRole(String appId);
}
