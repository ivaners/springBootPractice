package com.code.demo.dao.master;

import com.code.demo.domain.bo.AuthUser;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthUserMapper {

    int deleteByPrimaryKey(String uid) throws DataAccessException;

    int insert(AuthUser record) throws DataAccessException;

    int insertSelective(AuthUser record) throws DataAccessException;

    AuthUser selectByPrimaryKey(String uid) throws DataAccessException;

    int updateByPrimaryKeySelective(AuthUser record) throws DataAccessException;

    int updateByPrimaryKey(AuthUser record) throws DataAccessException;

    String selectUserRoles(String appId) throws DataAccessException;

    AuthUser selectByUniqueKey(String appId) throws DataAccessException;

    List<AuthUser> selectUserList() throws DataAccessException;

    List<AuthUser> selectUserListByRoleId(Integer roleId) throws DataAccessException;

    List<AuthUser> selectUserListExtendByRoleId(Integer roleId) throws DataAccessException;
}