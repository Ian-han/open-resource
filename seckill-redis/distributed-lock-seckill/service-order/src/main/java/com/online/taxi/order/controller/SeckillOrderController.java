package com.online.taxi.order.controller;


import com.online.taxi.order.service.GrabService;
import com.online.taxi.order.service.SeckillGrabService;
import com.online.taxi.order.service.SeckillOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 秒杀controller
 * @author 马士兵教育：晁鹏飞
 */
@RestController
@RequestMapping("/seckill")
public class SeckillOrderController {

    // 无锁
    @Autowired
//    @Qualifier("seckillNoLockService")
    // jvm锁
//    @Qualifier("seckillJvmLockService")
    // mysql锁
//    @Qualifier("seckillMysqlLockService")
    //单个Redisson=redis son
//    @Qualifier("seckillRedisRedissonService")
    // 红锁
    @Qualifier("seckillRedisRedissonRedLockLockService")
    // cloud 锁
//    @Qualifier("seckillCloudService")
    // zookeeper
//    @Qualifier("seckillZookeeperService")
    private SeckillGrabService grabService;

    @GetMapping("/do/{goodsId}")
    public String grabMysql(@PathVariable("goodsId") int goodsId, int userId){
        System.out.println("goodsId:"+goodsId+",userId:"+userId);
        grabService.grabOrder(goodsId,userId);
        return "";
    }

    /**
     * 单个redisson
     */
    @Autowired
    @Qualifier("grabRedisRedissonService")
    private GrabService redisGrabService;


    @GetMapping("/do-redis/{orderId}")
    public String grabRedis(@PathVariable("orderId") int orderId, int driverId){
        System.out.println("order:"+orderId+",driverId:"+driverId);
        redisGrabService.grabOrder(orderId,driverId);
        return "";
    }
}