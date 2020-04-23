/**
 * AutoInsurancePolicy.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.arb.ws.clients.reply;

public class AutoInsurancePolicy  extends com.arb.ws.clients.reply.Policy  implements java.io.Serializable {
    private boolean currentlyInsured;

    private com.arb.ws.clients.reply.AutoCoverageType coverageType;

    public AutoInsurancePolicy() {
    }

    public AutoInsurancePolicy(
           java.util.Calendar requestDate,
           java.util.Calendar policyExpDate,
           java.lang.String currentInsCarrier,
           java.lang.String firstName,
           java.lang.String lastName,
           java.lang.String address1,
           java.lang.String address2,
           java.lang.String city,
           java.lang.String state,
           java.lang.String dayPhoneNum,
           java.lang.String evePhoneNum,
           java.lang.String zipCode,
           java.lang.String emailAddress,
           com.arb.ws.clients.reply.CreditHistoryType creditHistory,
           short yrsContinuouslyInsured,
           short yrsCurrentlyInsured,
           short mosContinuouslyInsured,
           short mosCurrentlyInsured,
           boolean recentClaim,
           boolean replacementPolicy,
           boolean currentlyInsured,
           com.arb.ws.clients.reply.AutoCoverageType coverageType) {
        super(
            requestDate,
            policyExpDate,
            currentInsCarrier,
            firstName,
            lastName,
            address1,
            address2,
            city,
            state,
            dayPhoneNum,
            evePhoneNum,
            zipCode,
            emailAddress,
            creditHistory,
            yrsContinuouslyInsured,
            yrsCurrentlyInsured,
            mosContinuouslyInsured,
            mosCurrentlyInsured,
            recentClaim,
            replacementPolicy);
        this.currentlyInsured = currentlyInsured;
        this.coverageType = coverageType;
    }


    /**
     * Gets the currentlyInsured value for this AutoInsurancePolicy.
     * 
     * @return currentlyInsured
     */
    public boolean isCurrentlyInsured() {
        return currentlyInsured;
    }


    /**
     * Sets the currentlyInsured value for this AutoInsurancePolicy.
     * 
     * @param currentlyInsured
     */
    public void setCurrentlyInsured(boolean currentlyInsured) {
        this.currentlyInsured = currentlyInsured;
    }


    /**
     * Gets the coverageType value for this AutoInsurancePolicy.
     * 
     * @return coverageType
     */
    public com.arb.ws.clients.reply.AutoCoverageType getCoverageType() {
        return coverageType;
    }


    /**
     * Sets the coverageType value for this AutoInsurancePolicy.
     * 
     * @param coverageType
     */
    public void setCoverageType(com.arb.ws.clients.reply.AutoCoverageType coverageType) {
        this.coverageType = coverageType;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AutoInsurancePolicy)) return false;
        AutoInsurancePolicy other = (AutoInsurancePolicy) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            this.currentlyInsured == other.isCurrentlyInsured() &&
            ((this.coverageType==null && other.getCoverageType()==null) || 
             (this.coverageType!=null &&
              this.coverageType.equals(other.getCoverageType())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        _hashCode += (isCurrentlyInsured() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getCoverageType() != null) {
            _hashCode += getCoverageType().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AutoInsurancePolicy.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "AutoInsurancePolicy"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("currentlyInsured");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "CurrentlyInsured"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("coverageType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "CoverageType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "AutoCoverageType"));
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
