/**
 * MilitaryBranch.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.arb.ws.clients.reply;

public class MilitaryBranch implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected MilitaryBranch(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _None = "None";
    public static final java.lang.String _USArmy = "USArmy";
    public static final java.lang.String _USNavy = "USNavy";
    public static final java.lang.String _USAirForce = "USAirForce";
    public static final java.lang.String _USMarineCorps = "USMarineCorps";
    public static final java.lang.String _USCoastGuard = "USCoastGuard";
    public static final MilitaryBranch None = new MilitaryBranch(_None);
    public static final MilitaryBranch USArmy = new MilitaryBranch(_USArmy);
    public static final MilitaryBranch USNavy = new MilitaryBranch(_USNavy);
    public static final MilitaryBranch USAirForce = new MilitaryBranch(_USAirForce);
    public static final MilitaryBranch USMarineCorps = new MilitaryBranch(_USMarineCorps);
    public static final MilitaryBranch USCoastGuard = new MilitaryBranch(_USCoastGuard);
    public java.lang.String getValue() { return _value_;}
    public static MilitaryBranch fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        MilitaryBranch enumeration = (MilitaryBranch)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static MilitaryBranch fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(MilitaryBranch.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "MilitaryBranch"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
