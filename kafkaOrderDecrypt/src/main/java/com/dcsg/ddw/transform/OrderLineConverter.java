package com.dcsg.ddw.transform;

import static java.util.stream.Collectors.groupingBy;

import com.dsg.customerorder.avro.LineItem;
import com.dsg.customerorder.avro.Order;
import com.dsg.customerorder.avro.State;
import com.dcsg.ddw.eom.TXML.Message.Order.OrderLines;
import com.dcsg.ddw.eom.TXML.Message.Order.OrderLines.OrderLine;
import com.dcsg.ddw.eom.TXML.Message.Order.OrderLines.OrderLine.ComponentDataList;
import com.dcsg.ddw.eom.TXML.Message.Order.OrderLines.OrderLine.LineReferenceFields;
import com.dcsg.ddw.eom.TXML.Message.Order.OrderLines.OrderLine.PriceInfo;
import com.dcsg.ddw.eom.TXML.Message.Order.OrderLines.OrderLine.Quantity;
import com.dcsg.ddw.eom.TXML.Message.Order.OrderLines.OrderLine.ComponentDataList.ComponentData;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//kc
import com.dcsg.ddw.ord.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Component
@Slf4j
public class OrderLineConverter {

  private ComponentDataList componentDataList;

  private CustomerOrderUtil customerOrderUtil;

  private AddressConverter addressConverter;

  @Autowired
  public OrderLineConverter(AddressConverter addressConverter) {

    this.addressConverter = addressConverter;

    customerOrderUtil = new CustomerOrderUtil();
  }

  /* orig
  protected OrderLines convertOrderLines(Order avroOrder) {
    OrderLines orderLines = new OrderLines();

    List<OrderLine> linesInOrder = new ArrayList<>();

    try {
      // Get all the units for the external identifier - OrderItemId and populate a map
      Map<CharSequence, List<LineItem>> lineMap = avroOrder.getLineItems().stream()
          .collect(groupingBy(lineItem -> lineItem.getExternalItemIdentifier()));

      for (CharSequence c : lineMap.keySet()) {
        LineItem lineItem = lineMap.get(c).get(0);
        List<LineItem> lineItems = lineMap.get(c);
        
        OrderLine orderLine = new OrderLine();
        orderLine.setLineNumber(convertOrderLineId(lineItem));
        orderLine.setItemID(convertItemId(lineItem));
        orderLine.setComponentDataList(setComponentData());
        orderLine.setLineReferenceFields(
            convertLineReferenceFields(lineItem, avroOrder.getOrderNumber().toString()));

        orderLine.setQuantity(convertQuantity(lineItems));
        orderLine.setPriceInfo(convertPriceInfo(lineItem));
        orderLine.setLineTotal(aggregateLineTotal(lineItems).toString());
        orderLine.setShippingInfo(addressConverter
            .convertShippingInfo(lineItem, avroOrder.getCustomerDetails().getEmail().toString()));
        orderLine.setCanceled(isOrderLineCanceled(avroOrder));
        linesInOrder.add(orderLine);

      }
      orderLines.getOrderLine().addAll(linesInOrder);
    } catch (ShippingAddressNotFoundException e) {
      log.error("Error in Converting Shipping Address for Order {}",
          avroOrder.getOrderNumber().toString());
      throw new ShippingAddressNotFoundException(e.getMessage());
    } catch (Exception e) {
      log.error("Error converting Order Lines for Order {}", avroOrder.getOrderNumber().toString());
      throw new OrderLineNotFoundException(e.getMessage());
    }

    return orderLines;
  }
*/

  //kc new
  protected List<OrderLineDDW> convertOrderLines(Order avroOrder) {
	     //orig
	    //OrderLines orderLines = new OrderLines();
	    //List<OrderLine> linesInOrder = new ArrayList<>();
	  //kc

	  List<OrderLineDDW> linesInOrder = new ArrayList<>();

	    try {
	      // Get all the units for the external identifier - OrderItemId and populate a map
	      Map<CharSequence, List<LineItem>> lineMap = avroOrder.getLineItems().stream()
	          .collect(groupingBy(lineItem -> lineItem.getExternalItemIdentifier()));

	      for (CharSequence c : lineMap.keySet()) {
	        LineItem lineItem = lineMap.get(c).get(0);
	        List<LineItem> lineItems = lineMap.get(c); //lineItems is array; each element is a sku
	        
	        //OrderLine orderLine = new OrderLine();
	        OrderLineDDW orderLine = new OrderLineDDW();
	       
	        /*
	        orderLine.setLineNumber(convertOrderLineId(lineItem));
	        orderLine.setItemID(convertItemId(lineItem));
	        orderLine.setComponentDataList(setComponentData());
	        orderLine.setLineReferenceFields(
	            convertLineReferenceFields(lineItem, avroOrder.getOrderNumber().toString()));

	        orderLine.setQuantity(convertQuantity(lineItems));
	        orderLine.setPriceInfo(convertPriceInfo(lineItem));
	        orderLine.setLineTotal(aggregateLineTotal(lineItems).toString());
	        orderLine.setShippingInfo(addressConverter
	            .convertShippingInfo(lineItem, avroOrder.getCustomerDetails().getEmail().toString()));
	        orderLine.setCanceled(isOrderLineCanceled(avroOrder));
	        */


		        orderLine.setOrderNum(avroOrder.getOrderNumber().toString());
		        orderLine.setSku(lineItem.getSku().toString());
		        orderLine.setQty("1"); // ??
		        orderLine.setLineNum(lineItem.getLineNumber().toString());
                orderLine.setOrigPrice(lineItem.getOriginalPrice().toString());
                orderLine.setPurchasePrice(lineItem.getPurchasePrice().toString());
                orderLine.setDiscountAmt(lineItem.getDiscount().toString());
		        
		        orderLine.setShipCity(addressConverter
			            .convertShipCity(lineItem));
		        
		        orderLine.setShipState(addressConverter
			            .convertShipState(lineItem));
		        orderLine.setShipZip(addressConverter
			            .convertShipZip(lineItem));
		        
			    //todo: add to ora table  
		        orderLine.setEstimateDeliverDt(lineItem.getEstimatedDeliveryDate().toString() );

		        //comment out to test custom orer.avsc
		        //orderLine.setShipCarrier(lineItem.getShippingDetails().getCarrier().toString());
		        //orderLine.setshipMethod(lineItem.getShippingDetails().getShipMode().toString());
		        
		        linesInOrder.add(orderLine);
	        

	      }
	      //orderLines.getOrderLine().addAll(linesInOrder);
	      

	    } catch (ShippingAddressNotFoundException e) {
	      log.error("Error in Converting Shipping Address for Order {}",
	          avroOrder.getOrderNumber().toString());
	      throw new ShippingAddressNotFoundException(e.getMessage());
	    } catch (Exception e) {
	      log.error("Error converting Order Lines for Order {}", avroOrder.getOrderNumber().toString());
	      throw new OrderLineNotFoundException(e.getMessage());
	    }

	    //return orderLines;
	    //System.out.println("linesInOrder: " + linesInOrder.size() );
	    return linesInOrder;
	  }
  
  
  protected BigDecimal aggregateLineTotal(List<LineItem> lineItems) {

    BigDecimal lineTotal = BigDecimal.ZERO;

    for (LineItem lineItem : lineItems) {

      BigDecimal purchasePrice = new BigDecimal(lineItem.getPurchasePrice().toString());
      BigDecimal estimatedUnitTax = new BigDecimal(lineItem.getEstimatedUnitTax().toString());
      BigDecimal purchaseShipCost = new BigDecimal(
          lineItem.getShippingDetails().getPurchaseShipCost().toString());
     // BigDecimal estimatedShipTax = new BigDecimal(
     //     lineItem.getShippingDetails().getEstimatedShipTax().toString());
      
      BigDecimal estimatedShipTax = new BigDecimal(
    		    lineItem.getShippingDetails().getShippingTaxDetails().getEstimatedShipTax().toString());

      lineTotal = lineTotal.add(purchasePrice).add(estimatedUnitTax).add(purchaseShipCost)
          .add(estimatedShipTax);
    }

    return lineTotal;
  }
  //new
  protected BigDecimal aggregateShipTotal(List<LineItem> lineItems) {

	    BigDecimal lineTotal = BigDecimal.ZERO;

	    for (LineItem lineItem : lineItems) {

	       BigDecimal purchaseShipCost = new BigDecimal(
	          lineItem.getShippingDetails().getPurchaseShipCost().toString());
	    
	      lineTotal = lineTotal.add(purchaseShipCost);
	    }

	    return lineTotal;
	  }

  
  protected BigDecimal aggregatePurchasePrice(List<LineItem> lineItems) {

	    BigDecimal lineTotal = BigDecimal.ZERO;

	    for (LineItem lineItem : lineItems) {

	      BigDecimal purchasePrice = new BigDecimal(lineItem.getPurchasePrice().toString());
	   

	      lineTotal = lineTotal.add(purchasePrice);
	    }

	    return lineTotal;
	  }
  //new end
  
  protected PriceInfo convertPriceInfo(LineItem lineItem) {

    PriceInfo priceInfo = new PriceInfo();
    priceInfo.setPrice(lineItem.getOriginalPrice().toString());

    return priceInfo;
  }

  protected String isOrderLineCanceled(Order avroOrder) {

    if (avroOrder.getState().equals(State.canceled)) {
      return "true";
    }
    return "false";
  }

  protected LineReferenceFields convertLineReferenceFields(LineItem lineItem, String orderNumber)
      throws Exception {

    LineReferenceFields lineReferenceFields = new LineReferenceFields();
    if (lineItem.getEstimatedDeliveryDate() != null && !lineItem.getEstimatedDeliveryDate()
        .toString().isEmpty()) {
      lineReferenceFields.setReferenceField6(customerOrderUtil
          .convertDateFormat(lineItem.getEstimatedDeliveryDate().toString(), orderNumber));
    }
    return lineReferenceFields;
  }


  protected Quantity convertQuantity(List<LineItem> lineItems) {

    Quantity quantity = new Quantity();
    quantity.setOrderedQty(String.valueOf(lineItems.size()));
    quantity.setOrderedQtyUOM(CustomerOrderConstants.ORDERED_QTY_UOM);

    return quantity;
  }

  private ComponentDataList setComponentData() {

    componentDataList = new ComponentDataList();
    List<ComponentData> componentList = componentDataList.getComponentData();
    ComponentData componentData = new ComponentData();
    componentData.setComponentGroup(CustomerOrderConstants.COMPONENT_GROUP);
    componentList.add(componentData);

    return componentDataList;
  }

  protected String convertOrderLineId(LineItem lineItem) {

    return lineItem.getExternalItemIdentifier().toString();
  }

  protected String convertItemId(LineItem lineItem) {
    StringBuilder sb = new StringBuilder();
    sb.append(CustomerOrderConstants.EOM_PREPEND).append(lineItem.getSku().toString());

    return sb.toString();
  }

}