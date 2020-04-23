/**
 * RoofingMaterial.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.arb.ws.clients.reply;

public class RoofingMaterial implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected RoofingMaterial(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _AsphaltShingle = "AsphaltShingle";
    public static final java.lang.String _WoodShingle = "WoodShingle";
    public static final java.lang.String _Tile = "Tile";
    public static final java.lang.String _Concrete = "Concrete";
    public static final java.lang.String _Other = "Other";
    public static final java.lang.String _NotApplicable = "NotApplicable";
    public static final RoofingMaterial AsphaltShingle = new RoofingMaterial(_AsphaltShingle);
    public static final RoofingMaterial WoodShingle = new RoofingMaterial(_WoodShingle);
    public static final RoofingMaterial Tile = new RoofingMaterial(_Tile);
    public static final RoofingMaterial Concrete = new RoofingMaterial(_Concrete);
    public static final RoofingMaterial Other = new RoofingMaterial(_Other);
    public static final RoofingMaterial NotApplicable = new RoofingMaterial(_NotApplicable);
    public java.lang.String getValue() { return _value_;}
    public static RoofingMaterial fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        RoofingMaterial enumeration = (RoofingMaterial)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static RoofingMaterial fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(RoofingMaterial.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "RoofingMaterial"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
