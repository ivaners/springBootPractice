package com.code.demo.dao.master;

import com.code.demo.domain.bo.AuthAccountLog;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;

/* *
 *
 * @Description 
 * @Date 8:27 2018/4/22
 */
@Repository
public interface AuthAccountLogMapper {

    List<AuthAccountLog> selectAccountLogList();

    int insertSelective(AuthAccountLog authAccountLog) throws DataAccessException;

}
