package com.online.taxi.order.service;


/**
 * @author yueyi2019
 */
public interface SeckillGrabService {

    /**
     * 秒杀
     * @param goodsId
     * @param userId
     * @return
     */
    public String grabOrder(int goodsId, int userId);
}
