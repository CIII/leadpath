/**
 * MilitaryStatus.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.arb.ws.clients.reply;

public class MilitaryStatus implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected MilitaryStatus(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _None = "None";
    public static final java.lang.String _ActiveDuty = "ActiveDuty";
    public static final java.lang.String _SelectiveReserve = "SelectiveReserve";
    public static final java.lang.String _SpouseOfADorSR = "SpouseOfADorSR";
    public static final java.lang.String _Veteran = "Veteran";
    public static final java.lang.String _Civilian = "Civilian";
    public static final MilitaryStatus None = new MilitaryStatus(_None);
    public static final MilitaryStatus ActiveDuty = new MilitaryStatus(_ActiveDuty);
    public static final MilitaryStatus SelectiveReserve = new MilitaryStatus(_SelectiveReserve);
    public static final MilitaryStatus SpouseOfADorSR = new MilitaryStatus(_SpouseOfADorSR);
    public static final MilitaryStatus Veteran = new MilitaryStatus(_Veteran);
    public static final MilitaryStatus Civilian = new MilitaryStatus(_Civilian);
    public java.lang.String getValue() { return _value_;}
    public static MilitaryStatus fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        MilitaryStatus enumeration = (MilitaryStatus)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static MilitaryStatus fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(MilitaryStatus.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "MilitaryStatus"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
