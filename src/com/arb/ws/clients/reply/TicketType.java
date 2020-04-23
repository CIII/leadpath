/**
 * TicketType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.arb.ws.clients.reply;

public class TicketType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected TicketType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _CarelessDriving = "CarelessDriving";
    public static final java.lang.String _DefectiveEquipment = "DefectiveEquipment";
    public static final java.lang.String _FailureToObeySignal = "FailureToObeySignal";
    public static final java.lang.String _SpeedingViolation = "SpeedingViolation";
    public static final java.lang.String _Other = "Other";
    public static final TicketType CarelessDriving = new TicketType(_CarelessDriving);
    public static final TicketType DefectiveEquipment = new TicketType(_DefectiveEquipment);
    public static final TicketType FailureToObeySignal = new TicketType(_FailureToObeySignal);
    public static final TicketType SpeedingViolation = new TicketType(_SpeedingViolation);
    public static final TicketType Other = new TicketType(_Other);
    public java.lang.String getValue() { return _value_;}
    public static TicketType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        TicketType enumeration = (TicketType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static TicketType fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(TicketType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "TicketType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
