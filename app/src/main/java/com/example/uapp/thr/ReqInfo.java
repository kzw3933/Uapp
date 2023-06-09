package com.example.uapp.thr;

/**
 * Autogenerated by Thrift Compiler (0.18.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.18.1)", date = "2023-05-23")
public class ReqInfo implements org.apache.thrift.TBase<ReqInfo, ReqInfo._Fields>, java.io.Serializable, Cloneable, Comparable<ReqInfo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ReqInfo");

  private static final org.apache.thrift.protocol.TField POST_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("post_id", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField FOR_LOST_ITEM_FIELD_DESC = new org.apache.thrift.protocol.TField("for_lost_item", org.apache.thrift.protocol.TType.BOOL, (short)2);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new ReqInfoStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new ReqInfoTupleSchemeFactory();

  public @org.apache.thrift.annotation.Nullable String post_id; // required
  public boolean for_lost_item; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    POST_ID((short)1, "post_id"),
    FOR_LOST_ITEM((short)2, "for_lost_item");

    private static final java.util.Map<String, _Fields> byName = new java.util.HashMap<String, _Fields>();

    static {
      for (_Fields field : java.util.EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    @org.apache.thrift.annotation.Nullable
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // POST_ID
          return POST_ID;
        case 2: // FOR_LOST_ITEM
          return FOR_LOST_ITEM;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    @org.apache.thrift.annotation.Nullable
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    @Override
    public short getThriftFieldId() {
      return _thriftId;
    }

    @Override
    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __FOR_LOST_ITEM_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.POST_ID, new org.apache.thrift.meta_data.FieldMetaData("post_id", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.FOR_LOST_ITEM, new org.apache.thrift.meta_data.FieldMetaData("for_lost_item", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ReqInfo.class, metaDataMap);
  }

  public ReqInfo() {
  }

  public ReqInfo(
    String post_id,
    boolean for_lost_item)
  {
    this();
    this.post_id = post_id;
    this.for_lost_item = for_lost_item;
    setFor_lost_itemIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ReqInfo(ReqInfo other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetPost_id()) {
      this.post_id = other.post_id;
    }
    this.for_lost_item = other.for_lost_item;
  }

  @Override
  public ReqInfo deepCopy() {
    return new ReqInfo(this);
  }

  @Override
  public void clear() {
    this.post_id = null;
    setFor_lost_itemIsSet(false);
    this.for_lost_item = false;
  }

  @org.apache.thrift.annotation.Nullable
  public String getPost_id() {
    return this.post_id;
  }

  public ReqInfo setPost_id(@org.apache.thrift.annotation.Nullable String post_id) {
    this.post_id = post_id;
    return this;
  }

  public void unsetPost_id() {
    this.post_id = null;
  }

  /** Returns true if field post_id is set (has been assigned a value) and false otherwise */
  public boolean isSetPost_id() {
    return this.post_id != null;
  }

  public void setPost_idIsSet(boolean value) {
    if (!value) {
      this.post_id = null;
    }
  }

  public boolean isFor_lost_item() {
    return this.for_lost_item;
  }

  public ReqInfo setFor_lost_item(boolean for_lost_item) {
    this.for_lost_item = for_lost_item;
    setFor_lost_itemIsSet(true);
    return this;
  }

  public void unsetFor_lost_item() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __FOR_LOST_ITEM_ISSET_ID);
  }

  /** Returns true if field for_lost_item is set (has been assigned a value) and false otherwise */
  public boolean isSetFor_lost_item() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __FOR_LOST_ITEM_ISSET_ID);
  }

  public void setFor_lost_itemIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __FOR_LOST_ITEM_ISSET_ID, value);
  }

  @Override
  public void setFieldValue(_Fields field, @org.apache.thrift.annotation.Nullable Object value) {
    switch (field) {
    case POST_ID:
      if (value == null) {
        unsetPost_id();
      } else {
        setPost_id((String)value);
      }
      break;

    case FOR_LOST_ITEM:
      if (value == null) {
        unsetFor_lost_item();
      } else {
        setFor_lost_item((Boolean)value);
      }
      break;

    }
  }

  @org.apache.thrift.annotation.Nullable
  @Override
  public Object getFieldValue(_Fields field) {
    switch (field) {
    case POST_ID:
      return getPost_id();

    case FOR_LOST_ITEM:
      return isFor_lost_item();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  @Override
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case POST_ID:
      return isSetPost_id();
    case FOR_LOST_ITEM:
      return isSetFor_lost_item();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that instanceof ReqInfo)
      return this.equals((ReqInfo)that);
    return false;
  }

  public boolean equals(ReqInfo that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_post_id = true && this.isSetPost_id();
    boolean that_present_post_id = true && that.isSetPost_id();
    if (this_present_post_id || that_present_post_id) {
      if (!(this_present_post_id && that_present_post_id))
        return false;
      if (!this.post_id.equals(that.post_id))
        return false;
    }

    boolean this_present_for_lost_item = true;
    boolean that_present_for_lost_item = true;
    if (this_present_for_lost_item || that_present_for_lost_item) {
      if (!(this_present_for_lost_item && that_present_for_lost_item))
        return false;
      if (this.for_lost_item != that.for_lost_item)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetPost_id()) ? 131071 : 524287);
    if (isSetPost_id())
      hashCode = hashCode * 8191 + post_id.hashCode();

    hashCode = hashCode * 8191 + ((for_lost_item) ? 131071 : 524287);

    return hashCode;
  }

  @Override
  public int compareTo(ReqInfo other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.compare(isSetPost_id(), other.isSetPost_id());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPost_id()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.post_id, other.post_id);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.compare(isSetFor_lost_item(), other.isSetFor_lost_item());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetFor_lost_item()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.for_lost_item, other.for_lost_item);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  @org.apache.thrift.annotation.Nullable
  @Override
  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  @Override
  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    scheme(iprot).read(iprot, this);
  }

  @Override
  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    scheme(oprot).write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("ReqInfo(");
    boolean first = true;

    sb.append("post_id:");
    if (this.post_id == null) {
      sb.append("null");
    } else {
      sb.append(this.post_id);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("for_lost_item:");
    sb.append(this.for_lost_item);
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class ReqInfoStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    @Override
    public ReqInfoStandardScheme getScheme() {
      return new ReqInfoStandardScheme();
    }
  }

  private static class ReqInfoStandardScheme extends org.apache.thrift.scheme.StandardScheme<ReqInfo> {

    @Override
    public void read(org.apache.thrift.protocol.TProtocol iprot, ReqInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // POST_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.post_id = iprot.readString();
              struct.setPost_idIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // FOR_LOST_ITEM
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.for_lost_item = iprot.readBool();
              struct.setFor_lost_itemIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    @Override
    public void write(org.apache.thrift.protocol.TProtocol oprot, ReqInfo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.post_id != null) {
        oprot.writeFieldBegin(POST_ID_FIELD_DESC);
        oprot.writeString(struct.post_id);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(FOR_LOST_ITEM_FIELD_DESC);
      oprot.writeBool(struct.for_lost_item);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class ReqInfoTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    @Override
    public ReqInfoTupleScheme getScheme() {
      return new ReqInfoTupleScheme();
    }
  }

  private static class ReqInfoTupleScheme extends org.apache.thrift.scheme.TupleScheme<ReqInfo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, ReqInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetPost_id()) {
        optionals.set(0);
      }
      if (struct.isSetFor_lost_item()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetPost_id()) {
        oprot.writeString(struct.post_id);
      }
      if (struct.isSetFor_lost_item()) {
        oprot.writeBool(struct.for_lost_item);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, ReqInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.post_id = iprot.readString();
        struct.setPost_idIsSet(true);
      }
      if (incoming.get(1)) {
        struct.for_lost_item = iprot.readBool();
        struct.setFor_lost_itemIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

