/**
 * OrganicRealEstateRequestRealEstateLead.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.arb.ws.clients.reply;

public class OrganicRealEstateRequestRealEstateLead  extends com.arb.ws.clients.reply.RealEstateLead  implements java.io.Serializable {
    private com.arb.ws.clients.reply.OrganicRealEstateRequestRealEstateLeadRating rating;

    public OrganicRealEstateRequestRealEstateLead() {
    }

    public OrganicRealEstateRequestRealEstateLead(
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
           com.arb.ws.clients.reply.RealEstateLeadLeadType leadType,
           com.arb.ws.clients.reply.RealEstateLeadPropertyType propertyType,
           int timeFrame,
           java.lang.String buyZip1,
           java.lang.String buyZip2,
           java.lang.String buyZip3,
           java.math.BigDecimal priceRangeStart,
           java.math.BigDecimal priceRangeEnd,
           int squareFootage,
           int bedroomCount,
           int bathroomCount,
           java.lang.String creditHistory,
           boolean hasAgent,
           boolean foundHome,
           java.lang.String whySelling,
           int intent,
           com.arb.ws.clients.reply.OrganicRealEstateRequestRealEstateLeadRating rating) {
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
            propertyType,
            timeFrame,
            buyZip1,
            buyZip2,
            buyZip3,
            priceRangeStart,
            priceRangeEnd,
            squareFootage,
            bedroomCount,
            bathroomCount,
            creditHistory,
            hasAgent,
            foundHome,
            whySelling,
            intent);
        this.rating = rating;
    }


    /**
     * Gets the rating value for this OrganicRealEstateRequestRealEstateLead.
     * 
     * @return rating
     */
    public com.arb.ws.clients.reply.OrganicRealEstateRequestRealEstateLeadRating getRating() {
        return rating;
    }


    /**
     * Sets the rating value for this OrganicRealEstateRequestRealEstateLead.
     * 
     * @param rating
     */
    public void setRating(com.arb.ws.clients.reply.OrganicRealEstateRequestRealEstateLeadRating rating) {
        this.rating = rating;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof OrganicRealEstateRequestRealEstateLead)) return false;
        OrganicRealEstateRequestRealEstateLead other = (OrganicRealEstateRequestRealEstateLead) obj;
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
        new org.apache.axis.description.TypeDesc(OrganicRealEstateRequestRealEstateLead.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", ">OrganicRealEstateRequest>RealEstateLead"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rating");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "Rating"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", ">>OrganicRealEstateRequest>RealEstateLead>Rating"));
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
