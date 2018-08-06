package com.code.demo.modules.auth.service;

import com.code.demo.domain.bo.AuthResource;

import java.util.List;

/* *
 *
 * @Description 
 * @Date 13:39 2018/3/18
 */
public interface ResourceService {

    List<AuthResource> getAuthorityMenusByUid(String appId);

    List<AuthResource> getMenus();

    Boolean addMenu(AuthResource menu);

    Boolean modifyMenu(AuthResource menu);

    Boolean deleteMenuByMenuId(Integer menuId);


    List<AuthResource> getApiTeamList();

    List<AuthResource> getApiList();

    List<AuthResource> getApiListByTeamId(Integer teamId);

    List<AuthResource> getAuthorityApisByRoleId(Integer roleId);

    List<AuthResource> getAuthorityMenusByRoleId(Integer roleId);

    List<AuthResource> getNotAuthorityApisByRoleId(Integer roleId);

    List<AuthResource> getNotAuthorityMenusByRoleId(Integer roleId);
}
