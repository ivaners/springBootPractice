package com.code.demo.modules.auth.service;

import com.code.demo.domain.bo.AuthUser;

import java.util.List;

/* *
 *
 * @Description 
 * @Date 21:14 2018/3/17
 */
public interface UserService {

    String loadAccountRole(String appId);

    List<AuthUser> getUserList();

    List<AuthUser> getUserListByRoleId(Integer roleId);

    boolean authorityUserRole(String appId, int roleId);

    boolean deleteAuthorityUserRole(String uid,int roleId);

    AuthUser getUserByAppId(String appId);

    List<AuthUser> getNotAuthorityUserListByRoleId(Integer roleId);
}
