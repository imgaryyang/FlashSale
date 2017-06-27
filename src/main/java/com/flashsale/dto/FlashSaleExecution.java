package com.flashsale.dto;

import com.flashsale.entity.Order;
import com.flashsale.enums.FlashSaleEnum;

/**
 * ��װ��ɱִ�к󷵻ص�����
 * 
 * @author Zhuo He (Lyn)
 * @Date 2017-06-26
 *
 */
public class FlashSaleExecution {
	private long productId;
	// ״̬��
	private int status;
	// ״̬ϸ��
	private String statusInfo;
	// ��Ӧ����
	private Order order;

	public FlashSaleExecution(long productId, FlashSaleEnum flashSaleEnum, Order order) {
		super();
		this.productId = productId;
		this.status = flashSaleEnum.getStatus();
		this.statusInfo = flashSaleEnum.getStatusInfo();
		this.order = order;
	}

	public FlashSaleExecution(long productId, FlashSaleEnum flashSaleEnum) {
		super();
		this.productId = productId;
		this.status = flashSaleEnum.getStatus();
		this.statusInfo = flashSaleEnum.getStatusInfo();
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getStatusInfo() {
		return statusInfo;
	}

	public void setStatusInfo(String statusInfo) {
		this.statusInfo = statusInfo;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	@Override
	public String toString() {
		return "FlashSaleExecution [productId=" + productId + ", status=" + status + ", statusInfo=" + statusInfo
				+ ", order=" + order + "]";
	}

}
