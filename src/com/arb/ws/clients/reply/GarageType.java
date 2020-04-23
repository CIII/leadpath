/**
 * GarageType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.arb.ws.clients.reply;

public class GarageType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected GarageType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _Attached1Car = "Attached1Car";
    public static final java.lang.String _Attached2Car = "Attached2Car";
    public static final java.lang.String _Attached3Car = "Attached3Car";
    public static final java.lang.String _AttachedCarport = "AttachedCarport";
    public static final java.lang.String _Detached1Car = "Detached1Car";
    public static final java.lang.String _Detached2Car = "Detached2Car";
    public static final java.lang.String _Detached3Car = "Detached3Car";
    public static final java.lang.String _DetachedCarport = "DetachedCarport";
    public static final java.lang.String _NoGarage = "NoGarage";
    public static final java.lang.String _Other = "Other";
    public static final java.lang.String _NotApplicable = "NotApplicable";
    public static final GarageType Attached1Car = new GarageType(_Attached1Car);
    public static final GarageType Attached2Car = new GarageType(_Attached2Car);
    public static final GarageType Attached3Car = new GarageType(_Attached3Car);
    public static final GarageType AttachedCarport = new GarageType(_AttachedCarport);
    public static final GarageType Detached1Car = new GarageType(_Detached1Car);
    public static final GarageType Detached2Car = new GarageType(_Detached2Car);
    public static final GarageType Detached3Car = new GarageType(_Detached3Car);
    public static final GarageType DetachedCarport = new GarageType(_DetachedCarport);
    public static final GarageType NoGarage = new GarageType(_NoGarage);
    public static final GarageType Other = new GarageType(_Other);
    public static final GarageType NotApplicable = new GarageType(_NotApplicable);
    public java.lang.String getValue() { return _value_;}
    public static GarageType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        GarageType enumeration = (GarageType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static GarageType fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(GarageType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "GarageType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
