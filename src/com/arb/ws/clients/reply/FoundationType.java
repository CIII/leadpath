/**
 * FoundationType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.arb.ws.clients.reply;

public class FoundationType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected FoundationType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _BasementFullyFinished = "BasementFullyFinished";
    public static final java.lang.String _BasementHalfFinished = "BasementHalfFinished";
    public static final java.lang.String _BasementUnfinished = "BasementUnfinished";
    public static final java.lang.String _CrawlSpace = "CrawlSpace";
    public static final java.lang.String _Slab = "Slab";
    public static final java.lang.String _Other = "Other";
    public static final java.lang.String _NotApplicable = "NotApplicable";
    public static final FoundationType BasementFullyFinished = new FoundationType(_BasementFullyFinished);
    public static final FoundationType BasementHalfFinished = new FoundationType(_BasementHalfFinished);
    public static final FoundationType BasementUnfinished = new FoundationType(_BasementUnfinished);
    public static final FoundationType CrawlSpace = new FoundationType(_CrawlSpace);
    public static final FoundationType Slab = new FoundationType(_Slab);
    public static final FoundationType Other = new FoundationType(_Other);
    public static final FoundationType NotApplicable = new FoundationType(_NotApplicable);
    public java.lang.String getValue() { return _value_;}
    public static FoundationType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        FoundationType enumeration = (FoundationType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static FoundationType fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(FoundationType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "FoundationType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
