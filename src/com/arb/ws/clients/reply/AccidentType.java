/**
 * AccidentType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.arb.ws.clients.reply;

public class AccidentType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected AccidentType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _VehicleHitVehicle = "VehicleHitVehicle";
    public static final java.lang.String _VehicleHitPedestrian = "VehicleHitPedestrian";
    public static final java.lang.String _VehicleHitProperty = "VehicleHitProperty";
    public static final java.lang.String _VehicleAvoidingAccident = "VehicleAvoidingAccident";
    public static final java.lang.String _OtherVehicleHitYours = "OtherVehicleHitYours";
    public static final java.lang.String _AtFaultNotListed = "AtFaultNotListed";
    public static final java.lang.String _NotAtFaultNotListed = "NotAtFaultNotListed";
    public static final AccidentType VehicleHitVehicle = new AccidentType(_VehicleHitVehicle);
    public static final AccidentType VehicleHitPedestrian = new AccidentType(_VehicleHitPedestrian);
    public static final AccidentType VehicleHitProperty = new AccidentType(_VehicleHitProperty);
    public static final AccidentType VehicleAvoidingAccident = new AccidentType(_VehicleAvoidingAccident);
    public static final AccidentType OtherVehicleHitYours = new AccidentType(_OtherVehicleHitYours);
    public static final AccidentType AtFaultNotListed = new AccidentType(_AtFaultNotListed);
    public static final AccidentType NotAtFaultNotListed = new AccidentType(_NotAtFaultNotListed);
    public java.lang.String getValue() { return _value_;}
    public static AccidentType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        AccidentType enumeration = (AccidentType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static AccidentType fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(AccidentType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "AccidentType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
