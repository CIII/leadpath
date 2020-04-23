/**
 * HomeInsurancePolicy.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.arb.ws.clients.reply;

public class HomeInsurancePolicy  extends com.arb.ws.clients.reply.Policy  implements java.io.Serializable {
    private java.util.Calendar DOB;

    private java.lang.String dwellingReplCost;

    private java.lang.String personalLiab;

    private java.lang.String deductible;

    private java.lang.String persPropReplacementCost;

    private com.arb.ws.clients.reply.Gender gender;

    public HomeInsurancePolicy() {
    }

    public HomeInsurancePolicy(
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
           boolean replacementPolicy,
           java.util.Calendar DOB,
           java.lang.String dwellingReplCost,
           java.lang.String personalLiab,
           java.lang.String deductible,
           java.lang.String persPropReplacementCost,
           com.arb.ws.clients.reply.Gender gender) {
        super(
            requestDate,
            policyExpDate,
            currentInsCarrier,
            firstName,
            lastName,
            address1,
            address2,
            city,
            state,
            dayPhoneNum,
            evePhoneNum,
            zipCode,
            emailAddress,
            creditHistory,
            yrsContinuouslyInsured,
            yrsCurrentlyInsured,
            mosContinuouslyInsured,
            mosCurrentlyInsured,
            recentClaim,
            replacementPolicy);
        this.DOB = DOB;
        this.dwellingReplCost = dwellingReplCost;
        this.personalLiab = personalLiab;
        this.deductible = deductible;
        this.persPropReplacementCost = persPropReplacementCost;
        this.gender = gender;
    }


    /**
     * Gets the DOB value for this HomeInsurancePolicy.
     * 
     * @return DOB
     */
    public java.util.Calendar getDOB() {
        return DOB;
    }


    /**
     * Sets the DOB value for this HomeInsurancePolicy.
     * 
     * @param DOB
     */
    public void setDOB(java.util.Calendar DOB) {
        this.DOB = DOB;
    }


    /**
     * Gets the dwellingReplCost value for this HomeInsurancePolicy.
     * 
     * @return dwellingReplCost
     */
    public java.lang.String getDwellingReplCost() {
        return dwellingReplCost;
    }


    /**
     * Sets the dwellingReplCost value for this HomeInsurancePolicy.
     * 
     * @param dwellingReplCost
     */
    public void setDwellingReplCost(java.lang.String dwellingReplCost) {
        this.dwellingReplCost = dwellingReplCost;
    }


    /**
     * Gets the personalLiab value for this HomeInsurancePolicy.
     * 
     * @return personalLiab
     */
    public java.lang.String getPersonalLiab() {
        return personalLiab;
    }


    /**
     * Sets the personalLiab value for this HomeInsurancePolicy.
     * 
     * @param personalLiab
     */
    public void setPersonalLiab(java.lang.String personalLiab) {
        this.personalLiab = personalLiab;
    }


    /**
     * Gets the deductible value for this HomeInsurancePolicy.
     * 
     * @return deductible
     */
    public java.lang.String getDeductible() {
        return deductible;
    }


    /**
     * Sets the deductible value for this HomeInsurancePolicy.
     * 
     * @param deductible
     */
    public void setDeductible(java.lang.String deductible) {
        this.deductible = deductible;
    }


    /**
     * Gets the persPropReplacementCost value for this HomeInsurancePolicy.
     * 
     * @return persPropReplacementCost
     */
    public java.lang.String getPersPropReplacementCost() {
        return persPropReplacementCost;
    }


    /**
     * Sets the persPropReplacementCost value for this HomeInsurancePolicy.
     * 
     * @param persPropReplacementCost
     */
    public void setPersPropReplacementCost(java.lang.String persPropReplacementCost) {
        this.persPropReplacementCost = persPropReplacementCost;
    }


    /**
     * Gets the gender value for this HomeInsurancePolicy.
     * 
     * @return gender
     */
    public com.arb.ws.clients.reply.Gender getGender() {
        return gender;
    }


    /**
     * Sets the gender value for this HomeInsurancePolicy.
     * 
     * @param gender
     */
    public void setGender(com.arb.ws.clients.reply.Gender gender) {
        this.gender = gender;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof HomeInsurancePolicy)) return false;
        HomeInsurancePolicy other = (HomeInsurancePolicy) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.DOB==null && other.getDOB()==null) || 
             (this.DOB!=null &&
              this.DOB.equals(other.getDOB()))) &&
            ((this.dwellingReplCost==null && other.getDwellingReplCost()==null) || 
             (this.dwellingReplCost!=null &&
              this.dwellingReplCost.equals(other.getDwellingReplCost()))) &&
            ((this.personalLiab==null && other.getPersonalLiab()==null) || 
             (this.personalLiab!=null &&
              this.personalLiab.equals(other.getPersonalLiab()))) &&
            ((this.deductible==null && other.getDeductible()==null) || 
             (this.deductible!=null &&
              this.deductible.equals(other.getDeductible()))) &&
            ((this.persPropReplacementCost==null && other.getPersPropReplacementCost()==null) || 
             (this.persPropReplacementCost!=null &&
              this.persPropReplacementCost.equals(other.getPersPropReplacementCost()))) &&
            ((this.gender==null && other.getGender()==null) || 
             (this.gender!=null &&
              this.gender.equals(other.getGender())));
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
        if (getDOB() != null) {
            _hashCode += getDOB().hashCode();
        }
        if (getDwellingReplCost() != null) {
            _hashCode += getDwellingReplCost().hashCode();
        }
        if (getPersonalLiab() != null) {
            _hashCode += getPersonalLiab().hashCode();
        }
        if (getDeductible() != null) {
            _hashCode += getDeductible().hashCode();
        }
        if (getPersPropReplacementCost() != null) {
            _hashCode += getPersPropReplacementCost().hashCode();
        }
        if (getGender() != null) {
            _hashCode += getGender().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(HomeInsurancePolicy.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "HomeInsurancePolicy"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("DOB");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "DOB"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dwellingReplCost");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "DwellingReplCost"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("personalLiab");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "PersonalLiab"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("deductible");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "Deductible"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("persPropReplacementCost");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "PersPropReplacementCost"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("gender");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "Gender"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "Gender"));
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
