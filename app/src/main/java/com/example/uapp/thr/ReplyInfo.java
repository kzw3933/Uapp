package com.example.uapp.thr;

/**
 * Autogenerated by Thrift Compiler (0.18.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.18.1)", date = "2023-04-17")
public class ReplyInfo implements org.apache.thrift.TBase<ReplyInfo, ReplyInfo._Fields>, java.io.Serializable, Cloneable, Comparable<ReplyInfo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ReplyInfo");

  private static final org.apache.thrift.protocol.TField POST_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("post_id", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField CONTENT_FIELD_DESC = new org.apache.thrift.protocol.TField("content", org.apache.thrift.protocol.TType.STRING, (short)2);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new ReplyInfoStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new ReplyInfoTupleSchemeFactory();

  public int post_id; // required
  public @org.apache.thrift.annotation.Nullable String content; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    POST_ID((short)1, "post_id"),
    CONTENT((short)2, "content");

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
        case 2: // CONTENT
          return CONTENT;
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
  private static final int __POST_ID_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.POST_ID, new org.apache.thrift.meta_data.FieldMetaData("post_id", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.CONTENT, new org.apache.thrift.meta_data.FieldMetaData("content", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ReplyInfo.class, metaDataMap);
  }

  public ReplyInfo() {
  }

  public ReplyInfo(
    int post_id,
    String content)
  {
    this();
    this.post_id = post_id;
    setPost_idIsSet(true);
    this.content = content;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ReplyInfo(ReplyInfo other) {
    __isset_bitfield = other.__isset_bitfield;
    this.post_id = other.post_id;
    if (other.isSetContent()) {
      this.content = other.content;
    }
  }

  @Override
  public ReplyInfo deepCopy() {
    return new ReplyInfo(this);
  }

  @Override
  public void clear() {
    setPost_idIsSet(false);
    this.post_id = 0;
    this.content = null;
  }

  public int getPost_id() {
    return this.post_id;
  }

  public ReplyInfo setPost_id(int post_id) {
    this.post_id = post_id;
    setPost_idIsSet(true);
    return this;
  }

  public void unsetPost_id() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __POST_ID_ISSET_ID);
  }

  /** Returns true if field post_id is set (has been assigned a value) and false otherwise */
  public boolean isSetPost_id() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __POST_ID_ISSET_ID);
  }

  public void setPost_idIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __POST_ID_ISSET_ID, value);
  }

  @org.apache.thrift.annotation.Nullable
  public String getContent() {
    return this.content;
  }

  public ReplyInfo setContent(@org.apache.thrift.annotation.Nullable String content) {
    this.content = content;
    return this;
  }

  public void unsetContent() {
    this.content = null;
  }

  /** Returns true if field content is set (has been assigned a value) and false otherwise */
  public boolean isSetContent() {
    return this.content != null;
  }

  public void setContentIsSet(boolean value) {
    if (!value) {
      this.content = null;
    }
  }

  @Override
  public void setFieldValue(_Fields field, @org.apache.thrift.annotation.Nullable Object value) {
    switch (field) {
    case POST_ID:
      if (value == null) {
        unsetPost_id();
      } else {
        setPost_id((Integer)value);
      }
      break;

    case CONTENT:
      if (value == null) {
        unsetContent();
      } else {
        setContent((String)value);
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

    case CONTENT:
      return getContent();

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
    case CONTENT:
      return isSetContent();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that instanceof ReplyInfo)
      return this.equals((ReplyInfo)that);
    return false;
  }

  public boolean equals(ReplyInfo that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_post_id = true;
    boolean that_present_post_id = true;
    if (this_present_post_id || that_present_post_id) {
      if (!(this_present_post_id && that_present_post_id))
        return false;
      if (this.post_id != that.post_id)
        return false;
    }

    boolean this_present_content = true && this.isSetContent();
    boolean that_present_content = true && that.isSetContent();
    if (this_present_content || that_present_content) {
      if (!(this_present_content && that_present_content))
        return false;
      if (!this.content.equals(that.content))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + post_id;

    hashCode = hashCode * 8191 + ((isSetContent()) ? 131071 : 524287);
    if (isSetContent())
      hashCode = hashCode * 8191 + content.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(ReplyInfo other) {
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
    lastComparison = Boolean.compare(isSetContent(), other.isSetContent());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetContent()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.content, other.content);
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
    StringBuilder sb = new StringBuilder("ReplyInfo(");
    boolean first = true;

    sb.append("post_id:");
    sb.append(this.post_id);
    first = false;
    if (!first) sb.append(", ");
    sb.append("content:");
    if (this.content == null) {
      sb.append("null");
    } else {
      sb.append(this.content);
    }
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

  private static class ReplyInfoStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    @Override
    public ReplyInfoStandardScheme getScheme() {
      return new ReplyInfoStandardScheme();
    }
  }

  private static class ReplyInfoStandardScheme extends org.apache.thrift.scheme.StandardScheme<ReplyInfo> {

    @Override
    public void read(org.apache.thrift.protocol.TProtocol iprot, ReplyInfo struct) throws org.apache.thrift.TException {
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
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.post_id = iprot.readI32();
              struct.setPost_idIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // CONTENT
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.content = iprot.readString();
              struct.setContentIsSet(true);
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
    public void write(org.apache.thrift.protocol.TProtocol oprot, ReplyInfo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(POST_ID_FIELD_DESC);
      oprot.writeI32(struct.post_id);
      oprot.writeFieldEnd();
      if (struct.content != null) {
        oprot.writeFieldBegin(CONTENT_FIELD_DESC);
        oprot.writeString(struct.content);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class ReplyInfoTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    @Override
    public ReplyInfoTupleScheme getScheme() {
      return new ReplyInfoTupleScheme();
    }
  }

  private static class ReplyInfoTupleScheme extends org.apache.thrift.scheme.TupleScheme<ReplyInfo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, ReplyInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetPost_id()) {
        optionals.set(0);
      }
      if (struct.isSetContent()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetPost_id()) {
        oprot.writeI32(struct.post_id);
      }
      if (struct.isSetContent()) {
        oprot.writeString(struct.content);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, ReplyInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.post_id = iprot.readI32();
        struct.setPost_idIsSet(true);
      }
      if (incoming.get(1)) {
        struct.content = iprot.readString();
        struct.setContentIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

