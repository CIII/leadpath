/**
 * NumberStories.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.arb.ws.clients.reply;

public class NumberStories implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected NumberStories(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _OneStory = "OneStory";
    public static final java.lang.String _BiLevel = "BiLevel";
    public static final java.lang.String _TwoStory = "TwoStory";
    public static final java.lang.String _TriLevel = "TriLevel";
    public static final java.lang.String _Other = "Other";
    public static final java.lang.String _NotApplicable = "NotApplicable";
    public static final NumberStories OneStory = new NumberStories(_OneStory);
    public static final NumberStories BiLevel = new NumberStories(_BiLevel);
    public static final NumberStories TwoStory = new NumberStories(_TwoStory);
    public static final NumberStories TriLevel = new NumberStories(_TriLevel);
    public static final NumberStories Other = new NumberStories(_Other);
    public static final NumberStories NotApplicable = new NumberStories(_NotApplicable);
    public java.lang.String getValue() { return _value_;}
    public static NumberStories fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        NumberStories enumeration = (NumberStories)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static NumberStories fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(NumberStories.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "NumberStories"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
