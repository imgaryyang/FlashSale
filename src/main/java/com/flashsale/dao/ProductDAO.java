package com.flashsale.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.flashsale.entity.Product;



/**
 *
 * @author Zhuo He (Lyn)
 * @Date 2017-07-04
 *
 */
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
	
	
	/**
	 * ʹ�����ݿ�洢���̽�����ɱ
	 * 
	 * @param paramMap
	 */
	void saleByProcedure(Map<String,Object> paramMap);
}
