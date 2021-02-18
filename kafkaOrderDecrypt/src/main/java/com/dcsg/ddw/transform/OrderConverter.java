package com.dcsg.ddw.transform;

import static java.util.stream.Collectors.groupingBy;

import com.dsg.customerorder.avro.LineItem;
import com.dsg.customerorder.avro.Order;
import com.dsg.customerorder.avro.State;
import com.dcsg.ddw.eom.TXML.Message.Order.OrderLines;
import com.dsg.customerorder.enums.OrderSourceEnum;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderConverter {

  private OrderLineConverter orderLineConverter;
  private CustomerOrderUtil customerOrderUtil;

  @Autowired
  public OrderConverter(OrderLineConverter orderLineConverter,
      CustomerOrderUtil customerOrderUtil) {
    this.orderLineConverter = orderLineConverter;
    this.customerOrderUtil = customerOrderUtil;
  }

/*kc
  public OrderLines getOrderLines(Order avroOrder) {

    OrderLines orderLines = null;
    try {
      orderLines = orderLineConverter.convertOrderLines(avroOrder);
    } catch (OrderLineNotFoundException e) {
      log.error("OrderLineNotFoundException Occurred: ",
          e.getMessage() + convertOrderId(avroOrder) + " and exception message is: " + e
              .getErrorMessage());
    } catch (ShippingAddressNotFoundException e) {
      log.error("ShippingAddressNotFoundException Occurred: ",
          e.getMessage() + convertOrderId(avroOrder) + " and exception message is: " + e
              .getErrorMessage());
    }
    return orderLines;
  }
*/

  public String convertOrderId(Order avroOrder) {

    return avroOrder.getOrderNumber().toString();
  }


  public String convertOrderCaptureDate(Order avroOrder) throws Exception {

    return customerOrderUtil.convertDateFormat(avroOrder.getTimePlaced().toString(),
        avroOrder.getOrderNumber().toString());
  }

  public String convertOrderLastUpdatedDate(Order avroOrder) throws Exception {

    return customerOrderUtil.convertDateFormat(avroOrder.getLastUpdated().toString(),
        avroOrder.getOrderNumber().toString());
  }

  public String convertOrderConfirmed() {

    return CustomerOrderConstants.CONFIRMED;
  }

  public String convertOrderCanceled(Order avroOrder) {
    if (avroOrder.getState().equals(State.canceled)) {
      return "true";
    }
    return "false";
  }

  public String convertOrderCurrency() {
    return CustomerOrderConstants.ORDER_CURRENCY;
  }

  public String convertOrderType(Order avroOrder) {
    return OrderSourceEnum.findEomSourceIdBySource(avroOrder.getSource().toString());
  }

  protected String convertOrderTotal(Order avroOrder) {
    BigDecimal orderTotal = BigDecimal.ZERO;

    try {
      Map<CharSequence, List<LineItem>> lineMap = avroOrder.getLineItems().stream()
          .collect(groupingBy(lineItem -> lineItem.getExternalItemIdentifier()));

      for (CharSequence c : lineMap.keySet()) {
        List<LineItem> lineItems = lineMap.get(c);
        BigDecimal lineTotal = orderLineConverter.aggregateLineTotal((lineItems));
        orderTotal = orderTotal.add(lineTotal);
      }
    } catch (Exception e) {
      throw new OrderLineNotFoundException(e.getMessage());
    }

    return orderTotal.toString();
  }
  
  
  //new start
  protected String convertShipTotal(Order avroOrder) {
	    BigDecimal orderShipTotal = BigDecimal.ZERO;

	    try {
	      Map<CharSequence, List<LineItem>> lineMap = avroOrder.getLineItems().stream()
	          .collect(groupingBy(lineItem -> lineItem.getExternalItemIdentifier()));

	      for (CharSequence c : lineMap.keySet()) {
	        List<LineItem> lineItems = lineMap.get(c);
	        BigDecimal lineTotal = orderLineConverter.aggregateShipTotal((lineItems));
	        orderShipTotal = orderShipTotal.add(lineTotal);
	      }
	    } catch (Exception e) {
	      throw new OrderLineNotFoundException(e.getMessage());
	    }

	    return orderShipTotal.toString();
	  }  protected String convertPurchasePrice(Order avroOrder) {
		    BigDecimal orderTotal = BigDecimal.ZERO;

		    try {
		      Map<CharSequence, List<LineItem>> lineMap = avroOrder.getLineItems().stream()
		          .collect(groupingBy(lineItem -> lineItem.getExternalItemIdentifier()));

		      for (CharSequence c : lineMap.keySet()) {
		        List<LineItem> lineItems = lineMap.get(c);
		        BigDecimal lineTotal = orderLineConverter.aggregatePurchasePrice((lineItems));
		        orderTotal = orderTotal.add(lineTotal);
		      }
		    } catch (Exception e) {
		      throw new OrderLineNotFoundException(e.getMessage());
		    }

		    return orderTotal.toString();
		  }
  
  //new eind


}
