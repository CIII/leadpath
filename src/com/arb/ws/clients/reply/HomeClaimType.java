/**
 * HomeClaimType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.arb.ws.clients.reply;

public class HomeClaimType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected HomeClaimType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _FireOrLightningDamage = "FireOrLightningDamage";
    public static final java.lang.String _WindstormOrHailDamage = "WindstormOrHailDamage";
    public static final java.lang.String _IceSnowSleetDamage = "IceSnowSleetDamage";
    public static final java.lang.String _SmokeDamage = "SmokeDamage";
    public static final java.lang.String _WaterDamage = "WaterDamage";
    public static final java.lang.String _Vandalism = "Vandalism";
    public static final java.lang.String _Theft = "Theft";
    public static final java.lang.String _Other = "Other";
    public static final HomeClaimType FireOrLightningDamage = new HomeClaimType(_FireOrLightningDamage);
    public static final HomeClaimType WindstormOrHailDamage = new HomeClaimType(_WindstormOrHailDamage);
    public static final HomeClaimType IceSnowSleetDamage = new HomeClaimType(_IceSnowSleetDamage);
    public static final HomeClaimType SmokeDamage = new HomeClaimType(_SmokeDamage);
    public static final HomeClaimType WaterDamage = new HomeClaimType(_WaterDamage);
    public static final HomeClaimType Vandalism = new HomeClaimType(_Vandalism);
    public static final HomeClaimType Theft = new HomeClaimType(_Theft);
    public static final HomeClaimType Other = new HomeClaimType(_Other);
    public java.lang.String getValue() { return _value_;}
    public static HomeClaimType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        HomeClaimType enumeration = (HomeClaimType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static HomeClaimType fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(HomeClaimType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "HomeClaimType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
