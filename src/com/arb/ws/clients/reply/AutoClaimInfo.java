/**
 * AutoClaimInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.arb.ws.clients.reply;

public class AutoClaimInfo  extends com.arb.ws.clients.reply.ClaimInfo  implements java.io.Serializable {
    private com.arb.ws.clients.reply.AutoClaimType claimType;

    private com.arb.ws.clients.reply.DamageType damageType;

    public AutoClaimInfo() {
    }

    public AutoClaimInfo(
           java.util.Calendar claimDate,
           int amountPaid,
           com.arb.ws.clients.reply.AutoClaimType claimType,
           com.arb.ws.clients.reply.DamageType damageType) {
        super(
            claimDate,
            amountPaid);
        this.claimType = claimType;
        this.damageType = damageType;
    }


    /**
     * Gets the claimType value for this AutoClaimInfo.
     * 
     * @return claimType
     */
    public com.arb.ws.clients.reply.AutoClaimType getClaimType() {
        return claimType;
    }


    /**
     * Sets the claimType value for this AutoClaimInfo.
     * 
     * @param claimType
     */
    public void setClaimType(com.arb.ws.clients.reply.AutoClaimType claimType) {
        this.claimType = claimType;
    }


    /**
     * Gets the damageType value for this AutoClaimInfo.
     * 
     * @return damageType
     */
    public com.arb.ws.clients.reply.DamageType getDamageType() {
        return damageType;
    }


    /**
     * Sets the damageType value for this AutoClaimInfo.
     * 
     * @param damageType
     */
    public void setDamageType(com.arb.ws.clients.reply.DamageType damageType) {
        this.damageType = damageType;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AutoClaimInfo)) return false;
        AutoClaimInfo other = (AutoClaimInfo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.claimType==null && other.getClaimType()==null) || 
             (this.claimType!=null &&
              this.claimType.equals(other.getClaimType()))) &&
            ((this.damageType==null && other.getDamageType()==null) || 
             (this.damageType!=null &&
              this.damageType.equals(other.getDamageType())));
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
        if (getClaimType() != null) {
            _hashCode += getClaimType().hashCode();
        }
        if (getDamageType() != null) {
            _hashCode += getDamageType().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AutoClaimInfo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "AutoClaimInfo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("claimType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "ClaimType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "AutoClaimType"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("damageType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "DamageType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "DamageType"));
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
