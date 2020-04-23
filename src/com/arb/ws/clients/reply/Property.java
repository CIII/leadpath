/**
 * Property.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.arb.ws.clients.reply;

public class Property  implements java.io.Serializable {
    private com.arb.ws.clients.reply.PropertyAddress address;

    private com.arb.ws.clients.reply.PropertyType type;

    private int bathroomCount;

    private int bedroomCount;

    private java.math.BigDecimal maxPrice;

    private java.math.BigDecimal minPrice;

    private int squareFootage;

    public Property() {
    }

    public Property(
           com.arb.ws.clients.reply.PropertyAddress address,
           com.arb.ws.clients.reply.PropertyType type,
           int bathroomCount,
           int bedroomCount,
           java.math.BigDecimal maxPrice,
           java.math.BigDecimal minPrice,
           int squareFootage) {
           this.address = address;
           this.type = type;
           this.bathroomCount = bathroomCount;
           this.bedroomCount = bedroomCount;
           this.maxPrice = maxPrice;
           this.minPrice = minPrice;
           this.squareFootage = squareFootage;
    }


    /**
     * Gets the address value for this Property.
     * 
     * @return address
     */
    public com.arb.ws.clients.reply.PropertyAddress getAddress() {
        return address;
    }


    /**
     * Sets the address value for this Property.
     * 
     * @param address
     */
    public void setAddress(com.arb.ws.clients.reply.PropertyAddress address) {
        this.address = address;
    }


    /**
     * Gets the type value for this Property.
     * 
     * @return type
     */
    public com.arb.ws.clients.reply.PropertyType getType() {
        return type;
    }


    /**
     * Sets the type value for this Property.
     * 
     * @param type
     */
    public void setType(com.arb.ws.clients.reply.PropertyType type) {
        this.type = type;
    }


    /**
     * Gets the bathroomCount value for this Property.
     * 
     * @return bathroomCount
     */
    public int getBathroomCount() {
        return bathroomCount;
    }


    /**
     * Sets the bathroomCount value for this Property.
     * 
     * @param bathroomCount
     */
    public void setBathroomCount(int bathroomCount) {
        this.bathroomCount = bathroomCount;
    }


    /**
     * Gets the bedroomCount value for this Property.
     * 
     * @return bedroomCount
     */
    public int getBedroomCount() {
        return bedroomCount;
    }


    /**
     * Sets the bedroomCount value for this Property.
     * 
     * @param bedroomCount
     */
    public void setBedroomCount(int bedroomCount) {
        this.bedroomCount = bedroomCount;
    }


    /**
     * Gets the maxPrice value for this Property.
     * 
     * @return maxPrice
     */
    public java.math.BigDecimal getMaxPrice() {
        return maxPrice;
    }


    /**
     * Sets the maxPrice value for this Property.
     * 
     * @param maxPrice
     */
    public void setMaxPrice(java.math.BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
    }


    /**
     * Gets the minPrice value for this Property.
     * 
     * @return minPrice
     */
    public java.math.BigDecimal getMinPrice() {
        return minPrice;
    }


    /**
     * Sets the minPrice value for this Property.
     * 
     * @param minPrice
     */
    public void setMinPrice(java.math.BigDecimal minPrice) {
        this.minPrice = minPrice;
    }


    /**
     * Gets the squareFootage value for this Property.
     * 
     * @return squareFootage
     */
    public int getSquareFootage() {
        return squareFootage;
    }


    /**
     * Sets the squareFootage value for this Property.
     * 
     * @param squareFootage
     */
    public void setSquareFootage(int squareFootage) {
        this.squareFootage = squareFootage;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Property)) return false;
        Property other = (Property) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.address==null && other.getAddress()==null) || 
             (this.address!=null &&
              this.address.equals(other.getAddress()))) &&
            ((this.type==null && other.getType()==null) || 
             (this.type!=null &&
              this.type.equals(other.getType()))) &&
            this.bathroomCount == other.getBathroomCount() &&
            this.bedroomCount == other.getBedroomCount() &&
            ((this.maxPrice==null && other.getMaxPrice()==null) || 
             (this.maxPrice!=null &&
              this.maxPrice.equals(other.getMaxPrice()))) &&
            ((this.minPrice==null && other.getMinPrice()==null) || 
             (this.minPrice!=null &&
              this.minPrice.equals(other.getMinPrice()))) &&
            this.squareFootage == other.getSquareFootage();
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
        if (getAddress() != null) {
            _hashCode += getAddress().hashCode();
        }
        if (getType() != null) {
            _hashCode += getType().hashCode();
        }
        _hashCode += getBathroomCount();
        _hashCode += getBedroomCount();
        if (getMaxPrice() != null) {
            _hashCode += getMaxPrice().hashCode();
        }
        if (getMinPrice() != null) {
            _hashCode += getMinPrice().hashCode();
        }
        _hashCode += getSquareFootage();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Property.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "Property"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("address");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "Address"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", ">Property>Address"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("type");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "Type"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", ">Property>Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bathroomCount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "BathroomCount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bedroomCount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "BedroomCount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("maxPrice");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "MaxPrice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("minPrice");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "MinPrice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("squareFootage");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "SquareFootage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
