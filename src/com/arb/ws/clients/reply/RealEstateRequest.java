/**
 * RealEstateRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.arb.ws.clients.reply;

public class RealEstateRequest  implements java.io.Serializable {
    private com.arb.ws.clients.reply.RealEstateLead realEstateLead;

    private com.arb.ws.clients.reply.RealEstateRequestUser user;

    private java.lang.String reservationToken;

    public RealEstateRequest() {
    }

    public RealEstateRequest(
           com.arb.ws.clients.reply.RealEstateLead realEstateLead,
           com.arb.ws.clients.reply.RealEstateRequestUser user,
           java.lang.String reservationToken) {
           this.realEstateLead = realEstateLead;
           this.user = user;
           this.reservationToken = reservationToken;
    }


    /**
     * Gets the realEstateLead value for this RealEstateRequest.
     * 
     * @return realEstateLead
     */
    public com.arb.ws.clients.reply.RealEstateLead getRealEstateLead() {
        return realEstateLead;
    }


    /**
     * Sets the realEstateLead value for this RealEstateRequest.
     * 
     * @param realEstateLead
     */
    public void setRealEstateLead(com.arb.ws.clients.reply.RealEstateLead realEstateLead) {
        this.realEstateLead = realEstateLead;
    }


    /**
     * Gets the user value for this RealEstateRequest.
     * 
     * @return user
     */
    public com.arb.ws.clients.reply.RealEstateRequestUser getUser() {
        return user;
    }


    /**
     * Sets the user value for this RealEstateRequest.
     * 
     * @param user
     */
    public void setUser(com.arb.ws.clients.reply.RealEstateRequestUser user) {
        this.user = user;
    }


    /**
     * Gets the reservationToken value for this RealEstateRequest.
     * 
     * @return reservationToken
     */
    public java.lang.String getReservationToken() {
        return reservationToken;
    }


    /**
     * Sets the reservationToken value for this RealEstateRequest.
     * 
     * @param reservationToken
     */
    public void setReservationToken(java.lang.String reservationToken) {
        this.reservationToken = reservationToken;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RealEstateRequest)) return false;
        RealEstateRequest other = (RealEstateRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.realEstateLead==null && other.getRealEstateLead()==null) || 
             (this.realEstateLead!=null &&
              this.realEstateLead.equals(other.getRealEstateLead()))) &&
            ((this.user==null && other.getUser()==null) || 
             (this.user!=null &&
              this.user.equals(other.getUser()))) &&
            ((this.reservationToken==null && other.getReservationToken()==null) || 
             (this.reservationToken!=null &&
              this.reservationToken.equals(other.getReservationToken())));
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
        if (getRealEstateLead() != null) {
            _hashCode += getRealEstateLead().hashCode();
        }
        if (getUser() != null) {
            _hashCode += getUser().hashCode();
        }
        if (getReservationToken() != null) {
            _hashCode += getReservationToken().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RealEstateRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "RealEstateRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("realEstateLead");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "RealEstateLead"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "RealEstateLead"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("user");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "User"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", ">RealEstateRequest>User"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reservationToken");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "ReservationToken"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
