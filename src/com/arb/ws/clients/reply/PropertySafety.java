/**
 * PropertySafety.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.arb.ws.clients.reply;

public class PropertySafety  implements java.io.Serializable {
    private com.arb.ws.clients.reply.SecuritySystem securitySystem;

    private com.arb.ws.clients.reply.FireAlarmSystem fireAlarmSystem;

    private boolean fireExtinguishers;

    private boolean deadboltLocks;

    private boolean swimmingPool;

    private boolean trampoline;

    private boolean coveredDeckOrPatio;

    public PropertySafety() {
    }

    public PropertySafety(
           com.arb.ws.clients.reply.SecuritySystem securitySystem,
           com.arb.ws.clients.reply.FireAlarmSystem fireAlarmSystem,
           boolean fireExtinguishers,
           boolean deadboltLocks,
           boolean swimmingPool,
           boolean trampoline,
           boolean coveredDeckOrPatio) {
           this.securitySystem = securitySystem;
           this.fireAlarmSystem = fireAlarmSystem;
           this.fireExtinguishers = fireExtinguishers;
           this.deadboltLocks = deadboltLocks;
           this.swimmingPool = swimmingPool;
           this.trampoline = trampoline;
           this.coveredDeckOrPatio = coveredDeckOrPatio;
    }


    /**
     * Gets the securitySystem value for this PropertySafety.
     * 
     * @return securitySystem
     */
    public com.arb.ws.clients.reply.SecuritySystem getSecuritySystem() {
        return securitySystem;
    }


    /**
     * Sets the securitySystem value for this PropertySafety.
     * 
     * @param securitySystem
     */
    public void setSecuritySystem(com.arb.ws.clients.reply.SecuritySystem securitySystem) {
        this.securitySystem = securitySystem;
    }


    /**
     * Gets the fireAlarmSystem value for this PropertySafety.
     * 
     * @return fireAlarmSystem
     */
    public com.arb.ws.clients.reply.FireAlarmSystem getFireAlarmSystem() {
        return fireAlarmSystem;
    }


    /**
     * Sets the fireAlarmSystem value for this PropertySafety.
     * 
     * @param fireAlarmSystem
     */
    public void setFireAlarmSystem(com.arb.ws.clients.reply.FireAlarmSystem fireAlarmSystem) {
        this.fireAlarmSystem = fireAlarmSystem;
    }


    /**
     * Gets the fireExtinguishers value for this PropertySafety.
     * 
     * @return fireExtinguishers
     */
    public boolean isFireExtinguishers() {
        return fireExtinguishers;
    }


    /**
     * Sets the fireExtinguishers value for this PropertySafety.
     * 
     * @param fireExtinguishers
     */
    public void setFireExtinguishers(boolean fireExtinguishers) {
        this.fireExtinguishers = fireExtinguishers;
    }


    /**
     * Gets the deadboltLocks value for this PropertySafety.
     * 
     * @return deadboltLocks
     */
    public boolean isDeadboltLocks() {
        return deadboltLocks;
    }


    /**
     * Sets the deadboltLocks value for this PropertySafety.
     * 
     * @param deadboltLocks
     */
    public void setDeadboltLocks(boolean deadboltLocks) {
        this.deadboltLocks = deadboltLocks;
    }


    /**
     * Gets the swimmingPool value for this PropertySafety.
     * 
     * @return swimmingPool
     */
    public boolean isSwimmingPool() {
        return swimmingPool;
    }


    /**
     * Sets the swimmingPool value for this PropertySafety.
     * 
     * @param swimmingPool
     */
    public void setSwimmingPool(boolean swimmingPool) {
        this.swimmingPool = swimmingPool;
    }


    /**
     * Gets the trampoline value for this PropertySafety.
     * 
     * @return trampoline
     */
    public boolean isTrampoline() {
        return trampoline;
    }


    /**
     * Sets the trampoline value for this PropertySafety.
     * 
     * @param trampoline
     */
    public void setTrampoline(boolean trampoline) {
        this.trampoline = trampoline;
    }


    /**
     * Gets the coveredDeckOrPatio value for this PropertySafety.
     * 
     * @return coveredDeckOrPatio
     */
    public boolean isCoveredDeckOrPatio() {
        return coveredDeckOrPatio;
    }


    /**
     * Sets the coveredDeckOrPatio value for this PropertySafety.
     * 
     * @param coveredDeckOrPatio
     */
    public void setCoveredDeckOrPatio(boolean coveredDeckOrPatio) {
        this.coveredDeckOrPatio = coveredDeckOrPatio;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PropertySafety)) return false;
        PropertySafety other = (PropertySafety) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.securitySystem==null && other.getSecuritySystem()==null) || 
             (this.securitySystem!=null &&
              this.securitySystem.equals(other.getSecuritySystem()))) &&
            ((this.fireAlarmSystem==null && other.getFireAlarmSystem()==null) || 
             (this.fireAlarmSystem!=null &&
              this.fireAlarmSystem.equals(other.getFireAlarmSystem()))) &&
            this.fireExtinguishers == other.isFireExtinguishers() &&
            this.deadboltLocks == other.isDeadboltLocks() &&
            this.swimmingPool == other.isSwimmingPool() &&
            this.trampoline == other.isTrampoline() &&
            this.coveredDeckOrPatio == other.isCoveredDeckOrPatio();
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
        if (getSecuritySystem() != null) {
            _hashCode += getSecuritySystem().hashCode();
        }
        if (getFireAlarmSystem() != null) {
            _hashCode += getFireAlarmSystem().hashCode();
        }
        _hashCode += (isFireExtinguishers() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isDeadboltLocks() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isSwimmingPool() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isTrampoline() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isCoveredDeckOrPatio() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PropertySafety.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "PropertySafety"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("securitySystem");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "SecuritySystem"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "SecuritySystem"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fireAlarmSystem");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "FireAlarmSystem"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "FireAlarmSystem"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fireExtinguishers");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "FireExtinguishers"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("deadboltLocks");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "DeadboltLocks"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("swimmingPool");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "SwimmingPool"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("trampoline");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "Trampoline"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("coveredDeckOrPatio");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "CoveredDeckOrPatio"));
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
