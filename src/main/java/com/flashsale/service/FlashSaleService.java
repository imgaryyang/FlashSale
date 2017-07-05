package com.flashsale.service;

import java.util.List;

import com.flashsale.dto.Exposer;
import com.flashsale.dto.FlashSaleExecution;
import com.flashsale.entity.Product;
import com.flashsale.exception.FlashSaleClosed;
import com.flashsale.exception.FlashSaleException;
import com.flashsale.exception.RepeatSaleException;

/**
 *
 * @author Zhuo He (Lyn)
 * @Date 2017-06-26
 *
 */
public interface FlashSaleService {
	
	List<Product> getProductsList();
	
	Product getProductById(long productId);
	

	/**
	 * �����ɱ�ӿڵ�ַ,�������ϵͳʱ��
	 * @param productId
	 * @return
	 */
	Exposer exportFlashSaleUrl(long productId);
	
	/**
	 * ʹ�����ݿ�洢����ִ����ɱ
	 * @return
	 */
	FlashSaleExecution excuteFlashSaleProcedure(long productId,long userPhone, String md5);
	/**
	 * ִ����ɱ����
	 * @param priductId
	 * @param userPhone
	 * @param md5
	 * @return
	 * @throws FlashSaleException
	 * @throws FlashSaleClosed
	 * @throws RepeatSaleException
	 * ���������װ FlashSaleExecution
	 */
	FlashSaleExecution excuteFlashSale(long productId,long userPhone, String md5) 
			throws FlashSaleException,FlashSaleClosed,RepeatSaleException;
}
