/**
 * AutoInsuranceLead.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.arb.ws.clients.reply;

public class AutoInsuranceLead  extends com.arb.ws.clients.reply.InsuranceLead  implements java.io.Serializable {
    private com.arb.ws.clients.reply.AutoInsLeadType leadType;

    private com.arb.ws.clients.reply.AutoInsurancePolicy policy;

    private com.arb.ws.clients.reply.Driver[] drivers;

    private com.arb.ws.clients.reply.Incident[] incidents;

    private com.arb.ws.clients.reply.VehicleCoverage[] vehicleCoverages;

    public AutoInsuranceLead() {
    }

    public AutoInsuranceLead(
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
           com.arb.ws.clients.reply.AutoInsLeadType leadType,
           com.arb.ws.clients.reply.AutoInsurancePolicy policy,
           com.arb.ws.clients.reply.Driver[] drivers,
           com.arb.ws.clients.reply.Incident[] incidents,
           com.arb.ws.clients.reply.VehicleCoverage[] vehicleCoverages) {
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
        this.drivers = drivers;
        this.incidents = incidents;
        this.vehicleCoverages = vehicleCoverages;
    }


    /**
     * Gets the leadType value for this AutoInsuranceLead.
     * 
     * @return leadType
     */
    public com.arb.ws.clients.reply.AutoInsLeadType getLeadType() {
        return leadType;
    }


    /**
     * Sets the leadType value for this AutoInsuranceLead.
     * 
     * @param leadType
     */
    public void setLeadType(com.arb.ws.clients.reply.AutoInsLeadType leadType) {
        this.leadType = leadType;
    }


    /**
     * Gets the policy value for this AutoInsuranceLead.
     * 
     * @return policy
     */
    public com.arb.ws.clients.reply.AutoInsurancePolicy getPolicy() {
        return policy;
    }


    /**
     * Sets the policy value for this AutoInsuranceLead.
     * 
     * @param policy
     */
    public void setPolicy(com.arb.ws.clients.reply.AutoInsurancePolicy policy) {
        this.policy = policy;
    }


    /**
     * Gets the drivers value for this AutoInsuranceLead.
     * 
     * @return drivers
     */
    public com.arb.ws.clients.reply.Driver[] getDrivers() {
        return drivers;
    }


    /**
     * Sets the drivers value for this AutoInsuranceLead.
     * 
     * @param drivers
     */
    public void setDrivers(com.arb.ws.clients.reply.Driver[] drivers) {
        this.drivers = drivers;
    }


    /**
     * Gets the incidents value for this AutoInsuranceLead.
     * 
     * @return incidents
     */
    public com.arb.ws.clients.reply.Incident[] getIncidents() {
        return incidents;
    }


    /**
     * Sets the incidents value for this AutoInsuranceLead.
     * 
     * @param incidents
     */
    public void setIncidents(com.arb.ws.clients.reply.Incident[] incidents) {
        this.incidents = incidents;
    }


    /**
     * Gets the vehicleCoverages value for this AutoInsuranceLead.
     * 
     * @return vehicleCoverages
     */
    public com.arb.ws.clients.reply.VehicleCoverage[] getVehicleCoverages() {
        return vehicleCoverages;
    }


    /**
     * Sets the vehicleCoverages value for this AutoInsuranceLead.
     * 
     * @param vehicleCoverages
     */
    public void setVehicleCoverages(com.arb.ws.clients.reply.VehicleCoverage[] vehicleCoverages) {
        this.vehicleCoverages = vehicleCoverages;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AutoInsuranceLead)) return false;
        AutoInsuranceLead other = (AutoInsuranceLead) obj;
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
            ((this.drivers==null && other.getDrivers()==null) || 
             (this.drivers!=null &&
              java.util.Arrays.equals(this.drivers, other.getDrivers()))) &&
            ((this.incidents==null && other.getIncidents()==null) || 
             (this.incidents!=null &&
              java.util.Arrays.equals(this.incidents, other.getIncidents()))) &&
            ((this.vehicleCoverages==null && other.getVehicleCoverages()==null) || 
             (this.vehicleCoverages!=null &&
              java.util.Arrays.equals(this.vehicleCoverages, other.getVehicleCoverages())));
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
        if (getDrivers() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDrivers());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDrivers(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getIncidents() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getIncidents());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getIncidents(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getVehicleCoverages() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getVehicleCoverages());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getVehicleCoverages(), i);
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
        new org.apache.axis.description.TypeDesc(AutoInsuranceLead.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "AutoInsuranceLead"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("leadType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "LeadType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "AutoInsLeadType"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("policy");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "Policy"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "AutoInsurancePolicy"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("drivers");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "Drivers"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "Driver"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "Driver"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("incidents");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "Incidents"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "Incident"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "Incident"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vehicleCoverages");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "VehicleCoverages"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "VehicleCoverage"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "VehicleCoverage"));
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
