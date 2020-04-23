/**
 * Zip.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.arb.ws.clients.reply;

public class Zip  implements java.io.Serializable, org.apache.axis.encoding.SimpleType {
    private java.lang.String _value;

    private java.math.BigDecimal bid;  // attribute

    private long campaignId;  // attribute

    private java.lang.String dealerID;  // attribute

    private java.lang.String dealerName;  // attribute

    private java.lang.String dealerAddress;  // attribute

    private java.lang.String dealerCity;  // attribute

    private java.lang.String dealerState;  // attribute

    private java.math.BigDecimal distance;  // attribute

    private java.math.BigDecimal longitude;  // attribute

    private java.math.BigDecimal lattitude;  // attribute

    private java.lang.String phone;  // attribute

    private int programID;  // attribute

    private java.lang.String dealerCode;  // attribute

    private boolean listItem;  // attribute

    public Zip() {
    }

    // Simple Types must have a String constructor
    public Zip(java.lang.String _value) {
        this._value = _value;
    }
    // Simple Types must have a toString for serializing the value
    public java.lang.String toString() {
        return _value;
    }


    /**
     * Gets the _value value for this Zip.
     * 
     * @return _value
     */
    public java.lang.String get_value() {
        return _value;
    }


    /**
     * Sets the _value value for this Zip.
     * 
     * @param _value
     */
    public void set_value(java.lang.String _value) {
        this._value = _value;
    }


    /**
     * Gets the bid value for this Zip.
     * 
     * @return bid
     */
    public java.math.BigDecimal getBid() {
        return bid;
    }


    /**
     * Sets the bid value for this Zip.
     * 
     * @param bid
     */
    public void setBid(java.math.BigDecimal bid) {
        this.bid = bid;
    }


    /**
     * Gets the campaignId value for this Zip.
     * 
     * @return campaignId
     */
    public long getCampaignId() {
        return campaignId;
    }


    /**
     * Sets the campaignId value for this Zip.
     * 
     * @param campaignId
     */
    public void setCampaignId(long campaignId) {
        this.campaignId = campaignId;
    }


    /**
     * Gets the dealerID value for this Zip.
     * 
     * @return dealerID
     */
    public java.lang.String getDealerID() {
        return dealerID;
    }


    /**
     * Sets the dealerID value for this Zip.
     * 
     * @param dealerID
     */
    public void setDealerID(java.lang.String dealerID) {
        this.dealerID = dealerID;
    }


    /**
     * Gets the dealerName value for this Zip.
     * 
     * @return dealerName
     */
    public java.lang.String getDealerName() {
        return dealerName;
    }


    /**
     * Sets the dealerName value for this Zip.
     * 
     * @param dealerName
     */
    public void setDealerName(java.lang.String dealerName) {
        this.dealerName = dealerName;
    }


    /**
     * Gets the dealerAddress value for this Zip.
     * 
     * @return dealerAddress
     */
    public java.lang.String getDealerAddress() {
        return dealerAddress;
    }


    /**
     * Sets the dealerAddress value for this Zip.
     * 
     * @param dealerAddress
     */
    public void setDealerAddress(java.lang.String dealerAddress) {
        this.dealerAddress = dealerAddress;
    }


    /**
     * Gets the dealerCity value for this Zip.
     * 
     * @return dealerCity
     */
    public java.lang.String getDealerCity() {
        return dealerCity;
    }


    /**
     * Sets the dealerCity value for this Zip.
     * 
     * @param dealerCity
     */
    public void setDealerCity(java.lang.String dealerCity) {
        this.dealerCity = dealerCity;
    }


    /**
     * Gets the dealerState value for this Zip.
     * 
     * @return dealerState
     */
    public java.lang.String getDealerState() {
        return dealerState;
    }


    /**
     * Sets the dealerState value for this Zip.
     * 
     * @param dealerState
     */
    public void setDealerState(java.lang.String dealerState) {
        this.dealerState = dealerState;
    }


    /**
     * Gets the distance value for this Zip.
     * 
     * @return distance
     */
    public java.math.BigDecimal getDistance() {
        return distance;
    }


    /**
     * Sets the distance value for this Zip.
     * 
     * @param distance
     */
    public void setDistance(java.math.BigDecimal distance) {
        this.distance = distance;
    }


    /**
     * Gets the longitude value for this Zip.
     * 
     * @return longitude
     */
    public java.math.BigDecimal getLongitude() {
        return longitude;
    }


    /**
     * Sets the longitude value for this Zip.
     * 
     * @param longitude
     */
    public void setLongitude(java.math.BigDecimal longitude) {
        this.longitude = longitude;
    }


    /**
     * Gets the lattitude value for this Zip.
     * 
     * @return lattitude
     */
    public java.math.BigDecimal getLattitude() {
        return lattitude;
    }


    /**
     * Sets the lattitude value for this Zip.
     * 
     * @param lattitude
     */
    public void setLattitude(java.math.BigDecimal lattitude) {
        this.lattitude = lattitude;
    }


    /**
     * Gets the phone value for this Zip.
     * 
     * @return phone
     */
    public java.lang.String getPhone() {
        return phone;
    }


    /**
     * Sets the phone value for this Zip.
     * 
     * @param phone
     */
    public void setPhone(java.lang.String phone) {
        this.phone = phone;
    }


    /**
     * Gets the programID value for this Zip.
     * 
     * @return programID
     */
    public int getProgramID() {
        return programID;
    }


    /**
     * Sets the programID value for this Zip.
     * 
     * @param programID
     */
    public void setProgramID(int programID) {
        this.programID = programID;
    }


    /**
     * Gets the dealerCode value for this Zip.
     * 
     * @return dealerCode
     */
    public java.lang.String getDealerCode() {
        return dealerCode;
    }


    /**
     * Sets the dealerCode value for this Zip.
     * 
     * @param dealerCode
     */
    public void setDealerCode(java.lang.String dealerCode) {
        this.dealerCode = dealerCode;
    }


    /**
     * Gets the listItem value for this Zip.
     * 
     * @return listItem
     */
    public boolean isListItem() {
        return listItem;
    }


    /**
     * Sets the listItem value for this Zip.
     * 
     * @param listItem
     */
    public void setListItem(boolean listItem) {
        this.listItem = listItem;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Zip)) return false;
        Zip other = (Zip) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this._value==null && other.get_value()==null) || 
             (this._value!=null &&
              this._value.equals(other.get_value()))) &&
            ((this.bid==null && other.getBid()==null) || 
             (this.bid!=null &&
              this.bid.equals(other.getBid()))) &&
            this.campaignId == other.getCampaignId() &&
            ((this.dealerID==null && other.getDealerID()==null) || 
             (this.dealerID!=null &&
              this.dealerID.equals(other.getDealerID()))) &&
            ((this.dealerName==null && other.getDealerName()==null) || 
             (this.dealerName!=null &&
              this.dealerName.equals(other.getDealerName()))) &&
            ((this.dealerAddress==null && other.getDealerAddress()==null) || 
             (this.dealerAddress!=null &&
              this.dealerAddress.equals(other.getDealerAddress()))) &&
            ((this.dealerCity==null && other.getDealerCity()==null) || 
             (this.dealerCity!=null &&
              this.dealerCity.equals(other.getDealerCity()))) &&
            ((this.dealerState==null && other.getDealerState()==null) || 
             (this.dealerState!=null &&
              this.dealerState.equals(other.getDealerState()))) &&
            ((this.distance==null && other.getDistance()==null) || 
             (this.distance!=null &&
              this.distance.equals(other.getDistance()))) &&
            ((this.longitude==null && other.getLongitude()==null) || 
             (this.longitude!=null &&
              this.longitude.equals(other.getLongitude()))) &&
            ((this.lattitude==null && other.getLattitude()==null) || 
             (this.lattitude!=null &&
              this.lattitude.equals(other.getLattitude()))) &&
            ((this.phone==null && other.getPhone()==null) || 
             (this.phone!=null &&
              this.phone.equals(other.getPhone()))) &&
            this.programID == other.getProgramID() &&
            ((this.dealerCode==null && other.getDealerCode()==null) || 
             (this.dealerCode!=null &&
              this.dealerCode.equals(other.getDealerCode()))) &&
            this.listItem == other.isListItem();
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
        if (get_value() != null) {
            _hashCode += get_value().hashCode();
        }
        if (getBid() != null) {
            _hashCode += getBid().hashCode();
        }
        _hashCode += new Long(getCampaignId()).hashCode();
        if (getDealerID() != null) {
            _hashCode += getDealerID().hashCode();
        }
        if (getDealerName() != null) {
            _hashCode += getDealerName().hashCode();
        }
        if (getDealerAddress() != null) {
            _hashCode += getDealerAddress().hashCode();
        }
        if (getDealerCity() != null) {
            _hashCode += getDealerCity().hashCode();
        }
        if (getDealerState() != null) {
            _hashCode += getDealerState().hashCode();
        }
        if (getDistance() != null) {
            _hashCode += getDistance().hashCode();
        }
        if (getLongitude() != null) {
            _hashCode += getLongitude().hashCode();
        }
        if (getLattitude() != null) {
            _hashCode += getLattitude().hashCode();
        }
        if (getPhone() != null) {
            _hashCode += getPhone().hashCode();
        }
        _hashCode += getProgramID();
        if (getDealerCode() != null) {
            _hashCode += getDealerCode().hashCode();
        }
        _hashCode += (isListItem() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Zip.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "Zip"));
        org.apache.axis.description.AttributeDesc attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("bid");
        attrField.setXmlName(new javax.xml.namespace.QName("", "Bid"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("campaignId");
        attrField.setXmlName(new javax.xml.namespace.QName("", "CampaignId"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("dealerID");
        attrField.setXmlName(new javax.xml.namespace.QName("", "DealerID"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("dealerName");
        attrField.setXmlName(new javax.xml.namespace.QName("", "DealerName"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("dealerAddress");
        attrField.setXmlName(new javax.xml.namespace.QName("", "DealerAddress"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("dealerCity");
        attrField.setXmlName(new javax.xml.namespace.QName("", "DealerCity"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("dealerState");
        attrField.setXmlName(new javax.xml.namespace.QName("", "DealerState"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("distance");
        attrField.setXmlName(new javax.xml.namespace.QName("", "Distance"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("longitude");
        attrField.setXmlName(new javax.xml.namespace.QName("", "Longitude"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("lattitude");
        attrField.setXmlName(new javax.xml.namespace.QName("", "Lattitude"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("phone");
        attrField.setXmlName(new javax.xml.namespace.QName("", "Phone"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("programID");
        attrField.setXmlName(new javax.xml.namespace.QName("", "ProgramID"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("dealerCode");
        attrField.setXmlName(new javax.xml.namespace.QName("", "DealerCode"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("listItem");
        attrField.setXmlName(new javax.xml.namespace.QName("", "ListItem"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        typeDesc.addFieldDesc(attrField);
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_value");
        elemField.setXmlName(new javax.xml.namespace.QName("", "_value"));
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
          new  org.apache.axis.encoding.ser.SimpleSerializer(
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
          new  org.apache.axis.encoding.ser.SimpleDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
