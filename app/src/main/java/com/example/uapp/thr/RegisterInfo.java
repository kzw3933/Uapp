package com.example.uapp.thr;

/**
 * Autogenerated by Thrift Compiler (0.18.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.18.1)", date = "2023-06-05")
public class RegisterInfo implements org.apache.thrift.TBase<RegisterInfo, RegisterInfo._Fields>, java.io.Serializable, Cloneable, Comparable<RegisterInfo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("RegisterInfo");

  private static final org.apache.thrift.protocol.TField STUDENT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("student_id", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField USERNAME_FIELD_DESC = new org.apache.thrift.protocol.TField("username", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField EMAIL_FIELD_DESC = new org.apache.thrift.protocol.TField("email", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField PASSWORD_FIELD_DESC = new org.apache.thrift.protocol.TField("password", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField CONTACT_FIELD_DESC = new org.apache.thrift.protocol.TField("contact", org.apache.thrift.protocol.TType.STRING, (short)5);
  private static final org.apache.thrift.protocol.TField HEADSHOT_FIELD_DESC = new org.apache.thrift.protocol.TField("headshot", org.apache.thrift.protocol.TType.STRING, (short)6);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new RegisterInfoStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new RegisterInfoTupleSchemeFactory();

  public @org.apache.thrift.annotation.Nullable String student_id; // required
  public @org.apache.thrift.annotation.Nullable String username; // required
  public @org.apache.thrift.annotation.Nullable String email; // required
  public @org.apache.thrift.annotation.Nullable String password; // required
  public @org.apache.thrift.annotation.Nullable String contact; // required
  public @org.apache.thrift.annotation.Nullable java.nio.ByteBuffer headshot; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    STUDENT_ID((short)1, "student_id"),
    USERNAME((short)2, "username"),
    EMAIL((short)3, "email"),
    PASSWORD((short)4, "password"),
    CONTACT((short)5, "contact"),
    HEADSHOT((short)6, "headshot");

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
        case 1: // STUDENT_ID
          return STUDENT_ID;
        case 2: // USERNAME
          return USERNAME;
        case 3: // EMAIL
          return EMAIL;
        case 4: // PASSWORD
          return PASSWORD;
        case 5: // CONTACT
          return CONTACT;
        case 6: // HEADSHOT
          return HEADSHOT;
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
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.STUDENT_ID, new org.apache.thrift.meta_data.FieldMetaData("student_id", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.USERNAME, new org.apache.thrift.meta_data.FieldMetaData("username", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.EMAIL, new org.apache.thrift.meta_data.FieldMetaData("email", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.PASSWORD, new org.apache.thrift.meta_data.FieldMetaData("password", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.CONTACT, new org.apache.thrift.meta_data.FieldMetaData("contact", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.HEADSHOT, new org.apache.thrift.meta_data.FieldMetaData("headshot", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING        , true)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(RegisterInfo.class, metaDataMap);
  }

  public RegisterInfo() {
  }

  public RegisterInfo(
    String student_id,
    String username,
    String email,
    String password,
    String contact,
    java.nio.ByteBuffer headshot)
  {
    this();
    this.student_id = student_id;
    this.username = username;
    this.email = email;
    this.password = password;
    this.contact = contact;
    this.headshot = org.apache.thrift.TBaseHelper.copyBinary(headshot);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public RegisterInfo(RegisterInfo other) {
    if (other.isSetStudent_id()) {
      this.student_id = other.student_id;
    }
    if (other.isSetUsername()) {
      this.username = other.username;
    }
    if (other.isSetEmail()) {
      this.email = other.email;
    }
    if (other.isSetPassword()) {
      this.password = other.password;
    }
    if (other.isSetContact()) {
      this.contact = other.contact;
    }
    if (other.isSetHeadshot()) {
      this.headshot = org.apache.thrift.TBaseHelper.copyBinary(other.headshot);
    }
  }

  @Override
  public RegisterInfo deepCopy() {
    return new RegisterInfo(this);
  }

  @Override
  public void clear() {
    this.student_id = null;
    this.username = null;
    this.email = null;
    this.password = null;
    this.contact = null;
    this.headshot = null;
  }

  @org.apache.thrift.annotation.Nullable
  public String getStudent_id() {
    return this.student_id;
  }

  public RegisterInfo setStudent_id(@org.apache.thrift.annotation.Nullable String student_id) {
    this.student_id = student_id;
    return this;
  }

  public void unsetStudent_id() {
    this.student_id = null;
  }

  /** Returns true if field student_id is set (has been assigned a value) and false otherwise */
  public boolean isSetStudent_id() {
    return this.student_id != null;
  }

  public void setStudent_idIsSet(boolean value) {
    if (!value) {
      this.student_id = null;
    }
  }

  @org.apache.thrift.annotation.Nullable
  public String getUsername() {
    return this.username;
  }

  public RegisterInfo setUsername(@org.apache.thrift.annotation.Nullable String username) {
    this.username = username;
    return this;
  }

  public void unsetUsername() {
    this.username = null;
  }

  /** Returns true if field username is set (has been assigned a value) and false otherwise */
  public boolean isSetUsername() {
    return this.username != null;
  }

  public void setUsernameIsSet(boolean value) {
    if (!value) {
      this.username = null;
    }
  }

  @org.apache.thrift.annotation.Nullable
  public String getEmail() {
    return this.email;
  }

  public RegisterInfo setEmail(@org.apache.thrift.annotation.Nullable String email) {
    this.email = email;
    return this;
  }

  public void unsetEmail() {
    this.email = null;
  }

  /** Returns true if field email is set (has been assigned a value) and false otherwise */
  public boolean isSetEmail() {
    return this.email != null;
  }

  public void setEmailIsSet(boolean value) {
    if (!value) {
      this.email = null;
    }
  }

  @org.apache.thrift.annotation.Nullable
  public String getPassword() {
    return this.password;
  }

  public RegisterInfo setPassword(@org.apache.thrift.annotation.Nullable String password) {
    this.password = password;
    return this;
  }

  public void unsetPassword() {
    this.password = null;
  }

  /** Returns true if field password is set (has been assigned a value) and false otherwise */
  public boolean isSetPassword() {
    return this.password != null;
  }

  public void setPasswordIsSet(boolean value) {
    if (!value) {
      this.password = null;
    }
  }

  @org.apache.thrift.annotation.Nullable
  public String getContact() {
    return this.contact;
  }

  public RegisterInfo setContact(@org.apache.thrift.annotation.Nullable String contact) {
    this.contact = contact;
    return this;
  }

  public void unsetContact() {
    this.contact = null;
  }

  /** Returns true if field contact is set (has been assigned a value) and false otherwise */
  public boolean isSetContact() {
    return this.contact != null;
  }

  public void setContactIsSet(boolean value) {
    if (!value) {
      this.contact = null;
    }
  }

  public byte[] getHeadshot() {
    setHeadshot(org.apache.thrift.TBaseHelper.rightSize(headshot));
    return headshot == null ? null : headshot.array();
  }

  public java.nio.ByteBuffer bufferForHeadshot() {
    return org.apache.thrift.TBaseHelper.copyBinary(headshot);
  }

  public RegisterInfo setHeadshot(byte[] headshot) {
    this.headshot = headshot == null ? (java.nio.ByteBuffer)null   : java.nio.ByteBuffer.wrap(headshot.clone());
    return this;
  }

  public RegisterInfo setHeadshot(@org.apache.thrift.annotation.Nullable java.nio.ByteBuffer headshot) {
    this.headshot = org.apache.thrift.TBaseHelper.copyBinary(headshot);
    return this;
  }

  public void unsetHeadshot() {
    this.headshot = null;
  }

  /** Returns true if field headshot is set (has been assigned a value) and false otherwise */
  public boolean isSetHeadshot() {
    return this.headshot != null;
  }

  public void setHeadshotIsSet(boolean value) {
    if (!value) {
      this.headshot = null;
    }
  }

  @Override
  public void setFieldValue(_Fields field, @org.apache.thrift.annotation.Nullable Object value) {
    switch (field) {
    case STUDENT_ID:
      if (value == null) {
        unsetStudent_id();
      } else {
        setStudent_id((String)value);
      }
      break;

    case USERNAME:
      if (value == null) {
        unsetUsername();
      } else {
        setUsername((String)value);
      }
      break;

    case EMAIL:
      if (value == null) {
        unsetEmail();
      } else {
        setEmail((String)value);
      }
      break;

    case PASSWORD:
      if (value == null) {
        unsetPassword();
      } else {
        setPassword((String)value);
      }
      break;

    case CONTACT:
      if (value == null) {
        unsetContact();
      } else {
        setContact((String)value);
      }
      break;

    case HEADSHOT:
      if (value == null) {
        unsetHeadshot();
      } else {
        if (value instanceof byte[]) {
          setHeadshot((byte[])value);
        } else {
          setHeadshot((java.nio.ByteBuffer)value);
        }
      }
      break;

    }
  }

  @org.apache.thrift.annotation.Nullable
  @Override
  public Object getFieldValue(_Fields field) {
    switch (field) {
    case STUDENT_ID:
      return getStudent_id();

    case USERNAME:
      return getUsername();

    case EMAIL:
      return getEmail();

    case PASSWORD:
      return getPassword();

    case CONTACT:
      return getContact();

    case HEADSHOT:
      return getHeadshot();

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
    case STUDENT_ID:
      return isSetStudent_id();
    case USERNAME:
      return isSetUsername();
    case EMAIL:
      return isSetEmail();
    case PASSWORD:
      return isSetPassword();
    case CONTACT:
      return isSetContact();
    case HEADSHOT:
      return isSetHeadshot();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that instanceof RegisterInfo)
      return this.equals((RegisterInfo)that);
    return false;
  }

  public boolean equals(RegisterInfo that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_student_id = true && this.isSetStudent_id();
    boolean that_present_student_id = true && that.isSetStudent_id();
    if (this_present_student_id || that_present_student_id) {
      if (!(this_present_student_id && that_present_student_id))
        return false;
      if (!this.student_id.equals(that.student_id))
        return false;
    }

    boolean this_present_username = true && this.isSetUsername();
    boolean that_present_username = true && that.isSetUsername();
    if (this_present_username || that_present_username) {
      if (!(this_present_username && that_present_username))
        return false;
      if (!this.username.equals(that.username))
        return false;
    }

    boolean this_present_email = true && this.isSetEmail();
    boolean that_present_email = true && that.isSetEmail();
    if (this_present_email || that_present_email) {
      if (!(this_present_email && that_present_email))
        return false;
      if (!this.email.equals(that.email))
        return false;
    }

    boolean this_present_password = true && this.isSetPassword();
    boolean that_present_password = true && that.isSetPassword();
    if (this_present_password || that_present_password) {
      if (!(this_present_password && that_present_password))
        return false;
      if (!this.password.equals(that.password))
        return false;
    }

    boolean this_present_contact = true && this.isSetContact();
    boolean that_present_contact = true && that.isSetContact();
    if (this_present_contact || that_present_contact) {
      if (!(this_present_contact && that_present_contact))
        return false;
      if (!this.contact.equals(that.contact))
        return false;
    }

    boolean this_present_headshot = true && this.isSetHeadshot();
    boolean that_present_headshot = true && that.isSetHeadshot();
    if (this_present_headshot || that_present_headshot) {
      if (!(this_present_headshot && that_present_headshot))
        return false;
      if (!this.headshot.equals(that.headshot))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetStudent_id()) ? 131071 : 524287);
    if (isSetStudent_id())
      hashCode = hashCode * 8191 + student_id.hashCode();

    hashCode = hashCode * 8191 + ((isSetUsername()) ? 131071 : 524287);
    if (isSetUsername())
      hashCode = hashCode * 8191 + username.hashCode();

    hashCode = hashCode * 8191 + ((isSetEmail()) ? 131071 : 524287);
    if (isSetEmail())
      hashCode = hashCode * 8191 + email.hashCode();

    hashCode = hashCode * 8191 + ((isSetPassword()) ? 131071 : 524287);
    if (isSetPassword())
      hashCode = hashCode * 8191 + password.hashCode();

    hashCode = hashCode * 8191 + ((isSetContact()) ? 131071 : 524287);
    if (isSetContact())
      hashCode = hashCode * 8191 + contact.hashCode();

    hashCode = hashCode * 8191 + ((isSetHeadshot()) ? 131071 : 524287);
    if (isSetHeadshot())
      hashCode = hashCode * 8191 + headshot.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(RegisterInfo other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.compare(isSetStudent_id(), other.isSetStudent_id());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetStudent_id()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.student_id, other.student_id);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.compare(isSetUsername(), other.isSetUsername());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUsername()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.username, other.username);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.compare(isSetEmail(), other.isSetEmail());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetEmail()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.email, other.email);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.compare(isSetPassword(), other.isSetPassword());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPassword()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.password, other.password);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.compare(isSetContact(), other.isSetContact());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetContact()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.contact, other.contact);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.compare(isSetHeadshot(), other.isSetHeadshot());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetHeadshot()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.headshot, other.headshot);
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
    StringBuilder sb = new StringBuilder("RegisterInfo(");
    boolean first = true;

    sb.append("student_id:");
    if (this.student_id == null) {
      sb.append("null");
    } else {
      sb.append(this.student_id);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("username:");
    if (this.username == null) {
      sb.append("null");
    } else {
      sb.append(this.username);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("email:");
    if (this.email == null) {
      sb.append("null");
    } else {
      sb.append(this.email);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("password:");
    if (this.password == null) {
      sb.append("null");
    } else {
      sb.append(this.password);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("contact:");
    if (this.contact == null) {
      sb.append("null");
    } else {
      sb.append(this.contact);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("headshot:");
    if (this.headshot == null) {
      sb.append("null");
    } else {
      org.apache.thrift.TBaseHelper.toString(this.headshot, sb);
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
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class RegisterInfoStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    @Override
    public RegisterInfoStandardScheme getScheme() {
      return new RegisterInfoStandardScheme();
    }
  }

  private static class RegisterInfoStandardScheme extends org.apache.thrift.scheme.StandardScheme<RegisterInfo> {

    @Override
    public void read(org.apache.thrift.protocol.TProtocol iprot, RegisterInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // STUDENT_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.student_id = iprot.readString();
              struct.setStudent_idIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // USERNAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.username = iprot.readString();
              struct.setUsernameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // EMAIL
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.email = iprot.readString();
              struct.setEmailIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // PASSWORD
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.password = iprot.readString();
              struct.setPasswordIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // CONTACT
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.contact = iprot.readString();
              struct.setContactIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // HEADSHOT
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.headshot = iprot.readBinary();
              struct.setHeadshotIsSet(true);
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
    public void write(org.apache.thrift.protocol.TProtocol oprot, RegisterInfo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.student_id != null) {
        oprot.writeFieldBegin(STUDENT_ID_FIELD_DESC);
        oprot.writeString(struct.student_id);
        oprot.writeFieldEnd();
      }
      if (struct.username != null) {
        oprot.writeFieldBegin(USERNAME_FIELD_DESC);
        oprot.writeString(struct.username);
        oprot.writeFieldEnd();
      }
      if (struct.email != null) {
        oprot.writeFieldBegin(EMAIL_FIELD_DESC);
        oprot.writeString(struct.email);
        oprot.writeFieldEnd();
      }
      if (struct.password != null) {
        oprot.writeFieldBegin(PASSWORD_FIELD_DESC);
        oprot.writeString(struct.password);
        oprot.writeFieldEnd();
      }
      if (struct.contact != null) {
        oprot.writeFieldBegin(CONTACT_FIELD_DESC);
        oprot.writeString(struct.contact);
        oprot.writeFieldEnd();
      }
      if (struct.headshot != null) {
        oprot.writeFieldBegin(HEADSHOT_FIELD_DESC);
        oprot.writeBinary(struct.headshot);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class RegisterInfoTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    @Override
    public RegisterInfoTupleScheme getScheme() {
      return new RegisterInfoTupleScheme();
    }
  }

  private static class RegisterInfoTupleScheme extends org.apache.thrift.scheme.TupleScheme<RegisterInfo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, RegisterInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetStudent_id()) {
        optionals.set(0);
      }
      if (struct.isSetUsername()) {
        optionals.set(1);
      }
      if (struct.isSetEmail()) {
        optionals.set(2);
      }
      if (struct.isSetPassword()) {
        optionals.set(3);
      }
      if (struct.isSetContact()) {
        optionals.set(4);
      }
      if (struct.isSetHeadshot()) {
        optionals.set(5);
      }
      oprot.writeBitSet(optionals, 6);
      if (struct.isSetStudent_id()) {
        oprot.writeString(struct.student_id);
      }
      if (struct.isSetUsername()) {
        oprot.writeString(struct.username);
      }
      if (struct.isSetEmail()) {
        oprot.writeString(struct.email);
      }
      if (struct.isSetPassword()) {
        oprot.writeString(struct.password);
      }
      if (struct.isSetContact()) {
        oprot.writeString(struct.contact);
      }
      if (struct.isSetHeadshot()) {
        oprot.writeBinary(struct.headshot);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, RegisterInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(6);
      if (incoming.get(0)) {
        struct.student_id = iprot.readString();
        struct.setStudent_idIsSet(true);
      }
      if (incoming.get(1)) {
        struct.username = iprot.readString();
        struct.setUsernameIsSet(true);
      }
      if (incoming.get(2)) {
        struct.email = iprot.readString();
        struct.setEmailIsSet(true);
      }
      if (incoming.get(3)) {
        struct.password = iprot.readString();
        struct.setPasswordIsSet(true);
      }
      if (incoming.get(4)) {
        struct.contact = iprot.readString();
        struct.setContactIsSet(true);
      }
      if (incoming.get(5)) {
        struct.headshot = iprot.readBinary();
        struct.setHeadshotIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

