package com.code.demo.dao.master;

import com.code.demo.domain.bo.AuthRoleResource;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRoleResourceMapper {

    int deleteByPrimaryKey(Integer id) throws DataAccessException;

    int insert(AuthRoleResource record) throws DataAccessException;

    int insertSelective(AuthRoleResource record) throws DataAccessException;

    AuthRoleResource selectByPrimaryKey(Integer id) throws DataAccessException;

    int updateByPrimaryKeySelective(AuthRoleResource record) throws DataAccessException;

    int updateByPrimaryKey(AuthRoleResource record) throws DataAccessException;

    int deleteByUniqueKey(@Param("roleId") Integer roleId,@Param("resourceId") Integer resourceId) throws DataAccessException;
}