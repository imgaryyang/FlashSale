package com.flashsale.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.flashsale.entity.Product;



public interface ProductDAO {



	/**
	 * ͨ��ID���ٲ�Ʒ���
	 * @param productId
	 * @param saleTime
	 * @return
	 * �Ƿ�ɹ�
	 */
	int reduceStock(@Param("productId") long productId, @Param("saleTime") Date saleTime);
	
	/**
	 * ͨ��ID��ȡ1����Ʒ����
	 * @param productId
	 * @return
	 */
	Product queryById(long productId);
	
	/**
	 * ��ѯ���в�Ʒ
	 * @return
	 */
	List<Product> queryAllProducts();
	
	/**
	 * ��ѯ��Χ�ڲ�Ʒ
	 * @param offset
	 * @param limit
	 * @return
	 */
	List<Product> queryAllProducts(@Param("offset") int offset, @Param("limit") int limit);
}
