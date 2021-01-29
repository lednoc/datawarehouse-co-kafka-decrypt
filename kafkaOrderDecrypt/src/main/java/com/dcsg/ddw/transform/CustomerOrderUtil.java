package com.dcsg.ddw.transform;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import com.dcsg.ddw.eom.TXML.Header;

@Slf4j
@Component
public class CustomerOrderUtil {

  public Header setXmlHeader() {
    Header header = new Header();
    header.setSource(CustomerOrderConstants.SOURCE);
    header.setActionType(CustomerOrderConstants.ACTION_TYPE);
    header.setMessageType(CustomerOrderConstants.MESSAGE_TYPE);
    header.setCompanyID(CustomerOrderConstants.COMPANY_ID);
    header.setMsgTimeZone(CustomerOrderConstants.MSG_TIME_ZONE);

    return header;
  }

  public String convertDateFormat(String dateToConvert, String orderNumber)
      throws java.text.ParseException {
    SimpleDateFormat outgoingFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
    SimpleDateFormat incomingFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    String convertedDate = "";

    try {
      Date date = incomingFormat.parse(dateToConvert);
      convertedDate = outgoingFormat.format(date);

    } catch (ParseException e) {
      log.error("Error parsing date {} for orderNumber: {}", dateToConvert, orderNumber);
      throw new ParseException(e.getMessage(), 0);
    } catch (Exception e) {
      log.error("Error converting date {} for orderNumber: {}", dateToConvert, orderNumber);
      log.error("Exception is: {}", e.getMessage());
      throw e;
    }

    return convertedDate;
  }
}
