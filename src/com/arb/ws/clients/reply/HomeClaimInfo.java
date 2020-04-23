/**
 * HomeClaimInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.arb.ws.clients.reply;

public class HomeClaimInfo  extends com.arb.ws.clients.reply.ClaimInfo  implements java.io.Serializable {
    private com.arb.ws.clients.reply.HomeClaimType claimType;

    public HomeClaimInfo() {
    }

    public HomeClaimInfo(
           java.util.Calendar claimDate,
           int amountPaid,
           com.arb.ws.clients.reply.HomeClaimType claimType) {
        super(
            claimDate,
            amountPaid);
        this.claimType = claimType;
    }


    /**
     * Gets the claimType value for this HomeClaimInfo.
     * 
     * @return claimType
     */
    public com.arb.ws.clients.reply.HomeClaimType getClaimType() {
        return claimType;
    }


    /**
     * Sets the claimType value for this HomeClaimInfo.
     * 
     * @param claimType
     */
    public void setClaimType(com.arb.ws.clients.reply.HomeClaimType claimType) {
        this.claimType = claimType;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof HomeClaimInfo)) return false;
        HomeClaimInfo other = (HomeClaimInfo) obj;
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
              this.claimType.equals(other.getClaimType())));
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
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(HomeClaimInfo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "HomeClaimInfo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("claimType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "ClaimType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "HomeClaimType"));
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
