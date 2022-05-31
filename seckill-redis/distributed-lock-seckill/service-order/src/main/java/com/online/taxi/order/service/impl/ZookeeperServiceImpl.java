package com.online.taxi.order.service.impl;

import com.online.taxi.order.service.GrabService;
import com.online.taxi.order.service.OrderService;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreMutex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author 马士兵教育:chaopengfei
 * @date 2021/1/4
 */
@Service("zookeeperService")
public class ZookeeperServiceImpl implements GrabService {

	@Autowired
	OrderService orderService;

	/**
	 * curator中zk客户端对象
	 */
	@Autowired
	private CuratorFramework client;

	@Override
	public String grabOrder(int orderId, int driverId) {
		// 抢锁路径，同一个锁path需一致
		String lockPath = "/order/"+orderId;

		// 创建分布式锁
		InterProcessMutex lock = new InterProcessMutex(client, lockPath);
		try {
			// 获取锁资源
			if (lock.acquire(10, TimeUnit.HOURS)) {
				System.out.println("司机:"+driverId+" 执行抢单逻辑");

				boolean b = orderService.grab(orderId, driverId);
				if(b) {
					System.out.println("司机:"+driverId+" 抢单成功");
				}else {
					System.out.println("司机:"+driverId+" 抢单失败");
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
