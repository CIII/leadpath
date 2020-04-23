/**
 * Lead.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.arb.ws.clients.reply;

public abstract class Lead  implements java.io.Serializable {
    private java.lang.String zipcode;

    private com.arb.ws.clients.reply.LeadContact contact;

    private long LCPId;

    private java.util.Calendar collectionDate;

    private long leadId;

    private java.lang.String legsSold;

    private java.lang.String legsAvailable;

    private java.lang.String exclusive;

    private java.lang.String reservePrice;

    private java.lang.String trackingId;

    private java.lang.String sourceId;

    private java.lang.String position;

    private java.lang.String leadPoolId;

    private java.lang.String consumerIP;

    public Lead() {
    }

    public Lead(
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
           java.lang.String consumerIP) {
           this.zipcode = zipcode;
           this.contact = contact;
           this.LCPId = LCPId;
           this.collectionDate = collectionDate;
           this.leadId = leadId;
           this.legsSold = legsSold;
           this.legsAvailable = legsAvailable;
           this.exclusive = exclusive;
           this.reservePrice = reservePrice;
           this.trackingId = trackingId;
           this.sourceId = sourceId;
           this.position = position;
           this.leadPoolId = leadPoolId;
           this.consumerIP = consumerIP;
    }


    /**
     * Gets the zipcode value for this Lead.
     * 
     * @return zipcode
     */
    public java.lang.String getZipcode() {
        return zipcode;
    }


    /**
     * Sets the zipcode value for this Lead.
     * 
     * @param zipcode
     */
    public void setZipcode(java.lang.String zipcode) {
        this.zipcode = zipcode;
    }


    /**
     * Gets the contact value for this Lead.
     * 
     * @return contact
     */
    public com.arb.ws.clients.reply.LeadContact getContact() {
        return contact;
    }


    /**
     * Sets the contact value for this Lead.
     * 
     * @param contact
     */
    public void setContact(com.arb.ws.clients.reply.LeadContact contact) {
        this.contact = contact;
    }


    /**
     * Gets the LCPId value for this Lead.
     * 
     * @return LCPId
     */
    public long getLCPId() {
        return LCPId;
    }


    /**
     * Sets the LCPId value for this Lead.
     * 
     * @param LCPId
     */
    public void setLCPId(long LCPId) {
        this.LCPId = LCPId;
    }


    /**
     * Gets the collectionDate value for this Lead.
     * 
     * @return collectionDate
     */
    public java.util.Calendar getCollectionDate() {
        return collectionDate;
    }


    /**
     * Sets the collectionDate value for this Lead.
     * 
     * @param collectionDate
     */
    public void setCollectionDate(java.util.Calendar collectionDate) {
        this.collectionDate = collectionDate;
    }


    /**
     * Gets the leadId value for this Lead.
     * 
     * @return leadId
     */
    public long getLeadId() {
        return leadId;
    }


    /**
     * Sets the leadId value for this Lead.
     * 
     * @param leadId
     */
    public void setLeadId(long leadId) {
        this.leadId = leadId;
    }


    /**
     * Gets the legsSold value for this Lead.
     * 
     * @return legsSold
     */
    public java.lang.String getLegsSold() {
        return legsSold;
    }


    /**
     * Sets the legsSold value for this Lead.
     * 
     * @param legsSold
     */
    public void setLegsSold(java.lang.String legsSold) {
        this.legsSold = legsSold;
    }


    /**
     * Gets the legsAvailable value for this Lead.
     * 
     * @return legsAvailable
     */
    public java.lang.String getLegsAvailable() {
        return legsAvailable;
    }


    /**
     * Sets the legsAvailable value for this Lead.
     * 
     * @param legsAvailable
     */
    public void setLegsAvailable(java.lang.String legsAvailable) {
        this.legsAvailable = legsAvailable;
    }


    /**
     * Gets the exclusive value for this Lead.
     * 
     * @return exclusive
     */
    public java.lang.String getExclusive() {
        return exclusive;
    }


    /**
     * Sets the exclusive value for this Lead.
     * 
     * @param exclusive
     */
    public void setExclusive(java.lang.String exclusive) {
        this.exclusive = exclusive;
    }


    /**
     * Gets the reservePrice value for this Lead.
     * 
     * @return reservePrice
     */
    public java.lang.String getReservePrice() {
        return reservePrice;
    }


    /**
     * Sets the reservePrice value for this Lead.
     * 
     * @param reservePrice
     */
    public void setReservePrice(java.lang.String reservePrice) {
        this.reservePrice = reservePrice;
    }


    /**
     * Gets the trackingId value for this Lead.
     * 
     * @return trackingId
     */
    public java.lang.String getTrackingId() {
        return trackingId;
    }


    /**
     * Sets the trackingId value for this Lead.
     * 
     * @param trackingId
     */
    public void setTrackingId(java.lang.String trackingId) {
        this.trackingId = trackingId;
    }


    /**
     * Gets the sourceId value for this Lead.
     * 
     * @return sourceId
     */
    public java.lang.String getSourceId() {
        return sourceId;
    }


    /**
     * Sets the sourceId value for this Lead.
     * 
     * @param sourceId
     */
    public void setSourceId(java.lang.String sourceId) {
        this.sourceId = sourceId;
    }


    /**
     * Gets the position value for this Lead.
     * 
     * @return position
     */
    public java.lang.String getPosition() {
        return position;
    }


    /**
     * Sets the position value for this Lead.
     * 
     * @param position
     */
    public void setPosition(java.lang.String position) {
        this.position = position;
    }


    /**
     * Gets the leadPoolId value for this Lead.
     * 
     * @return leadPoolId
     */
    public java.lang.String getLeadPoolId() {
        return leadPoolId;
    }


    /**
     * Sets the leadPoolId value for this Lead.
     * 
     * @param leadPoolId
     */
    public void setLeadPoolId(java.lang.String leadPoolId) {
        this.leadPoolId = leadPoolId;
    }


    /**
     * Gets the consumerIP value for this Lead.
     * 
     * @return consumerIP
     */
    public java.lang.String getConsumerIP() {
        return consumerIP;
    }


    /**
     * Sets the consumerIP value for this Lead.
     * 
     * @param consumerIP
     */
    public void setConsumerIP(java.lang.String consumerIP) {
        this.consumerIP = consumerIP;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Lead)) return false;
        Lead other = (Lead) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.zipcode==null && other.getZipcode()==null) || 
             (this.zipcode!=null &&
              this.zipcode.equals(other.getZipcode()))) &&
            ((this.contact==null && other.getContact()==null) || 
             (this.contact!=null &&
              this.contact.equals(other.getContact()))) &&
            this.LCPId == other.getLCPId() &&
            ((this.collectionDate==null && other.getCollectionDate()==null) || 
             (this.collectionDate!=null &&
              this.collectionDate.equals(other.getCollectionDate()))) &&
            this.leadId == other.getLeadId() &&
            ((this.legsSold==null && other.getLegsSold()==null) || 
             (this.legsSold!=null &&
              this.legsSold.equals(other.getLegsSold()))) &&
            ((this.legsAvailable==null && other.getLegsAvailable()==null) || 
             (this.legsAvailable!=null &&
              this.legsAvailable.equals(other.getLegsAvailable()))) &&
            ((this.exclusive==null && other.getExclusive()==null) || 
             (this.exclusive!=null &&
              this.exclusive.equals(other.getExclusive()))) &&
            ((this.reservePrice==null && other.getReservePrice()==null) || 
             (this.reservePrice!=null &&
              this.reservePrice.equals(other.getReservePrice()))) &&
            ((this.trackingId==null && other.getTrackingId()==null) || 
             (this.trackingId!=null &&
              this.trackingId.equals(other.getTrackingId()))) &&
            ((this.sourceId==null && other.getSourceId()==null) || 
             (this.sourceId!=null &&
              this.sourceId.equals(other.getSourceId()))) &&
            ((this.position==null && other.getPosition()==null) || 
             (this.position!=null &&
              this.position.equals(other.getPosition()))) &&
            ((this.leadPoolId==null && other.getLeadPoolId()==null) || 
             (this.leadPoolId!=null &&
              this.leadPoolId.equals(other.getLeadPoolId()))) &&
            ((this.consumerIP==null && other.getConsumerIP()==null) || 
             (this.consumerIP!=null &&
              this.consumerIP.equals(other.getConsumerIP())));
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
        if (getZipcode() != null) {
            _hashCode += getZipcode().hashCode();
        }
        if (getContact() != null) {
            _hashCode += getContact().hashCode();
        }
        _hashCode += new Long(getLCPId()).hashCode();
        if (getCollectionDate() != null) {
            _hashCode += getCollectionDate().hashCode();
        }
        _hashCode += new Long(getLeadId()).hashCode();
        if (getLegsSold() != null) {
            _hashCode += getLegsSold().hashCode();
        }
        if (getLegsAvailable() != null) {
            _hashCode += getLegsAvailable().hashCode();
        }
        if (getExclusive() != null) {
            _hashCode += getExclusive().hashCode();
        }
        if (getReservePrice() != null) {
            _hashCode += getReservePrice().hashCode();
        }
        if (getTrackingId() != null) {
            _hashCode += getTrackingId().hashCode();
        }
        if (getSourceId() != null) {
            _hashCode += getSourceId().hashCode();
        }
        if (getPosition() != null) {
            _hashCode += getPosition().hashCode();
        }
        if (getLeadPoolId() != null) {
            _hashCode += getLeadPoolId().hashCode();
        }
        if (getConsumerIP() != null) {
            _hashCode += getConsumerIP().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Lead.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "Lead"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("zipcode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "Zipcode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contact");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "Contact"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", ">Lead>Contact"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("LCPId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "LCPId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("collectionDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "CollectionDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("leadId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "LeadId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("legsSold");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "LegsSold"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("legsAvailable");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "LegsAvailable"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("exclusive");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "Exclusive"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reservePrice");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "ReservePrice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("trackingId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "TrackingId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sourceId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "SourceId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("position");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "Position"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("leadPoolId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "LeadPoolId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("consumerIP");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "ConsumerIP"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
