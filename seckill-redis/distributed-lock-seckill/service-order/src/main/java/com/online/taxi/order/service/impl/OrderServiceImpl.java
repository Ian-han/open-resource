package com.online.taxi.order.service.impl;

import com.online.taxi.order.dao.TblOrderDao;
import com.online.taxi.order.entity.TblOrder;
import com.online.taxi.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Timer;
import java.util.concurrent.TimeUnit;

@Service("orderService")
public class OrderServiceImpl implements OrderService {

	@Autowired
	private TblOrderDao mapper;


	public boolean grab(int orderId, int driverId) {
		TblOrder order = mapper.selectByPrimaryKey(orderId);
		try {
			Thread.sleep(2000);
//			TimeUnit.MINUTES.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// 还有填写其他很多业务信息。包括哪个司机啥的。起点，终点。

		if (order.getOrderStatus().intValue() == 0) {
			order.setOrderStatus(1);
			order.setOrderDescription("司机"+driverId+"  抢单成功");
			order.setDriverId(driverId);
			mapper.updateByPrimaryKeySelective(order);

			return true;
		}
		return false;

	}
}
