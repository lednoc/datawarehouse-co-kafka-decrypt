package com.dcsg.ddw.ord;

public class OrderLineDDW {
	

	private String orderNum;
    private String sku; 
	private String qty;
	private String lineNum;
	private String origPrice;
	private String purchasePrice;
	private String discountAmt;
	
	
	private String shipMethod;
	private String estimateDeliverDt;
	private String shipCarrier;
	
	private String shipCity;
	private String shipState;
	private String shipZip;
	
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getQty() {
		return qty;
	}
	public void setQty(String qty) {
		this.qty = qty;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public String getShipCity() {
		return shipCity;
	}
	public void setShipCity(String shipCity) {
		this.shipCity = shipCity;
	}
	public String getShipState() {
		return shipState;
	}
	public void setShipState(String shipState) {
		this.shipState = shipState;
	}
	public String getShipZip() {
		return shipZip;
	}
	public void setShipZip(String shipZip) {
		this.shipZip = shipZip;
	}
	public String getshipMethod() {
		return shipMethod;
	}
	public void setshipMethod(String shipMethod) {
		this.shipMethod = shipMethod;
	}
	public String getEstimateDeliverDt() {
		return this.estimateDeliverDt;
	}
	public void setEstimateDeliverDt(String estimateDeliverDt) {
		this.estimateDeliverDt = estimateDeliverDt;
	}
	public String getLineNum() {
		return lineNum;
	}
	public void setLineNum(String lineNum) {
		this.lineNum = lineNum;
	}
	public String getOrigPrice() {
		return origPrice;
	}
	public void setOrigPrice(String origPrice) {
		this.origPrice = origPrice;
	}
	public String getPurchasePrice() {
		return purchasePrice;
	}
	public void setPurchasePrice(String purchasePrice) {
		this.purchasePrice = purchasePrice;
	}
	public String getDiscountAmt() {
		return discountAmt;
	}
	public void setDiscountAmt(String discountAmt) {
		this.discountAmt = discountAmt;
	}
	public String getShipCarrier() {
		return shipCarrier;
	}
	public void setShipCarrier(String shipCarrier) {
		this.shipCarrier = shipCarrier;
	}


}
