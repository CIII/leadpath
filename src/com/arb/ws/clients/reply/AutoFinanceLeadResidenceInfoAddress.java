/**
 * AutoFinanceLeadResidenceInfoAddress.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.arb.ws.clients.reply;

public class AutoFinanceLeadResidenceInfoAddress  implements java.io.Serializable {
    private com.arb.ws.clients.reply.AutoFinanceLeadResidenceInfoAddressStreet[] street;

    private java.lang.String apartment;

    private java.lang.String city;

    private java.lang.String state;

    private java.lang.String zipcode;

    private java.lang.String zip;  // attribute

    private com.arb.ws.clients.reply.AutoFinanceLeadResidenceInfoAddressType type;  // attribute

    private boolean isStandardized;  // attribute

    public AutoFinanceLeadResidenceInfoAddress() {
    }

    public AutoFinanceLeadResidenceInfoAddress(
           com.arb.ws.clients.reply.AutoFinanceLeadResidenceInfoAddressStreet[] street,
           java.lang.String apartment,
           java.lang.String city,
           java.lang.String state,
           java.lang.String zipcode,
           java.lang.String zip,
           com.arb.ws.clients.reply.AutoFinanceLeadResidenceInfoAddressType type,
           boolean isStandardized) {
           this.street = street;
           this.apartment = apartment;
           this.city = city;
           this.state = state;
           this.zipcode = zipcode;
           this.zip = zip;
           this.type = type;
           this.isStandardized = isStandardized;
    }


    /**
     * Gets the street value for this AutoFinanceLeadResidenceInfoAddress.
     * 
     * @return street
     */
    public com.arb.ws.clients.reply.AutoFinanceLeadResidenceInfoAddressStreet[] getStreet() {
        return street;
    }


    /**
     * Sets the street value for this AutoFinanceLeadResidenceInfoAddress.
     * 
     * @param street
     */
    public void setStreet(com.arb.ws.clients.reply.AutoFinanceLeadResidenceInfoAddressStreet[] street) {
        this.street = street;
    }

    public com.arb.ws.clients.reply.AutoFinanceLeadResidenceInfoAddressStreet getStreet(int i) {
        return this.street[i];
    }

    public void setStreet(int i, com.arb.ws.clients.reply.AutoFinanceLeadResidenceInfoAddressStreet _value) {
        this.street[i] = _value;
    }


    /**
     * Gets the apartment value for this AutoFinanceLeadResidenceInfoAddress.
     * 
     * @return apartment
     */
    public java.lang.String getApartment() {
        return apartment;
    }


    /**
     * Sets the apartment value for this AutoFinanceLeadResidenceInfoAddress.
     * 
     * @param apartment
     */
    public void setApartment(java.lang.String apartment) {
        this.apartment = apartment;
    }


    /**
     * Gets the city value for this AutoFinanceLeadResidenceInfoAddress.
     * 
     * @return city
     */
    public java.lang.String getCity() {
        return city;
    }


    /**
     * Sets the city value for this AutoFinanceLeadResidenceInfoAddress.
     * 
     * @param city
     */
    public void setCity(java.lang.String city) {
        this.city = city;
    }


    /**
     * Gets the state value for this AutoFinanceLeadResidenceInfoAddress.
     * 
     * @return state
     */
    public java.lang.String getState() {
        return state;
    }


    /**
     * Sets the state value for this AutoFinanceLeadResidenceInfoAddress.
     * 
     * @param state
     */
    public void setState(java.lang.String state) {
        this.state = state;
    }


    /**
     * Gets the zipcode value for this AutoFinanceLeadResidenceInfoAddress.
     * 
     * @return zipcode
     */
    public java.lang.String getZipcode() {
        return zipcode;
    }


    /**
     * Sets the zipcode value for this AutoFinanceLeadResidenceInfoAddress.
     * 
     * @param zipcode
     */
    public void setZipcode(java.lang.String zipcode) {
        this.zipcode = zipcode;
    }


    /**
     * Gets the zip value for this AutoFinanceLeadResidenceInfoAddress.
     * 
     * @return zip
     */
    public java.lang.String getZip() {
        return zip;
    }


    /**
     * Sets the zip value for this AutoFinanceLeadResidenceInfoAddress.
     * 
     * @param zip
     */
    public void setZip(java.lang.String zip) {
        this.zip = zip;
    }


    /**
     * Gets the type value for this AutoFinanceLeadResidenceInfoAddress.
     * 
     * @return type
     */
    public com.arb.ws.clients.reply.AutoFinanceLeadResidenceInfoAddressType getType() {
        return type;
    }


    /**
     * Sets the type value for this AutoFinanceLeadResidenceInfoAddress.
     * 
     * @param type
     */
    public void setType(com.arb.ws.clients.reply.AutoFinanceLeadResidenceInfoAddressType type) {
        this.type = type;
    }


    /**
     * Gets the isStandardized value for this AutoFinanceLeadResidenceInfoAddress.
     * 
     * @return isStandardized
     */
    public boolean isIsStandardized() {
        return isStandardized;
    }


    /**
     * Sets the isStandardized value for this AutoFinanceLeadResidenceInfoAddress.
     * 
     * @param isStandardized
     */
    public void setIsStandardized(boolean isStandardized) {
        this.isStandardized = isStandardized;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AutoFinanceLeadResidenceInfoAddress)) return false;
        AutoFinanceLeadResidenceInfoAddress other = (AutoFinanceLeadResidenceInfoAddress) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.street==null && other.getStreet()==null) || 
             (this.street!=null &&
              java.util.Arrays.equals(this.street, other.getStreet()))) &&
            ((this.apartment==null && other.getApartment()==null) || 
             (this.apartment!=null &&
              this.apartment.equals(other.getApartment()))) &&
            ((this.city==null && other.getCity()==null) || 
             (this.city!=null &&
              this.city.equals(other.getCity()))) &&
            ((this.state==null && other.getState()==null) || 
             (this.state!=null &&
              this.state.equals(other.getState()))) &&
            ((this.zipcode==null && other.getZipcode()==null) || 
             (this.zipcode!=null &&
              this.zipcode.equals(other.getZipcode()))) &&
            ((this.zip==null && other.getZip()==null) || 
             (this.zip!=null &&
              this.zip.equals(other.getZip()))) &&
            ((this.type==null && other.getType()==null) || 
             (this.type!=null &&
              this.type.equals(other.getType()))) &&
            this.isStandardized == other.isIsStandardized();
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
        if (getStreet() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getStreet());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getStreet(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getApartment() != null) {
            _hashCode += getApartment().hashCode();
        }
        if (getCity() != null) {
            _hashCode += getCity().hashCode();
        }
        if (getState() != null) {
            _hashCode += getState().hashCode();
        }
        if (getZipcode() != null) {
            _hashCode += getZipcode().hashCode();
        }
        if (getZip() != null) {
            _hashCode += getZip().hashCode();
        }
        if (getType() != null) {
            _hashCode += getType().hashCode();
        }
        _hashCode += (isIsStandardized() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AutoFinanceLeadResidenceInfoAddress.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", ">>AutoFinanceLead>ResidenceInfo>Address"));
        org.apache.axis.description.AttributeDesc attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("zip");
        attrField.setXmlName(new javax.xml.namespace.QName("", "Zip"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("type");
        attrField.setXmlName(new javax.xml.namespace.QName("", "Type"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", ">>>AutoFinanceLead>ResidenceInfo>Address>Type"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("isStandardized");
        attrField.setXmlName(new javax.xml.namespace.QName("", "IsStandardized"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        typeDesc.addFieldDesc(attrField);
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("street");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "Street"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", ">>>AutoFinanceLead>ResidenceInfo>Address>Street"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("apartment");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "Apartment"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("city");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "City"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("state");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "State"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("zipcode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "Zipcode"));
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
