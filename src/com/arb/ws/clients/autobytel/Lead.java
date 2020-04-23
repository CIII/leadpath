/**
 * Lead.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.arb.ws.clients.autobytel;

public class Lead  implements java.io.Serializable {
    private java.lang.String prospectID;

    private java.util.Calendar requestDate;

    private com.arb.ws.clients.autobytel.Vehicle vehicle;

    private com.arb.ws.clients.autobytel.Tradein tradein;

    private com.arb.ws.clients.autobytel.Customer customer;

    private com.arb.ws.clients.autobytel.Dealer[] dealers;

    private com.arb.ws.clients.autobytel.Provider provider;

    public Lead() {
    }

    public Lead(
           java.lang.String prospectID,
           java.util.Calendar requestDate,
           com.arb.ws.clients.autobytel.Vehicle vehicle,
           com.arb.ws.clients.autobytel.Tradein tradein,
           com.arb.ws.clients.autobytel.Customer customer,
           com.arb.ws.clients.autobytel.Dealer[] dealers,
           com.arb.ws.clients.autobytel.Provider provider) {
           this.prospectID = prospectID;
           this.requestDate = requestDate;
           this.vehicle = vehicle;
           this.tradein = tradein;
           this.customer = customer;
           this.dealers = dealers;
           this.provider = provider;
    }


    /**
     * Gets the prospectID value for this Lead.
     * 
     * @return prospectID
     */
    public java.lang.String getProspectID() {
        return prospectID;
    }


    /**
     * Sets the prospectID value for this Lead.
     * 
     * @param prospectID
     */
    public void setProspectID(java.lang.String prospectID) {
        this.prospectID = prospectID;
    }


    /**
     * Gets the requestDate value for this Lead.
     * 
     * @return requestDate
     */
    public java.util.Calendar getRequestDate() {
        return requestDate;
    }


    /**
     * Sets the requestDate value for this Lead.
     * 
     * @param requestDate
     */
    public void setRequestDate(java.util.Calendar requestDate) {
        this.requestDate = requestDate;
    }


    /**
     * Gets the vehicle value for this Lead.
     * 
     * @return vehicle
     */
    public com.arb.ws.clients.autobytel.Vehicle getVehicle() {
        return vehicle;
    }


    /**
     * Sets the vehicle value for this Lead.
     * 
     * @param vehicle
     */
    public void setVehicle(com.arb.ws.clients.autobytel.Vehicle vehicle) {
        this.vehicle = vehicle;
    }


    /**
     * Gets the tradein value for this Lead.
     * 
     * @return tradein
     */
    public com.arb.ws.clients.autobytel.Tradein getTradein() {
        return tradein;
    }


    /**
     * Sets the tradein value for this Lead.
     * 
     * @param tradein
     */
    public void setTradein(com.arb.ws.clients.autobytel.Tradein tradein) {
        this.tradein = tradein;
    }


    /**
     * Gets the customer value for this Lead.
     * 
     * @return customer
     */
    public com.arb.ws.clients.autobytel.Customer getCustomer() {
        return customer;
    }


    /**
     * Sets the customer value for this Lead.
     * 
     * @param customer
     */
    public void setCustomer(com.arb.ws.clients.autobytel.Customer customer) {
        this.customer = customer;
    }


    /**
     * Gets the dealers value for this Lead.
     * 
     * @return dealers
     */
    public com.arb.ws.clients.autobytel.Dealer[] getDealers() {
        return dealers;
    }


    /**
     * Sets the dealers value for this Lead.
     * 
     * @param dealers
     */
    public void setDealers(com.arb.ws.clients.autobytel.Dealer[] dealers) {
        this.dealers = dealers;
    }


    /**
     * Gets the provider value for this Lead.
     * 
     * @return provider
     */
    public com.arb.ws.clients.autobytel.Provider getProvider() {
        return provider;
    }


    /**
     * Sets the provider value for this Lead.
     * 
     * @param provider
     */
    public void setProvider(com.arb.ws.clients.autobytel.Provider provider) {
        this.provider = provider;
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
            ((this.prospectID==null && other.getProspectID()==null) || 
             (this.prospectID!=null &&
              this.prospectID.equals(other.getProspectID()))) &&
            ((this.requestDate==null && other.getRequestDate()==null) || 
             (this.requestDate!=null &&
              this.requestDate.equals(other.getRequestDate()))) &&
            ((this.vehicle==null && other.getVehicle()==null) || 
             (this.vehicle!=null &&
              this.vehicle.equals(other.getVehicle()))) &&
            ((this.tradein==null && other.getTradein()==null) || 
             (this.tradein!=null &&
              this.tradein.equals(other.getTradein()))) &&
            ((this.customer==null && other.getCustomer()==null) || 
             (this.customer!=null &&
              this.customer.equals(other.getCustomer()))) &&
            ((this.dealers==null && other.getDealers()==null) || 
             (this.dealers!=null &&
              java.util.Arrays.equals(this.dealers, other.getDealers()))) &&
            ((this.provider==null && other.getProvider()==null) || 
             (this.provider!=null &&
              this.provider.equals(other.getProvider())));
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
        if (getProspectID() != null) {
            _hashCode += getProspectID().hashCode();
        }
        if (getRequestDate() != null) {
            _hashCode += getRequestDate().hashCode();
        }
        if (getVehicle() != null) {
            _hashCode += getVehicle().hashCode();
        }
        if (getTradein() != null) {
            _hashCode += getTradein().hashCode();
        }
        if (getCustomer() != null) {
            _hashCode += getCustomer().hashCode();
        }
        if (getDealers() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDealers());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDealers(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getProvider() != null) {
            _hashCode += getProvider().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Lead.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.autobytel.com/", "Lead"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("prospectID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.autobytel.com/", "ProspectID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("requestDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.autobytel.com/", "RequestDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vehicle");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.autobytel.com/", "Vehicle"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.autobytel.com/", "Vehicle"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tradein");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.autobytel.com/", "Tradein"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.autobytel.com/", "Tradein"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("customer");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.autobytel.com/", "Customer"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.autobytel.com/", "Customer"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dealers");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.autobytel.com/", "Dealers"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.autobytel.com/", "Dealer"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://www.autobytel.com/", "Dealer"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("provider");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.autobytel.com/", "Provider"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.autobytel.com/", "Provider"));
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
