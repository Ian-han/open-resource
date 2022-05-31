package com.online.taxi.order.service.impl;

import com.online.taxi.order.service.GrabService;
import com.online.taxi.order.service.OrderService;
import com.online.taxi.order.service.SeckillGrabService;
import com.online.taxi.order.service.SeckillOrderService;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author 马士兵教育:chaopengfei
 * @date 2021/1/4
 */
@Service("seckillZookeeperService")
public class SeckillZookeeperServiceImpl implements SeckillGrabService {

	@Autowired
	SeckillOrderService seckillOrderService;

	/**
	 * curator中zk客户端对象
	 */
	@Autowired
	private CuratorFramework client;

	@Override
	public String grabOrder(int goodsId, int userId) {
		// 抢锁路径，同一个锁path需一致
		String lockPath = "/order/"+goodsId;

		// 创建分布式锁
		InterProcessMutex lock = new InterProcessMutex(client, lockPath);
		try {
			// 获取锁资源
			if (lock.acquire(10, TimeUnit.HOURS)) {
				System.out.println("用户:"+userId+" 执行秒杀");

				boolean b = seckillOrderService.grab(goodsId, userId);
				if(b) {
					System.out.println("用户:"+userId+" 抢单成功");
				}else {
					System.out.println("用户:"+userId+" 抢单失败");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				lock.release();
				System.out.println("释放资源");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return null;
	}
}
