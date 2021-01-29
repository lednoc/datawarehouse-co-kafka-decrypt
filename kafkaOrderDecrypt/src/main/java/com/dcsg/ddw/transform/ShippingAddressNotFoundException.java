package com.dcsg.ddw.transform;

import lombok.Data;

@Data
public class ShippingAddressNotFoundException extends RuntimeException {

  private static final String SHIPPING_ADDRESS_NOT_FOUND = "Shipping Address Not Found Exception Occurred For OrderId: ";
  private final String errorMessage;


  @Override
  public String getMessage() {

    return SHIPPING_ADDRESS_NOT_FOUND;
  }
}

