package com.dcsg.ddw.transform;


import com.dsg.customerorder.avro.Order;
import com.dsg.customerorder.avro.State;
import com.dcsg.ddw.eom.TXML.Message.Order.ReferenceFields;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ReferenceFieldConverter {


  protected String convertReferenceField4(Order order) {
    if (order.getState().equals(State.in_review)) {
      return "Yes";
    }
    return "No";
  }

  protected String convertReferenceField5(Order order) {
    String referenceField5 = order.getCustomerDetails().getLoyaltyAccount().toString();
    return referenceField5;
  }

  protected String isLoyaltyNumberPresent(Order order) {
    if (order.getCustomerDetails().getLoyaltyAccount() == null || StringUtils
        .isEmpty(order.getCustomerDetails().getLoyaltyAccount().toString())) {
      return "0";
    } else {
      return "1";
    }
  }

  protected ReferenceFields convertReferenceFields(Order order) {
    ReferenceFields referenceFields = new ReferenceFields();

    try {
      referenceFields.setReferenceField4(convertReferenceField4(order));
      referenceFields.setReferenceNumber1(isLoyaltyNumberPresent(order));

      if (isLoyaltyNumberPresent(order).equals("1")) {
        referenceFields.setReferenceField5(convertReferenceField5(order));
      }

    } catch (Exception e) {
      log.error("Error in converting Reference Fields for Order {}",
          order.getOrderNumber().toString());
    }
    return referenceFields;
  }

}
