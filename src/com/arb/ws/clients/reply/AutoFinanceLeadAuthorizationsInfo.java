/**
 * AutoFinanceLeadAuthorizationsInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.arb.ws.clients.reply;

public class AutoFinanceLeadAuthorizationsInfo  implements java.io.Serializable {
    private boolean creditCheck;

    private boolean forwrdApplication;

    private boolean specialOffers;

    public AutoFinanceLeadAuthorizationsInfo() {
    }

    public AutoFinanceLeadAuthorizationsInfo(
           boolean creditCheck,
           boolean forwrdApplication,
           boolean specialOffers) {
           this.creditCheck = creditCheck;
           this.forwrdApplication = forwrdApplication;
           this.specialOffers = specialOffers;
    }


    /**
     * Gets the creditCheck value for this AutoFinanceLeadAuthorizationsInfo.
     * 
     * @return creditCheck
     */
    public boolean isCreditCheck() {
        return creditCheck;
    }


    /**
     * Sets the creditCheck value for this AutoFinanceLeadAuthorizationsInfo.
     * 
     * @param creditCheck
     */
    public void setCreditCheck(boolean creditCheck) {
        this.creditCheck = creditCheck;
    }


    /**
     * Gets the forwrdApplication value for this AutoFinanceLeadAuthorizationsInfo.
     * 
     * @return forwrdApplication
     */
    public boolean isForwrdApplication() {
        return forwrdApplication;
    }


    /**
     * Sets the forwrdApplication value for this AutoFinanceLeadAuthorizationsInfo.
     * 
     * @param forwrdApplication
     */
    public void setForwrdApplication(boolean forwrdApplication) {
        this.forwrdApplication = forwrdApplication;
    }


    /**
     * Gets the specialOffers value for this AutoFinanceLeadAuthorizationsInfo.
     * 
     * @return specialOffers
     */
    public boolean isSpecialOffers() {
        return specialOffers;
    }


    /**
     * Sets the specialOffers value for this AutoFinanceLeadAuthorizationsInfo.
     * 
     * @param specialOffers
     */
    public void setSpecialOffers(boolean specialOffers) {
        this.specialOffers = specialOffers;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AutoFinanceLeadAuthorizationsInfo)) return false;
        AutoFinanceLeadAuthorizationsInfo other = (AutoFinanceLeadAuthorizationsInfo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.creditCheck == other.isCreditCheck() &&
            this.forwrdApplication == other.isForwrdApplication() &&
            this.specialOffers == other.isSpecialOffers();
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
        _hashCode += (isCreditCheck() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isForwrdApplication() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isSpecialOffers() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AutoFinanceLeadAuthorizationsInfo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", ">AutoFinanceLead>AuthorizationsInfo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("creditCheck");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "CreditCheck"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("forwrdApplication");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "ForwrdApplication"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("specialOffers");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "SpecialOffers"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
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
          new  org.apache.axis.encoding.ser.BeanSerializer(
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
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
