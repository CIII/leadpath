/**
 * AutoFinanceRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.arb.ws.clients.reply;

public class AutoFinanceRequest  implements java.io.Serializable {
    private com.arb.ws.clients.reply.AutoFinanceLead autoFinanceLead;

    private com.arb.ws.clients.reply.AutoFinanceRequestUser user;

    private java.lang.String reservationToken;

    public AutoFinanceRequest() {
    }

    public AutoFinanceRequest(
           com.arb.ws.clients.reply.AutoFinanceLead autoFinanceLead,
           com.arb.ws.clients.reply.AutoFinanceRequestUser user,
           java.lang.String reservationToken) {
           this.autoFinanceLead = autoFinanceLead;
           this.user = user;
           this.reservationToken = reservationToken;
    }


    /**
     * Gets the autoFinanceLead value for this AutoFinanceRequest.
     * 
     * @return autoFinanceLead
     */
    public com.arb.ws.clients.reply.AutoFinanceLead getAutoFinanceLead() {
        return autoFinanceLead;
    }


    /**
     * Sets the autoFinanceLead value for this AutoFinanceRequest.
     * 
     * @param autoFinanceLead
     */
    public void setAutoFinanceLead(com.arb.ws.clients.reply.AutoFinanceLead autoFinanceLead) {
        this.autoFinanceLead = autoFinanceLead;
    }


    /**
     * Gets the user value for this AutoFinanceRequest.
     * 
     * @return user
     */
    public com.arb.ws.clients.reply.AutoFinanceRequestUser getUser() {
        return user;
    }


    /**
     * Sets the user value for this AutoFinanceRequest.
     * 
     * @param user
     */
    public void setUser(com.arb.ws.clients.reply.AutoFinanceRequestUser user) {
        this.user = user;
    }


    /**
     * Gets the reservationToken value for this AutoFinanceRequest.
     * 
     * @return reservationToken
     */
    public java.lang.String getReservationToken() {
        return reservationToken;
    }


    /**
     * Sets the reservationToken value for this AutoFinanceRequest.
     * 
     * @param reservationToken
     */
    public void setReservationToken(java.lang.String reservationToken) {
        this.reservationToken = reservationToken;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AutoFinanceRequest)) return false;
        AutoFinanceRequest other = (AutoFinanceRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.autoFinanceLead==null && other.getAutoFinanceLead()==null) || 
             (this.autoFinanceLead!=null &&
              this.autoFinanceLead.equals(other.getAutoFinanceLead()))) &&
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
        if (getAutoFinanceLead() != null) {
            _hashCode += getAutoFinanceLead().hashCode();
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
        new org.apache.axis.description.TypeDesc(AutoFinanceRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "AutoFinanceRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("autoFinanceLead");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "AutoFinanceLead"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "AutoFinanceLead"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("user");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "User"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", ">AutoFinanceRequest>User"));
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
