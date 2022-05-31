package com.online.taxi.order.service.impl;

import com.online.taxi.order.constant.RedisKeyConstant;
import com.online.taxi.order.service.GrabService;
import com.online.taxi.order.service.OrderService;
import com.online.taxi.order.service.SeckillGrabService;
import com.online.taxi.order.service.SeckillOrderService;
import org.redisson.RedissonRedLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author 马士兵教育：晁鹏飞
 */
@Service("seckillRedisRedissonRedLockLockService")
public class SeckillRedisRedissonRedLockLockServiceImpl implements SeckillGrabService {

    // 红锁
    @Autowired
    @Qualifier("redissonRed1")
    private RedissonClient redissonRed1;
    @Autowired
    @Qualifier("redissonRed2")
    private RedissonClient redissonRed2;
    @Autowired
    @Qualifier("redissonRed3")
    private RedissonClient redissonRed3;
    @Autowired
    @Qualifier("redissonRed4")
    private RedissonClient redissonRed4;
    @Autowired
    @Qualifier("redissonRed5")
    private RedissonClient redissonRed5;
//
//    @Autowired
//    private Redisson redisson;

    @Autowired
    SeckillOrderService seckillOrderService;

    @Override
    public String grabOrder(int goodsId, int userId) {
        System.out.println("红锁实现类");
        //生成key
        String lockKey = ("goodsId_" + goodsId).intern();


        //redisson锁 单节点
//        RLock rLock = redissonRed1.getLock(lockKey);

        //红锁 redis son
        RLock rLock1 = redissonRed1.getLock(lockKey);
        RLock rLock2 = redissonRed2.getLock(lockKey);
        RLock rLock3 = redissonRed3.getLock(lockKey);
        RLock rLock4 = redissonRed4.getLock(lockKey);
        RLock rLock5 = redissonRed5.getLock(lockKey);
        RedissonRedLock rLock = new RedissonRedLock(rLock1,rLock2,rLock3,rLock4,rLock5);



        try {
            // 红锁
            rLock.lock();

            System.out.println("加锁成功");
            // 此代码默认 设置key 超时时间30秒，过10秒，再延时

            System.out.println("用户:"+userId+" 执行秒杀");
//                try {
//                TimeUnit.MINUTES.sleep(5);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            boolean b = seckillOrderService.grab(goodsId, userId);
            if(b) {
                System.out.println("用户:"+userId+" 抢单成功");
            }else {
                System.out.println("用户:"+userId+" 抢单失败");
            }




        } finally {
        	rLock.unlock();
        }
        return null;
    }


}