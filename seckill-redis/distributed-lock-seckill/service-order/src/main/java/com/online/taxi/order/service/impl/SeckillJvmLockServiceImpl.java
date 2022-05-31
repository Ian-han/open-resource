package com.online.taxi.order.service.impl;

import com.online.taxi.order.service.GrabService;
import com.online.taxi.order.service.OrderService;
import com.online.taxi.order.service.SeckillGrabService;
import com.online.taxi.order.service.SeckillOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("seckillJvmLockService")
public class SeckillJvmLockServiceImpl implements SeckillGrabService {

	@Autowired
	SeckillOrderService seckillOrderService;

	@Override
	public String grabOrder(int goodsId, int userId) {
		System.out.println("jvm 锁");
		String lock = (goodsId+"");

		synchronized (lock.intern()) {
			try {
				System.out.println("用户:"+userId+" 执行秒杀");

				boolean b = seckillOrderService.grab(goodsId, userId);
				if(b) {
					System.out.println("用户:"+userId+" 抢单成功");
				}else {
					System.out.println("用户:"+userId+" 抢单失败");
				}

			} finally {


			}
		}


		return null;
	}


	

}
