/**
 * LeadContactPhoneTime.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.arb.ws.clients.reply;

public class LeadContactPhoneTime implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected LeadContactPhoneTime(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _Morning = "Morning";
    public static final java.lang.String _Afternoon = "Afternoon";
    public static final java.lang.String _Evening = "Evening";
    public static final java.lang.String _NoPreference = "NoPreference";
    public static final java.lang.String _Day = "Day";
    public static final LeadContactPhoneTime Morning = new LeadContactPhoneTime(_Morning);
    public static final LeadContactPhoneTime Afternoon = new LeadContactPhoneTime(_Afternoon);
    public static final LeadContactPhoneTime Evening = new LeadContactPhoneTime(_Evening);
    public static final LeadContactPhoneTime NoPreference = new LeadContactPhoneTime(_NoPreference);
    public static final LeadContactPhoneTime Day = new LeadContactPhoneTime(_Day);
    public java.lang.String getValue() { return _value_;}
    public static LeadContactPhoneTime fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        LeadContactPhoneTime enumeration = (LeadContactPhoneTime)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static LeadContactPhoneTime fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(LeadContactPhoneTime.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", ">>>Lead>Contact>Phone>Time"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
