package com.dcsg.ddw.ord;

public class OrderHeaderDDW {
	
	private String orderNum;
	private String orderSubmitDt;
	private String orderWebStore;

	private String orderTotAmt;
	private String orderTotUnits;
	private String purhasePriceAmt;
	private String orderCustomerId;
	private String orderScorecard;
	
	private String orderShipAmt;
	private String loyalityId;


	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getOrderSubmitDt() {
		return orderSubmitDt;
	}

	public void setOrderSubmitDt(String orderSubmitDt) {
		this.orderSubmitDt = orderSubmitDt;
	}

	public String getOrderShipAmt() {
		return orderShipAmt;
	}

	public void setOrderShipAmt(String orderShipAmt) {
		this.orderShipAmt = orderShipAmt;
	}

	public String getOrderTotAmt() {
		return orderTotAmt;
	}

	public void setOrderTotAmt(String orderTotAmt) {
		this.orderTotAmt = orderTotAmt;
	}

	public String getOrderTotUnits() {
		return orderTotUnits;
	}

	public void setOrderTotUnits(String orderTotUnits) {
		this.orderTotUnits = orderTotUnits;
	}

	public String getOrderCustomerId() {
		return orderCustomerId;
	}

	public void setOrderCustomerId(String orderCustomerId) {
		this.orderCustomerId = orderCustomerId;
	}

	public String getOrderScorecard() {
		return orderScorecard;
	}

	public void setOrderScorecard(String orderScorecard) {
		this.orderScorecard = orderScorecard;
	}

	public String getOrderWebStore() {
		return orderWebStore;
	}

	public void setOrderWebStore(String orderWebStore) {
		this.orderWebStore = orderWebStore;
	}

	public String getPurhasePriceAmt() {
		return purhasePriceAmt;
	}

	public void setPurhasePriceAmt(String purhasePriceAmt) {
		this.purhasePriceAmt = purhasePriceAmt;
	}

	public String getLoyalityId() {
		return loyalityId;
	}

	public void setLoyalityId(String loyalityId) {
		this.loyalityId = loyalityId;
	}

}
