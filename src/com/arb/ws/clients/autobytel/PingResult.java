/**
 * PingResult.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.arb.ws.clients.autobytel;

public class PingResult  implements java.io.Serializable {
    private boolean coverage;

    private com.arb.ws.clients.autobytel.Error[] errors;

    private com.arb.ws.clients.autobytel.Dealer[] dealers;

    private java.lang.String transactionID;

    public PingResult() {
    }

    public PingResult(
           boolean coverage,
           com.arb.ws.clients.autobytel.Error[] errors,
           com.arb.ws.clients.autobytel.Dealer[] dealers,
           java.lang.String transactionID) {
           this.coverage = coverage;
           this.errors = errors;
           this.dealers = dealers;
           this.transactionID = transactionID;
    }


    /**
     * Gets the coverage value for this PingResult.
     * 
     * @return coverage
     */
    public boolean isCoverage() {
        return coverage;
    }


    /**
     * Sets the coverage value for this PingResult.
     * 
     * @param coverage
     */
    public void setCoverage(boolean coverage) {
        this.coverage = coverage;
    }


    /**
     * Gets the errors value for this PingResult.
     * 
     * @return errors
     */
    public com.arb.ws.clients.autobytel.Error[] getErrors() {
        return errors;
    }


    /**
     * Sets the errors value for this PingResult.
     * 
     * @param errors
     */
    public void setErrors(com.arb.ws.clients.autobytel.Error[] errors) {
        this.errors = errors;
    }


    /**
     * Gets the dealers value for this PingResult.
     * 
     * @return dealers
     */
    public com.arb.ws.clients.autobytel.Dealer[] getDealers() {
        return dealers;
    }


    /**
     * Sets the dealers value for this PingResult.
     * 
     * @param dealers
     */
    public void setDealers(com.arb.ws.clients.autobytel.Dealer[] dealers) {
        this.dealers = dealers;
    }


    /**
     * Gets the transactionID value for this PingResult.
     * 
     * @return transactionID
     */
    public java.lang.String getTransactionID() {
        return transactionID;
    }


    /**
     * Sets the transactionID value for this PingResult.
     * 
     * @param transactionID
     */
    public void setTransactionID(java.lang.String transactionID) {
        this.transactionID = transactionID;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PingResult)) return false;
        PingResult other = (PingResult) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.coverage == other.isCoverage() &&
            ((this.errors==null && other.getErrors()==null) || 
             (this.errors!=null &&
              java.util.Arrays.equals(this.errors, other.getErrors()))) &&
            ((this.dealers==null && other.getDealers()==null) || 
             (this.dealers!=null &&
              java.util.Arrays.equals(this.dealers, other.getDealers()))) &&
            ((this.transactionID==null && other.getTransactionID()==null) || 
             (this.transactionID!=null &&
              this.transactionID.equals(other.getTransactionID())));
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
        _hashCode += (isCoverage() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getErrors() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getErrors());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getErrors(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getDealers() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDealers());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDealers(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getTransactionID() != null) {
            _hashCode += getTransactionID().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PingResult.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.autobytel.com/", "PingResult"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("coverage");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.autobytel.com/", "Coverage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("errors");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.autobytel.com/", "Errors"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.autobytel.com/", "Error"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://www.autobytel.com/", "Error"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dealers");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.autobytel.com/", "Dealers"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.autobytel.com/", "Dealer"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://www.autobytel.com/", "Dealer"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("transactionID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.autobytel.com/", "TransactionID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
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
