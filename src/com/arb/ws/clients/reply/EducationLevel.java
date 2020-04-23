/**
 * EducationLevel.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.arb.ws.clients.reply;

public class EducationLevel implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected EducationLevel(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _SomeOrNoHighSchool = "SomeOrNoHighSchool";
    public static final java.lang.String _HighSchoolDiploma = "HighSchoolDiploma";
    public static final java.lang.String _GED = "GED";
    public static final java.lang.String _SomeCollege = "SomeCollege";
    public static final java.lang.String _AssociatesDegree = "AssociatesDegree";
    public static final java.lang.String _BachelorsDegree = "BachelorsDegree";
    public static final java.lang.String _MastersDegree = "MastersDegree";
    public static final java.lang.String _DoctorateDegree = "DoctorateDegree";
    public static final java.lang.String _OtherProfessionalDegree = "OtherProfessionalDegree";
    public static final java.lang.String _OtherNonProfessionalDegree = "OtherNonProfessionalDegree";
    public static final java.lang.String _TradeOrVocationalSchool = "TradeOrVocationalSchool";
    public static final EducationLevel SomeOrNoHighSchool = new EducationLevel(_SomeOrNoHighSchool);
    public static final EducationLevel HighSchoolDiploma = new EducationLevel(_HighSchoolDiploma);
    public static final EducationLevel GED = new EducationLevel(_GED);
    public static final EducationLevel SomeCollege = new EducationLevel(_SomeCollege);
    public static final EducationLevel AssociatesDegree = new EducationLevel(_AssociatesDegree);
    public static final EducationLevel BachelorsDegree = new EducationLevel(_BachelorsDegree);
    public static final EducationLevel MastersDegree = new EducationLevel(_MastersDegree);
    public static final EducationLevel DoctorateDegree = new EducationLevel(_DoctorateDegree);
    public static final EducationLevel OtherProfessionalDegree = new EducationLevel(_OtherProfessionalDegree);
    public static final EducationLevel OtherNonProfessionalDegree = new EducationLevel(_OtherNonProfessionalDegree);
    public static final EducationLevel TradeOrVocationalSchool = new EducationLevel(_TradeOrVocationalSchool);
    public java.lang.String getValue() { return _value_;}
    public static EducationLevel fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        EducationLevel enumeration = (EducationLevel)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static EducationLevel fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(EducationLevel.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "EducationLevel"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
