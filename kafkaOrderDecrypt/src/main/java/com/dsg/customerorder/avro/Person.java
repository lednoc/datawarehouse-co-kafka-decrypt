/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package com.dsg.customerorder.avro;

import org.apache.avro.generic.GenericArray;
import org.apache.avro.specific.SpecificData;
import org.apache.avro.util.Utf8;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@org.apache.avro.specific.AvroGenerated
public class Person extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = -5273010216125649826L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"Person\",\"namespace\":\"com.dsg.customerorder.avro\",\"fields\":[{\"name\":\"loyaltyAccount\",\"type\":[\"null\",\"string\"],\"default\":null},{\"name\":\"address\",\"type\":{\"type\":\"record\",\"name\":\"Address\",\"fields\":[{\"name\":\"address1\",\"type\":\"string\"},{\"name\":\"address2\",\"type\":[\"null\",\"string\"],\"default\":null},{\"name\":\"address3\",\"type\":[\"null\",\"string\"],\"default\":null},{\"name\":\"city\",\"type\":\"string\"},{\"name\":\"state\",\"type\":\"string\"},{\"name\":\"postalCode\",\"type\":\"string\"},{\"name\":\"country\",\"type\":\"string\"}]}}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<Person> ENCODER =
      new BinaryMessageEncoder<Person>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<Person> DECODER =
      new BinaryMessageDecoder<Person>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageEncoder instance used by this class.
   * @return the message encoder used by this class
   */
  public static BinaryMessageEncoder<Person> getEncoder() {
    return ENCODER;
  }

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   * @return the message decoder used by this class
   */
  public static BinaryMessageDecoder<Person> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   * @return a BinaryMessageDecoder instance for this class backed by the given SchemaStore
   */
  public static BinaryMessageDecoder<Person> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<Person>(MODEL$, SCHEMA$, resolver);
  }

  /**
   * Serializes this Person to a ByteBuffer.
   * @return a buffer holding the serialized data for this instance
   * @throws java.io.IOException if this instance could not be serialized
   */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /**
   * Deserializes a Person from a ByteBuffer.
   * @param b a byte buffer holding serialized data for an instance of this class
   * @return a Person instance decoded from the given buffer
   * @throws java.io.IOException if the given bytes could not be deserialized into an instance of this class
   */
  public static Person fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

   private java.lang.CharSequence loyaltyAccount;
   private com.dsg.customerorder.avro.Address address;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public Person() {}

  /**
   * All-args constructor.
   * @param loyaltyAccount The new value for loyaltyAccount
   * @param address The new value for address
   */
  public Person(java.lang.CharSequence loyaltyAccount, com.dsg.customerorder.avro.Address address) {
    this.loyaltyAccount = loyaltyAccount;
    this.address = address;
  }

  public org.apache.avro.specific.SpecificData getSpecificData() { return MODEL$; }
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return loyaltyAccount;
    case 1: return address;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: loyaltyAccount = (java.lang.CharSequence)value$; break;
    case 1: address = (com.dsg.customerorder.avro.Address)value$; break;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  /**
   * Gets the value of the 'loyaltyAccount' field.
   * @return The value of the 'loyaltyAccount' field.
   */
  public java.lang.CharSequence getLoyaltyAccount() {
    return loyaltyAccount;
  }


  /**
   * Sets the value of the 'loyaltyAccount' field.
   * @param value the value to set.
   */
  public void setLoyaltyAccount(java.lang.CharSequence value) {
    this.loyaltyAccount = value;
  }

  /**
   * Gets the value of the 'address' field.
   * @return The value of the 'address' field.
   */
  public com.dsg.customerorder.avro.Address getAddress() {
    return address;
  }


  /**
   * Sets the value of the 'address' field.
   * @param value the value to set.
   */
  public void setAddress(com.dsg.customerorder.avro.Address value) {
    this.address = value;
  }

  /**
   * Creates a new Person RecordBuilder.
   * @return A new Person RecordBuilder
   */
  public static com.dsg.customerorder.avro.Person.Builder newBuilder() {
    return new com.dsg.customerorder.avro.Person.Builder();
  }

  /**
   * Creates a new Person RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new Person RecordBuilder
   */
  public static com.dsg.customerorder.avro.Person.Builder newBuilder(com.dsg.customerorder.avro.Person.Builder other) {
    if (other == null) {
      return new com.dsg.customerorder.avro.Person.Builder();
    } else {
      return new com.dsg.customerorder.avro.Person.Builder(other);
    }
  }

  /**
   * Creates a new Person RecordBuilder by copying an existing Person instance.
   * @param other The existing instance to copy.
   * @return A new Person RecordBuilder
   */
  public static com.dsg.customerorder.avro.Person.Builder newBuilder(com.dsg.customerorder.avro.Person other) {
    if (other == null) {
      return new com.dsg.customerorder.avro.Person.Builder();
    } else {
      return new com.dsg.customerorder.avro.Person.Builder(other);
    }
  }

  /**
   * RecordBuilder for Person instances.
   */
  @org.apache.avro.specific.AvroGenerated
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<Person>
    implements org.apache.avro.data.RecordBuilder<Person> {

    private java.lang.CharSequence loyaltyAccount;
    private com.dsg.customerorder.avro.Address address;
    private com.dsg.customerorder.avro.Address.Builder addressBuilder;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(com.dsg.customerorder.avro.Person.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.loyaltyAccount)) {
        this.loyaltyAccount = data().deepCopy(fields()[0].schema(), other.loyaltyAccount);
        fieldSetFlags()[0] = other.fieldSetFlags()[0];
      }
      if (isValidValue(fields()[1], other.address)) {
        this.address = data().deepCopy(fields()[1].schema(), other.address);
        fieldSetFlags()[1] = other.fieldSetFlags()[1];
      }
      if (other.hasAddressBuilder()) {
        this.addressBuilder = com.dsg.customerorder.avro.Address.newBuilder(other.getAddressBuilder());
      }
    }

    /**
     * Creates a Builder by copying an existing Person instance
     * @param other The existing instance to copy.
     */
    private Builder(com.dsg.customerorder.avro.Person other) {
      super(SCHEMA$);
      if (isValidValue(fields()[0], other.loyaltyAccount)) {
        this.loyaltyAccount = data().deepCopy(fields()[0].schema(), other.loyaltyAccount);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.address)) {
        this.address = data().deepCopy(fields()[1].schema(), other.address);
        fieldSetFlags()[1] = true;
      }
      this.addressBuilder = null;
    }

    /**
      * Gets the value of the 'loyaltyAccount' field.
      * @return The value.
      */
    public java.lang.CharSequence getLoyaltyAccount() {
      return loyaltyAccount;
    }


    /**
      * Sets the value of the 'loyaltyAccount' field.
      * @param value The value of 'loyaltyAccount'.
      * @return This builder.
      */
    public com.dsg.customerorder.avro.Person.Builder setLoyaltyAccount(java.lang.CharSequence value) {
      validate(fields()[0], value);
      this.loyaltyAccount = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'loyaltyAccount' field has been set.
      * @return True if the 'loyaltyAccount' field has been set, false otherwise.
      */
    public boolean hasLoyaltyAccount() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'loyaltyAccount' field.
      * @return This builder.
      */
    public com.dsg.customerorder.avro.Person.Builder clearLoyaltyAccount() {
      loyaltyAccount = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'address' field.
      * @return The value.
      */
    public com.dsg.customerorder.avro.Address getAddress() {
      return address;
    }


    /**
      * Sets the value of the 'address' field.
      * @param value The value of 'address'.
      * @return This builder.
      */
    public com.dsg.customerorder.avro.Person.Builder setAddress(com.dsg.customerorder.avro.Address value) {
      validate(fields()[1], value);
      this.addressBuilder = null;
      this.address = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'address' field has been set.
      * @return True if the 'address' field has been set, false otherwise.
      */
    public boolean hasAddress() {
      return fieldSetFlags()[1];
    }

    /**
     * Gets the Builder instance for the 'address' field and creates one if it doesn't exist yet.
     * @return This builder.
     */
    public com.dsg.customerorder.avro.Address.Builder getAddressBuilder() {
      if (addressBuilder == null) {
        if (hasAddress()) {
          setAddressBuilder(com.dsg.customerorder.avro.Address.newBuilder(address));
        } else {
          setAddressBuilder(com.dsg.customerorder.avro.Address.newBuilder());
        }
      }
      return addressBuilder;
    }

    /**
     * Sets the Builder instance for the 'address' field
     * @param value The builder instance that must be set.
     * @return This builder.
     */

    public com.dsg.customerorder.avro.Person.Builder setAddressBuilder(com.dsg.customerorder.avro.Address.Builder value) {
      clearAddress();
      addressBuilder = value;
      return this;
    }

    /**
     * Checks whether the 'address' field has an active Builder instance
     * @return True if the 'address' field has an active Builder instance
     */
    public boolean hasAddressBuilder() {
      return addressBuilder != null;
    }

    /**
      * Clears the value of the 'address' field.
      * @return This builder.
      */
    public com.dsg.customerorder.avro.Person.Builder clearAddress() {
      address = null;
      addressBuilder = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Person build() {
      try {
        Person record = new Person();
        record.loyaltyAccount = fieldSetFlags()[0] ? this.loyaltyAccount : (java.lang.CharSequence) defaultValue(fields()[0]);
        if (addressBuilder != null) {
          try {
            record.address = this.addressBuilder.build();
          } catch (org.apache.avro.AvroMissingFieldException e) {
            e.addParentField(record.getSchema().getField("address"));
            throw e;
          }
        } else {
          record.address = fieldSetFlags()[1] ? this.address : (com.dsg.customerorder.avro.Address) defaultValue(fields()[1]);
        }
        return record;
      } catch (org.apache.avro.AvroMissingFieldException e) {
        throw e;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<Person>
    WRITER$ = (org.apache.avro.io.DatumWriter<Person>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<Person>
    READER$ = (org.apache.avro.io.DatumReader<Person>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

  @Override protected boolean hasCustomCoders() { return true; }

  @Override public void customEncode(org.apache.avro.io.Encoder out)
    throws java.io.IOException
  {
    if (this.loyaltyAccount == null) {
      out.writeIndex(0);
      out.writeNull();
    } else {
      out.writeIndex(1);
      out.writeString(this.loyaltyAccount);
    }

    this.address.customEncode(out);

  }

  @Override public void customDecode(org.apache.avro.io.ResolvingDecoder in)
    throws java.io.IOException
  {
    org.apache.avro.Schema.Field[] fieldOrder = in.readFieldOrderIfDiff();
    if (fieldOrder == null) {
      if (in.readIndex() != 1) {
        in.readNull();
        this.loyaltyAccount = null;
      } else {
        this.loyaltyAccount = in.readString(this.loyaltyAccount instanceof Utf8 ? (Utf8)this.loyaltyAccount : null);
      }

      if (this.address == null) {
        this.address = new com.dsg.customerorder.avro.Address();
      }
      this.address.customDecode(in);

    } else {
      for (int i = 0; i < 2; i++) {
        switch (fieldOrder[i].pos()) {
        case 0:
          if (in.readIndex() != 1) {
            in.readNull();
            this.loyaltyAccount = null;
          } else {
            this.loyaltyAccount = in.readString(this.loyaltyAccount instanceof Utf8 ? (Utf8)this.loyaltyAccount : null);
          }
          break;

        case 1:
          if (this.address == null) {
            this.address = new com.dsg.customerorder.avro.Address();
          }
          this.address.customDecode(in);
          break;

        default:
          throw new java.io.IOException("Corrupt ResolvingDecoder.");
        }
      }
    }
  }
}










