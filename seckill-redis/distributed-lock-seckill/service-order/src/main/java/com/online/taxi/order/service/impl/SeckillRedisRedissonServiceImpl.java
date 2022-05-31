package com.online.taxi.order.service.impl;

import com.online.taxi.order.service.GrabService;
import com.online.taxi.order.service.OrderService;
import com.online.taxi.order.service.SeckillGrabService;
import com.online.taxi.order.service.SeckillOrderService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author yueyi2019
 */
@Service("seckillRedisRedissonService")
public class SeckillRedisRedissonServiceImpl implements SeckillGrabService {

	@Autowired
	RedissonClient redissonClient;

//	@Autowired
//	Redisson redisson;

	@Autowired
	SeckillOrderService seckillOrderService;
	
    @Override
    public String grabOrder(int goodsId, int userId) {
        //生成key
    	String lock = "goodsId_"+(goodsId+"");
    	
    	RLock rlock = redissonClient.getLock(lock.intern());

//        RLock lock1 = redisson.getLock(lock.intern());

        try {
    		// 此代码默认 设置key 超时时间30秒，过10秒，再延时
    		rlock.lock();

//            lock1.lock();
			System.out.println("用户:"+userId+" 执行秒杀");

			try {
				TimeUnit.MINUTES.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			boolean b = seckillOrderService.grab(goodsId, userId);
			if(b) {
				System.out.println("用户:"+userId+" 抢单成功");
			}else {
				System.out.println("用户:"+userId+" 抢单失败");
			}
            

		} finally {
        	rlock.unlock();
//            lock1.unlock();
        }
        return null;
    }
}