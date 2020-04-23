/**
 * OrganicAutoRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.arb.ws.clients.reply;

public class OrganicAutoRequest  implements java.io.Serializable {
    private com.arb.ws.clients.reply.OrganicAutoRequestAutomotiveLead automotiveLead;

    private com.arb.ws.clients.reply.OrganicAutoRequestUser user;

    private java.lang.String reservationToken;

    private boolean ignoreExpiration;

    public OrganicAutoRequest() {
    }

    public OrganicAutoRequest(
           com.arb.ws.clients.reply.OrganicAutoRequestAutomotiveLead automotiveLead,
           com.arb.ws.clients.reply.OrganicAutoRequestUser user,
           java.lang.String reservationToken,
           boolean ignoreExpiration) {
           this.automotiveLead = automotiveLead;
           this.user = user;
           this.reservationToken = reservationToken;
           this.ignoreExpiration = ignoreExpiration;
    }


    /**
     * Gets the automotiveLead value for this OrganicAutoRequest.
     * 
     * @return automotiveLead
     */
    public com.arb.ws.clients.reply.OrganicAutoRequestAutomotiveLead getAutomotiveLead() {
        return automotiveLead;
    }


    /**
     * Sets the automotiveLead value for this OrganicAutoRequest.
     * 
     * @param automotiveLead
     */
    public void setAutomotiveLead(com.arb.ws.clients.reply.OrganicAutoRequestAutomotiveLead automotiveLead) {
        this.automotiveLead = automotiveLead;
    }


    /**
     * Gets the user value for this OrganicAutoRequest.
     * 
     * @return user
     */
    public com.arb.ws.clients.reply.OrganicAutoRequestUser getUser() {
        return user;
    }


    /**
     * Sets the user value for this OrganicAutoRequest.
     * 
     * @param user
     */
    public void setUser(com.arb.ws.clients.reply.OrganicAutoRequestUser user) {
        this.user = user;
    }


    /**
     * Gets the reservationToken value for this OrganicAutoRequest.
     * 
     * @return reservationToken
     */
    public java.lang.String getReservationToken() {
        return reservationToken;
    }


    /**
     * Sets the reservationToken value for this OrganicAutoRequest.
     * 
     * @param reservationToken
     */
    public void setReservationToken(java.lang.String reservationToken) {
        this.reservationToken = reservationToken;
    }


    /**
     * Gets the ignoreExpiration value for this OrganicAutoRequest.
     * 
     * @return ignoreExpiration
     */
    public boolean isIgnoreExpiration() {
        return ignoreExpiration;
    }


    /**
     * Sets the ignoreExpiration value for this OrganicAutoRequest.
     * 
     * @param ignoreExpiration
     */
    public void setIgnoreExpiration(boolean ignoreExpiration) {
        this.ignoreExpiration = ignoreExpiration;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof OrganicAutoRequest)) return false;
        OrganicAutoRequest other = (OrganicAutoRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.automotiveLead==null && other.getAutomotiveLead()==null) || 
             (this.automotiveLead!=null &&
              this.automotiveLead.equals(other.getAutomotiveLead()))) &&
            ((this.user==null && other.getUser()==null) || 
             (this.user!=null &&
              this.user.equals(other.getUser()))) &&
            ((this.reservationToken==null && other.getReservationToken()==null) || 
             (this.reservationToken!=null &&
              this.reservationToken.equals(other.getReservationToken()))) &&
            this.ignoreExpiration == other.isIgnoreExpiration();
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
        if (getAutomotiveLead() != null) {
            _hashCode += getAutomotiveLead().hashCode();
        }
        if (getUser() != null) {
            _hashCode += getUser().hashCode();
        }
        if (getReservationToken() != null) {
            _hashCode += getReservationToken().hashCode();
        }
        _hashCode += (isIgnoreExpiration() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(OrganicAutoRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "OrganicAutoRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("automotiveLead");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "AutomotiveLead"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", ">OrganicAutoRequest>AutomotiveLead"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("user");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "User"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", ">OrganicAutoRequest>User"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reservationToken");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "ReservationToken"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ignoreExpiration");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "IgnoreExpiration"));
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
