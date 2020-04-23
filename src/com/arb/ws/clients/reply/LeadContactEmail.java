/**
 * LeadContactEmail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.arb.ws.clients.reply;

public class LeadContactEmail  implements java.io.Serializable, org.apache.axis.encoding.SimpleType {
    private java.lang.String _value;

    private com.arb.ws.clients.reply.LeadContactEmailPreferredContact preferredContact;  // attribute

    public LeadContactEmail() {
    }

    // Simple Types must have a String constructor
    public LeadContactEmail(java.lang.String _value) {
        this._value = _value;
    }
    // Simple Types must have a toString for serializing the value
    public java.lang.String toString() {
        return _value;
    }


    /**
     * Gets the _value value for this LeadContactEmail.
     * 
     * @return _value
     */
    public java.lang.String get_value() {
        return _value;
    }


    /**
     * Sets the _value value for this LeadContactEmail.
     * 
     * @param _value
     */
    public void set_value(java.lang.String _value) {
        this._value = _value;
    }


    /**
     * Gets the preferredContact value for this LeadContactEmail.
     * 
     * @return preferredContact
     */
    public com.arb.ws.clients.reply.LeadContactEmailPreferredContact getPreferredContact() {
        return preferredContact;
    }


    /**
     * Sets the preferredContact value for this LeadContactEmail.
     * 
     * @param preferredContact
     */
    public void setPreferredContact(com.arb.ws.clients.reply.LeadContactEmailPreferredContact preferredContact) {
        this.preferredContact = preferredContact;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof LeadContactEmail)) return false;
        LeadContactEmail other = (LeadContactEmail) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this._value==null && other.get_value()==null) || 
             (this._value!=null &&
              this._value.equals(other.get_value()))) &&
            ((this.preferredContact==null && other.getPreferredContact()==null) || 
             (this.preferredContact!=null &&
              this.preferredContact.equals(other.getPreferredContact())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (get_value() != null) {
            _hashCode += get_value().hashCode();
        }
        if (getPreferredContact() != null) {
            _hashCode += getPreferredContact().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(LeadContactEmail.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", ">>Lead>Contact>Email"));
        org.apache.axis.description.AttributeDesc attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("preferredContact");
        attrField.setXmlName(new javax.xml.namespace.QName("", "PreferredContact"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", ">>>Lead>Contact>Email>PreferredContact"));
        typeDesc.addFieldDesc(attrField);
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_value");
        elemField.setXmlName(new javax.xml.namespace.QName("", "_value"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.SimpleSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.SimpleDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}