package com.dcsg.ddw.transform;

import com.dsg.customerorder.avro.Order;
import com.dcsg.ddw.eom.TXML.Message.Order.CustomFieldList;
import com.dcsg.ddw.eom.TXML.Message.Order.CustomFieldList.CustomField;
import com.dsg.customerorder.enums.OrderChannelEnum;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CustomFieldConverter {

  protected CustomField setCfBillToFirstName(Order order) {
    CustomField field = new CustomField();
    field.setName(CustomerOrderConstants.CF_BILL_TO_CUSTOMER_FIRST_NAME);
   // field.setValue(order.getCustomerDetails().getFirstName().toString());
    return field;
  }

  protected CustomField setCfBillToLastName(Order order) {
    CustomField field = new CustomField();
    field.setName(CustomerOrderConstants.CF_BILL_TO_CUSTOMER_LAST_NAME);
   // field.setValue(order.getCustomerDetails().getLastName().toString());
    return field;
  }

  protected CustomField setCfOrderEntryMethod(Order order) {
    CustomField field = new CustomField();
    field.setName(CustomerOrderConstants.CF_ORDER_ENTRY_METHOD);
    field.setValue(OrderChannelEnum.getChannelIdByName(order.getChannel()));

    return field;
  }

  protected CustomField setCfLocationId() {
    CustomField field = new CustomField();
    field.setName(CustomerOrderConstants.CF_LOCATION_ID);
    return field;
  }

  protected CustomField setCfShipToFirstName(Order order) {
    CustomField field = new CustomField();
    field.setName(CustomerOrderConstants.CF_SHIP_TO_CUSTOMER_FIRST_NAME);
   // field.setValue(order.getCustomerDetails().getFirstName().toString());
    return field;
  }

  protected CustomField setCfShipToLastName(Order order) {
    CustomField field = new CustomField();
    field.setName(CustomerOrderConstants.CF_SHIP_TO_CUSTOMER_LAST_NAME);
   // field.setValue(order.getCustomerDetails().getLastName().toString());
    return field;
  }

  protected CustomFieldList convertCustomFields(Order avroOrder) {
    CustomFieldList customFieldList = new CustomFieldList();
    List<CustomField> customFields = new ArrayList<>();

    try {
      customFields.add(setCfBillToFirstName(avroOrder));
      customFields.add(setCfBillToLastName(avroOrder));
      customFields.add(setCfOrderEntryMethod(avroOrder));
      customFields.add(setCfLocationId());
      customFields.add(setCfShipToFirstName(avroOrder));
      customFields.add(setCfShipToLastName(avroOrder));

      customFieldList.getCustomField().addAll(customFields);
    } catch (Exception e) {
      log.error("Error in Converting Custom Fields for Order {}",
          avroOrder.getOrderNumber().toString());
    }

    return customFieldList;

  }
}
