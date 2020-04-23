/**
 * AutoFinanceLeadPersonalInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.arb.ws.clients.reply;

public class AutoFinanceLeadPersonalInfo  implements java.io.Serializable {
    private java.lang.String SSN;

    private java.lang.String firstName;

    private java.lang.String lastName;

    private java.util.Calendar DOB;

    private org.apache.axis.types.UnsignedShort gender;

    private java.lang.Boolean bankruptcy;

    private java.lang.Boolean cosigner;

    private java.math.BigDecimal grossMonthlyIncome;

    public AutoFinanceLeadPersonalInfo() {
    }

    public AutoFinanceLeadPersonalInfo(
           java.lang.String SSN,
           java.lang.String firstName,
           java.lang.String lastName,
           java.util.Calendar DOB,
           org.apache.axis.types.UnsignedShort gender,
           java.lang.Boolean bankruptcy,
           java.lang.Boolean cosigner,
           java.math.BigDecimal grossMonthlyIncome) {
           this.SSN = SSN;
           this.firstName = firstName;
           this.lastName = lastName;
           this.DOB = DOB;
           this.gender = gender;
           this.bankruptcy = bankruptcy;
           this.cosigner = cosigner;
           this.grossMonthlyIncome = grossMonthlyIncome;
    }


    /**
     * Gets the SSN value for this AutoFinanceLeadPersonalInfo.
     * 
     * @return SSN
     */
    public java.lang.String getSSN() {
        return SSN;
    }


    /**
     * Sets the SSN value for this AutoFinanceLeadPersonalInfo.
     * 
     * @param SSN
     */
    public void setSSN(java.lang.String SSN) {
        this.SSN = SSN;
    }


    /**
     * Gets the firstName value for this AutoFinanceLeadPersonalInfo.
     * 
     * @return firstName
     */
    public java.lang.String getFirstName() {
        return firstName;
    }


    /**
     * Sets the firstName value for this AutoFinanceLeadPersonalInfo.
     * 
     * @param firstName
     */
    public void setFirstName(java.lang.String firstName) {
        this.firstName = firstName;
    }


    /**
     * Gets the lastName value for this AutoFinanceLeadPersonalInfo.
     * 
     * @return lastName
     */
    public java.lang.String getLastName() {
        return lastName;
    }


    /**
     * Sets the lastName value for this AutoFinanceLeadPersonalInfo.
     * 
     * @param lastName
     */
    public void setLastName(java.lang.String lastName) {
        this.lastName = lastName;
    }


    /**
     * Gets the DOB value for this AutoFinanceLeadPersonalInfo.
     * 
     * @return DOB
     */
    public java.util.Calendar getDOB() {
        return DOB;
    }


    /**
     * Sets the DOB value for this AutoFinanceLeadPersonalInfo.
     * 
     * @param DOB
     */
    public void setDOB(java.util.Calendar DOB) {
        this.DOB = DOB;
    }


    /**
     * Gets the gender value for this AutoFinanceLeadPersonalInfo.
     * 
     * @return gender
     */
    public org.apache.axis.types.UnsignedShort getGender() {
        return gender;
    }


    /**
     * Sets the gender value for this AutoFinanceLeadPersonalInfo.
     * 
     * @param gender
     */
    public void setGender(org.apache.axis.types.UnsignedShort gender) {
        this.gender = gender;
    }


    /**
     * Gets the bankruptcy value for this AutoFinanceLeadPersonalInfo.
     * 
     * @return bankruptcy
     */
    public java.lang.Boolean getBankruptcy() {
        return bankruptcy;
    }


    /**
     * Sets the bankruptcy value for this AutoFinanceLeadPersonalInfo.
     * 
     * @param bankruptcy
     */
    public void setBankruptcy(java.lang.Boolean bankruptcy) {
        this.bankruptcy = bankruptcy;
    }


    /**
     * Gets the cosigner value for this AutoFinanceLeadPersonalInfo.
     * 
     * @return cosigner
     */
    public java.lang.Boolean getCosigner() {
        return cosigner;
    }


    /**
     * Sets the cosigner value for this AutoFinanceLeadPersonalInfo.
     * 
     * @param cosigner
     */
    public void setCosigner(java.lang.Boolean cosigner) {
        this.cosigner = cosigner;
    }


    /**
     * Gets the grossMonthlyIncome value for this AutoFinanceLeadPersonalInfo.
     * 
     * @return grossMonthlyIncome
     */
    public java.math.BigDecimal getGrossMonthlyIncome() {
        return grossMonthlyIncome;
    }


    /**
     * Sets the grossMonthlyIncome value for this AutoFinanceLeadPersonalInfo.
     * 
     * @param grossMonthlyIncome
     */
    public void setGrossMonthlyIncome(java.math.BigDecimal grossMonthlyIncome) {
        this.grossMonthlyIncome = grossMonthlyIncome;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AutoFinanceLeadPersonalInfo)) return false;
        AutoFinanceLeadPersonalInfo other = (AutoFinanceLeadPersonalInfo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.SSN==null && other.getSSN()==null) || 
             (this.SSN!=null &&
              this.SSN.equals(other.getSSN()))) &&
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
            ((this.bankruptcy==null && other.getBankruptcy()==null) || 
             (this.bankruptcy!=null &&
              this.bankruptcy.equals(other.getBankruptcy()))) &&
            ((this.cosigner==null && other.getCosigner()==null) || 
             (this.cosigner!=null &&
              this.cosigner.equals(other.getCosigner()))) &&
            ((this.grossMonthlyIncome==null && other.getGrossMonthlyIncome()==null) || 
             (this.grossMonthlyIncome!=null &&
              this.grossMonthlyIncome.equals(other.getGrossMonthlyIncome())));
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
        if (getSSN() != null) {
            _hashCode += getSSN().hashCode();
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
        if (getBankruptcy() != null) {
            _hashCode += getBankruptcy().hashCode();
        }
        if (getCosigner() != null) {
            _hashCode += getCosigner().hashCode();
        }
        if (getGrossMonthlyIncome() != null) {
            _hashCode += getGrossMonthlyIncome().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AutoFinanceLeadPersonalInfo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", ">AutoFinanceLead>PersonalInfo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SSN");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "SSN"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
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
        elemField.setFieldName("DOB");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "DOB"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("gender");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "Gender"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "unsignedShort"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bankruptcy");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "Bankruptcy"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cosigner");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "Cosigner"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("grossMonthlyIncome");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "GrossMonthlyIncome"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
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
