package com.dcsg.ddw.ord;

import java.util.ArrayList;
import java.util.List;

public class OrdersDDW {
	
	  private List<OrderHeaderDDW> orderHeaders = new ArrayList<>();
	  private List<OrderLineDDW> orderLines = new ArrayList<>();
	  private List<OrderPaymentDDW> orderPayment = new ArrayList<>();
	  
	 // private HashMap<Integer, OrderLn> orderMap;

	public List<OrderLineDDW> getOrderLines() {
		return orderLines;
	}

	public void setOrderLines(List<OrderLineDDW> orderLines) {
		this.orderLines = orderLines;
	}


	public List<OrderPaymentDDW> getOrderPayment() {
		return orderPayment;
	}

	public void setOrderPayment(List<OrderPaymentDDW> orderPayment) {
		this.orderPayment = orderPayment;
	}

	public List<OrderHeaderDDW> getOrderHeaders() {
		return orderHeaders;
	}

	public void setOrderHeaders(List<OrderHeaderDDW> orderHeaders) {
		this.orderHeaders = orderHeaders;
	}



}
