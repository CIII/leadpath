/**
 * InsurancePropertyPropertyType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.arb.ws.clients.reply;

public class InsurancePropertyPropertyType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected InsurancePropertyPropertyType(java.lang.String value) {
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
    public static final InsurancePropertyPropertyType SingleFamily = new InsurancePropertyPropertyType(_SingleFamily);
    public static final InsurancePropertyPropertyType MultiFamily = new InsurancePropertyPropertyType(_MultiFamily);
    public static final InsurancePropertyPropertyType Retail = new InsurancePropertyPropertyType(_Retail);
    public static final InsurancePropertyPropertyType Commercial = new InsurancePropertyPropertyType(_Commercial);
    public static final InsurancePropertyPropertyType Other = new InsurancePropertyPropertyType(_Other);
    public static final InsurancePropertyPropertyType Condominium = new InsurancePropertyPropertyType(_Condominium);
    public static final InsurancePropertyPropertyType Apartment = new InsurancePropertyPropertyType(_Apartment);
    public static final InsurancePropertyPropertyType Duplex = new InsurancePropertyPropertyType(_Duplex);
    public static final InsurancePropertyPropertyType TownHome = new InsurancePropertyPropertyType(_TownHome);
    public static final InsurancePropertyPropertyType MobileHome = new InsurancePropertyPropertyType(_MobileHome);
    public static final InsurancePropertyPropertyType SingleFamilyDetached = new InsurancePropertyPropertyType(_SingleFamilyDetached);
    public static final InsurancePropertyPropertyType Rental = new InsurancePropertyPropertyType(_Rental);
    public static final InsurancePropertyPropertyType Loft = new InsurancePropertyPropertyType(_Loft);
    public static final InsurancePropertyPropertyType MixedUse = new InsurancePropertyPropertyType(_MixedUse);
    public static final InsurancePropertyPropertyType Cooperative = new InsurancePropertyPropertyType(_Cooperative);
    public static final InsurancePropertyPropertyType VacationHome = new InsurancePropertyPropertyType(_VacationHome);
    public static final InsurancePropertyPropertyType ManufacturedHouse = new InsurancePropertyPropertyType(_ManufacturedHouse);
    public java.lang.String getValue() { return _value_;}
    public static InsurancePropertyPropertyType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        InsurancePropertyPropertyType enumeration = (InsurancePropertyPropertyType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static InsurancePropertyPropertyType fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(InsurancePropertyPropertyType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", ">InsuranceProperty>PropertyType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
