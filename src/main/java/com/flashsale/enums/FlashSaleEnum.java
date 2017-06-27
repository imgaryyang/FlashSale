package com.flashsale.enums;

/**
 * ʹ��ö�ٱ�ʾ��������
 * @author Zhuo He (Lyn)
 * @Date 2017-06-27
 *
 */
public enum FlashSaleEnum {
	SUCCESS(1, "��ɱ�ɹ�"),
    END(0, "��ɱ����"),
    REPEAT_SALE(-1, "�ظ���ɱ"),
    INNER_ERROR(-2, "ϵͳ�쳣"),
    DATA_ERROR(-3, "���ݴ۸�");
	
	private int status;
	private String statusInfo;
	
	private FlashSaleEnum(int status, String statusInfo) {
		this.status = status;
		this.statusInfo = statusInfo;
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
	
	public static FlashSaleEnum statusOf(int index){
		for (FlashSaleEnum status : values()) {
			if (status.getStatus() == index){
				return status;
			}
		}
		return null;
	}
}
