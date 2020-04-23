/**
 * AutoFinanceLeadResidenceInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.arb.ws.clients.reply;

public class AutoFinanceLeadResidenceInfo  implements java.io.Serializable {
    private com.arb.ws.clients.reply.AutoFinanceLeadResidenceInfoType type;

    private com.arb.ws.clients.reply.AutoFinanceLeadResidenceInfoAddress address;

    private int yearsAtResidence;

    private int monthAtResidence;

    private java.math.BigDecimal monthlyPayment;

    public AutoFinanceLeadResidenceInfo() {
    }

    public AutoFinanceLeadResidenceInfo(
           com.arb.ws.clients.reply.AutoFinanceLeadResidenceInfoType type,
           com.arb.ws.clients.reply.AutoFinanceLeadResidenceInfoAddress address,
           int yearsAtResidence,
           int monthAtResidence,
           java.math.BigDecimal monthlyPayment) {
           this.type = type;
           this.address = address;
           this.yearsAtResidence = yearsAtResidence;
           this.monthAtResidence = monthAtResidence;
           this.monthlyPayment = monthlyPayment;
    }


    /**
     * Gets the type value for this AutoFinanceLeadResidenceInfo.
     * 
     * @return type
     */
    public com.arb.ws.clients.reply.AutoFinanceLeadResidenceInfoType getType() {
        return type;
    }


    /**
     * Sets the type value for this AutoFinanceLeadResidenceInfo.
     * 
     * @param type
     */
    public void setType(com.arb.ws.clients.reply.AutoFinanceLeadResidenceInfoType type) {
        this.type = type;
    }


    /**
     * Gets the address value for this AutoFinanceLeadResidenceInfo.
     * 
     * @return address
     */
    public com.arb.ws.clients.reply.AutoFinanceLeadResidenceInfoAddress getAddress() {
        return address;
    }


    /**
     * Sets the address value for this AutoFinanceLeadResidenceInfo.
     * 
     * @param address
     */
    public void setAddress(com.arb.ws.clients.reply.AutoFinanceLeadResidenceInfoAddress address) {
        this.address = address;
    }


    /**
     * Gets the yearsAtResidence value for this AutoFinanceLeadResidenceInfo.
     * 
     * @return yearsAtResidence
     */
    public int getYearsAtResidence() {
        return yearsAtResidence;
    }


    /**
     * Sets the yearsAtResidence value for this AutoFinanceLeadResidenceInfo.
     * 
     * @param yearsAtResidence
     */
    public void setYearsAtResidence(int yearsAtResidence) {
        this.yearsAtResidence = yearsAtResidence;
    }


    /**
     * Gets the monthAtResidence value for this AutoFinanceLeadResidenceInfo.
     * 
     * @return monthAtResidence
     */
    public int getMonthAtResidence() {
        return monthAtResidence;
    }


    /**
     * Sets the monthAtResidence value for this AutoFinanceLeadResidenceInfo.
     * 
     * @param monthAtResidence
     */
    public void setMonthAtResidence(int monthAtResidence) {
        this.monthAtResidence = monthAtResidence;
    }


    /**
     * Gets the monthlyPayment value for this AutoFinanceLeadResidenceInfo.
     * 
     * @return monthlyPayment
     */
    public java.math.BigDecimal getMonthlyPayment() {
        return monthlyPayment;
    }


    /**
     * Sets the monthlyPayment value for this AutoFinanceLeadResidenceInfo.
     * 
     * @param monthlyPayment
     */
    public void setMonthlyPayment(java.math.BigDecimal monthlyPayment) {
        this.monthlyPayment = monthlyPayment;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AutoFinanceLeadResidenceInfo)) return false;
        AutoFinanceLeadResidenceInfo other = (AutoFinanceLeadResidenceInfo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.type==null && other.getType()==null) || 
             (this.type!=null &&
              this.type.equals(other.getType()))) &&
            ((this.address==null && other.getAddress()==null) || 
             (this.address!=null &&
              this.address.equals(other.getAddress()))) &&
            this.yearsAtResidence == other.getYearsAtResidence() &&
            this.monthAtResidence == other.getMonthAtResidence() &&
            ((this.monthlyPayment==null && other.getMonthlyPayment()==null) || 
             (this.monthlyPayment!=null &&
              this.monthlyPayment.equals(other.getMonthlyPayment())));
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
        if (getType() != null) {
            _hashCode += getType().hashCode();
        }
        if (getAddress() != null) {
            _hashCode += getAddress().hashCode();
        }
        _hashCode += getYearsAtResidence();
        _hashCode += getMonthAtResidence();
        if (getMonthlyPayment() != null) {
            _hashCode += getMonthlyPayment().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AutoFinanceLeadResidenceInfo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", ">AutoFinanceLead>ResidenceInfo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("type");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "Type"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", ">>AutoFinanceLead>ResidenceInfo>Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("address");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "Address"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", ">>AutoFinanceLead>ResidenceInfo>Address"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("yearsAtResidence");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "YearsAtResidence"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("monthAtResidence");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "MonthAtResidence"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("monthlyPayment");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "MonthlyPayment"));
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
