package com.dcsg.ddw.transform;

import com.dcsg.ddw.eom.TXML.Message.Order.OrderLines.OrderLine.ShippingInfo;
import com.dcsg.ddw.eom.TXML.Message.Order.OrderLines.OrderLine.ShippingInfo.ShippingAddress;
import com.dcsg.ddw.eom.TXML.Message.Order.PaymentDetails.PaymentDetail.BillToDetail;
import com.dsg.customerorder.avro.LineItem;
import com.dsg.customerorder.avro.Order;
import com.dsg.customerorder.enums.ShippingDetailEnum;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AddressConverter {

  //orig -- eventually drop 	
  protected ShippingInfo convertShippingInfo(LineItem lineItem, String email) {

    ShippingInfo shippingInfo = new ShippingInfo();

    try {
      shippingInfo.setShipVia(ShippingDetailEnum
          .getEomShipVia(lineItem.getShippingDetails().getShipMode()));
      shippingInfo.setDeliveryOption(CustomerOrderConstants.DELIVERY_OPTION);
      shippingInfo.setShippingAddress(convertShippingAddress(lineItem, email));
    } catch (Exception e) {
      log.error("Shipping Address Not Found");
      throw new ShippingAddressNotFoundException(e.getMessage());
    }

    return shippingInfo;
  }

  protected String convertShipCity(LineItem lineItem) {

	    //ShippingInfo shippingInfo = new ShippingInfo();
	    String shipCity = new String();

	    try {
	    	
	    	shipCity = lineItem.getShippingDetails().
	          getShippingRecipient().getAddress().getCity().toString();
	      
	    } catch (Exception e) {
	      log.error("Shipping Address Not Found");
	      throw new ShippingAddressNotFoundException(e.getMessage());
	    }

	    return shipCity;
	  }
  

  protected String convertShipState(LineItem lineItem) {
;
	    String shipState = new String();
	    try {
	    	
	    	shipState = lineItem.getShippingDetails().
	          getShippingRecipient().getAddress().getState().toString();
	      
	    } catch (Exception e) {
	      log.error("Shipping State Not Found");
	      throw new ShippingAddressNotFoundException(e.getMessage());
	    }

	    return shipState;
	  }
  
  protected String convertShipZip(LineItem lineItem) {


	    String shipZip = new String();
	    try {
	    	
	    	shipZip = lineItem.getShippingDetails().
	          getShippingRecipient().getAddress().getPostalCode().toString();	      
	    } catch (Exception e) {
	      log.error("Shipping Zip Cd Not Found");
	      throw new ShippingAddressNotFoundException(e.getMessage());
	    }

	    return shipZip;
	  }

  
  
  protected ShippingAddress convertShippingAddress(LineItem lineItem, String email) {

    ShippingAddress shippingAddress = new ShippingAddress();
    try {
      //shippingAddress.setShipToFirstName(lineItem.getShippingDetails().
      //    getShippingRecipient().getFirstName().toString());
      //shippingAddress.setShipToLastName(lineItem.getShippingDetails().
      //    getShippingRecipient().getLastName().toString());
      shippingAddress.setShipToAddressLine1(lineItem.getShippingDetails().
          getShippingRecipient().getAddress().getAddress1().toString());
      if (lineItem.getShippingDetails().
          getShippingRecipient().getAddress().getAddress2() != null) {
        shippingAddress.setShipToAddressLine2(lineItem.getShippingDetails().
            getShippingRecipient().getAddress().getAddress2().toString());
      }
      shippingAddress.setShipToCity(lineItem.getShippingDetails().
          getShippingRecipient().getAddress().getCity().toString());
      shippingAddress.setShipToState(lineItem.getShippingDetails().
          getShippingRecipient().getAddress().getState().toString());
      shippingAddress.setShipToPostalCode(lineItem.getShippingDetails().
          getShippingRecipient().getAddress().getPostalCode().toString());
      shippingAddress.setShipToCountry(lineItem.getShippingDetails().getShippingRecipient().
          getAddress().getCountry().toString());
      shippingAddress.setShipToEmail(email);
     // shippingAddress.setShipToPhone(lineItem.getShippingDetails().getShippingRecipient().
      //    getPhone().toString());
    } catch (Exception e) {
      log.error("Shipping Address not found");
      throw new ShippingAddressNotFoundException(e.getMessage());
    }

    return shippingAddress;
  }

  protected BillToDetail convertBillingAddress(Order order) {
    BillToDetail billToDetail = new BillToDetail();

    try {
    //  billToDetail.setBillToFirstName(order.getCustomerDetails().getFirstName().toString());
     // billToDetail.setBillToLastName(order.getCustomerDetails().getLastName().toString());
      billToDetail
          .setBillToAddressLine1(order.getCustomerDetails().getAddress().getAddress1().toString());
      if (order.getCustomerDetails().getAddress().getAddress2() != null) {
        billToDetail
            .setBillToAddressLine2(
                order.getCustomerDetails().getAddress().getAddress2().toString());
      }
      billToDetail.setBillToCity(order.getCustomerDetails().getAddress().getCity().toString());
      billToDetail.setBillToState(order.getCustomerDetails().getAddress().getState().toString());
      billToDetail
          .setBillToPostalCode(order.getCustomerDetails().getAddress().getPostalCode().toString());
      billToDetail
          .setBillToCountry(order.getCustomerDetails().getAddress().getCountry().toString());
   //   billToDetail.setBillToPhone(order.getCustomerDetails().getPhone().toString());
   //   billToDetail.setBillToEmail(order.getCustomerDetails().getEmail().toString());
    } catch (Exception e) {
      log.error("Error converting Billing Detail for Order {}", order);
    }

    return billToDetail;
  }

}
