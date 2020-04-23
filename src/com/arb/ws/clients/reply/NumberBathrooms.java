/**
 * NumberBathrooms.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.arb.ws.clients.reply;

public class NumberBathrooms implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected NumberBathrooms(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _One = "One";
    public static final java.lang.String _OneAndHalf = "OneAndHalf";
    public static final java.lang.String _Two = "Two";
    public static final java.lang.String _TwoAndHalf = "TwoAndHalf";
    public static final java.lang.String _Three = "Three";
    public static final java.lang.String _ThreeAndHalf = "ThreeAndHalf";
    public static final java.lang.String _FourPlus = "FourPlus";
    public static final java.lang.String _NotApplicable = "NotApplicable";
    public static final NumberBathrooms One = new NumberBathrooms(_One);
    public static final NumberBathrooms OneAndHalf = new NumberBathrooms(_OneAndHalf);
    public static final NumberBathrooms Two = new NumberBathrooms(_Two);
    public static final NumberBathrooms TwoAndHalf = new NumberBathrooms(_TwoAndHalf);
    public static final NumberBathrooms Three = new NumberBathrooms(_Three);
    public static final NumberBathrooms ThreeAndHalf = new NumberBathrooms(_ThreeAndHalf);
    public static final NumberBathrooms FourPlus = new NumberBathrooms(_FourPlus);
    public static final NumberBathrooms NotApplicable = new NumberBathrooms(_NotApplicable);
    public java.lang.String getValue() { return _value_;}
    public static NumberBathrooms fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        NumberBathrooms enumeration = (NumberBathrooms)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static NumberBathrooms fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(NumberBathrooms.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "NumberBathrooms"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
