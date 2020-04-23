/**
 * Policy.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.arb.ws.clients.reply;

public class Policy  implements java.io.Serializable {
    private java.util.Calendar requestDate;

    private java.util.Calendar policyExpDate;

    private java.lang.String currentInsCarrier;

    private java.lang.String firstName;

    private java.lang.String lastName;

    private java.lang.String address1;

    private java.lang.String address2;

    private java.lang.String city;

    private java.lang.String state;

    private java.lang.String dayPhoneNum;

    private java.lang.String evePhoneNum;

    private java.lang.String zipCode;

    private java.lang.String emailAddress;

    private com.arb.ws.clients.reply.CreditHistoryType creditHistory;

    private short yrsContinuouslyInsured;

    private short yrsCurrentlyInsured;

    private short mosContinuouslyInsured;

    private short mosCurrentlyInsured;

    private boolean recentClaim;

    private boolean replacementPolicy;

    public Policy() {
    }

    public Policy(
           java.util.Calendar requestDate,
           java.util.Calendar policyExpDate,
           java.lang.String currentInsCarrier,
           java.lang.String firstName,
           java.lang.String lastName,
           java.lang.String address1,
           java.lang.String address2,
           java.lang.String city,
           java.lang.String state,
           java.lang.String dayPhoneNum,
           java.lang.String evePhoneNum,
           java.lang.String zipCode,
           java.lang.String emailAddress,
           com.arb.ws.clients.reply.CreditHistoryType creditHistory,
           short yrsContinuouslyInsured,
           short yrsCurrentlyInsured,
           short mosContinuouslyInsured,
           short mosCurrentlyInsured,
           boolean recentClaim,
           boolean replacementPolicy) {
           this.requestDate = requestDate;
           this.policyExpDate = policyExpDate;
           this.currentInsCarrier = currentInsCarrier;
           this.firstName = firstName;
           this.lastName = lastName;
           this.address1 = address1;
           this.address2 = address2;
           this.city = city;
           this.state = state;
           this.dayPhoneNum = dayPhoneNum;
           this.evePhoneNum = evePhoneNum;
           this.zipCode = zipCode;
           this.emailAddress = emailAddress;
           this.creditHistory = creditHistory;
           this.yrsContinuouslyInsured = yrsContinuouslyInsured;
           this.yrsCurrentlyInsured = yrsCurrentlyInsured;
           this.mosContinuouslyInsured = mosContinuouslyInsured;
           this.mosCurrentlyInsured = mosCurrentlyInsured;
           this.recentClaim = recentClaim;
           this.replacementPolicy = replacementPolicy;
    }


    /**
     * Gets the requestDate value for this Policy.
     * 
     * @return requestDate
     */
    public java.util.Calendar getRequestDate() {
        return requestDate;
    }


    /**
     * Sets the requestDate value for this Policy.
     * 
     * @param requestDate
     */
    public void setRequestDate(java.util.Calendar requestDate) {
        this.requestDate = requestDate;
    }


    /**
     * Gets the policyExpDate value for this Policy.
     * 
     * @return policyExpDate
     */
    public java.util.Calendar getPolicyExpDate() {
        return policyExpDate;
    }


    /**
     * Sets the policyExpDate value for this Policy.
     * 
     * @param policyExpDate
     */
    public void setPolicyExpDate(java.util.Calendar policyExpDate) {
        this.policyExpDate = policyExpDate;
    }


    /**
     * Gets the currentInsCarrier value for this Policy.
     * 
     * @return currentInsCarrier
     */
    public java.lang.String getCurrentInsCarrier() {
        return currentInsCarrier;
    }


    /**
     * Sets the currentInsCarrier value for this Policy.
     * 
     * @param currentInsCarrier
     */
    public void setCurrentInsCarrier(java.lang.String currentInsCarrier) {
        this.currentInsCarrier = currentInsCarrier;
    }


    /**
     * Gets the firstName value for this Policy.
     * 
     * @return firstName
     */
    public java.lang.String getFirstName() {
        return firstName;
    }


    /**
     * Sets the firstName value for this Policy.
     * 
     * @param firstName
     */
    public void setFirstName(java.lang.String firstName) {
        this.firstName = firstName;
    }


    /**
     * Gets the lastName value for this Policy.
     * 
     * @return lastName
     */
    public java.lang.String getLastName() {
        return lastName;
    }


    /**
     * Sets the lastName value for this Policy.
     * 
     * @param lastName
     */
    public void setLastName(java.lang.String lastName) {
        this.lastName = lastName;
    }


    /**
     * Gets the address1 value for this Policy.
     * 
     * @return address1
     */
    public java.lang.String getAddress1() {
        return address1;
    }


    /**
     * Sets the address1 value for this Policy.
     * 
     * @param address1
     */
    public void setAddress1(java.lang.String address1) {
        this.address1 = address1;
    }


    /**
     * Gets the address2 value for this Policy.
     * 
     * @return address2
     */
    public java.lang.String getAddress2() {
        return address2;
    }


    /**
     * Sets the address2 value for this Policy.
     * 
     * @param address2
     */
    public void setAddress2(java.lang.String address2) {
        this.address2 = address2;
    }


    /**
     * Gets the city value for this Policy.
     * 
     * @return city
     */
    public java.lang.String getCity() {
        return city;
    }


    /**
     * Sets the city value for this Policy.
     * 
     * @param city
     */
    public void setCity(java.lang.String city) {
        this.city = city;
    }


    /**
     * Gets the state value for this Policy.
     * 
     * @return state
     */
    public java.lang.String getState() {
        return state;
    }


    /**
     * Sets the state value for this Policy.
     * 
     * @param state
     */
    public void setState(java.lang.String state) {
        this.state = state;
    }


    /**
     * Gets the dayPhoneNum value for this Policy.
     * 
     * @return dayPhoneNum
     */
    public java.lang.String getDayPhoneNum() {
        return dayPhoneNum;
    }


    /**
     * Sets the dayPhoneNum value for this Policy.
     * 
     * @param dayPhoneNum
     */
    public void setDayPhoneNum(java.lang.String dayPhoneNum) {
        this.dayPhoneNum = dayPhoneNum;
    }


    /**
     * Gets the evePhoneNum value for this Policy.
     * 
     * @return evePhoneNum
     */
    public java.lang.String getEvePhoneNum() {
        return evePhoneNum;
    }


    /**
     * Sets the evePhoneNum value for this Policy.
     * 
     * @param evePhoneNum
     */
    public void setEvePhoneNum(java.lang.String evePhoneNum) {
        this.evePhoneNum = evePhoneNum;
    }


    /**
     * Gets the zipCode value for this Policy.
     * 
     * @return zipCode
     */
    public java.lang.String getZipCode() {
        return zipCode;
    }


    /**
     * Sets the zipCode value for this Policy.
     * 
     * @param zipCode
     */
    public void setZipCode(java.lang.String zipCode) {
        this.zipCode = zipCode;
    }


    /**
     * Gets the emailAddress value for this Policy.
     * 
     * @return emailAddress
     */
    public java.lang.String getEmailAddress() {
        return emailAddress;
    }


    /**
     * Sets the emailAddress value for this Policy.
     * 
     * @param emailAddress
     */
    public void setEmailAddress(java.lang.String emailAddress) {
        this.emailAddress = emailAddress;
    }


    /**
     * Gets the creditHistory value for this Policy.
     * 
     * @return creditHistory
     */
    public com.arb.ws.clients.reply.CreditHistoryType getCreditHistory() {
        return creditHistory;
    }


    /**
     * Sets the creditHistory value for this Policy.
     * 
     * @param creditHistory
     */
    public void setCreditHistory(com.arb.ws.clients.reply.CreditHistoryType creditHistory) {
        this.creditHistory = creditHistory;
    }


    /**
     * Gets the yrsContinuouslyInsured value for this Policy.
     * 
     * @return yrsContinuouslyInsured
     */
    public short getYrsContinuouslyInsured() {
        return yrsContinuouslyInsured;
    }


    /**
     * Sets the yrsContinuouslyInsured value for this Policy.
     * 
     * @param yrsContinuouslyInsured
     */
    public void setYrsContinuouslyInsured(short yrsContinuouslyInsured) {
        this.yrsContinuouslyInsured = yrsContinuouslyInsured;
    }


    /**
     * Gets the yrsCurrentlyInsured value for this Policy.
     * 
     * @return yrsCurrentlyInsured
     */
    public short getYrsCurrentlyInsured() {
        return yrsCurrentlyInsured;
    }


    /**
     * Sets the yrsCurrentlyInsured value for this Policy.
     * 
     * @param yrsCurrentlyInsured
     */
    public void setYrsCurrentlyInsured(short yrsCurrentlyInsured) {
        this.yrsCurrentlyInsured = yrsCurrentlyInsured;
    }


    /**
     * Gets the mosContinuouslyInsured value for this Policy.
     * 
     * @return mosContinuouslyInsured
     */
    public short getMosContinuouslyInsured() {
        return mosContinuouslyInsured;
    }


    /**
     * Sets the mosContinuouslyInsured value for this Policy.
     * 
     * @param mosContinuouslyInsured
     */
    public void setMosContinuouslyInsured(short mosContinuouslyInsured) {
        this.mosContinuouslyInsured = mosContinuouslyInsured;
    }


    /**
     * Gets the mosCurrentlyInsured value for this Policy.
     * 
     * @return mosCurrentlyInsured
     */
    public short getMosCurrentlyInsured() {
        return mosCurrentlyInsured;
    }


    /**
     * Sets the mosCurrentlyInsured value for this Policy.
     * 
     * @param mosCurrentlyInsured
     */
    public void setMosCurrentlyInsured(short mosCurrentlyInsured) {
        this.mosCurrentlyInsured = mosCurrentlyInsured;
    }


    /**
     * Gets the recentClaim value for this Policy.
     * 
     * @return recentClaim
     */
    public boolean isRecentClaim() {
        return recentClaim;
    }


    /**
     * Sets the recentClaim value for this Policy.
     * 
     * @param recentClaim
     */
    public void setRecentClaim(boolean recentClaim) {
        this.recentClaim = recentClaim;
    }


    /**
     * Gets the replacementPolicy value for this Policy.
     * 
     * @return replacementPolicy
     */
    public boolean isReplacementPolicy() {
        return replacementPolicy;
    }


    /**
     * Sets the replacementPolicy value for this Policy.
     * 
     * @param replacementPolicy
     */
    public void setReplacementPolicy(boolean replacementPolicy) {
        this.replacementPolicy = replacementPolicy;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Policy)) return false;
        Policy other = (Policy) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.requestDate==null && other.getRequestDate()==null) || 
             (this.requestDate!=null &&
              this.requestDate.equals(other.getRequestDate()))) &&
            ((this.policyExpDate==null && other.getPolicyExpDate()==null) || 
             (this.policyExpDate!=null &&
              this.policyExpDate.equals(other.getPolicyExpDate()))) &&
            ((this.currentInsCarrier==null && other.getCurrentInsCarrier()==null) || 
             (this.currentInsCarrier!=null &&
              this.currentInsCarrier.equals(other.getCurrentInsCarrier()))) &&
            ((this.firstName==null && other.getFirstName()==null) || 
             (this.firstName!=null &&
              this.firstName.equals(other.getFirstName()))) &&
            ((this.lastName==null && other.getLastName()==null) || 
             (this.lastName!=null &&
              this.lastName.equals(other.getLastName()))) &&
            ((this.address1==null && other.getAddress1()==null) || 
             (this.address1!=null &&
              this.address1.equals(other.getAddress1()))) &&
            ((this.address2==null && other.getAddress2()==null) || 
             (this.address2!=null &&
              this.address2.equals(other.getAddress2()))) &&
            ((this.city==null && other.getCity()==null) || 
             (this.city!=null &&
              this.city.equals(other.getCity()))) &&
            ((this.state==null && other.getState()==null) || 
             (this.state!=null &&
              this.state.equals(other.getState()))) &&
            ((this.dayPhoneNum==null && other.getDayPhoneNum()==null) || 
             (this.dayPhoneNum!=null &&
              this.dayPhoneNum.equals(other.getDayPhoneNum()))) &&
            ((this.evePhoneNum==null && other.getEvePhoneNum()==null) || 
             (this.evePhoneNum!=null &&
              this.evePhoneNum.equals(other.getEvePhoneNum()))) &&
            ((this.zipCode==null && other.getZipCode()==null) || 
             (this.zipCode!=null &&
              this.zipCode.equals(other.getZipCode()))) &&
            ((this.emailAddress==null && other.getEmailAddress()==null) || 
             (this.emailAddress!=null &&
              this.emailAddress.equals(other.getEmailAddress()))) &&
            ((this.creditHistory==null && other.getCreditHistory()==null) || 
             (this.creditHistory!=null &&
              this.creditHistory.equals(other.getCreditHistory()))) &&
            this.yrsContinuouslyInsured == other.getYrsContinuouslyInsured() &&
            this.yrsCurrentlyInsured == other.getYrsCurrentlyInsured() &&
            this.mosContinuouslyInsured == other.getMosContinuouslyInsured() &&
            this.mosCurrentlyInsured == other.getMosCurrentlyInsured() &&
            this.recentClaim == other.isRecentClaim() &&
            this.replacementPolicy == other.isReplacementPolicy();
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
        if (getRequestDate() != null) {
            _hashCode += getRequestDate().hashCode();
        }
        if (getPolicyExpDate() != null) {
            _hashCode += getPolicyExpDate().hashCode();
        }
        if (getCurrentInsCarrier() != null) {
            _hashCode += getCurrentInsCarrier().hashCode();
        }
        if (getFirstName() != null) {
            _hashCode += getFirstName().hashCode();
        }
        if (getLastName() != null) {
            _hashCode += getLastName().hashCode();
        }
        if (getAddress1() != null) {
            _hashCode += getAddress1().hashCode();
        }
        if (getAddress2() != null) {
            _hashCode += getAddress2().hashCode();
        }
        if (getCity() != null) {
            _hashCode += getCity().hashCode();
        }
        if (getState() != null) {
            _hashCode += getState().hashCode();
        }
        if (getDayPhoneNum() != null) {
            _hashCode += getDayPhoneNum().hashCode();
        }
        if (getEvePhoneNum() != null) {
            _hashCode += getEvePhoneNum().hashCode();
        }
        if (getZipCode() != null) {
            _hashCode += getZipCode().hashCode();
        }
        if (getEmailAddress() != null) {
            _hashCode += getEmailAddress().hashCode();
        }
        if (getCreditHistory() != null) {
            _hashCode += getCreditHistory().hashCode();
        }
        _hashCode += getYrsContinuouslyInsured();
        _hashCode += getYrsCurrentlyInsured();
        _hashCode += getMosContinuouslyInsured();
        _hashCode += getMosCurrentlyInsured();
        _hashCode += (isRecentClaim() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isReplacementPolicy() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Policy.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "Policy"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("requestDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "RequestDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("policyExpDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "PolicyExpDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("currentInsCarrier");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "CurrentInsCarrier"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("firstName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "FirstName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lastName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "LastName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("address1");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "Address1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("address2");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "Address2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("city");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "City"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("state");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "State"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dayPhoneNum");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "DayPhoneNum"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("evePhoneNum");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "EvePhoneNum"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("zipCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "ZipCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("emailAddress");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "EmailAddress"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("creditHistory");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "CreditHistory"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "CreditHistoryType"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("yrsContinuouslyInsured");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "YrsContinuouslyInsured"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "short"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("yrsCurrentlyInsured");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "YrsCurrentlyInsured"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "short"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mosContinuouslyInsured");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "MosContinuouslyInsured"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "short"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mosCurrentlyInsured");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "MosCurrentlyInsured"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "short"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("recentClaim");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "RecentClaim"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("replacementPolicy");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "ReplacementPolicy"));
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
