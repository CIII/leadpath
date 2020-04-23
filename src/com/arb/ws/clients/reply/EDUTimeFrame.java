/**
 * EDUTimeFrame.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.arb.ws.clients.reply;

public class EDUTimeFrame implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected EDUTimeFrame(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _None = "None";
    public static final java.lang.String _LessThanOneMonth = "LessThanOneMonth";
    public static final java.lang.String _OnetoThreeMonths = "OnetoThreeMonths";
    public static final java.lang.String _FourToSixMonths = "FourToSixMonths";
    public static final java.lang.String _MoreThanSixMonths = "MoreThanSixMonths";
    public static final EDUTimeFrame None = new EDUTimeFrame(_None);
    public static final EDUTimeFrame LessThanOneMonth = new EDUTimeFrame(_LessThanOneMonth);
    public static final EDUTimeFrame OnetoThreeMonths = new EDUTimeFrame(_OnetoThreeMonths);
    public static final EDUTimeFrame FourToSixMonths = new EDUTimeFrame(_FourToSixMonths);
    public static final EDUTimeFrame MoreThanSixMonths = new EDUTimeFrame(_MoreThanSixMonths);
    public java.lang.String getValue() { return _value_;}
    public static EDUTimeFrame fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        EDUTimeFrame enumeration = (EDUTimeFrame)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static EDUTimeFrame fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(EDUTimeFrame.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "EDUTimeFrame"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
