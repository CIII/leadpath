/**
 * VehiclePrimaryUse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.arb.ws.clients.reply;

public class VehiclePrimaryUse implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected VehiclePrimaryUse(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _CommuteToWork = "CommuteToWork";
    public static final java.lang.String _CommuteToSchool = "CommuteToSchool";
    public static final java.lang.String _CommuteVaries = "CommuteVaries";
    public static final java.lang.String _Pleasure = "Pleasure";
    public static final java.lang.String _Business = "Business";
    public static final VehiclePrimaryUse CommuteToWork = new VehiclePrimaryUse(_CommuteToWork);
    public static final VehiclePrimaryUse CommuteToSchool = new VehiclePrimaryUse(_CommuteToSchool);
    public static final VehiclePrimaryUse CommuteVaries = new VehiclePrimaryUse(_CommuteVaries);
    public static final VehiclePrimaryUse Pleasure = new VehiclePrimaryUse(_Pleasure);
    public static final VehiclePrimaryUse Business = new VehiclePrimaryUse(_Business);
    public java.lang.String getValue() { return _value_;}
    public static VehiclePrimaryUse fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        VehiclePrimaryUse enumeration = (VehiclePrimaryUse)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static VehiclePrimaryUse fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(VehiclePrimaryUse.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "VehiclePrimaryUse"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
