/**
 * OrganicEducationRequestEducationLead.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.arb.ws.clients.reply;

public class OrganicEducationRequestEducationLead  extends com.arb.ws.clients.reply.EducationLead  implements java.io.Serializable {
    private com.arb.ws.clients.reply.OrganicEducationRequestEducationLeadRating rating;

    public OrganicEducationRequestEducationLead() {
    }

    public OrganicEducationRequestEducationLead(
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
           com.arb.ws.clients.reply.AreaOfStudy areaOfStudy,
           com.arb.ws.clients.reply.DegreeType degreeType,
           com.arb.ws.clients.reply.Concentration concentration,
           boolean online,
           boolean ground,
           int age,
           com.arb.ws.clients.reply.HighestDegree highestDegreeAttained,
           boolean militaryAffiliated,
           int yearGraduated,
           boolean USCitizen,
           com.arb.ws.clients.reply.MilitaryBranch militaryBranch,
           com.arb.ws.clients.reply.MilitaryStatus militaryStatus,
           com.arb.ws.clients.reply.EDUTimeFrame timeFrame,
           com.arb.ws.clients.reply.School[] schools,
           com.arb.ws.clients.reply.OrganicEducationRequestEducationLeadRating rating) {
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
            areaOfStudy,
            degreeType,
            concentration,
            online,
            ground,
            age,
            highestDegreeAttained,
            militaryAffiliated,
            yearGraduated,
            USCitizen,
            militaryBranch,
            militaryStatus,
            timeFrame,
            schools);
        this.rating = rating;
    }


    /**
     * Gets the rating value for this OrganicEducationRequestEducationLead.
     * 
     * @return rating
     */
    public com.arb.ws.clients.reply.OrganicEducationRequestEducationLeadRating getRating() {
        return rating;
    }


    /**
     * Sets the rating value for this OrganicEducationRequestEducationLead.
     * 
     * @param rating
     */
    public void setRating(com.arb.ws.clients.reply.OrganicEducationRequestEducationLeadRating rating) {
        this.rating = rating;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof OrganicEducationRequestEducationLead)) return false;
        OrganicEducationRequestEducationLead other = (OrganicEducationRequestEducationLead) obj;
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
        new org.apache.axis.description.TypeDesc(OrganicEducationRequestEducationLead.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", ">OrganicEducationRequest>EducationLead"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rating");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "Rating"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", ">>OrganicEducationRequest>EducationLead>Rating"));
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
