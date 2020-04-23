/**
 * DegreeType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.arb.ws.clients.reply;

public class DegreeType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected DegreeType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _Unknown = "Unknown";
    public static final java.lang.String _AssociatesDegrees = "AssociatesDegrees";
    public static final java.lang.String _BachelorsDegrees = "BachelorsDegrees";
    public static final java.lang.String _CertificatePrograms = "CertificatePrograms";
    public static final java.lang.String _DoctoralDegrees = "DoctoralDegrees";
    public static final java.lang.String _MastersDegrees = "MastersDegrees";
    public static final DegreeType Unknown = new DegreeType(_Unknown);
    public static final DegreeType AssociatesDegrees = new DegreeType(_AssociatesDegrees);
    public static final DegreeType BachelorsDegrees = new DegreeType(_BachelorsDegrees);
    public static final DegreeType CertificatePrograms = new DegreeType(_CertificatePrograms);
    public static final DegreeType DoctoralDegrees = new DegreeType(_DoctoralDegrees);
    public static final DegreeType MastersDegrees = new DegreeType(_MastersDegrees);
    public java.lang.String getValue() { return _value_;}
    public static DegreeType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        DegreeType enumeration = (DegreeType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static DegreeType fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(DegreeType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "DegreeType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
