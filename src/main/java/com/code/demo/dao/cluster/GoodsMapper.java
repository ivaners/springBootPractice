package com.code.demo.dao.cluster;

import com.code.demo.domain.bo.Goods;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface GoodsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Goods record);

    int insertSelective(Goods record);

    Goods selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Goods record);

    int updateByPrimaryKey(Goods record);

    List<Goods> findAll(String name);

    List<Goods> findByTime(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

    void doOrder(Map<String, Object> paramMap);

    Goods getLock(Integer id);

    void updateStoreById(Map param);

    int updateStore(Map param);
}