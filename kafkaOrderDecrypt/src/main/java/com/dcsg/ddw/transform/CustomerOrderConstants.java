package com.dcsg.ddw.transform;

public class CustomerOrderConstants {

  // Header Constants
  public static final String SOURCE = "WCS";
  public static final String ACTION_TYPE = "Update";
  public static final String MESSAGE_TYPE = "CustomerOrder";
  public static final String COMPANY_ID = "1";
  public static final String MSG_TIME_ZONE = "America/New_York";

  // LineItem Constants
  public static final String COMPONENT_GROUP = "lineItemType";
  public static final String ORDERED_QTY_UOM = "Unit";
  public static final String SHIP_VIA = "UPSG";
  public static final String DELIVERY_OPTION = "Ship to Address";

  //EOM Leading Zero for SKU
  public static final String EOM_PREPEND = "0";

  // Custom Field Constants
  public static final String CF_BILL_TO_CUSTOMER_FIRST_NAME = "CF_BillTo_CustomerFirstName";
  public static final String CF_BILL_TO_CUSTOMER_LAST_NAME = "CF_BillTo_CustomerLastName";
  public static final String CF_ORDER_ENTRY_METHOD = "CF_OrderEntryMethod";
  public static final String CF_LOCATION_ID = "CF_LocationID";
  public static final String CF_SHIP_TO_CUSTOMER_FIRST_NAME = "CF_ShipTo_CustomerFirstName";
  public static final String CF_SHIP_TO_CUSTOMER_LAST_NAME = "CF_ShipTo_CustomerLastName";

  // Order Field Constants
  public static final String CONFIRMED = "true";
  public static final String ORDER_CURRENCY = "USD";

  //cancel reason code.
  public static final String CANCELLED_REASON_CODE = "998";

  //Customer ID for EOM
  public static final String CUSTOMER_ID = "24601";

  //Payment Method Constants
  public static final String CREDIT_CARD = "Credit Card";

}
