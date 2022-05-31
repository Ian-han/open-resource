package com.online.taxi.order.service.impl;

import com.online.taxi.order.entity.TblOrderLock;
import com.online.taxi.order.lock.MysqlLock;
import com.online.taxi.order.service.GrabService;
import com.online.taxi.order.service.OrderService;
import com.online.taxi.order.service.SeckillGrabService;
import com.online.taxi.order.service.SeckillOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("seckillMysqlLockService")
public class SeckillMysqlLockServiceImpl implements SeckillGrabService {

    @Autowired
    private MysqlLock lock;


    @Autowired
    SeckillOrderService seckillOrderService;

    ThreadLocal<TblOrderLock> orderLock = new ThreadLocal<>();

    @Override
    public String grabOrder(int goodsId, int userId) {
        System.out.println("mysql 分布锁");
        //生成key
        TblOrderLock ol = new TblOrderLock();
        ol.setOrderId(goodsId);
        ol.setDriverId(userId);

        orderLock.set(ol);
        lock.setOrderLockThreadLocal(orderLock);

        // lock
        lock.lock();

        // 执行业务
        try {
            System.out.println("用户:"+userId+" 执行秒杀");

            boolean b = seckillOrderService.grab(goodsId, userId);
            if(b) {
                System.out.println("用户:"+userId+" 抢单成功");
            }else {
                System.out.println("用户:"+userId+" 抢单失败");
            }
        }finally {
            // 释放锁
            lock.unlock();
        }

        // 执行业务

        return null;
    }
}
