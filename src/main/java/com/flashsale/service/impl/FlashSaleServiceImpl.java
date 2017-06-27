package com.flashsale.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import com.flashsale.dao.OrderDAO;
import com.flashsale.dao.ProductDAO;
import com.flashsale.dto.Exposer;
import com.flashsale.dto.FlashSaleExecution;
import com.flashsale.entity.Order;
import com.flashsale.entity.Product;
import com.flashsale.enums.FlashSaleEnum;
import com.flashsale.exception.FlashSaleClosed;
import com.flashsale.exception.FlashSaleException;
import com.flashsale.exception.RepeatSaleException;
import com.flashsale.service.FlashSaleService;

@Service
public class FlashSaleServiceImpl implements FlashSaleService {
	static Logger log = Logger.getLogger(FlashSaleServiceImpl.class);
	
	@Resource
	private ProductDAO productDAO;
	@Resource
	private OrderDAO orderDAO;
	private final String chaos = "dwnad2982h88dh8**!*831nfan/1*daw~21DWcWWA";

	public List<Product> getProductsList() {
		return productDAO.queryAllProducts();
	}

	public Product getProductById(long productId) {
		return productDAO.queryById(productId);
	}

	public Exposer exportFlashSaleUrl(long productId) {

		Product product = productDAO.queryById(productId);

		if (product == null) {
			return new Exposer(false, productId);
		}
		Date startTime = product.getStartTime();
		Date endTime = product.getEndTime();
		Date now = new Date();

		if (now.getTime() < startTime.getTime() || now.getTime() > endTime.getTime()) {
			return new Exposer(false, productId, now.getTime(), startTime.getTime(), endTime.getTime());
		}
		// ת���ض��ַ���,����,������
		String md5 = getMD5(productId);
		return new Exposer(true, md5, productId);
	}


	/* 
	 * ���񷽷�
	 */
	@Transactional
	public FlashSaleExecution excuteFlashSale(long productId, long userPhone, String md5)
			throws FlashSaleException, FlashSaleClosed, RepeatSaleException {

		if (md5 == null || !getMD5(productId).equals(md5)) {
			throw new FlashSaleException("��⵽���ݱ��޸�,����ʧ��");
		}
		try {
			// ִ����ɱҵ���߼�
			// �����,�ӹ����¼
			int reduceResult = productDAO.reduceStock(productId, new Date());
			// ���ٿ�����
			if (reduceResult <= 0) {
				throw new FlashSaleClosed("��ɱ��ѽ���!");
			}
			// ��¼Order
			int orderResult = orderDAO.insertOrder(productId, userPhone);
			if (orderResult <= 0) {
				throw new RepeatSaleException("�����ظ��ύ����!");
			} else {
				Order order = orderDAO.queryOrderByIdWithProduct(productId, userPhone);
				return new FlashSaleExecution(productId,FlashSaleEnum.SUCCESS, order);
			}
		} catch (FlashSaleClosed e1) {
			throw e1;
		} catch (RepeatSaleException e2) {
			throw e2;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new FlashSaleException("�ڲ�����: " + e.getMessage());
		}
	}

	private String getMD5(long productId) {
		String base = productId + "/" + chaos;
		return DigestUtils.md5DigestAsHex(base.getBytes());
	}
}
