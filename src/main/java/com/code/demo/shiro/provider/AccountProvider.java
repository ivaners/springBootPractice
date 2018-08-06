package com.code.demo.shiro.provider;


import com.code.demo.domain.vo.Account;

/* *
 *
 * @Description  数据库用户密码账户提供
 * @Date 16:35 2018/2/11
 */
public interface AccountProvider {

    Account loadAccount(String appId);

}
