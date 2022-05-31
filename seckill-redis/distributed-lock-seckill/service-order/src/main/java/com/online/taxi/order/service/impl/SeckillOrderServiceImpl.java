package com.online.taxi.order.service.impl;

import com.online.taxi.order.dao.TblInventoryDao;
import com.online.taxi.order.dao.TblOrderDao;
import com.online.taxi.order.dao.TblSeckillOrderDao;
import com.online.taxi.order.entity.TblInventory;
import com.online.taxi.order.entity.TblOrder;
import com.online.taxi.order.entity.TblSeckillOrder;
import com.online.taxi.order.service.SeckillOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("seckillOrderService")
public class SeckillOrderServiceImpl implements SeckillOrderService {

	@Autowired
	private TblInventoryDao mapper;

	@Autowired
	private TblSeckillOrderDao seckillOrderDao;

	@Override
	public boolean grab(int goodId, int userId) {
		TblInventory tblInventory = mapper.selectByPrimaryKey(goodId);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// 还有填写其他很多业务信息。包括哪个司机啥的。起点，终点。
		int num = tblInventory.getNum().intValue();
		System.out.println("num:"+num);
		if (num > 0) {
			// 扣减库存
			tblInventory.setNum(num-1);
			mapper.updateByPrimaryKeySelective(tblInventory);

			// 新增订单
			TblSeckillOrder order = new TblSeckillOrder();
			order.setOrderDescription("用户"+userId+"抢到了茅台");
			order.setOrderStatus(1);
			order.setUserId(userId);
			seckillOrderDao.insert(order);

			return true;
		}
		return false;

	}
}
