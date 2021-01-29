package com.dcsg.ddw.ord;


import java.util.List;

public class OrderDDW {

  private OrderHeaderDDW orderHeader;
  private List<OrderLineDDW> orderLines;
  private List<OrderPaymentDDW> orderPayment;
  
 // private HashMap<Integer, OrderLn> orderMap;

public List<OrderLineDDW> getOrderLines() {
	return orderLines;
}

public void setOrderLines(List<OrderLineDDW> orderLines) {
	this.orderLines = orderLines;
}

public OrderHeaderDDW getOrderHeader() {
	return orderHeader;
}

public void setOrderHeader(OrderHeaderDDW orderHeader) {
	this.orderHeader = orderHeader;
}

public List<OrderPaymentDDW> getOrderPayment() {
	return orderPayment;
}

public void setOrderPayment(List<OrderPaymentDDW> orderPayment) {
	this.orderPayment = orderPayment;
}


}
