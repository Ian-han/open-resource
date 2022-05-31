package com.online.taxi.order.dao;

import com.online.taxi.order.entity.TblInventory;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TblInventoryDao {
    int deleteByPrimaryKey(Integer goodId);

    int insert(TblInventory record);

    int insertSelective(TblInventory record);

    TblInventory selectByPrimaryKey(Integer goodId);

    int updateByPrimaryKeySelective(TblInventory record);

    int updateByPrimaryKey(TblInventory record);
}