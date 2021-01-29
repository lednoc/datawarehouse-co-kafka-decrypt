package com.dcsg.ddw.transform;

import com.dcsg.ddw.eom.TXML;
import com.dcsg.ddw.eom.TXML.Message;
import com.dcsg.ddw.eom.TXML.Message.Order.OrderLines.OrderLine;
import com.dsg.customerorder.avro.Order;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//kc
import com.dcsg.ddw.ord.*;

@Component
@Slf4j
public class DDWOrderBuilder {

  private Order orderToProcess;
  private TXML eomOrder;
  
  private OrderDDW ddwOrder;

  private OrderConverter orderConverter;
  private OrderLineConverter orderLineConverter;
  private CustomerOrderUtil customerOrderUtil;
  private PaymentDetailsConverter paymentDetailsConverter;
  private CustomFieldConverter customFieldConverter;
  private ReferenceFieldConverter referenceFieldConverter;
  private CustomerInfoConverter customerInfoConverter;

  @Autowired
  public DDWOrderBuilder(CustomFieldConverter customFieldConverter,
      ReferenceFieldConverter referenceFieldConverter,
      OrderLineConverter orderLineConverter,
      PaymentDetailsConverter paymentDetailsConverter,
      CustomerOrderUtil customerOrderUtil,
      OrderConverter orderConverter,
      CustomerInfoConverter customerInfoConverter) {

    this.customFieldConverter = customFieldConverter;
    this.referenceFieldConverter = referenceFieldConverter;
    this.orderLineConverter = orderLineConverter;
    this.paymentDetailsConverter = paymentDetailsConverter;
    this.customerOrderUtil = customerOrderUtil;
    this.orderConverter = orderConverter;
    this.customerInfoConverter = customerInfoConverter;

  }

  public void setOrder(Order ord) {
    this.orderToProcess = ord;
   
  }

  

  //public TXML generateEomXml(Order avroOrder) throws Exception {
  public OrderDDW generateDdwOrder(Order avroOrder) throws Exception {
	  
	//kc
	ddwOrder = new OrderDDW();
	OrderHeaderDDW ordHdrDDW = new OrderHeaderDDW();
	
	eomOrder = new TXML();
    eomOrder.setHeader(customerOrderUtil.setXmlHeader());
    Message.Order orderMessage = new Message.Order(); //new message to populate from avroOrder
    Message message = new Message();
    message.setOrder(orderMessage);

    orderMessage.setOrderNumber(orderConverter.convertOrderId(avroOrder));
    orderMessage.setOrderCaptureDate(orderConverter.convertOrderCaptureDate(avroOrder));
    //orderMessage.setLastUpdatedDTTM(orderConverter.convertOrderLastUpdatedDate(avroOrder));
    orderMessage.setLastUpdatedDTTM(orderConverter.convertOrderType(avroOrder)); //source
    
    orderMessage.setOrderType(orderConverter.convertOrderType(avroOrder));
    orderMessage.setOrderSubtotal(orderConverter.convertOrderTotal(avroOrder));
    orderMessage.setOrderTotal(orderConverter.convertOrderTotal(avroOrder));
    orderMessage.setOrderCurrency(orderConverter.convertOrderCurrency());
    orderMessage.setConfirmed(orderConverter.convertOrderConfirmed());
    orderMessage.setCanceled(orderConverter.convertOrderCanceled(avroOrder));
    orderMessage.setCustomerInfo(customerInfoConverter.convertCustomerInfo(avroOrder));
    orderMessage.setPaymentDetails(paymentDetailsConverter.convertPaymentDetails(avroOrder));
    orderMessage.setReferenceFields(referenceFieldConverter.convertReferenceFields(avroOrder));
    orderMessage.setCustomFieldList(customFieldConverter.convertCustomFields(avroOrder));    
    //orderMessage.setOrderLines(orderLineConverter.convertOrderLines(avroOrder));
    
    
    //build DDW order from avro message
    //get order header/totals
    ordHdrDDW.setOrderNum(orderConverter.convertOrderId(avroOrder));
    ordHdrDDW.setOrderSubmitDt(orderConverter.convertOrderCaptureDate(avroOrder));
    ordHdrDDW.setOrderWebStore(orderConverter.convertOrderType(avroOrder));
    ordHdrDDW.setOrderTotAmt(orderConverter.convertOrderTotal(avroOrder));
    ordHdrDDW.setPurhasePriceAmt(orderConverter.convertPurchasePrice(avroOrder));
    if(avroOrder.getCustomerDetails().getLoyaltyAccount() != null)
      ordHdrDDW.setLoyalityId(avroOrder.getCustomerDetails().getLoyaltyAccount().toString());
    
    ordHdrDDW.setOrderTotUnits(String.valueOf(avroOrder.getLineItems().size()));    
    ordHdrDDW.setOrderShipAmt(orderConverter.convertShipTotal(avroOrder));
    
    ddwOrder.setOrderHeader(ordHdrDDW);
    
    //get order lines/detail
    ddwOrder.setOrderLines( orderLineConverter.convertOrderLines(avroOrder) );
    //get payment info
    ddwOrder.setOrderPayment(paymentDetailsConverter.convertPaymentInfo(avroOrder));
    

    if (orderMessage.getCanceled() != null && Boolean.valueOf(orderMessage.getCanceled())) {
      //Hardcoding the cancelled reason code.
      orderMessage.setReasonCode(CustomerOrderConstants.CANCELLED_REASON_CODE);

    }

    eomOrder.setMessage(message);
       
    //return eomOrder;
    return ddwOrder;
  }

  public String marshallXml(Object eomOrder) {
    OutputStream outStream = new ByteArrayOutputStream();

    try {
      JAXBContext jaxbContext = JAXBContext
          .newInstance(
              TXML.class.getPackage().getName());
      Marshaller marshaller = jaxbContext.createMarshaller();
      marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
      marshaller.setProperty("com.sun.xml.internal.bind.xmlHeaders",
          "<?xml version=\"1.0\"?>");
      marshaller.marshal(eomOrder, System.out);
      marshaller.marshal(eomOrder, outStream);
    } catch (Exception e) {
      log.error("Exception in creating Marshalling XML for {}", orderToProcess.getOrderNumber());
    }
    return new String(((ByteArrayOutputStream) outStream).toByteArray());
  }

}
