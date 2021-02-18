package com.dcsg.ddw.transform;

import com.dsg.customerorder.avro.Order;
import com.dsg.customerorder.avro.Person;
import com.dcsg.ddw.eom.TXML.Message.Order.CustomerInfo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomerInfoConverter {

  CustomerInfo customerInfo = new CustomerInfo();

  public CustomerInfo convertCustomerInfo(Order order) {

    try {
      Person customer = order.getCustomerDetails();
      customerInfo.setCustomerId(CustomerOrderConstants.CUSTOMER_ID);
      //customerInfo.setCustomerFirstName(customer.getFirstName().toString());
      //customerInfo.setCustomerLastName(customer.getLastName().toString());
      //customerInfo.setCustomerEmail(customer.getEmail().toString());
      //customerInfo.setCustomerPhone(customer.getPhone().toString());
    } catch (Exception e) {
      log.error("Error converting Customer Info for Order Number: {}",
          order.getOrderNumber().toString());
      throw e;
    }
    return customerInfo;
  }

}
