/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package com.dsg.customerorder.avro;

import org.apache.avro.specific.SpecificData;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public class Payment extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = -8714106789568211224L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"Payment\",\"namespace\":\"com.dsg.customerorder.avro\",\"fields\":[{\"name\":\"paymentType\",\"type\":{\"type\":\"enum\",\"name\":\"PaymentType\",\"symbols\":[\"ValueLink\",\"Mastercard\",\"Visa\",\"Discover\",\"AmericanExpress\",\"PLCC\",\"Resiliency\",\"JCB\",\"COMC\",\"DinersClub\",\"PayPal\",\"None\"]}}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<Payment> ENCODER =
      new BinaryMessageEncoder<Payment>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<Payment> DECODER =
      new BinaryMessageDecoder<Payment>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   */
  public static BinaryMessageDecoder<Payment> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   */
  public static BinaryMessageDecoder<Payment> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<Payment>(MODEL$, SCHEMA$, resolver);
  }

  /** Serializes this Payment to a ByteBuffer. */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /** Deserializes a Payment from a ByteBuffer. */
  public static Payment fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  @Deprecated public com.dsg.customerorder.avro.PaymentType paymentType;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public Payment() {}

  /**
   * All-args constructor.
   * @param paymentType The new value for paymentType
   */
  public Payment(com.dsg.customerorder.avro.PaymentType paymentType) {
    this.paymentType = paymentType;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return paymentType;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: paymentType = (com.dsg.customerorder.avro.PaymentType)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'paymentType' field.
   * @return The value of the 'paymentType' field.
   */
  public com.dsg.customerorder.avro.PaymentType getPaymentType() {
    return paymentType;
  }

  /**
   * Sets the value of the 'paymentType' field.
   * @param value the value to set.
   */
  public void setPaymentType(com.dsg.customerorder.avro.PaymentType value) {
    this.paymentType = value;
  }

  /**
   * Creates a new Payment RecordBuilder.
   * @return A new Payment RecordBuilder
   */
  public static com.dsg.customerorder.avro.Payment.Builder newBuilder() {
    return new com.dsg.customerorder.avro.Payment.Builder();
  }

  /**
   * Creates a new Payment RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new Payment RecordBuilder
   */
  public static com.dsg.customerorder.avro.Payment.Builder newBuilder(com.dsg.customerorder.avro.Payment.Builder other) {
    return new com.dsg.customerorder.avro.Payment.Builder(other);
  }

  /**
   * Creates a new Payment RecordBuilder by copying an existing Payment instance.
   * @param other The existing instance to copy.
   * @return A new Payment RecordBuilder
   */
  public static com.dsg.customerorder.avro.Payment.Builder newBuilder(com.dsg.customerorder.avro.Payment other) {
    return new com.dsg.customerorder.avro.Payment.Builder(other);
  }

  /**
   * RecordBuilder for Payment instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<Payment>
    implements org.apache.avro.data.RecordBuilder<Payment> {

    private com.dsg.customerorder.avro.PaymentType paymentType;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(com.dsg.customerorder.avro.Payment.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.paymentType)) {
        this.paymentType = data().deepCopy(fields()[0].schema(), other.paymentType);
        fieldSetFlags()[0] = true;
      }
    }

    /**
     * Creates a Builder by copying an existing Payment instance
     * @param other The existing instance to copy.
     */
    private Builder(com.dsg.customerorder.avro.Payment other) {
            super(SCHEMA$);
      if (isValidValue(fields()[0], other.paymentType)) {
        this.paymentType = data().deepCopy(fields()[0].schema(), other.paymentType);
        fieldSetFlags()[0] = true;
      }
    }

    /**
      * Gets the value of the 'paymentType' field.
      * @return The value.
      */
    public com.dsg.customerorder.avro.PaymentType getPaymentType() {
      return paymentType;
    }

    /**
      * Sets the value of the 'paymentType' field.
      * @param value The value of 'paymentType'.
      * @return This builder.
      */
    public com.dsg.customerorder.avro.Payment.Builder setPaymentType(com.dsg.customerorder.avro.PaymentType value) {
      validate(fields()[0], value);
      this.paymentType = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'paymentType' field has been set.
      * @return True if the 'paymentType' field has been set, false otherwise.
      */
    public boolean hasPaymentType() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'paymentType' field.
      * @return This builder.
      */
    public com.dsg.customerorder.avro.Payment.Builder clearPaymentType() {
      paymentType = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Payment build() {
      try {
        Payment record = new Payment();
        record.paymentType = fieldSetFlags()[0] ? this.paymentType : (com.dsg.customerorder.avro.PaymentType) defaultValue(fields()[0]);
        return record;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<Payment>
    WRITER$ = (org.apache.avro.io.DatumWriter<Payment>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<Payment>
    READER$ = (org.apache.avro.io.DatumReader<Payment>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

}
