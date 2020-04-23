/**
 * HeatingCoolingSystem.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.arb.ws.clients.reply;

public class HeatingCoolingSystem implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected HeatingCoolingSystem(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _Gas = "Gas";
    public static final java.lang.String _Electric = "Electric";
    public static final java.lang.String _HotWaterSteamRadiator = "HotWaterSteamRadiator";
    public static final java.lang.String _OilOrCoalOrKerosene = "OilOrCoalOrKerosene";
    public static final java.lang.String _Propane = "Propane";
    public static final java.lang.String _Stove = "Stove";
    public static final java.lang.String _NotApplicable = "NotApplicable";
    public static final HeatingCoolingSystem Gas = new HeatingCoolingSystem(_Gas);
    public static final HeatingCoolingSystem Electric = new HeatingCoolingSystem(_Electric);
    public static final HeatingCoolingSystem HotWaterSteamRadiator = new HeatingCoolingSystem(_HotWaterSteamRadiator);
    public static final HeatingCoolingSystem OilOrCoalOrKerosene = new HeatingCoolingSystem(_OilOrCoalOrKerosene);
    public static final HeatingCoolingSystem Propane = new HeatingCoolingSystem(_Propane);
    public static final HeatingCoolingSystem Stove = new HeatingCoolingSystem(_Stove);
    public static final HeatingCoolingSystem NotApplicable = new HeatingCoolingSystem(_NotApplicable);
    public java.lang.String getValue() { return _value_;}
    public static HeatingCoolingSystem fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        HeatingCoolingSystem enumeration = (HeatingCoolingSystem)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static HeatingCoolingSystem fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(HeatingCoolingSystem.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "HeatingCoolingSystem"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
