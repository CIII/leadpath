/**
 * DistributionIncludes.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.arb.ws.clients.reply;

public class DistributionIncludes  implements java.io.Serializable {
    private java.lang.String[] carrier_selected;

    private com.arb.ws.clients.reply.DistributionIncludesAgentSelected[] agent_selected;

    private com.arb.ws.clients.reply.DistributionIncludesAgentKeySelected[] agent_key_selected;

    private java.math.BigInteger distribution_limit;

    public DistributionIncludes() {
    }

    public DistributionIncludes(
           java.lang.String[] carrier_selected,
           com.arb.ws.clients.reply.DistributionIncludesAgentSelected[] agent_selected,
           com.arb.ws.clients.reply.DistributionIncludesAgentKeySelected[] agent_key_selected,
           java.math.BigInteger distribution_limit) {
           this.carrier_selected = carrier_selected;
           this.agent_selected = agent_selected;
           this.agent_key_selected = agent_key_selected;
           this.distribution_limit = distribution_limit;
    }


    /**
     * Gets the carrier_selected value for this DistributionIncludes.
     * 
     * @return carrier_selected
     */
    public java.lang.String[] getCarrier_selected() {
        return carrier_selected;
    }


    /**
     * Sets the carrier_selected value for this DistributionIncludes.
     * 
     * @param carrier_selected
     */
    public void setCarrier_selected(java.lang.String[] carrier_selected) {
        this.carrier_selected = carrier_selected;
    }

    public java.lang.String getCarrier_selected(int i) {
        return this.carrier_selected[i];
    }

    public void setCarrier_selected(int i, java.lang.String _value) {
        this.carrier_selected[i] = _value;
    }


    /**
     * Gets the agent_selected value for this DistributionIncludes.
     * 
     * @return agent_selected
     */
    public com.arb.ws.clients.reply.DistributionIncludesAgentSelected[] getAgent_selected() {
        return agent_selected;
    }


    /**
     * Sets the agent_selected value for this DistributionIncludes.
     * 
     * @param agent_selected
     */
    public void setAgent_selected(com.arb.ws.clients.reply.DistributionIncludesAgentSelected[] agent_selected) {
        this.agent_selected = agent_selected;
    }

    public com.arb.ws.clients.reply.DistributionIncludesAgentSelected getAgent_selected(int i) {
        return this.agent_selected[i];
    }

    public void setAgent_selected(int i, com.arb.ws.clients.reply.DistributionIncludesAgentSelected _value) {
        this.agent_selected[i] = _value;
    }


    /**
     * Gets the agent_key_selected value for this DistributionIncludes.
     * 
     * @return agent_key_selected
     */
    public com.arb.ws.clients.reply.DistributionIncludesAgentKeySelected[] getAgent_key_selected() {
        return agent_key_selected;
    }


    /**
     * Sets the agent_key_selected value for this DistributionIncludes.
     * 
     * @param agent_key_selected
     */
    public void setAgent_key_selected(com.arb.ws.clients.reply.DistributionIncludesAgentKeySelected[] agent_key_selected) {
        this.agent_key_selected = agent_key_selected;
    }

    public com.arb.ws.clients.reply.DistributionIncludesAgentKeySelected getAgent_key_selected(int i) {
        return this.agent_key_selected[i];
    }

    public void setAgent_key_selected(int i, com.arb.ws.clients.reply.DistributionIncludesAgentKeySelected _value) {
        this.agent_key_selected[i] = _value;
    }


    /**
     * Gets the distribution_limit value for this DistributionIncludes.
     * 
     * @return distribution_limit
     */
    public java.math.BigInteger getDistribution_limit() {
        return distribution_limit;
    }


    /**
     * Sets the distribution_limit value for this DistributionIncludes.
     * 
     * @param distribution_limit
     */
    public void setDistribution_limit(java.math.BigInteger distribution_limit) {
        this.distribution_limit = distribution_limit;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DistributionIncludes)) return false;
        DistributionIncludes other = (DistributionIncludes) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.carrier_selected==null && other.getCarrier_selected()==null) || 
             (this.carrier_selected!=null &&
              java.util.Arrays.equals(this.carrier_selected, other.getCarrier_selected()))) &&
            ((this.agent_selected==null && other.getAgent_selected()==null) || 
             (this.agent_selected!=null &&
              java.util.Arrays.equals(this.agent_selected, other.getAgent_selected()))) &&
            ((this.agent_key_selected==null && other.getAgent_key_selected()==null) || 
             (this.agent_key_selected!=null &&
              java.util.Arrays.equals(this.agent_key_selected, other.getAgent_key_selected()))) &&
            ((this.distribution_limit==null && other.getDistribution_limit()==null) || 
             (this.distribution_limit!=null &&
              this.distribution_limit.equals(other.getDistribution_limit())));
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
        if (getCarrier_selected() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCarrier_selected());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCarrier_selected(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getAgent_selected() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAgent_selected());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAgent_selected(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getAgent_key_selected() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAgent_key_selected());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAgent_key_selected(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getDistribution_limit() != null) {
            _hashCode += getDistribution_limit().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DistributionIncludes.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "DistributionIncludes"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("carrier_selected");
        elemField.setXmlName(new javax.xml.namespace.QName("", "carrier_selected"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("agent_selected");
        elemField.setXmlName(new javax.xml.namespace.QName("", "agent_selected"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "DistributionIncludesAgentSelected"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("agent_key_selected");
        elemField.setXmlName(new javax.xml.namespace.QName("", "agent_key_selected"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "DistributionIncludesAgentKeySelected"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("distribution_limit");
        elemField.setXmlName(new javax.xml.namespace.QName("", "distribution_limit"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
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
