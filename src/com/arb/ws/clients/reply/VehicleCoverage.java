/**
 * VehicleCoverage.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.arb.ws.clients.reply;

public class VehicleCoverage  extends com.arb.ws.clients.reply.Vehicle  implements java.io.Serializable {
    private java.lang.String zipCode;

    private com.arb.ws.clients.reply.VehicleOwnership ownershipStatus;

    private com.arb.ws.clients.reply.VehiclePrimaryUse primaryUse;

    private java.lang.Integer oneWayMileage;

    private java.lang.Integer annualMileage;

    private com.arb.ws.clients.reply.AutoCoverageType coverageType;

    private java.lang.Short collisionDeductible;

    private java.lang.Short comprehensiveDeductible;

    private java.lang.Short driverId;

    public VehicleCoverage() {
    }

    public VehicleCoverage(
           short year,
           java.lang.String make,
           java.lang.String model,
           java.lang.String trim,
           short vehicleId,
           java.lang.String VIN,
           java.lang.String zipCode,
           com.arb.ws.clients.reply.VehicleOwnership ownershipStatus,
           com.arb.ws.clients.reply.VehiclePrimaryUse primaryUse,
           java.lang.Integer oneWayMileage,
           java.lang.Integer annualMileage,
           com.arb.ws.clients.reply.AutoCoverageType coverageType,
           java.lang.Short collisionDeductible,
           java.lang.Short comprehensiveDeductible,
           java.lang.Short driverId) {
        super(
            year,
            make,
            model,
            trim,
            vehicleId,
            VIN);
        this.zipCode = zipCode;
        this.ownershipStatus = ownershipStatus;
        this.primaryUse = primaryUse;
        this.oneWayMileage = oneWayMileage;
        this.annualMileage = annualMileage;
        this.coverageType = coverageType;
        this.collisionDeductible = collisionDeductible;
        this.comprehensiveDeductible = comprehensiveDeductible;
        this.driverId = driverId;
    }


    /**
     * Gets the zipCode value for this VehicleCoverage.
     * 
     * @return zipCode
     */
    public java.lang.String getZipCode() {
        return zipCode;
    }


    /**
     * Sets the zipCode value for this VehicleCoverage.
     * 
     * @param zipCode
     */
    public void setZipCode(java.lang.String zipCode) {
        this.zipCode = zipCode;
    }


    /**
     * Gets the ownershipStatus value for this VehicleCoverage.
     * 
     * @return ownershipStatus
     */
    public com.arb.ws.clients.reply.VehicleOwnership getOwnershipStatus() {
        return ownershipStatus;
    }


    /**
     * Sets the ownershipStatus value for this VehicleCoverage.
     * 
     * @param ownershipStatus
     */
    public void setOwnershipStatus(com.arb.ws.clients.reply.VehicleOwnership ownershipStatus) {
        this.ownershipStatus = ownershipStatus;
    }


    /**
     * Gets the primaryUse value for this VehicleCoverage.
     * 
     * @return primaryUse
     */
    public com.arb.ws.clients.reply.VehiclePrimaryUse getPrimaryUse() {
        return primaryUse;
    }


    /**
     * Sets the primaryUse value for this VehicleCoverage.
     * 
     * @param primaryUse
     */
    public void setPrimaryUse(com.arb.ws.clients.reply.VehiclePrimaryUse primaryUse) {
        this.primaryUse = primaryUse;
    }


    /**
     * Gets the oneWayMileage value for this VehicleCoverage.
     * 
     * @return oneWayMileage
     */
    public java.lang.Integer getOneWayMileage() {
        return oneWayMileage;
    }


    /**
     * Sets the oneWayMileage value for this VehicleCoverage.
     * 
     * @param oneWayMileage
     */
    public void setOneWayMileage(java.lang.Integer oneWayMileage) {
        this.oneWayMileage = oneWayMileage;
    }


    /**
     * Gets the annualMileage value for this VehicleCoverage.
     * 
     * @return annualMileage
     */
    public java.lang.Integer getAnnualMileage() {
        return annualMileage;
    }


    /**
     * Sets the annualMileage value for this VehicleCoverage.
     * 
     * @param annualMileage
     */
    public void setAnnualMileage(java.lang.Integer annualMileage) {
        this.annualMileage = annualMileage;
    }


    /**
     * Gets the coverageType value for this VehicleCoverage.
     * 
     * @return coverageType
     */
    public com.arb.ws.clients.reply.AutoCoverageType getCoverageType() {
        return coverageType;
    }


    /**
     * Sets the coverageType value for this VehicleCoverage.
     * 
     * @param coverageType
     */
    public void setCoverageType(com.arb.ws.clients.reply.AutoCoverageType coverageType) {
        this.coverageType = coverageType;
    }


    /**
     * Gets the collisionDeductible value for this VehicleCoverage.
     * 
     * @return collisionDeductible
     */
    public java.lang.Short getCollisionDeductible() {
        return collisionDeductible;
    }


    /**
     * Sets the collisionDeductible value for this VehicleCoverage.
     * 
     * @param collisionDeductible
     */
    public void setCollisionDeductible(java.lang.Short collisionDeductible) {
        this.collisionDeductible = collisionDeductible;
    }


    /**
     * Gets the comprehensiveDeductible value for this VehicleCoverage.
     * 
     * @return comprehensiveDeductible
     */
    public java.lang.Short getComprehensiveDeductible() {
        return comprehensiveDeductible;
    }


    /**
     * Sets the comprehensiveDeductible value for this VehicleCoverage.
     * 
     * @param comprehensiveDeductible
     */
    public void setComprehensiveDeductible(java.lang.Short comprehensiveDeductible) {
        this.comprehensiveDeductible = comprehensiveDeductible;
    }


    /**
     * Gets the driverId value for this VehicleCoverage.
     * 
     * @return driverId
     */
    public java.lang.Short getDriverId() {
        return driverId;
    }


    /**
     * Sets the driverId value for this VehicleCoverage.
     * 
     * @param driverId
     */
    public void setDriverId(java.lang.Short driverId) {
        this.driverId = driverId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof VehicleCoverage)) return false;
        VehicleCoverage other = (VehicleCoverage) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.zipCode==null && other.getZipCode()==null) || 
             (this.zipCode!=null &&
              this.zipCode.equals(other.getZipCode()))) &&
            ((this.ownershipStatus==null && other.getOwnershipStatus()==null) || 
             (this.ownershipStatus!=null &&
              this.ownershipStatus.equals(other.getOwnershipStatus()))) &&
            ((this.primaryUse==null && other.getPrimaryUse()==null) || 
             (this.primaryUse!=null &&
              this.primaryUse.equals(other.getPrimaryUse()))) &&
            ((this.oneWayMileage==null && other.getOneWayMileage()==null) || 
             (this.oneWayMileage!=null &&
              this.oneWayMileage.equals(other.getOneWayMileage()))) &&
            ((this.annualMileage==null && other.getAnnualMileage()==null) || 
             (this.annualMileage!=null &&
              this.annualMileage.equals(other.getAnnualMileage()))) &&
            ((this.coverageType==null && other.getCoverageType()==null) || 
             (this.coverageType!=null &&
              this.coverageType.equals(other.getCoverageType()))) &&
            ((this.collisionDeductible==null && other.getCollisionDeductible()==null) || 
             (this.collisionDeductible!=null &&
              this.collisionDeductible.equals(other.getCollisionDeductible()))) &&
            ((this.comprehensiveDeductible==null && other.getComprehensiveDeductible()==null) || 
             (this.comprehensiveDeductible!=null &&
              this.comprehensiveDeductible.equals(other.getComprehensiveDeductible()))) &&
            ((this.driverId==null && other.getDriverId()==null) || 
             (this.driverId!=null &&
              this.driverId.equals(other.getDriverId())));
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
        if (getZipCode() != null) {
            _hashCode += getZipCode().hashCode();
        }
        if (getOwnershipStatus() != null) {
            _hashCode += getOwnershipStatus().hashCode();
        }
        if (getPrimaryUse() != null) {
            _hashCode += getPrimaryUse().hashCode();
        }
        if (getOneWayMileage() != null) {
            _hashCode += getOneWayMileage().hashCode();
        }
        if (getAnnualMileage() != null) {
            _hashCode += getAnnualMileage().hashCode();
        }
        if (getCoverageType() != null) {
            _hashCode += getCoverageType().hashCode();
        }
        if (getCollisionDeductible() != null) {
            _hashCode += getCollisionDeductible().hashCode();
        }
        if (getComprehensiveDeductible() != null) {
            _hashCode += getComprehensiveDeductible().hashCode();
        }
        if (getDriverId() != null) {
            _hashCode += getDriverId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(VehicleCoverage.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "VehicleCoverage"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("zipCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "ZipCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ownershipStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "OwnershipStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "VehicleOwnership"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("primaryUse");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "PrimaryUse"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "VehiclePrimaryUse"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oneWayMileage");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "OneWayMileage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("annualMileage");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "AnnualMileage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("coverageType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "CoverageType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "AutoCoverageType"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("collisionDeductible");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "CollisionDeductible"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "short"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("comprehensiveDeductible");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "ComprehensiveDeductible"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "short"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("driverId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "DriverId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "short"));
        elemField.setNillable(true);
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
