/**
 * AutoFinanceLead.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.arb.ws.clients.reply;

public class AutoFinanceLead  extends com.arb.ws.clients.reply.Lead  implements java.io.Serializable {
    private com.arb.ws.clients.reply.AutoFinanceLeadPersonalInfo personalInfo;

    private com.arb.ws.clients.reply.AutoFinanceLeadResidenceInfo residenceInfo;

    private com.arb.ws.clients.reply.AutoFinanceLeadEmploymentInfo employmentInfo;

    private com.arb.ws.clients.reply.AutoFinanceLeadAuthorizationsInfo authorizationsInfo;

    private java.math.BigDecimal loanAmount;

    private java.lang.Integer loanTermMonth;

    private com.arb.ws.clients.reply.AutoFinanceLeadCarType carType;

    public AutoFinanceLead() {
    }

    public AutoFinanceLead(
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
           com.arb.ws.clients.reply.AutoFinanceLeadPersonalInfo personalInfo,
           com.arb.ws.clients.reply.AutoFinanceLeadResidenceInfo residenceInfo,
           com.arb.ws.clients.reply.AutoFinanceLeadEmploymentInfo employmentInfo,
           com.arb.ws.clients.reply.AutoFinanceLeadAuthorizationsInfo authorizationsInfo,
           java.math.BigDecimal loanAmount,
           java.lang.Integer loanTermMonth,
           com.arb.ws.clients.reply.AutoFinanceLeadCarType carType) {
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
            consumerIP);
        this.personalInfo = personalInfo;
        this.residenceInfo = residenceInfo;
        this.employmentInfo = employmentInfo;
        this.authorizationsInfo = authorizationsInfo;
        this.loanAmount = loanAmount;
        this.loanTermMonth = loanTermMonth;
        this.carType = carType;
    }


    /**
     * Gets the personalInfo value for this AutoFinanceLead.
     * 
     * @return personalInfo
     */
    public com.arb.ws.clients.reply.AutoFinanceLeadPersonalInfo getPersonalInfo() {
        return personalInfo;
    }


    /**
     * Sets the personalInfo value for this AutoFinanceLead.
     * 
     * @param personalInfo
     */
    public void setPersonalInfo(com.arb.ws.clients.reply.AutoFinanceLeadPersonalInfo personalInfo) {
        this.personalInfo = personalInfo;
    }


    /**
     * Gets the residenceInfo value for this AutoFinanceLead.
     * 
     * @return residenceInfo
     */
    public com.arb.ws.clients.reply.AutoFinanceLeadResidenceInfo getResidenceInfo() {
        return residenceInfo;
    }


    /**
     * Sets the residenceInfo value for this AutoFinanceLead.
     * 
     * @param residenceInfo
     */
    public void setResidenceInfo(com.arb.ws.clients.reply.AutoFinanceLeadResidenceInfo residenceInfo) {
        this.residenceInfo = residenceInfo;
    }


    /**
     * Gets the employmentInfo value for this AutoFinanceLead.
     * 
     * @return employmentInfo
     */
    public com.arb.ws.clients.reply.AutoFinanceLeadEmploymentInfo getEmploymentInfo() {
        return employmentInfo;
    }


    /**
     * Sets the employmentInfo value for this AutoFinanceLead.
     * 
     * @param employmentInfo
     */
    public void setEmploymentInfo(com.arb.ws.clients.reply.AutoFinanceLeadEmploymentInfo employmentInfo) {
        this.employmentInfo = employmentInfo;
    }


    /**
     * Gets the authorizationsInfo value for this AutoFinanceLead.
     * 
     * @return authorizationsInfo
     */
    public com.arb.ws.clients.reply.AutoFinanceLeadAuthorizationsInfo getAuthorizationsInfo() {
        return authorizationsInfo;
    }


    /**
     * Sets the authorizationsInfo value for this AutoFinanceLead.
     * 
     * @param authorizationsInfo
     */
    public void setAuthorizationsInfo(com.arb.ws.clients.reply.AutoFinanceLeadAuthorizationsInfo authorizationsInfo) {
        this.authorizationsInfo = authorizationsInfo;
    }


    /**
     * Gets the loanAmount value for this AutoFinanceLead.
     * 
     * @return loanAmount
     */
    public java.math.BigDecimal getLoanAmount() {
        return loanAmount;
    }


    /**
     * Sets the loanAmount value for this AutoFinanceLead.
     * 
     * @param loanAmount
     */
    public void setLoanAmount(java.math.BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
    }


    /**
     * Gets the loanTermMonth value for this AutoFinanceLead.
     * 
     * @return loanTermMonth
     */
    public java.lang.Integer getLoanTermMonth() {
        return loanTermMonth;
    }


    /**
     * Sets the loanTermMonth value for this AutoFinanceLead.
     * 
     * @param loanTermMonth
     */
    public void setLoanTermMonth(java.lang.Integer loanTermMonth) {
        this.loanTermMonth = loanTermMonth;
    }


    /**
     * Gets the carType value for this AutoFinanceLead.
     * 
     * @return carType
     */
    public com.arb.ws.clients.reply.AutoFinanceLeadCarType getCarType() {
        return carType;
    }


    /**
     * Sets the carType value for this AutoFinanceLead.
     * 
     * @param carType
     */
    public void setCarType(com.arb.ws.clients.reply.AutoFinanceLeadCarType carType) {
        this.carType = carType;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AutoFinanceLead)) return false;
        AutoFinanceLead other = (AutoFinanceLead) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.personalInfo==null && other.getPersonalInfo()==null) || 
             (this.personalInfo!=null &&
              this.personalInfo.equals(other.getPersonalInfo()))) &&
            ((this.residenceInfo==null && other.getResidenceInfo()==null) || 
             (this.residenceInfo!=null &&
              this.residenceInfo.equals(other.getResidenceInfo()))) &&
            ((this.employmentInfo==null && other.getEmploymentInfo()==null) || 
             (this.employmentInfo!=null &&
              this.employmentInfo.equals(other.getEmploymentInfo()))) &&
            ((this.authorizationsInfo==null && other.getAuthorizationsInfo()==null) || 
             (this.authorizationsInfo!=null &&
              this.authorizationsInfo.equals(other.getAuthorizationsInfo()))) &&
            ((this.loanAmount==null && other.getLoanAmount()==null) || 
             (this.loanAmount!=null &&
              this.loanAmount.equals(other.getLoanAmount()))) &&
            ((this.loanTermMonth==null && other.getLoanTermMonth()==null) || 
             (this.loanTermMonth!=null &&
              this.loanTermMonth.equals(other.getLoanTermMonth()))) &&
            ((this.carType==null && other.getCarType()==null) || 
             (this.carType!=null &&
              this.carType.equals(other.getCarType())));
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
        if (getPersonalInfo() != null) {
            _hashCode += getPersonalInfo().hashCode();
        }
        if (getResidenceInfo() != null) {
            _hashCode += getResidenceInfo().hashCode();
        }
        if (getEmploymentInfo() != null) {
            _hashCode += getEmploymentInfo().hashCode();
        }
        if (getAuthorizationsInfo() != null) {
            _hashCode += getAuthorizationsInfo().hashCode();
        }
        if (getLoanAmount() != null) {
            _hashCode += getLoanAmount().hashCode();
        }
        if (getLoanTermMonth() != null) {
            _hashCode += getLoanTermMonth().hashCode();
        }
        if (getCarType() != null) {
            _hashCode += getCarType().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AutoFinanceLead.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "AutoFinanceLead"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("personalInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "PersonalInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", ">AutoFinanceLead>PersonalInfo"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("residenceInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "ResidenceInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", ">AutoFinanceLead>ResidenceInfo"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("employmentInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "EmploymentInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", ">AutoFinanceLead>EmploymentInfo"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("authorizationsInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "AuthorizationsInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", ">AutoFinanceLead>AuthorizationsInfo"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("loanAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "LoanAmount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("loanTermMonth");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "LoanTermMonth"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("carType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "CarType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", ">AutoFinanceLead>CarType"));
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
