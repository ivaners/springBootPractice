package com.code.demo.dao.master;

import com.code.demo.domain.bo.AuthResource;
import com.code.demo.shiro.rule.RolePermRule;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthResourceMapper {
    int deleteByPrimaryKey(Integer id) throws DataAccessException;

    int insert(AuthResource record) throws DataAccessException;

    int insertSelective(AuthResource record) throws DataAccessException;

    AuthResource selectByPrimaryKey(Integer id) throws DataAccessException;

    int updateByPrimaryKeySelective(AuthResource record) throws DataAccessException;

    int updateByPrimaryKey(AuthResource record) throws DataAccessException;

    List<RolePermRule> selectRoleRules()  throws DataAccessException;

    List<AuthResource> selectAuthorityMenusByUid(String appId) throws DataAccessException;

    List<AuthResource> selectMenus() throws DataAccessException;

    List<AuthResource> selectApiTeamList() throws DataAccessException;

    List<AuthResource> selectApiList() throws DataAccessException;

    List<AuthResource> selectApiListByTeamId(Integer teamId) throws DataAccessException;

    List<AuthResource> selectApisByRoleId(Integer roleId) throws DataAccessException;

    List<AuthResource> selectMenusByRoleId(Integer roleId) throws DataAccessException;

    List<AuthResource> selectNotAuthorityApisByRoleId(Integer roleId) throws DataAccessException;

    List<AuthResource> selectNotAuthorityMenusByRoleId(Integer roleId) throws DataAccessException;
}