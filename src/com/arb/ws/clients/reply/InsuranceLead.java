/**
 * InsuranceLead.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.arb.ws.clients.reply;

public abstract class InsuranceLead  extends com.arb.ws.clients.reply.Lead  implements java.io.Serializable {
    private com.arb.ws.clients.reply.CarrierInformation carrierInformation;

    private com.arb.ws.clients.reply.DistributionIncludes distributionIncludes;

    private boolean multiplePolicy;

    public InsuranceLead() {
    }

    public InsuranceLead(
           java.lang.String zipcode,
           com.arb.ws.clients.reply.LeadContact contact,
           long LCPId,
           java.util.Calendar collectionDate,
           long leadId,
           java.lang.String legsSold,
           java.lang.String legsAvailable,
           java.lang.String exclusive,
           java.lang.String reservePrice,
           java.lang.String trackingId,
           java.lang.String sourceId,
           java.lang.String position,
           java.lang.String leadPoolId,
           java.lang.String consumerIP,
           com.arb.ws.clients.reply.CarrierInformation carrierInformation,
           com.arb.ws.clients.reply.DistributionIncludes distributionIncludes,
           boolean multiplePolicy) {
        super(
            zipcode,
            contact,
            LCPId,
            collectionDate,
            leadId,
            legsSold,
            legsAvailable,
            exclusive,
            reservePrice,
            trackingId,
            sourceId,
            position,
            leadPoolId,
            consumerIP);
        this.carrierInformation = carrierInformation;
        this.distributionIncludes = distributionIncludes;
        this.multiplePolicy = multiplePolicy;
    }


    /**
     * Gets the carrierInformation value for this InsuranceLead.
     * 
     * @return carrierInformation
     */
    public com.arb.ws.clients.reply.CarrierInformation getCarrierInformation() {
        return carrierInformation;
    }


    /**
     * Sets the carrierInformation value for this InsuranceLead.
     * 
     * @param carrierInformation
     */
    public void setCarrierInformation(com.arb.ws.clients.reply.CarrierInformation carrierInformation) {
        this.carrierInformation = carrierInformation;
    }


    /**
     * Gets the distributionIncludes value for this InsuranceLead.
     * 
     * @return distributionIncludes
     */
    public com.arb.ws.clients.reply.DistributionIncludes getDistributionIncludes() {
        return distributionIncludes;
    }


    /**
     * Sets the distributionIncludes value for this InsuranceLead.
     * 
     * @param distributionIncludes
     */
    public void setDistributionIncludes(com.arb.ws.clients.reply.DistributionIncludes distributionIncludes) {
        this.distributionIncludes = distributionIncludes;
    }


    /**
     * Gets the multiplePolicy value for this InsuranceLead.
     * 
     * @return multiplePolicy
     */
    public boolean isMultiplePolicy() {
        return multiplePolicy;
    }


    /**
     * Sets the multiplePolicy value for this InsuranceLead.
     * 
     * @param multiplePolicy
     */
    public void setMultiplePolicy(boolean multiplePolicy) {
        this.multiplePolicy = multiplePolicy;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof InsuranceLead)) return false;
        InsuranceLead other = (InsuranceLead) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.carrierInformation==null && other.getCarrierInformation()==null) || 
             (this.carrierInformation!=null &&
              this.carrierInformation.equals(other.getCarrierInformation()))) &&
            ((this.distributionIncludes==null && other.getDistributionIncludes()==null) || 
             (this.distributionIncludes!=null &&
              this.distributionIncludes.equals(other.getDistributionIncludes()))) &&
            this.multiplePolicy == other.isMultiplePolicy();
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
        if (getCarrierInformation() != null) {
            _hashCode += getCarrierInformation().hashCode();
        }
        if (getDistributionIncludes() != null) {
            _hashCode += getDistributionIncludes().hashCode();
        }
        _hashCode += (isMultiplePolicy() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(InsuranceLead.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "InsuranceLead"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("carrierInformation");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "CarrierInformation"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "CarrierInformation"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("distributionIncludes");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "DistributionIncludes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "DistributionIncludes"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("multiplePolicy");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "MultiplePolicy"));
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
