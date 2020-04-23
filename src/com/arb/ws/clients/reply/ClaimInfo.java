/**
 * ClaimInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.arb.ws.clients.reply;

public abstract class ClaimInfo  implements java.io.Serializable {
    private java.util.Calendar claimDate;

    private int amountPaid;

    public ClaimInfo() {
    }

    public ClaimInfo(
           java.util.Calendar claimDate,
           int amountPaid) {
           this.claimDate = claimDate;
           this.amountPaid = amountPaid;
    }


    /**
     * Gets the claimDate value for this ClaimInfo.
     * 
     * @return claimDate
     */
    public java.util.Calendar getClaimDate() {
        return claimDate;
    }


    /**
     * Sets the claimDate value for this ClaimInfo.
     * 
     * @param claimDate
     */
    public void setClaimDate(java.util.Calendar claimDate) {
        this.claimDate = claimDate;
    }


    /**
     * Gets the amountPaid value for this ClaimInfo.
     * 
     * @return amountPaid
     */
    public int getAmountPaid() {
        return amountPaid;
    }


    /**
     * Sets the amountPaid value for this ClaimInfo.
     * 
     * @param amountPaid
     */
    public void setAmountPaid(int amountPaid) {
        this.amountPaid = amountPaid;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ClaimInfo)) return false;
        ClaimInfo other = (ClaimInfo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.claimDate==null && other.getClaimDate()==null) || 
             (this.claimDate!=null &&
              this.claimDate.equals(other.getClaimDate()))) &&
            this.amountPaid == other.getAmountPaid();
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
        if (getClaimDate() != null) {
            _hashCode += getClaimDate().hashCode();
        }
        _hashCode += getAmountPaid();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ClaimInfo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "ClaimInfo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("claimDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "ClaimDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("amountPaid");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "AmountPaid"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
