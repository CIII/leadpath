/**
 * Accident.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.arb.ws.clients.reply;

public class Accident  implements java.io.Serializable {
    private com.arb.ws.clients.reply.AccidentType accidentType;

    private com.arb.ws.clients.reply.DamageType damageType;

    private boolean driverAtFault;

    private int amountPaid;

    public Accident() {
    }

    public Accident(
           com.arb.ws.clients.reply.AccidentType accidentType,
           com.arb.ws.clients.reply.DamageType damageType,
           boolean driverAtFault,
           int amountPaid) {
           this.accidentType = accidentType;
           this.damageType = damageType;
           this.driverAtFault = driverAtFault;
           this.amountPaid = amountPaid;
    }


    /**
     * Gets the accidentType value for this Accident.
     * 
     * @return accidentType
     */
    public com.arb.ws.clients.reply.AccidentType getAccidentType() {
        return accidentType;
    }


    /**
     * Sets the accidentType value for this Accident.
     * 
     * @param accidentType
     */
    public void setAccidentType(com.arb.ws.clients.reply.AccidentType accidentType) {
        this.accidentType = accidentType;
    }


    /**
     * Gets the damageType value for this Accident.
     * 
     * @return damageType
     */
    public com.arb.ws.clients.reply.DamageType getDamageType() {
        return damageType;
    }


    /**
     * Sets the damageType value for this Accident.
     * 
     * @param damageType
     */
    public void setDamageType(com.arb.ws.clients.reply.DamageType damageType) {
        this.damageType = damageType;
    }


    /**
     * Gets the driverAtFault value for this Accident.
     * 
     * @return driverAtFault
     */
    public boolean isDriverAtFault() {
        return driverAtFault;
    }


    /**
     * Sets the driverAtFault value for this Accident.
     * 
     * @param driverAtFault
     */
    public void setDriverAtFault(boolean driverAtFault) {
        this.driverAtFault = driverAtFault;
    }


    /**
     * Gets the amountPaid value for this Accident.
     * 
     * @return amountPaid
     */
    public int getAmountPaid() {
        return amountPaid;
    }


    /**
     * Sets the amountPaid value for this Accident.
     * 
     * @param amountPaid
     */
    public void setAmountPaid(int amountPaid) {
        this.amountPaid = amountPaid;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Accident)) return false;
        Accident other = (Accident) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.accidentType==null && other.getAccidentType()==null) || 
             (this.accidentType!=null &&
              this.accidentType.equals(other.getAccidentType()))) &&
            ((this.damageType==null && other.getDamageType()==null) || 
             (this.damageType!=null &&
              this.damageType.equals(other.getDamageType()))) &&
            this.driverAtFault == other.isDriverAtFault() &&
            this.amountPaid == other.getAmountPaid();
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
        if (getAccidentType() != null) {
            _hashCode += getAccidentType().hashCode();
        }
        if (getDamageType() != null) {
            _hashCode += getDamageType().hashCode();
        }
        _hashCode += (isDriverAtFault() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += getAmountPaid();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Accident.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "Accident"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accidentType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "AccidentType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "AccidentType"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("damageType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "DamageType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "DamageType"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("driverAtFault");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "DriverAtFault"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("amountPaid");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "AmountPaid"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
