/**
 * RelationToApplicant.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.arb.ws.clients.reply;

public class RelationToApplicant implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected RelationToApplicant(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _Self = "Self";
    public static final java.lang.String _Spouse = "Spouse";
    public static final java.lang.String _Parent = "Parent";
    public static final java.lang.String _Child = "Child";
    public static final java.lang.String _Sibling = "Sibling";
    public static final java.lang.String _Grandparent = "Grandparent";
    public static final java.lang.String _Grandchild = "Grandchild";
    public static final java.lang.String _Other = "Other";
    public static final RelationToApplicant Self = new RelationToApplicant(_Self);
    public static final RelationToApplicant Spouse = new RelationToApplicant(_Spouse);
    public static final RelationToApplicant Parent = new RelationToApplicant(_Parent);
    public static final RelationToApplicant Child = new RelationToApplicant(_Child);
    public static final RelationToApplicant Sibling = new RelationToApplicant(_Sibling);
    public static final RelationToApplicant Grandparent = new RelationToApplicant(_Grandparent);
    public static final RelationToApplicant Grandchild = new RelationToApplicant(_Grandchild);
    public static final RelationToApplicant Other = new RelationToApplicant(_Other);
    public java.lang.String getValue() { return _value_;}
    public static RelationToApplicant fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        RelationToApplicant enumeration = (RelationToApplicant)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static RelationToApplicant fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(RelationToApplicant.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "RelationToApplicant"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
