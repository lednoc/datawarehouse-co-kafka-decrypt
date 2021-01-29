package com.dcsg.ddw.transform;

import lombok.Data;

@Data
public class OrderLineNotFoundException extends RuntimeException {

  private static final String ORDER_LINE_NOT_FOUND = "Order Line Not Found Exception Occurred For OrderId: ";
  private final String errorMessage;

  @Override
  public String getMessage() {
    return ORDER_LINE_NOT_FOUND;
  }
}

