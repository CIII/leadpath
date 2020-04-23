/**
 * Tradein.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.arb.ws.clients.autobytel;

public class Tradein  implements java.io.Serializable {
    private int year;

    private java.lang.String make;

    private java.lang.String model;

    private java.lang.String trim;

    private int odometer;

    public Tradein() {
    }

    public Tradein(
           int year,
           java.lang.String make,
           java.lang.String model,
           java.lang.String trim,
           int odometer) {
           this.year = year;
           this.make = make;
           this.model = model;
           this.trim = trim;
           this.odometer = odometer;
    }


    /**
     * Gets the year value for this Tradein.
     * 
     * @return year
     */
    public int getYear() {
        return year;
    }


    /**
     * Sets the year value for this Tradein.
     * 
     * @param year
     */
    public void setYear(int year) {
        this.year = year;
    }


    /**
     * Gets the make value for this Tradein.
     * 
     * @return make
     */
    public java.lang.String getMake() {
        return make;
    }


    /**
     * Sets the make value for this Tradein.
     * 
     * @param make
     */
    public void setMake(java.lang.String make) {
        this.make = make;
    }


    /**
     * Gets the model value for this Tradein.
     * 
     * @return model
     */
    public java.lang.String getModel() {
        return model;
    }


    /**
     * Sets the model value for this Tradein.
     * 
     * @param model
     */
    public void setModel(java.lang.String model) {
        this.model = model;
    }


    /**
     * Gets the trim value for this Tradein.
     * 
     * @return trim
     */
    public java.lang.String getTrim() {
        return trim;
    }


    /**
     * Sets the trim value for this Tradein.
     * 
     * @param trim
     */
    public void setTrim(java.lang.String trim) {
        this.trim = trim;
    }


    /**
     * Gets the odometer value for this Tradein.
     * 
     * @return odometer
     */
    public int getOdometer() {
        return odometer;
    }


    /**
     * Sets the odometer value for this Tradein.
     * 
     * @param odometer
     */
    public void setOdometer(int odometer) {
        this.odometer = odometer;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Tradein)) return false;
        Tradein other = (Tradein) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.year == other.getYear() &&
            ((this.make==null && other.getMake()==null) || 
             (this.make!=null &&
              this.make.equals(other.getMake()))) &&
            ((this.model==null && other.getModel()==null) || 
             (this.model!=null &&
              this.model.equals(other.getModel()))) &&
            ((this.trim==null && other.getTrim()==null) || 
             (this.trim!=null &&
              this.trim.equals(other.getTrim()))) &&
            this.odometer == other.getOdometer();
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
        _hashCode += getYear();
        if (getMake() != null) {
            _hashCode += getMake().hashCode();
        }
        if (getModel() != null) {
            _hashCode += getModel().hashCode();
        }
        if (getTrim() != null) {
            _hashCode += getTrim().hashCode();
        }
        _hashCode += getOdometer();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Tradein.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.autobytel.com/", "Tradein"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("year");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.autobytel.com/", "Year"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("make");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.autobytel.com/", "Make"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("model");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.autobytel.com/", "Model"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("trim");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.autobytel.com/", "Trim"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("odometer");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.autobytel.com/", "Odometer"));
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
