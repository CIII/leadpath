/**
 * HomeImprovementResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.arb.ws.clients.reply;

public class HomeImprovementResponse  implements java.io.Serializable {
    private com.arb.ws.clients.reply.HILead homeImprovementLead;

    private com.arb.ws.clients.reply.ArrayOfErrorError[] errors;

    private boolean success;

    private java.math.BigDecimal payout;

    private java.lang.String token;

    public HomeImprovementResponse() {
    }

    public HomeImprovementResponse(
           com.arb.ws.clients.reply.HILead homeImprovementLead,
           com.arb.ws.clients.reply.ArrayOfErrorError[] errors,
           boolean success,
           java.math.BigDecimal payout,
           java.lang.String token) {
           this.homeImprovementLead = homeImprovementLead;
           this.errors = errors;
           this.success = success;
           this.payout = payout;
           this.token = token;
    }


    /**
     * Gets the homeImprovementLead value for this HomeImprovementResponse.
     * 
     * @return homeImprovementLead
     */
    public com.arb.ws.clients.reply.HILead getHomeImprovementLead() {
        return homeImprovementLead;
    }


    /**
     * Sets the homeImprovementLead value for this HomeImprovementResponse.
     * 
     * @param homeImprovementLead
     */
    public void setHomeImprovementLead(com.arb.ws.clients.reply.HILead homeImprovementLead) {
        this.homeImprovementLead = homeImprovementLead;
    }


    /**
     * Gets the errors value for this HomeImprovementResponse.
     * 
     * @return errors
     */
    public com.arb.ws.clients.reply.ArrayOfErrorError[] getErrors() {
        return errors;
    }


    /**
     * Sets the errors value for this HomeImprovementResponse.
     * 
     * @param errors
     */
    public void setErrors(com.arb.ws.clients.reply.ArrayOfErrorError[] errors) {
        this.errors = errors;
    }


    /**
     * Gets the success value for this HomeImprovementResponse.
     * 
     * @return success
     */
    public boolean isSuccess() {
        return success;
    }


    /**
     * Sets the success value for this HomeImprovementResponse.
     * 
     * @param success
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }


    /**
     * Gets the payout value for this HomeImprovementResponse.
     * 
     * @return payout
     */
    public java.math.BigDecimal getPayout() {
        return payout;
    }


    /**
     * Sets the payout value for this HomeImprovementResponse.
     * 
     * @param payout
     */
    public void setPayout(java.math.BigDecimal payout) {
        this.payout = payout;
    }


    /**
     * Gets the token value for this HomeImprovementResponse.
     * 
     * @return token
     */
    public java.lang.String getToken() {
        return token;
    }


    /**
     * Sets the token value for this HomeImprovementResponse.
     * 
     * @param token
     */
    public void setToken(java.lang.String token) {
        this.token = token;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof HomeImprovementResponse)) return false;
        HomeImprovementResponse other = (HomeImprovementResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.homeImprovementLead==null && other.getHomeImprovementLead()==null) || 
             (this.homeImprovementLead!=null &&
              this.homeImprovementLead.equals(other.getHomeImprovementLead()))) &&
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
        if (getHomeImprovementLead() != null) {
            _hashCode += getHomeImprovementLead().hashCode();
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
        new org.apache.axis.description.TypeDesc(HomeImprovementResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "HomeImprovementResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("homeImprovementLead");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "HomeImprovementLead"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "HILead"));
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
