/**
 * Driver.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.arb.ws.clients.reply;

public class Driver  implements java.io.Serializable {
    private com.arb.ws.clients.reply.RelationToApplicant relationToApplicant;

    private java.lang.String firstName;

    private java.lang.String lastName;

    private java.util.Calendar DOB;

    private com.arb.ws.clients.reply.Gender gender;

    private com.arb.ws.clients.reply.MaritalStatus maritalStatus;

    private java.lang.String occupation;

    private com.arb.ws.clients.reply.EducationLevel educationLevel;

    private java.lang.Boolean goodStudent;

    private short licenseAge;

    private boolean licenseRevoked;

    private boolean filedBankruptcy;

    private boolean SR22Required;

    private com.arb.ws.clients.reply.CreditHistoryType creditHistory;

    private com.arb.ws.clients.reply.Occupant resOccupant;

    private short yrsAtCurrentResidence;

    private short mosAtCurrentResidence;

    private boolean driverTraining;

    private boolean validLicense;

    private java.lang.String stateCurrentlyLicensed;

    private java.lang.String licenseNumber;

    private boolean hasIncidents;

    private short driverId;

    private java.lang.Short vehicleId;

    private com.arb.ws.clients.reply.DriversLicenseType driversLicenseType;

    public Driver() {
    }

    public Driver(
           com.arb.ws.clients.reply.RelationToApplicant relationToApplicant,
           java.lang.String firstName,
           java.lang.String lastName,
           java.util.Calendar DOB,
           com.arb.ws.clients.reply.Gender gender,
           com.arb.ws.clients.reply.MaritalStatus maritalStatus,
           java.lang.String occupation,
           com.arb.ws.clients.reply.EducationLevel educationLevel,
           java.lang.Boolean goodStudent,
           short licenseAge,
           boolean licenseRevoked,
           boolean filedBankruptcy,
           boolean SR22Required,
           com.arb.ws.clients.reply.CreditHistoryType creditHistory,
           com.arb.ws.clients.reply.Occupant resOccupant,
           short yrsAtCurrentResidence,
           short mosAtCurrentResidence,
           boolean driverTraining,
           boolean validLicense,
           java.lang.String stateCurrentlyLicensed,
           java.lang.String licenseNumber,
           boolean hasIncidents,
           short driverId,
           java.lang.Short vehicleId,
           com.arb.ws.clients.reply.DriversLicenseType driversLicenseType) {
           this.relationToApplicant = relationToApplicant;
           this.firstName = firstName;
           this.lastName = lastName;
           this.DOB = DOB;
           this.gender = gender;
           this.maritalStatus = maritalStatus;
           this.occupation = occupation;
           this.educationLevel = educationLevel;
           this.goodStudent = goodStudent;
           this.licenseAge = licenseAge;
           this.licenseRevoked = licenseRevoked;
           this.filedBankruptcy = filedBankruptcy;
           this.SR22Required = SR22Required;
           this.creditHistory = creditHistory;
           this.resOccupant = resOccupant;
           this.yrsAtCurrentResidence = yrsAtCurrentResidence;
           this.mosAtCurrentResidence = mosAtCurrentResidence;
           this.driverTraining = driverTraining;
           this.validLicense = validLicense;
           this.stateCurrentlyLicensed = stateCurrentlyLicensed;
           this.licenseNumber = licenseNumber;
           this.hasIncidents = hasIncidents;
           this.driverId = driverId;
           this.vehicleId = vehicleId;
           this.driversLicenseType = driversLicenseType;
    }


    /**
     * Gets the relationToApplicant value for this Driver.
     * 
     * @return relationToApplicant
     */
    public com.arb.ws.clients.reply.RelationToApplicant getRelationToApplicant() {
        return relationToApplicant;
    }


    /**
     * Sets the relationToApplicant value for this Driver.
     * 
     * @param relationToApplicant
     */
    public void setRelationToApplicant(com.arb.ws.clients.reply.RelationToApplicant relationToApplicant) {
        this.relationToApplicant = relationToApplicant;
    }


    /**
     * Gets the firstName value for this Driver.
     * 
     * @return firstName
     */
    public java.lang.String getFirstName() {
        return firstName;
    }


    /**
     * Sets the firstName value for this Driver.
     * 
     * @param firstName
     */
    public void setFirstName(java.lang.String firstName) {
        this.firstName = firstName;
    }


    /**
     * Gets the lastName value for this Driver.
     * 
     * @return lastName
     */
    public java.lang.String getLastName() {
        return lastName;
    }


    /**
     * Sets the lastName value for this Driver.
     * 
     * @param lastName
     */
    public void setLastName(java.lang.String lastName) {
        this.lastName = lastName;
    }


    /**
     * Gets the DOB value for this Driver.
     * 
     * @return DOB
     */
    public java.util.Calendar getDOB() {
        return DOB;
    }


    /**
     * Sets the DOB value for this Driver.
     * 
     * @param DOB
     */
    public void setDOB(java.util.Calendar DOB) {
        this.DOB = DOB;
    }


    /**
     * Gets the gender value for this Driver.
     * 
     * @return gender
     */
    public com.arb.ws.clients.reply.Gender getGender() {
        return gender;
    }


    /**
     * Sets the gender value for this Driver.
     * 
     * @param gender
     */
    public void setGender(com.arb.ws.clients.reply.Gender gender) {
        this.gender = gender;
    }


    /**
     * Gets the maritalStatus value for this Driver.
     * 
     * @return maritalStatus
     */
    public com.arb.ws.clients.reply.MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }


    /**
     * Sets the maritalStatus value for this Driver.
     * 
     * @param maritalStatus
     */
    public void setMaritalStatus(com.arb.ws.clients.reply.MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }


    /**
     * Gets the occupation value for this Driver.
     * 
     * @return occupation
     */
    public java.lang.String getOccupation() {
        return occupation;
    }


    /**
     * Sets the occupation value for this Driver.
     * 
     * @param occupation
     */
    public void setOccupation(java.lang.String occupation) {
        this.occupation = occupation;
    }


    /**
     * Gets the educationLevel value for this Driver.
     * 
     * @return educationLevel
     */
    public com.arb.ws.clients.reply.EducationLevel getEducationLevel() {
        return educationLevel;
    }


    /**
     * Sets the educationLevel value for this Driver.
     * 
     * @param educationLevel
     */
    public void setEducationLevel(com.arb.ws.clients.reply.EducationLevel educationLevel) {
        this.educationLevel = educationLevel;
    }


    /**
     * Gets the goodStudent value for this Driver.
     * 
     * @return goodStudent
     */
    public java.lang.Boolean getGoodStudent() {
        return goodStudent;
    }


    /**
     * Sets the goodStudent value for this Driver.
     * 
     * @param goodStudent
     */
    public void setGoodStudent(java.lang.Boolean goodStudent) {
        this.goodStudent = goodStudent;
    }


    /**
     * Gets the licenseAge value for this Driver.
     * 
     * @return licenseAge
     */
    public short getLicenseAge() {
        return licenseAge;
    }


    /**
     * Sets the licenseAge value for this Driver.
     * 
     * @param licenseAge
     */
    public void setLicenseAge(short licenseAge) {
        this.licenseAge = licenseAge;
    }


    /**
     * Gets the licenseRevoked value for this Driver.
     * 
     * @return licenseRevoked
     */
    public boolean isLicenseRevoked() {
        return licenseRevoked;
    }


    /**
     * Sets the licenseRevoked value for this Driver.
     * 
     * @param licenseRevoked
     */
    public void setLicenseRevoked(boolean licenseRevoked) {
        this.licenseRevoked = licenseRevoked;
    }


    /**
     * Gets the filedBankruptcy value for this Driver.
     * 
     * @return filedBankruptcy
     */
    public boolean isFiledBankruptcy() {
        return filedBankruptcy;
    }


    /**
     * Sets the filedBankruptcy value for this Driver.
     * 
     * @param filedBankruptcy
     */
    public void setFiledBankruptcy(boolean filedBankruptcy) {
        this.filedBankruptcy = filedBankruptcy;
    }


    /**
     * Gets the SR22Required value for this Driver.
     * 
     * @return SR22Required
     */
    public boolean isSR22Required() {
        return SR22Required;
    }


    /**
     * Sets the SR22Required value for this Driver.
     * 
     * @param SR22Required
     */
    public void setSR22Required(boolean SR22Required) {
        this.SR22Required = SR22Required;
    }


    /**
     * Gets the creditHistory value for this Driver.
     * 
     * @return creditHistory
     */
    public com.arb.ws.clients.reply.CreditHistoryType getCreditHistory() {
        return creditHistory;
    }


    /**
     * Sets the creditHistory value for this Driver.
     * 
     * @param creditHistory
     */
    public void setCreditHistory(com.arb.ws.clients.reply.CreditHistoryType creditHistory) {
        this.creditHistory = creditHistory;
    }


    /**
     * Gets the resOccupant value for this Driver.
     * 
     * @return resOccupant
     */
    public com.arb.ws.clients.reply.Occupant getResOccupant() {
        return resOccupant;
    }


    /**
     * Sets the resOccupant value for this Driver.
     * 
     * @param resOccupant
     */
    public void setResOccupant(com.arb.ws.clients.reply.Occupant resOccupant) {
        this.resOccupant = resOccupant;
    }


    /**
     * Gets the yrsAtCurrentResidence value for this Driver.
     * 
     * @return yrsAtCurrentResidence
     */
    public short getYrsAtCurrentResidence() {
        return yrsAtCurrentResidence;
    }


    /**
     * Sets the yrsAtCurrentResidence value for this Driver.
     * 
     * @param yrsAtCurrentResidence
     */
    public void setYrsAtCurrentResidence(short yrsAtCurrentResidence) {
        this.yrsAtCurrentResidence = yrsAtCurrentResidence;
    }


    /**
     * Gets the mosAtCurrentResidence value for this Driver.
     * 
     * @return mosAtCurrentResidence
     */
    public short getMosAtCurrentResidence() {
        return mosAtCurrentResidence;
    }


    /**
     * Sets the mosAtCurrentResidence value for this Driver.
     * 
     * @param mosAtCurrentResidence
     */
    public void setMosAtCurrentResidence(short mosAtCurrentResidence) {
        this.mosAtCurrentResidence = mosAtCurrentResidence;
    }


    /**
     * Gets the driverTraining value for this Driver.
     * 
     * @return driverTraining
     */
    public boolean isDriverTraining() {
        return driverTraining;
    }


    /**
     * Sets the driverTraining value for this Driver.
     * 
     * @param driverTraining
     */
    public void setDriverTraining(boolean driverTraining) {
        this.driverTraining = driverTraining;
    }


    /**
     * Gets the validLicense value for this Driver.
     * 
     * @return validLicense
     */
    public boolean isValidLicense() {
        return validLicense;
    }


    /**
     * Sets the validLicense value for this Driver.
     * 
     * @param validLicense
     */
    public void setValidLicense(boolean validLicense) {
        this.validLicense = validLicense;
    }


    /**
     * Gets the stateCurrentlyLicensed value for this Driver.
     * 
     * @return stateCurrentlyLicensed
     */
    public java.lang.String getStateCurrentlyLicensed() {
        return stateCurrentlyLicensed;
    }


    /**
     * Sets the stateCurrentlyLicensed value for this Driver.
     * 
     * @param stateCurrentlyLicensed
     */
    public void setStateCurrentlyLicensed(java.lang.String stateCurrentlyLicensed) {
        this.stateCurrentlyLicensed = stateCurrentlyLicensed;
    }


    /**
     * Gets the licenseNumber value for this Driver.
     * 
     * @return licenseNumber
     */
    public java.lang.String getLicenseNumber() {
        return licenseNumber;
    }


    /**
     * Sets the licenseNumber value for this Driver.
     * 
     * @param licenseNumber
     */
    public void setLicenseNumber(java.lang.String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }


    /**
     * Gets the hasIncidents value for this Driver.
     * 
     * @return hasIncidents
     */
    public boolean isHasIncidents() {
        return hasIncidents;
    }


    /**
     * Sets the hasIncidents value for this Driver.
     * 
     * @param hasIncidents
     */
    public void setHasIncidents(boolean hasIncidents) {
        this.hasIncidents = hasIncidents;
    }


    /**
     * Gets the driverId value for this Driver.
     * 
     * @return driverId
     */
    public short getDriverId() {
        return driverId;
    }


    /**
     * Sets the driverId value for this Driver.
     * 
     * @param driverId
     */
    public void setDriverId(short driverId) {
        this.driverId = driverId;
    }


    /**
     * Gets the vehicleId value for this Driver.
     * 
     * @return vehicleId
     */
    public java.lang.Short getVehicleId() {
        return vehicleId;
    }


    /**
     * Sets the vehicleId value for this Driver.
     * 
     * @param vehicleId
     */
    public void setVehicleId(java.lang.Short vehicleId) {
        this.vehicleId = vehicleId;
    }


    /**
     * Gets the driversLicenseType value for this Driver.
     * 
     * @return driversLicenseType
     */
    public com.arb.ws.clients.reply.DriversLicenseType getDriversLicenseType() {
        return driversLicenseType;
    }


    /**
     * Sets the driversLicenseType value for this Driver.
     * 
     * @param driversLicenseType
     */
    public void setDriversLicenseType(com.arb.ws.clients.reply.DriversLicenseType driversLicenseType) {
        this.driversLicenseType = driversLicenseType;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Driver)) return false;
        Driver other = (Driver) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.relationToApplicant==null && other.getRelationToApplicant()==null) || 
             (this.relationToApplicant!=null &&
              this.relationToApplicant.equals(other.getRelationToApplicant()))) &&
            ((this.firstName==null && other.getFirstName()==null) || 
             (this.firstName!=null &&
              this.firstName.equals(other.getFirstName()))) &&
            ((this.lastName==null && other.getLastName()==null) || 
             (this.lastName!=null &&
              this.lastName.equals(other.getLastName()))) &&
            ((this.DOB==null && other.getDOB()==null) || 
             (this.DOB!=null &&
              this.DOB.equals(other.getDOB()))) &&
            ((this.gender==null && other.getGender()==null) || 
             (this.gender!=null &&
              this.gender.equals(other.getGender()))) &&
            ((this.maritalStatus==null && other.getMaritalStatus()==null) || 
             (this.maritalStatus!=null &&
              this.maritalStatus.equals(other.getMaritalStatus()))) &&
            ((this.occupation==null && other.getOccupation()==null) || 
             (this.occupation!=null &&
              this.occupation.equals(other.getOccupation()))) &&
            ((this.educationLevel==null && other.getEducationLevel()==null) || 
             (this.educationLevel!=null &&
              this.educationLevel.equals(other.getEducationLevel()))) &&
            ((this.goodStudent==null && other.getGoodStudent()==null) || 
             (this.goodStudent!=null &&
              this.goodStudent.equals(other.getGoodStudent()))) &&
            this.licenseAge == other.getLicenseAge() &&
            this.licenseRevoked == other.isLicenseRevoked() &&
            this.filedBankruptcy == other.isFiledBankruptcy() &&
            this.SR22Required == other.isSR22Required() &&
            ((this.creditHistory==null && other.getCreditHistory()==null) || 
             (this.creditHistory!=null &&
              this.creditHistory.equals(other.getCreditHistory()))) &&
            ((this.resOccupant==null && other.getResOccupant()==null) || 
             (this.resOccupant!=null &&
              this.resOccupant.equals(other.getResOccupant()))) &&
            this.yrsAtCurrentResidence == other.getYrsAtCurrentResidence() &&
            this.mosAtCurrentResidence == other.getMosAtCurrentResidence() &&
            this.driverTraining == other.isDriverTraining() &&
            this.validLicense == other.isValidLicense() &&
            ((this.stateCurrentlyLicensed==null && other.getStateCurrentlyLicensed()==null) || 
             (this.stateCurrentlyLicensed!=null &&
              this.stateCurrentlyLicensed.equals(other.getStateCurrentlyLicensed()))) &&
            ((this.licenseNumber==null && other.getLicenseNumber()==null) || 
             (this.licenseNumber!=null &&
              this.licenseNumber.equals(other.getLicenseNumber()))) &&
            this.hasIncidents == other.isHasIncidents() &&
            this.driverId == other.getDriverId() &&
            ((this.vehicleId==null && other.getVehicleId()==null) || 
             (this.vehicleId!=null &&
              this.vehicleId.equals(other.getVehicleId()))) &&
            ((this.driversLicenseType==null && other.getDriversLicenseType()==null) || 
             (this.driversLicenseType!=null &&
              this.driversLicenseType.equals(other.getDriversLicenseType())));
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
        if (getRelationToApplicant() != null) {
            _hashCode += getRelationToApplicant().hashCode();
        }
        if (getFirstName() != null) {
            _hashCode += getFirstName().hashCode();
        }
        if (getLastName() != null) {
            _hashCode += getLastName().hashCode();
        }
        if (getDOB() != null) {
            _hashCode += getDOB().hashCode();
        }
        if (getGender() != null) {
            _hashCode += getGender().hashCode();
        }
        if (getMaritalStatus() != null) {
            _hashCode += getMaritalStatus().hashCode();
        }
        if (getOccupation() != null) {
            _hashCode += getOccupation().hashCode();
        }
        if (getEducationLevel() != null) {
            _hashCode += getEducationLevel().hashCode();
        }
        if (getGoodStudent() != null) {
            _hashCode += getGoodStudent().hashCode();
        }
        _hashCode += getLicenseAge();
        _hashCode += (isLicenseRevoked() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isFiledBankruptcy() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isSR22Required() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getCreditHistory() != null) {
            _hashCode += getCreditHistory().hashCode();
        }
        if (getResOccupant() != null) {
            _hashCode += getResOccupant().hashCode();
        }
        _hashCode += getYrsAtCurrentResidence();
        _hashCode += getMosAtCurrentResidence();
        _hashCode += (isDriverTraining() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isValidLicense() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getStateCurrentlyLicensed() != null) {
            _hashCode += getStateCurrentlyLicensed().hashCode();
        }
        if (getLicenseNumber() != null) {
            _hashCode += getLicenseNumber().hashCode();
        }
        _hashCode += (isHasIncidents() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += getDriverId();
        if (getVehicleId() != null) {
            _hashCode += getVehicleId().hashCode();
        }
        if (getDriversLicenseType() != null) {
            _hashCode += getDriversLicenseType().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Driver.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "Driver"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("relationToApplicant");
        elemField.setXmlName(new javax.xml.namespace.QName("", "RelationToApplicant"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "RelationToApplicant"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("firstName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "FirstName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lastName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "LastName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("DOB");
        elemField.setXmlName(new javax.xml.namespace.QName("", "DOB"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("gender");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Gender"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "Gender"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("maritalStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("", "MaritalStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "MaritalStatus"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("occupation");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Occupation"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("educationLevel");
        elemField.setXmlName(new javax.xml.namespace.QName("", "EducationLevel"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "EducationLevel"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("goodStudent");
        elemField.setXmlName(new javax.xml.namespace.QName("", "GoodStudent"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("licenseAge");
        elemField.setXmlName(new javax.xml.namespace.QName("", "LicenseAge"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "short"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("licenseRevoked");
        elemField.setXmlName(new javax.xml.namespace.QName("", "LicenseRevoked"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("filedBankruptcy");
        elemField.setXmlName(new javax.xml.namespace.QName("", "FiledBankruptcy"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SR22Required");
        elemField.setXmlName(new javax.xml.namespace.QName("", "SR22Required"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("creditHistory");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CreditHistory"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "CreditHistoryType"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("resOccupant");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "ResOccupant"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "Occupant"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("yrsAtCurrentResidence");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "YrsAtCurrentResidence"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "short"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mosAtCurrentResidence");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "MosAtCurrentResidence"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "short"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("driverTraining");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "DriverTraining"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("validLicense");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "ValidLicense"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stateCurrentlyLicensed");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "StateCurrentlyLicensed"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("licenseNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "LicenseNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hasIncidents");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "HasIncidents"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("driverId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "DriverId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "short"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vehicleId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "VehicleId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "short"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("driversLicenseType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "DriversLicenseType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "DriversLicenseType"));
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
