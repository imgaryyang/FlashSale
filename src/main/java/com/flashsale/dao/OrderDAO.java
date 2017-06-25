package com.flashsale.dao;

import com.flashsale.entity.Order;

public interface OrderDAO {
	
	/**
	 * ����һ����¼
	 * @param productId
	 * @param userPhone
	 * @return
	 * ״̬ �ɹ����
	 */
	int insertOrder(long productId, long userPhone);

	/**
	 * ��ѯһ��Order
	 * @param productId
	 * @return
	 */
	Order queryOrderByIdWithProduct(long productId, long userPhone);
}
