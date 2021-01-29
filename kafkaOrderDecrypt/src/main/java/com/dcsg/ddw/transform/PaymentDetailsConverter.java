package com.dcsg.ddw.transform;

import com.dcsg.ddw.eom.TXML.Message.Order.PaymentDetails;
import com.dcsg.ddw.eom.TXML.Message.Order.PaymentDetails.PaymentDetail;
import com.dcsg.ddw.eom.TXML.Message.Order.PaymentDetails.PaymentDetail.BillToDetail;
import com.dcsg.ddw.ord.OrderLineDDW;
import com.dsg.customerorder.avro.Order;
import com.dsg.customerorder.avro.Payment;
import com.dsg.customerorder.enums.PaymentTypeEnum;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dcsg.ddw.ord.*;

@Slf4j
@Component
public class PaymentDetailsConverter {

  private AddressConverter addressConverter;

  @Autowired
  protected PaymentDetailsConverter(AddressConverter addressConverter) {

    this.addressConverter = addressConverter;
  }

  protected String convertExternalPaymentsDetailId() {
    return "12345678";
  }

  protected String convertChargeSequence() {
    return "1";
  }

  protected String convertPaymentMethod(Payment payment) {
    if (payment.getPaymentType().toString().equals("ValueLink") || payment.getPaymentType()
        .toString().equals("None")) {
      return "Gift Card";
    } else {
      return "Credit Card";
    }
  }

  protected String convertPaymentType(Payment payment) {
    return PaymentTypeEnum.getPaymentNameByType(payment.getPaymentType());
  }

  protected String convertAccountDisplayNumber() {
    return "************1684";
  }

  protected BigDecimal convertReqAutorizationAmount() {
    return BigDecimal.valueOf(0.01);
  }

  //orig -- to drop 
  protected PaymentDetails convertPaymentDetails(Order order) {
    List<Payment> payments = order.getPayments();
    PaymentDetails paymentDetails = new PaymentDetails();
    List<PaymentDetail> payDetails = new ArrayList<>();

    BillToDetail billToDetail = addressConverter.convertBillingAddress(order);

    try {
      for (Payment payment : payments) {
        PaymentDetail paymentDetail = new PaymentDetail();
        paymentDetail.setExternalPaymentDetailId(convertExternalPaymentsDetailId());
        paymentDetail.setPaymentMethod(convertPaymentMethod(payment));
        if (CustomerOrderConstants.CREDIT_CARD.equals(convertPaymentMethod(payment))) {
          paymentDetail.setCardType(convertPaymentType(payment));
        }
        paymentDetail.setAccountDisplayNumber(convertAccountDisplayNumber());
        paymentDetail.setReqAuthorizationAmount(convertReqAutorizationAmount());
        paymentDetail.setChargeSequence(convertChargeSequence());
        paymentDetail.setBillToDetail(billToDetail);
        payDetails.add(paymentDetail);
      }
      paymentDetails.getPaymentDetail().addAll(payDetails);
    } catch (Exception e) {
      log.error("Error converting Payment Details for Order {}", order.getOrderNumber().toString());
    }
    return paymentDetails;
  }
  
  //kc
  protected List<OrderPaymentDDW> convertPaymentInfo(Order order) {
	    List<Payment> payments = order.getPayments();
	    PaymentDetails paymentDetails = new PaymentDetails();
	    List<PaymentDetail> payDetails = new ArrayList<>();
	    
	    List<OrderPaymentDDW> ordPay = new ArrayList<>();

	    BillToDetail billToDetail = addressConverter.convertBillingAddress(order);

	    try {
	      for (Payment payment : payments) {
	        PaymentDetail paymentDetail = new PaymentDetail();
	        paymentDetail.setExternalPaymentDetailId(convertExternalPaymentsDetailId());
	        paymentDetail.setPaymentMethod(convertPaymentMethod(payment));
	        if (CustomerOrderConstants.CREDIT_CARD.equals(convertPaymentMethod(payment))) {
	          paymentDetail.setCardType(convertPaymentType(payment));
	        }
	        paymentDetail.setAccountDisplayNumber(convertAccountDisplayNumber());
	        paymentDetail.setReqAuthorizationAmount(convertReqAutorizationAmount());
	        paymentDetail.setChargeSequence(convertChargeSequence());
	        paymentDetail.setBillToDetail(billToDetail);
	        payDetails.add(paymentDetail);
	      }
	      paymentDetails.getPaymentDetail().addAll(payDetails);
	    } catch (Exception e) {
	      log.error("Error converting Payment Details for Order {}", order.getOrderNumber().toString());
	    }
	    return ordPay;
	  }

}
