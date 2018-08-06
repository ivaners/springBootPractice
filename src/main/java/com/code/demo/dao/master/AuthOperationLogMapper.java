package com.code.demo.dao.master;

import com.code.demo.domain.bo.AuthOperationLog;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;

/* *
 *
 * @Description 
 * @Date 8:28 2018/4/22
 */
@Repository
public interface AuthOperationLogMapper {

    List<AuthOperationLog> selectOperationLogList();

    int insertSelective(AuthOperationLog operationLog) throws DataAccessException;
}
