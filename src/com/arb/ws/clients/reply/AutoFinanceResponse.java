/**
 * AutoFinanceResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.arb.ws.clients.reply;

public class AutoFinanceResponse  implements java.io.Serializable {
    private com.arb.ws.clients.reply.AutoFinanceLead autoFinanceLead;

    private com.arb.ws.clients.reply.ArrayOfErrorError[] errors;

    private boolean success;

    private java.math.BigDecimal payout;

    private java.lang.String token;

    public AutoFinanceResponse() {
    }

    public AutoFinanceResponse(
           com.arb.ws.clients.reply.AutoFinanceLead autoFinanceLead,
           com.arb.ws.clients.reply.ArrayOfErrorError[] errors,
           boolean success,
           java.math.BigDecimal payout,
           java.lang.String token) {
           this.autoFinanceLead = autoFinanceLead;
           this.errors = errors;
           this.success = success;
           this.payout = payout;
           this.token = token;
    }


    /**
     * Gets the autoFinanceLead value for this AutoFinanceResponse.
     * 
     * @return autoFinanceLead
     */
    public com.arb.ws.clients.reply.AutoFinanceLead getAutoFinanceLead() {
        return autoFinanceLead;
    }


    /**
     * Sets the autoFinanceLead value for this AutoFinanceResponse.
     * 
     * @param autoFinanceLead
     */
    public void setAutoFinanceLead(com.arb.ws.clients.reply.AutoFinanceLead autoFinanceLead) {
        this.autoFinanceLead = autoFinanceLead;
    }


    /**
     * Gets the errors value for this AutoFinanceResponse.
     * 
     * @return errors
     */
    public com.arb.ws.clients.reply.ArrayOfErrorError[] getErrors() {
        return errors;
    }


    /**
     * Sets the errors value for this AutoFinanceResponse.
     * 
     * @param errors
     */
    public void setErrors(com.arb.ws.clients.reply.ArrayOfErrorError[] errors) {
        this.errors = errors;
    }


    /**
     * Gets the success value for this AutoFinanceResponse.
     * 
     * @return success
     */
    public boolean isSuccess() {
        return success;
    }


    /**
     * Sets the success value for this AutoFinanceResponse.
     * 
     * @param success
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }


    /**
     * Gets the payout value for this AutoFinanceResponse.
     * 
     * @return payout
     */
    public java.math.BigDecimal getPayout() {
        return payout;
    }


    /**
     * Sets the payout value for this AutoFinanceResponse.
     * 
     * @param payout
     */
    public void setPayout(java.math.BigDecimal payout) {
        this.payout = payout;
    }


    /**
     * Gets the token value for this AutoFinanceResponse.
     * 
     * @return token
     */
    public java.lang.String getToken() {
        return token;
    }


    /**
     * Sets the token value for this AutoFinanceResponse.
     * 
     * @param token
     */
    public void setToken(java.lang.String token) {
        this.token = token;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AutoFinanceResponse)) return false;
        AutoFinanceResponse other = (AutoFinanceResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.autoFinanceLead==null && other.getAutoFinanceLead()==null) || 
             (this.autoFinanceLead!=null &&
              this.autoFinanceLead.equals(other.getAutoFinanceLead()))) &&
            ((this.errors==null && other.getErrors()==null) || 
             (this.errors!=null &&
              java.util.Arrays.equals(this.errors, other.getErrors()))) &&
            this.success == other.isSuccess() &&
            ((this.payout==null && other.getPayout()==null) || 
             (this.payout!=null &&
              this.payout.equals(other.getPayout()))) &&
            ((this.token==null && other.getToken()==null) || 
             (this.token!=null &&
              this.token.equals(other.getToken())));
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
        if (getAutoFinanceLead() != null) {
            _hashCode += getAutoFinanceLead().hashCode();
        }
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
        _hashCode += (isSuccess() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getPayout() != null) {
            _hashCode += getPayout().hashCode();
        }
        if (getToken() != null) {
            _hashCode += getToken().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AutoFinanceResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "AutoFinanceResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("autoFinanceLead");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "AutoFinanceLead"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "AutoFinanceLead"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("errors");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "Errors"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", ">ArrayOfError>Error"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "Error"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("success");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "Success"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("payout");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "Payout"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("token");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "Token"));
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
