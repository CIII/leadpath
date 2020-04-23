/**
 * PropertyType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.arb.ws.clients.reply;

public class PropertyType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected PropertyType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _SingleFamily = "SingleFamily";
    public static final java.lang.String _MultiFamily = "MultiFamily";
    public static final java.lang.String _Retail = "Retail";
    public static final java.lang.String _Commercial = "Commercial";
    public static final java.lang.String _Other = "Other";
    public static final java.lang.String _Condominium = "Condominium";
    public static final java.lang.String _Apartment = "Apartment";
    public static final java.lang.String _Duplex = "Duplex";
    public static final java.lang.String _TownHome = "TownHome";
    public static final java.lang.String _MobileHome = "MobileHome";
    public static final java.lang.String _SingleFamilyDetached = "SingleFamilyDetached";
    public static final java.lang.String _Rental = "Rental";
    public static final java.lang.String _Loft = "Loft";
    public static final java.lang.String _MixedUse = "MixedUse";
    public static final java.lang.String _Cooperative = "Cooperative";
    public static final java.lang.String _VacationHome = "VacationHome";
    public static final java.lang.String _ManufacturedHouse = "ManufacturedHouse";
    public static final PropertyType SingleFamily = new PropertyType(_SingleFamily);
    public static final PropertyType MultiFamily = new PropertyType(_MultiFamily);
    public static final PropertyType Retail = new PropertyType(_Retail);
    public static final PropertyType Commercial = new PropertyType(_Commercial);
    public static final PropertyType Other = new PropertyType(_Other);
    public static final PropertyType Condominium = new PropertyType(_Condominium);
    public static final PropertyType Apartment = new PropertyType(_Apartment);
    public static final PropertyType Duplex = new PropertyType(_Duplex);
    public static final PropertyType TownHome = new PropertyType(_TownHome);
    public static final PropertyType MobileHome = new PropertyType(_MobileHome);
    public static final PropertyType SingleFamilyDetached = new PropertyType(_SingleFamilyDetached);
    public static final PropertyType Rental = new PropertyType(_Rental);
    public static final PropertyType Loft = new PropertyType(_Loft);
    public static final PropertyType MixedUse = new PropertyType(_MixedUse);
    public static final PropertyType Cooperative = new PropertyType(_Cooperative);
    public static final PropertyType VacationHome = new PropertyType(_VacationHome);
    public static final PropertyType ManufacturedHouse = new PropertyType(_ManufacturedHouse);
    public java.lang.String getValue() { return _value_;}
    public static PropertyType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        PropertyType enumeration = (PropertyType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static PropertyType fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(PropertyType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", ">Property>Type"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
