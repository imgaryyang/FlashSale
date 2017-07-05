package com.flashsale.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import com.flashsale.dao.OrderDAO;
import com.flashsale.dao.ProductDAO;
import com.flashsale.dao.cache.RedisDAO;
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
	@Resource
	private RedisDAO redisDAO;

	private final String chaos = "dwnad2982h88dh8**!*831nfan/1*daw~21DWcWWA";
	@Override
	public List<Product> getProductsList() {
		return productDAO.queryAllProducts();
	}
	@Override
	public Product getProductById(long productId) {
		return productDAO.queryById(productId);
	}
	@Override
	public Exposer exportFlashSaleUrl(long productId) {
		// �����Ż�������dao�µ�cache����
		// �������
		Product product = redisDAO.getProduct(productId);
		if (product == null) {
			product = productDAO.queryById(productId);
			if (product == null) {
				return new Exposer(false, productId);
			} else {
				redisDAO.putProduct(product);
			}
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
	@Override
	@Transactional
	public FlashSaleExecution excuteFlashSale(long productId, long userPhone, String md5)
			throws FlashSaleException, FlashSaleClosed, RepeatSaleException {

		if (md5 == null || !getMD5(productId).equals(md5)) {
			throw new FlashSaleException("��⵽���ݱ��޸�,����ʧ��");
		}
		try {
			// ִ����ɱҵ���߼�
			// ��¼Order
			int orderResult = orderDAO.insertOrder(productId, userPhone);
			if (orderResult <= 0) {
				// rollback
				throw new RepeatSaleException("�����ظ��ύ����!");
			} else {
				// ���ٿ�����,�м�������
				int reduceResult = productDAO.reduceStock(productId, new Date());
				if (reduceResult <= 0) {
					// rollback
					throw new FlashSaleClosed("��ɱ��ѽ���!");
				} else {
					// ��ɱ�ɹ� commit
					Order order = orderDAO.queryOrderByIdWithProduct(productId, userPhone);
					return new FlashSaleExecution(productId, FlashSaleEnum.SUCCESS, order);
				}

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

	@Override
	public FlashSaleExecution excuteFlashSaleProcedure(long productId, long userPhone, String md5) {
		if (md5 == null || !getMD5(productId).equals(md5)) {
			return new FlashSaleExecution(productId,FlashSaleEnum.DATA_ERROR);
		}
		Date saleTime = new Date();
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("productId", productId);
		map.put("phone", userPhone);
		map.put("saleTime", saleTime);
		map.put("result", null);
		try {
			productDAO.saleByProcedure(map);
			int result = MapUtils.getInteger(map, "result",-2);
			if (result == 1){
				Order order = orderDAO.queryOrderByIdWithProduct(productId, userPhone);
				return new FlashSaleExecution(productId,FlashSaleEnum.SUCCESS,order);
			}else{
				return new FlashSaleExecution(productId,FlashSaleEnum.statusOf(result));
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return new FlashSaleExecution(productId,FlashSaleEnum.INNER_ERROR);
		}
	}
}
