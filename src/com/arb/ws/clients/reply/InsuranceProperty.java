/**
 * InsuranceProperty.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.arb.ws.clients.reply;

public class InsuranceProperty  extends com.arb.ws.clients.reply.Property  implements java.io.Serializable {
    private com.arb.ws.clients.reply.InsurancePropertyPropertyType propertyType;

    private com.arb.ws.clients.reply.Occupant resOccupant;

    private com.arb.ws.clients.reply.FireHydrantDistance fireHydrantDistance;

    private com.arb.ws.clients.reply.FireStationDistance fireStationDistance;

    private com.arb.ws.clients.reply.FloodWaterType floodWaterType;

    private boolean dogsDangerous;

    private boolean businessPremises;

    private com.arb.ws.clients.reply.PropertyQuality propertyQuality;

    private boolean munipalLocation;

    private boolean residesHere;

    public InsuranceProperty() {
    }

    public InsuranceProperty(
           com.arb.ws.clients.reply.PropertyAddress address,
           com.arb.ws.clients.reply.PropertyType type,
           int bathroomCount,
           int bedroomCount,
           java.math.BigDecimal maxPrice,
           java.math.BigDecimal minPrice,
           int squareFootage,
           com.arb.ws.clients.reply.InsurancePropertyPropertyType propertyType,
           com.arb.ws.clients.reply.Occupant resOccupant,
           com.arb.ws.clients.reply.FireHydrantDistance fireHydrantDistance,
           com.arb.ws.clients.reply.FireStationDistance fireStationDistance,
           com.arb.ws.clients.reply.FloodWaterType floodWaterType,
           boolean dogsDangerous,
           boolean businessPremises,
           com.arb.ws.clients.reply.PropertyQuality propertyQuality,
           boolean munipalLocation,
           boolean residesHere) {
        super(
            address,
            type,
            bathroomCount,
            bedroomCount,
            maxPrice,
            minPrice,
            squareFootage);
        this.propertyType = propertyType;
        this.resOccupant = resOccupant;
        this.fireHydrantDistance = fireHydrantDistance;
        this.fireStationDistance = fireStationDistance;
        this.floodWaterType = floodWaterType;
        this.dogsDangerous = dogsDangerous;
        this.businessPremises = businessPremises;
        this.propertyQuality = propertyQuality;
        this.munipalLocation = munipalLocation;
        this.residesHere = residesHere;
    }


    /**
     * Gets the propertyType value for this InsuranceProperty.
     * 
     * @return propertyType
     */
    public com.arb.ws.clients.reply.InsurancePropertyPropertyType getPropertyType() {
        return propertyType;
    }


    /**
     * Sets the propertyType value for this InsuranceProperty.
     * 
     * @param propertyType
     */
    public void setPropertyType(com.arb.ws.clients.reply.InsurancePropertyPropertyType propertyType) {
        this.propertyType = propertyType;
    }


    /**
     * Gets the resOccupant value for this InsuranceProperty.
     * 
     * @return resOccupant
     */
    public com.arb.ws.clients.reply.Occupant getResOccupant() {
        return resOccupant;
    }


    /**
     * Sets the resOccupant value for this InsuranceProperty.
     * 
     * @param resOccupant
     */
    public void setResOccupant(com.arb.ws.clients.reply.Occupant resOccupant) {
        this.resOccupant = resOccupant;
    }


    /**
     * Gets the fireHydrantDistance value for this InsuranceProperty.
     * 
     * @return fireHydrantDistance
     */
    public com.arb.ws.clients.reply.FireHydrantDistance getFireHydrantDistance() {
        return fireHydrantDistance;
    }


    /**
     * Sets the fireHydrantDistance value for this InsuranceProperty.
     * 
     * @param fireHydrantDistance
     */
    public void setFireHydrantDistance(com.arb.ws.clients.reply.FireHydrantDistance fireHydrantDistance) {
        this.fireHydrantDistance = fireHydrantDistance;
    }


    /**
     * Gets the fireStationDistance value for this InsuranceProperty.
     * 
     * @return fireStationDistance
     */
    public com.arb.ws.clients.reply.FireStationDistance getFireStationDistance() {
        return fireStationDistance;
    }


    /**
     * Sets the fireStationDistance value for this InsuranceProperty.
     * 
     * @param fireStationDistance
     */
    public void setFireStationDistance(com.arb.ws.clients.reply.FireStationDistance fireStationDistance) {
        this.fireStationDistance = fireStationDistance;
    }


    /**
     * Gets the floodWaterType value for this InsuranceProperty.
     * 
     * @return floodWaterType
     */
    public com.arb.ws.clients.reply.FloodWaterType getFloodWaterType() {
        return floodWaterType;
    }


    /**
     * Sets the floodWaterType value for this InsuranceProperty.
     * 
     * @param floodWaterType
     */
    public void setFloodWaterType(com.arb.ws.clients.reply.FloodWaterType floodWaterType) {
        this.floodWaterType = floodWaterType;
    }


    /**
     * Gets the dogsDangerous value for this InsuranceProperty.
     * 
     * @return dogsDangerous
     */
    public boolean isDogsDangerous() {
        return dogsDangerous;
    }


    /**
     * Sets the dogsDangerous value for this InsuranceProperty.
     * 
     * @param dogsDangerous
     */
    public void setDogsDangerous(boolean dogsDangerous) {
        this.dogsDangerous = dogsDangerous;
    }


    /**
     * Gets the businessPremises value for this InsuranceProperty.
     * 
     * @return businessPremises
     */
    public boolean isBusinessPremises() {
        return businessPremises;
    }


    /**
     * Sets the businessPremises value for this InsuranceProperty.
     * 
     * @param businessPremises
     */
    public void setBusinessPremises(boolean businessPremises) {
        this.businessPremises = businessPremises;
    }


    /**
     * Gets the propertyQuality value for this InsuranceProperty.
     * 
     * @return propertyQuality
     */
    public com.arb.ws.clients.reply.PropertyQuality getPropertyQuality() {
        return propertyQuality;
    }


    /**
     * Sets the propertyQuality value for this InsuranceProperty.
     * 
     * @param propertyQuality
     */
    public void setPropertyQuality(com.arb.ws.clients.reply.PropertyQuality propertyQuality) {
        this.propertyQuality = propertyQuality;
    }


    /**
     * Gets the munipalLocation value for this InsuranceProperty.
     * 
     * @return munipalLocation
     */
    public boolean isMunipalLocation() {
        return munipalLocation;
    }


    /**
     * Sets the munipalLocation value for this InsuranceProperty.
     * 
     * @param munipalLocation
     */
    public void setMunipalLocation(boolean munipalLocation) {
        this.munipalLocation = munipalLocation;
    }


    /**
     * Gets the residesHere value for this InsuranceProperty.
     * 
     * @return residesHere
     */
    public boolean isResidesHere() {
        return residesHere;
    }


    /**
     * Sets the residesHere value for this InsuranceProperty.
     * 
     * @param residesHere
     */
    public void setResidesHere(boolean residesHere) {
        this.residesHere = residesHere;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof InsuranceProperty)) return false;
        InsuranceProperty other = (InsuranceProperty) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.propertyType==null && other.getPropertyType()==null) || 
             (this.propertyType!=null &&
              this.propertyType.equals(other.getPropertyType()))) &&
            ((this.resOccupant==null && other.getResOccupant()==null) || 
             (this.resOccupant!=null &&
              this.resOccupant.equals(other.getResOccupant()))) &&
            ((this.fireHydrantDistance==null && other.getFireHydrantDistance()==null) || 
             (this.fireHydrantDistance!=null &&
              this.fireHydrantDistance.equals(other.getFireHydrantDistance()))) &&
            ((this.fireStationDistance==null && other.getFireStationDistance()==null) || 
             (this.fireStationDistance!=null &&
              this.fireStationDistance.equals(other.getFireStationDistance()))) &&
            ((this.floodWaterType==null && other.getFloodWaterType()==null) || 
             (this.floodWaterType!=null &&
              this.floodWaterType.equals(other.getFloodWaterType()))) &&
            this.dogsDangerous == other.isDogsDangerous() &&
            this.businessPremises == other.isBusinessPremises() &&
            ((this.propertyQuality==null && other.getPropertyQuality()==null) || 
             (this.propertyQuality!=null &&
              this.propertyQuality.equals(other.getPropertyQuality()))) &&
            this.munipalLocation == other.isMunipalLocation() &&
            this.residesHere == other.isResidesHere();
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
        if (getPropertyType() != null) {
            _hashCode += getPropertyType().hashCode();
        }
        if (getResOccupant() != null) {
            _hashCode += getResOccupant().hashCode();
        }
        if (getFireHydrantDistance() != null) {
            _hashCode += getFireHydrantDistance().hashCode();
        }
        if (getFireStationDistance() != null) {
            _hashCode += getFireStationDistance().hashCode();
        }
        if (getFloodWaterType() != null) {
            _hashCode += getFloodWaterType().hashCode();
        }
        _hashCode += (isDogsDangerous() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isBusinessPremises() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getPropertyQuality() != null) {
            _hashCode += getPropertyQuality().hashCode();
        }
        _hashCode += (isMunipalLocation() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isResidesHere() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(InsuranceProperty.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "InsuranceProperty"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("propertyType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "PropertyType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", ">InsuranceProperty>PropertyType"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("resOccupant");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "ResOccupant"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "Occupant"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fireHydrantDistance");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "FireHydrantDistance"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "FireHydrantDistance"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fireStationDistance");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "FireStationDistance"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "FireStationDistance"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("floodWaterType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "FloodWaterType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "FloodWaterType"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dogsDangerous");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "DogsDangerous"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("businessPremises");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "BusinessPremises"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("propertyQuality");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "PropertyQuality"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "PropertyQuality"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("munipalLocation");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "MunipalLocation"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("residesHere");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "ResidesHere"));
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
