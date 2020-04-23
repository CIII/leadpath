/**
 * HILead.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.arb.ws.clients.reply;

public class HILead  extends com.arb.ws.clients.reply.Lead  implements java.io.Serializable {
    private com.arb.ws.clients.reply.ArrayOfHICategoryCategory[] categories;

    private com.arb.ws.clients.reply.HILeadHomeOwnership homeOwnership;

    private com.arb.ws.clients.reply.HILeadDefaultProjectInfo defaultProjectInfo;

    private java.lang.String[] hashBacks;

    private com.arb.ws.clients.reply.HILeadRating rating;

    public HILead() {
    }

    public HILead(
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
           com.arb.ws.clients.reply.ArrayOfHICategoryCategory[] categories,
           com.arb.ws.clients.reply.HILeadHomeOwnership homeOwnership,
           com.arb.ws.clients.reply.HILeadDefaultProjectInfo defaultProjectInfo,
           java.lang.String[] hashBacks,
           com.arb.ws.clients.reply.HILeadRating rating) {
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
            consumerIP);
        this.categories = categories;
        this.homeOwnership = homeOwnership;
        this.defaultProjectInfo = defaultProjectInfo;
        this.hashBacks = hashBacks;
        this.rating = rating;
    }


    /**
     * Gets the categories value for this HILead.
     * 
     * @return categories
     */
    public com.arb.ws.clients.reply.ArrayOfHICategoryCategory[] getCategories() {
        return categories;
    }


    /**
     * Sets the categories value for this HILead.
     * 
     * @param categories
     */
    public void setCategories(com.arb.ws.clients.reply.ArrayOfHICategoryCategory[] categories) {
        this.categories = categories;
    }


    /**
     * Gets the homeOwnership value for this HILead.
     * 
     * @return homeOwnership
     */
    public com.arb.ws.clients.reply.HILeadHomeOwnership getHomeOwnership() {
        return homeOwnership;
    }


    /**
     * Sets the homeOwnership value for this HILead.
     * 
     * @param homeOwnership
     */
    public void setHomeOwnership(com.arb.ws.clients.reply.HILeadHomeOwnership homeOwnership) {
        this.homeOwnership = homeOwnership;
    }


    /**
     * Gets the defaultProjectInfo value for this HILead.
     * 
     * @return defaultProjectInfo
     */
    public com.arb.ws.clients.reply.HILeadDefaultProjectInfo getDefaultProjectInfo() {
        return defaultProjectInfo;
    }


    /**
     * Sets the defaultProjectInfo value for this HILead.
     * 
     * @param defaultProjectInfo
     */
    public void setDefaultProjectInfo(com.arb.ws.clients.reply.HILeadDefaultProjectInfo defaultProjectInfo) {
        this.defaultProjectInfo = defaultProjectInfo;
    }


    /**
     * Gets the hashBacks value for this HILead.
     * 
     * @return hashBacks
     */
    public java.lang.String[] getHashBacks() {
        return hashBacks;
    }


    /**
     * Sets the hashBacks value for this HILead.
     * 
     * @param hashBacks
     */
    public void setHashBacks(java.lang.String[] hashBacks) {
        this.hashBacks = hashBacks;
    }


    /**
     * Gets the rating value for this HILead.
     * 
     * @return rating
     */
    public com.arb.ws.clients.reply.HILeadRating getRating() {
        return rating;
    }


    /**
     * Sets the rating value for this HILead.
     * 
     * @param rating
     */
    public void setRating(com.arb.ws.clients.reply.HILeadRating rating) {
        this.rating = rating;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof HILead)) return false;
        HILead other = (HILead) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.categories==null && other.getCategories()==null) || 
             (this.categories!=null &&
              java.util.Arrays.equals(this.categories, other.getCategories()))) &&
            ((this.homeOwnership==null && other.getHomeOwnership()==null) || 
             (this.homeOwnership!=null &&
              this.homeOwnership.equals(other.getHomeOwnership()))) &&
            ((this.defaultProjectInfo==null && other.getDefaultProjectInfo()==null) || 
             (this.defaultProjectInfo!=null &&
              this.defaultProjectInfo.equals(other.getDefaultProjectInfo()))) &&
            ((this.hashBacks==null && other.getHashBacks()==null) || 
             (this.hashBacks!=null &&
              java.util.Arrays.equals(this.hashBacks, other.getHashBacks()))) &&
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
        if (getCategories() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCategories());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCategories(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getHomeOwnership() != null) {
            _hashCode += getHomeOwnership().hashCode();
        }
        if (getDefaultProjectInfo() != null) {
            _hashCode += getDefaultProjectInfo().hashCode();
        }
        if (getHashBacks() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getHashBacks());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getHashBacks(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getRating() != null) {
            _hashCode += getRating().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(HILead.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "HILead"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("categories");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "Categories"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", ">ArrayOfHICategory>Category"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "Category"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("homeOwnership");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "HomeOwnership"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", ">HILead>HomeOwnership"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("defaultProjectInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "DefaultProjectInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", ">HILead>DefaultProjectInfo"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hashBacks");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "HashBacks"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "Hashcode"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rating");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "Rating"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", ">HILead>Rating"));
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
