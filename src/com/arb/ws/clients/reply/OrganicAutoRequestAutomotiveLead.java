/**
 * OrganicAutoRequestAutomotiveLead.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.arb.ws.clients.reply;

public class OrganicAutoRequestAutomotiveLead  extends com.arb.ws.clients.reply.AutomotiveLead  implements java.io.Serializable {
    private com.arb.ws.clients.reply.OrganicAutoRequestAutomotiveLeadRating rating;

    public OrganicAutoRequestAutomotiveLead() {
    }

    public OrganicAutoRequestAutomotiveLead(
           java.lang.String zipcode,
           com.arb.ws.clients.reply.LeadContact contact,
           long LCPId,
           java.util.Calendar collectionDate,
           long leadId,
           java.lang.String legsSold,
           java.lang.String legsAvailable,
           java.lang.String exclusive,
           java.lang.String reservePrice,
           java.lang.String trackingId,
           java.lang.String sourceId,
           java.lang.String position,
           java.lang.String leadPoolId,
           java.lang.String consumerIP,
           com.arb.ws.clients.reply.AutomotiveLeadLeadType leadType,
           java.lang.String make,
           java.lang.String model,
           java.lang.String year,
           java.lang.String trim,
           java.lang.String exteriorColor,
           java.lang.String interiorColor,
           java.lang.String transmission,
           boolean isDealerSelect,
           boolean tradeIn,
           java.lang.String[] zipbacks,
           java.lang.String[] dealerSelect,
           java.lang.String sequenceDupe,
           java.lang.String timeframe,
           com.arb.ws.clients.reply.OrganicAutoRequestAutomotiveLeadRating rating) {
        super(
            zipcode,
            contact,
            LCPId,
            collectionDate,
            leadId,
            legsSold,
            legsAvailable,
            exclusive,
            reservePrice,
            trackingId,
            sourceId,
            position,
            leadPoolId,
            consumerIP,
            leadType,
            make,
            model,
            year,
            trim,
            exteriorColor,
            interiorColor,
            transmission,
            isDealerSelect,
            tradeIn,
            zipbacks,
            dealerSelect,
            sequenceDupe,
            timeframe);
        this.rating = rating;
    }


    /**
     * Gets the rating value for this OrganicAutoRequestAutomotiveLead.
     * 
     * @return rating
     */
    public com.arb.ws.clients.reply.OrganicAutoRequestAutomotiveLeadRating getRating() {
        return rating;
    }


    /**
     * Sets the rating value for this OrganicAutoRequestAutomotiveLead.
     * 
     * @param rating
     */
    public void setRating(com.arb.ws.clients.reply.OrganicAutoRequestAutomotiveLeadRating rating) {
        this.rating = rating;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof OrganicAutoRequestAutomotiveLead)) return false;
        OrganicAutoRequestAutomotiveLead other = (OrganicAutoRequestAutomotiveLead) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.rating==null && other.getRating()==null) || 
             (this.rating!=null &&
              this.rating.equals(other.getRating())));
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
        if (getRating() != null) {
            _hashCode += getRating().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(OrganicAutoRequestAutomotiveLead.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", ">OrganicAutoRequest>AutomotiveLead"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rating");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "Rating"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", ">>OrganicAutoRequest>AutomotiveLead>Rating"));
        elemField.setMinOccurs(0);
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
