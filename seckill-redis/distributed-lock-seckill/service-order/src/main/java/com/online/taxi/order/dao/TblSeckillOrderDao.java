package com.online.taxi.order.dao;

import com.online.taxi.order.entity.TblSeckillOrder;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
public interface TblSeckillOrderDao {
    int deleteByPrimaryKey(Integer orderId);

    int insert(TblSeckillOrder record);

    int insertSelective(TblSeckillOrder record);

    TblSeckillOrder selectByPrimaryKey(Integer orderId);

    int updateByPrimaryKeySelective(TblSeckillOrder record);

    int updateByPrimaryKey(TblSeckillOrder record);
}