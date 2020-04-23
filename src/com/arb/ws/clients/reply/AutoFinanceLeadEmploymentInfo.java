/**
 * AutoFinanceLeadEmploymentInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.arb.ws.clients.reply;

public class AutoFinanceLeadEmploymentInfo  implements java.io.Serializable {
    private java.lang.String employerName;

    private com.arb.ws.clients.reply.AutoFinanceLeadEmploymentInfoEmployerPhone employerPhone;

    private int yearsAtEmployer;

    private int monthsAtEmployer;

    private java.lang.String jobTitle;

    public AutoFinanceLeadEmploymentInfo() {
    }

    public AutoFinanceLeadEmploymentInfo(
           java.lang.String employerName,
           com.arb.ws.clients.reply.AutoFinanceLeadEmploymentInfoEmployerPhone employerPhone,
           int yearsAtEmployer,
           int monthsAtEmployer,
           java.lang.String jobTitle) {
           this.employerName = employerName;
           this.employerPhone = employerPhone;
           this.yearsAtEmployer = yearsAtEmployer;
           this.monthsAtEmployer = monthsAtEmployer;
           this.jobTitle = jobTitle;
    }


    /**
     * Gets the employerName value for this AutoFinanceLeadEmploymentInfo.
     * 
     * @return employerName
     */
    public java.lang.String getEmployerName() {
        return employerName;
    }


    /**
     * Sets the employerName value for this AutoFinanceLeadEmploymentInfo.
     * 
     * @param employerName
     */
    public void setEmployerName(java.lang.String employerName) {
        this.employerName = employerName;
    }


    /**
     * Gets the employerPhone value for this AutoFinanceLeadEmploymentInfo.
     * 
     * @return employerPhone
     */
    public com.arb.ws.clients.reply.AutoFinanceLeadEmploymentInfoEmployerPhone getEmployerPhone() {
        return employerPhone;
    }


    /**
     * Sets the employerPhone value for this AutoFinanceLeadEmploymentInfo.
     * 
     * @param employerPhone
     */
    public void setEmployerPhone(com.arb.ws.clients.reply.AutoFinanceLeadEmploymentInfoEmployerPhone employerPhone) {
        this.employerPhone = employerPhone;
    }


    /**
     * Gets the yearsAtEmployer value for this AutoFinanceLeadEmploymentInfo.
     * 
     * @return yearsAtEmployer
     */
    public int getYearsAtEmployer() {
        return yearsAtEmployer;
    }


    /**
     * Sets the yearsAtEmployer value for this AutoFinanceLeadEmploymentInfo.
     * 
     * @param yearsAtEmployer
     */
    public void setYearsAtEmployer(int yearsAtEmployer) {
        this.yearsAtEmployer = yearsAtEmployer;
    }


    /**
     * Gets the monthsAtEmployer value for this AutoFinanceLeadEmploymentInfo.
     * 
     * @return monthsAtEmployer
     */
    public int getMonthsAtEmployer() {
        return monthsAtEmployer;
    }


    /**
     * Sets the monthsAtEmployer value for this AutoFinanceLeadEmploymentInfo.
     * 
     * @param monthsAtEmployer
     */
    public void setMonthsAtEmployer(int monthsAtEmployer) {
        this.monthsAtEmployer = monthsAtEmployer;
    }


    /**
     * Gets the jobTitle value for this AutoFinanceLeadEmploymentInfo.
     * 
     * @return jobTitle
     */
    public java.lang.String getJobTitle() {
        return jobTitle;
    }


    /**
     * Sets the jobTitle value for this AutoFinanceLeadEmploymentInfo.
     * 
     * @param jobTitle
     */
    public void setJobTitle(java.lang.String jobTitle) {
        this.jobTitle = jobTitle;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AutoFinanceLeadEmploymentInfo)) return false;
        AutoFinanceLeadEmploymentInfo other = (AutoFinanceLeadEmploymentInfo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.employerName==null && other.getEmployerName()==null) || 
             (this.employerName!=null &&
              this.employerName.equals(other.getEmployerName()))) &&
            ((this.employerPhone==null && other.getEmployerPhone()==null) || 
             (this.employerPhone!=null &&
              this.employerPhone.equals(other.getEmployerPhone()))) &&
            this.yearsAtEmployer == other.getYearsAtEmployer() &&
            this.monthsAtEmployer == other.getMonthsAtEmployer() &&
            ((this.jobTitle==null && other.getJobTitle()==null) || 
             (this.jobTitle!=null &&
              this.jobTitle.equals(other.getJobTitle())));
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
        if (getEmployerName() != null) {
            _hashCode += getEmployerName().hashCode();
        }
        if (getEmployerPhone() != null) {
            _hashCode += getEmployerPhone().hashCode();
        }
        _hashCode += getYearsAtEmployer();
        _hashCode += getMonthsAtEmployer();
        if (getJobTitle() != null) {
            _hashCode += getJobTitle().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AutoFinanceLeadEmploymentInfo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", ">AutoFinanceLead>EmploymentInfo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("employerName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "EmployerName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("employerPhone");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "EmployerPhone"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", ">>AutoFinanceLead>EmploymentInfo>EmployerPhone"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("yearsAtEmployer");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "YearsAtEmployer"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("monthsAtEmployer");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "MonthsAtEmployer"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("jobTitle");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "JobTitle"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
