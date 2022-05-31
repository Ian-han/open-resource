package com.online.taxi.order.service.impl;

import com.online.taxi.order.annotation.DistributedLock;
import com.online.taxi.order.service.GrabService;
import com.online.taxi.order.service.OrderService;
import com.online.taxi.order.service.SeckillGrabService;
import com.online.taxi.order.service.SeckillOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author yueyi2019
 */
@Service("seckillCloudService")
public class SeckillCloudServiceImpl implements SeckillGrabService {

    @Autowired
    SeckillOrderService seckillOrderService;

    @Override
    @DistributedLock(value = "redisLockRegistry",time = 10)
    public String grabOrder(int goodsId, int userId) {

        System.out.println("用户:"+userId+" 执行秒杀");
//        try {
//            TimeUnit.MINUTES.sleep(5);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        boolean b = seckillOrderService.grab(goodsId, userId);
        if(b) {
            System.out.println("用户:"+userId+" 抢单成功");
        }else {
            System.out.println("用户:"+userId+" 抢单失败");
        }

        return null;
    }
}