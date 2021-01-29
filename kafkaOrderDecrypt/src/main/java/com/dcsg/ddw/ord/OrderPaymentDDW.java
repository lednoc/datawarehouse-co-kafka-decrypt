package com.dcsg.ddw.ord;

public class OrderPaymentDDW {
	
	private String orderNum;
	private String orderPaymentId;
	private String orderPaymentType;
	private String orderPaymentAmt;
	private String orderBillToCity;
	private String orderBillToState;
	private String orderBillToZip;
	public String getOrderPaymentId() {
		return orderPaymentId;
	}
	public void setOrderPaymentId(String orderPaymentId) {
		this.orderPaymentId = orderPaymentId;
	}
	public String getOrderPaymentType() {
		return orderPaymentType;
	}
	public void setOrderPaymentType(String orderPaymentType) {
		this.orderPaymentType = orderPaymentType;
	}
	public String getOrderPaymentAmt() {
		return orderPaymentAmt;
	}
	public void setOrderPaymentAmt(String orderPaymentAmt) {
		this.orderPaymentAmt = orderPaymentAmt;
	}
	public String getOrderBillToCity() {
		return orderBillToCity;
	}
	public void setOrderBillToCity(String orderBillToCity) {
		this.orderBillToCity = orderBillToCity;
	}
	public String getOrderBillToState() {
		return orderBillToState;
	}
	public void setOrderBillToState(String orderBillToState) {
		this.orderBillToState = orderBillToState;
	}
	public String getOrderBillToZip() {
		return orderBillToZip;
	}
	public void setOrderBillToZip(String orderBillToZip) {
		this.orderBillToZip = orderBillToZip;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	
	
	

}
