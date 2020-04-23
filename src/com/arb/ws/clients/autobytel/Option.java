/**
 * Option.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.arb.ws.clients.autobytel;

public class Option  implements java.io.Serializable {
    private java.lang.String optionCode;

    private boolean optionFlag;

    public Option() {
    }

    public Option(
           java.lang.String optionCode,
           boolean optionFlag) {
           this.optionCode = optionCode;
           this.optionFlag = optionFlag;
    }


    /**
     * Gets the optionCode value for this Option.
     * 
     * @return optionCode
     */
    public java.lang.String getOptionCode() {
        return optionCode;
    }


    /**
     * Sets the optionCode value for this Option.
     * 
     * @param optionCode
     */
    public void setOptionCode(java.lang.String optionCode) {
        this.optionCode = optionCode;
    }


    /**
     * Gets the optionFlag value for this Option.
     * 
     * @return optionFlag
     */
    public boolean isOptionFlag() {
        return optionFlag;
    }


    /**
     * Sets the optionFlag value for this Option.
     * 
     * @param optionFlag
     */
    public void setOptionFlag(boolean optionFlag) {
        this.optionFlag = optionFlag;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Option)) return false;
        Option other = (Option) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.optionCode==null && other.getOptionCode()==null) || 
             (this.optionCode!=null &&
              this.optionCode.equals(other.getOptionCode()))) &&
            this.optionFlag == other.isOptionFlag();
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
        if (getOptionCode() != null) {
            _hashCode += getOptionCode().hashCode();
        }
        _hashCode += (isOptionFlag() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Option.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.autobytel.com/", "Option"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("optionCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.autobytel.com/", "OptionCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("optionFlag");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.autobytel.com/", "OptionFlag"));
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
