/**
 * HighestDegree.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.arb.ws.clients.reply;

public class HighestDegree implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected HighestDegree(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _None = "None";
    public static final java.lang.String _GED = "GED";
    public static final java.lang.String _HighSchool = "HighSchool";
    public static final java.lang.String _SomeCollegeCredits = "SomeCollegeCredits";
    public static final java.lang.String _AssociatesDegree = "AssociatesDegree";
    public static final java.lang.String _BachelorsDegree = "BachelorsDegree";
    public static final java.lang.String _MastersDegree = "MastersDegree";
    public static final java.lang.String _DoctorateDegree = "DoctorateDegree";
    public static final HighestDegree None = new HighestDegree(_None);
    public static final HighestDegree GED = new HighestDegree(_GED);
    public static final HighestDegree HighSchool = new HighestDegree(_HighSchool);
    public static final HighestDegree SomeCollegeCredits = new HighestDegree(_SomeCollegeCredits);
    public static final HighestDegree AssociatesDegree = new HighestDegree(_AssociatesDegree);
    public static final HighestDegree BachelorsDegree = new HighestDegree(_BachelorsDegree);
    public static final HighestDegree MastersDegree = new HighestDegree(_MastersDegree);
    public static final HighestDegree DoctorateDegree = new HighestDegree(_DoctorateDegree);
    public java.lang.String getValue() { return _value_;}
    public static HighestDegree fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        HighestDegree enumeration = (HighestDegree)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static HighestDegree fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(HighestDegree.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "HighestDegree"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
