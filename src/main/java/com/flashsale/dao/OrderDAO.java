package com.flashsale.dao;

import org.apache.ibatis.annotations.Param;

import com.flashsale.entity.Order;

public interface OrderDAO {
	
	/**
	 * ����һ����¼
	 * @param productId
	 * @param userPhone
	 * @return
	 * ״̬ �ɹ����
	 */
	int insertOrder(@Param("productId")long productId, @Param("userPhone")long userPhone);

	/**
	 * ��ѯһ��Order
	 * @param productId
	 * @return
	 */
	Order queryOrderByIdWithProduct(@Param("productId")long productId, @Param("userPhone")long userPhone);
}
