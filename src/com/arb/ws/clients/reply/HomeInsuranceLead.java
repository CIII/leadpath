/**
 * HomeInsuranceLead.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.arb.ws.clients.reply;

public class HomeInsuranceLead  extends com.arb.ws.clients.reply.InsuranceLead  implements java.io.Serializable {
    private com.arb.ws.clients.reply.HomeInsLeadType leadType;

    private com.arb.ws.clients.reply.HomeInsurancePolicy policy;

    private com.arb.ws.clients.reply.InsuranceProperty property;

    private com.arb.ws.clients.reply.PropertyStructure propertyStructure;

    private com.arb.ws.clients.reply.PropertySafety propertySafety;

    private com.arb.ws.clients.reply.HomeClaimInfo[] claimHistory;

    public HomeInsuranceLead() {
    }

    public HomeInsuranceLead(
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
           boolean multiplePolicy,
           com.arb.ws.clients.reply.HomeInsLeadType leadType,
           com.arb.ws.clients.reply.HomeInsurancePolicy policy,
           com.arb.ws.clients.reply.InsuranceProperty property,
           com.arb.ws.clients.reply.PropertyStructure propertyStructure,
           com.arb.ws.clients.reply.PropertySafety propertySafety,
           com.arb.ws.clients.reply.HomeClaimInfo[] claimHistory) {
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
            consumerIP,
            carrierInformation,
            distributionIncludes,
            multiplePolicy);
        this.leadType = leadType;
        this.policy = policy;
        this.property = property;
        this.propertyStructure = propertyStructure;
        this.propertySafety = propertySafety;
        this.claimHistory = claimHistory;
    }


    /**
     * Gets the leadType value for this HomeInsuranceLead.
     * 
     * @return leadType
     */
    public com.arb.ws.clients.reply.HomeInsLeadType getLeadType() {
        return leadType;
    }


    /**
     * Sets the leadType value for this HomeInsuranceLead.
     * 
     * @param leadType
     */
    public void setLeadType(com.arb.ws.clients.reply.HomeInsLeadType leadType) {
        this.leadType = leadType;
    }


    /**
     * Gets the policy value for this HomeInsuranceLead.
     * 
     * @return policy
     */
    public com.arb.ws.clients.reply.HomeInsurancePolicy getPolicy() {
        return policy;
    }


    /**
     * Sets the policy value for this HomeInsuranceLead.
     * 
     * @param policy
     */
    public void setPolicy(com.arb.ws.clients.reply.HomeInsurancePolicy policy) {
        this.policy = policy;
    }


    /**
     * Gets the property value for this HomeInsuranceLead.
     * 
     * @return property
     */
    public com.arb.ws.clients.reply.InsuranceProperty getProperty() {
        return property;
    }


    /**
     * Sets the property value for this HomeInsuranceLead.
     * 
     * @param property
     */
    public void setProperty(com.arb.ws.clients.reply.InsuranceProperty property) {
        this.property = property;
    }


    /**
     * Gets the propertyStructure value for this HomeInsuranceLead.
     * 
     * @return propertyStructure
     */
    public com.arb.ws.clients.reply.PropertyStructure getPropertyStructure() {
        return propertyStructure;
    }


    /**
     * Sets the propertyStructure value for this HomeInsuranceLead.
     * 
     * @param propertyStructure
     */
    public void setPropertyStructure(com.arb.ws.clients.reply.PropertyStructure propertyStructure) {
        this.propertyStructure = propertyStructure;
    }


    /**
     * Gets the propertySafety value for this HomeInsuranceLead.
     * 
     * @return propertySafety
     */
    public com.arb.ws.clients.reply.PropertySafety getPropertySafety() {
        return propertySafety;
    }


    /**
     * Sets the propertySafety value for this HomeInsuranceLead.
     * 
     * @param propertySafety
     */
    public void setPropertySafety(com.arb.ws.clients.reply.PropertySafety propertySafety) {
        this.propertySafety = propertySafety;
    }


    /**
     * Gets the claimHistory value for this HomeInsuranceLead.
     * 
     * @return claimHistory
     */
    public com.arb.ws.clients.reply.HomeClaimInfo[] getClaimHistory() {
        return claimHistory;
    }


    /**
     * Sets the claimHistory value for this HomeInsuranceLead.
     * 
     * @param claimHistory
     */
    public void setClaimHistory(com.arb.ws.clients.reply.HomeClaimInfo[] claimHistory) {
        this.claimHistory = claimHistory;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof HomeInsuranceLead)) return false;
        HomeInsuranceLead other = (HomeInsuranceLead) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.leadType==null && other.getLeadType()==null) || 
             (this.leadType!=null &&
              this.leadType.equals(other.getLeadType()))) &&
            ((this.policy==null && other.getPolicy()==null) || 
             (this.policy!=null &&
              this.policy.equals(other.getPolicy()))) &&
            ((this.property==null && other.getProperty()==null) || 
             (this.property!=null &&
              this.property.equals(other.getProperty()))) &&
            ((this.propertyStructure==null && other.getPropertyStructure()==null) || 
             (this.propertyStructure!=null &&
              this.propertyStructure.equals(other.getPropertyStructure()))) &&
            ((this.propertySafety==null && other.getPropertySafety()==null) || 
             (this.propertySafety!=null &&
              this.propertySafety.equals(other.getPropertySafety()))) &&
            ((this.claimHistory==null && other.getClaimHistory()==null) || 
             (this.claimHistory!=null &&
              java.util.Arrays.equals(this.claimHistory, other.getClaimHistory())));
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
        if (getLeadType() != null) {
            _hashCode += getLeadType().hashCode();
        }
        if (getPolicy() != null) {
            _hashCode += getPolicy().hashCode();
        }
        if (getProperty() != null) {
            _hashCode += getProperty().hashCode();
        }
        if (getPropertyStructure() != null) {
            _hashCode += getPropertyStructure().hashCode();
        }
        if (getPropertySafety() != null) {
            _hashCode += getPropertySafety().hashCode();
        }
        if (getClaimHistory() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getClaimHistory());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getClaimHistory(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(HomeInsuranceLead.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "HomeInsuranceLead"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("leadType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "LeadType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "HomeInsLeadType"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("policy");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "Policy"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "HomeInsurancePolicy"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("property");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "Property"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "InsuranceProperty"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("propertyStructure");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "PropertyStructure"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "PropertyStructure"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("propertySafety");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "PropertySafety"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "PropertySafety"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("claimHistory");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "ClaimHistory"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "HomeClaimInfo"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "HomeClaimInfo"));
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
