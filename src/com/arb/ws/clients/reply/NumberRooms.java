/**
 * NumberRooms.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.arb.ws.clients.reply;

public class NumberRooms implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected NumberRooms(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _One = "One";
    public static final java.lang.String _Two = "Two";
    public static final java.lang.String _Three = "Three";
    public static final java.lang.String _Four = "Four";
    public static final java.lang.String _Five = "Five";
    public static final java.lang.String _Six = "Six";
    public static final java.lang.String _Seven = "Seven";
    public static final java.lang.String _Eight = "Eight";
    public static final java.lang.String _Nine = "Nine";
    public static final java.lang.String _TenPlus = "TenPlus";
    public static final java.lang.String _NotApplicable = "NotApplicable";
    public static final NumberRooms One = new NumberRooms(_One);
    public static final NumberRooms Two = new NumberRooms(_Two);
    public static final NumberRooms Three = new NumberRooms(_Three);
    public static final NumberRooms Four = new NumberRooms(_Four);
    public static final NumberRooms Five = new NumberRooms(_Five);
    public static final NumberRooms Six = new NumberRooms(_Six);
    public static final NumberRooms Seven = new NumberRooms(_Seven);
    public static final NumberRooms Eight = new NumberRooms(_Eight);
    public static final NumberRooms Nine = new NumberRooms(_Nine);
    public static final NumberRooms TenPlus = new NumberRooms(_TenPlus);
    public static final NumberRooms NotApplicable = new NumberRooms(_NotApplicable);
    public java.lang.String getValue() { return _value_;}
    public static NumberRooms fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        NumberRooms enumeration = (NumberRooms)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static NumberRooms fromString(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumSerializer(
            _javaType, _xmlType);
    }
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumDeserializer(
            _javaType, _xmlType);
    }
    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(NumberRooms.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "NumberRooms"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
