/**
 * RealEstateLeadPropertyType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.arb.ws.clients.reply;

public class RealEstateLeadPropertyType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected RealEstateLeadPropertyType(java.lang.String value) {
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
    public static final RealEstateLeadPropertyType SingleFamily = new RealEstateLeadPropertyType(_SingleFamily);
    public static final RealEstateLeadPropertyType MultiFamily = new RealEstateLeadPropertyType(_MultiFamily);
    public static final RealEstateLeadPropertyType Retail = new RealEstateLeadPropertyType(_Retail);
    public static final RealEstateLeadPropertyType Commercial = new RealEstateLeadPropertyType(_Commercial);
    public static final RealEstateLeadPropertyType Other = new RealEstateLeadPropertyType(_Other);
    public static final RealEstateLeadPropertyType Condominium = new RealEstateLeadPropertyType(_Condominium);
    public static final RealEstateLeadPropertyType Apartment = new RealEstateLeadPropertyType(_Apartment);
    public static final RealEstateLeadPropertyType Duplex = new RealEstateLeadPropertyType(_Duplex);
    public static final RealEstateLeadPropertyType TownHome = new RealEstateLeadPropertyType(_TownHome);
    public static final RealEstateLeadPropertyType MobileHome = new RealEstateLeadPropertyType(_MobileHome);
    public static final RealEstateLeadPropertyType SingleFamilyDetached = new RealEstateLeadPropertyType(_SingleFamilyDetached);
    public static final RealEstateLeadPropertyType Rental = new RealEstateLeadPropertyType(_Rental);
    public static final RealEstateLeadPropertyType Loft = new RealEstateLeadPropertyType(_Loft);
    public static final RealEstateLeadPropertyType MixedUse = new RealEstateLeadPropertyType(_MixedUse);
    public static final RealEstateLeadPropertyType Cooperative = new RealEstateLeadPropertyType(_Cooperative);
    public static final RealEstateLeadPropertyType VacationHome = new RealEstateLeadPropertyType(_VacationHome);
    public static final RealEstateLeadPropertyType ManufacturedHouse = new RealEstateLeadPropertyType(_ManufacturedHouse);
    public java.lang.String getValue() { return _value_;}
    public static RealEstateLeadPropertyType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        RealEstateLeadPropertyType enumeration = (RealEstateLeadPropertyType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static RealEstateLeadPropertyType fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(RealEstateLeadPropertyType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", ">RealEstateLead>PropertyType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
