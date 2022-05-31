package com.online.taxi.order.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * tbl_seckill_order
 * @author 
 */
@Data
public class TblSeckillOrder implements Serializable {
    private Integer orderId;

    private Integer orderStatus;

    private String orderDescription;

    private Integer userId;

    private Date updateTime;

    private static final long serialVersionUID = 1L;
}