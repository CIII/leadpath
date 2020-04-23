/**
 * Vehicle.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.arb.ws.clients.autobytel;

public class Vehicle  implements java.io.Serializable {
    private com.arb.ws.clients.autobytel.VehicleStatus status;

    private java.lang.String vehicleID;

    private int year;

    private java.lang.String make;

    private java.lang.String model;

    private java.lang.String trim;

    private java.lang.String interiorColor;

    private java.lang.String exteriorColor;

    private java.lang.String[] options;

    private com.arb.ws.clients.autobytel.FinanceMethod preferedFinanceMethod;

    private int downPayment;

    private java.lang.String comments;

    private java.lang.String imageTag;

    public Vehicle() {
    }

    public Vehicle(
           com.arb.ws.clients.autobytel.VehicleStatus status,
           java.lang.String vehicleID,
           int year,
           java.lang.String make,
           java.lang.String model,
           java.lang.String trim,
           java.lang.String interiorColor,
           java.lang.String exteriorColor,
           java.lang.String[] options,
           com.arb.ws.clients.autobytel.FinanceMethod preferedFinanceMethod,
           int downPayment,
           java.lang.String comments,
           java.lang.String imageTag) {
           this.status = status;
           this.vehicleID = vehicleID;
           this.year = year;
           this.make = make;
           this.model = model;
           this.trim = trim;
           this.interiorColor = interiorColor;
           this.exteriorColor = exteriorColor;
           this.options = options;
           this.preferedFinanceMethod = preferedFinanceMethod;
           this.downPayment = downPayment;
           this.comments = comments;
           this.imageTag = imageTag;
    }


    /**
     * Gets the status value for this Vehicle.
     * 
     * @return status
     */
    public com.arb.ws.clients.autobytel.VehicleStatus getStatus() {
        return status;
    }


    /**
     * Sets the status value for this Vehicle.
     * 
     * @param status
     */
    public void setStatus(com.arb.ws.clients.autobytel.VehicleStatus status) {
        this.status = status;
    }


    /**
     * Gets the vehicleID value for this Vehicle.
     * 
     * @return vehicleID
     */
    public java.lang.String getVehicleID() {
        return vehicleID;
    }


    /**
     * Sets the vehicleID value for this Vehicle.
     * 
     * @param vehicleID
     */
    public void setVehicleID(java.lang.String vehicleID) {
        this.vehicleID = vehicleID;
    }


    /**
     * Gets the year value for this Vehicle.
     * 
     * @return year
     */
    public int getYear() {
        return year;
    }


    /**
     * Sets the year value for this Vehicle.
     * 
     * @param year
     */
    public void setYear(int year) {
        this.year = year;
    }


    /**
     * Gets the make value for this Vehicle.
     * 
     * @return make
     */
    public java.lang.String getMake() {
        return make;
    }


    /**
     * Sets the make value for this Vehicle.
     * 
     * @param make
     */
    public void setMake(java.lang.String make) {
        this.make = make;
    }


    /**
     * Gets the model value for this Vehicle.
     * 
     * @return model
     */
    public java.lang.String getModel() {
        return model;
    }


    /**
     * Sets the model value for this Vehicle.
     * 
     * @param model
     */
    public void setModel(java.lang.String model) {
        this.model = model;
    }


    /**
     * Gets the trim value for this Vehicle.
     * 
     * @return trim
     */
    public java.lang.String getTrim() {
        return trim;
    }


    /**
     * Sets the trim value for this Vehicle.
     * 
     * @param trim
     */
    public void setTrim(java.lang.String trim) {
        this.trim = trim;
    }


    /**
     * Gets the interiorColor value for this Vehicle.
     * 
     * @return interiorColor
     */
    public java.lang.String getInteriorColor() {
        return interiorColor;
    }


    /**
     * Sets the interiorColor value for this Vehicle.
     * 
     * @param interiorColor
     */
    public void setInteriorColor(java.lang.String interiorColor) {
        this.interiorColor = interiorColor;
    }


    /**
     * Gets the exteriorColor value for this Vehicle.
     * 
     * @return exteriorColor
     */
    public java.lang.String getExteriorColor() {
        return exteriorColor;
    }


    /**
     * Sets the exteriorColor value for this Vehicle.
     * 
     * @param exteriorColor
     */
    public void setExteriorColor(java.lang.String exteriorColor) {
        this.exteriorColor = exteriorColor;
    }


    /**
     * Gets the options value for this Vehicle.
     * 
     * @return options
     */
    public java.lang.String[] getOptions() {
        return options;
    }


    /**
     * Sets the options value for this Vehicle.
     * 
     * @param options
     */
    public void setOptions(java.lang.String[] options) {
        this.options = options;
    }


    /**
     * Gets the preferedFinanceMethod value for this Vehicle.
     * 
     * @return preferedFinanceMethod
     */
    public com.arb.ws.clients.autobytel.FinanceMethod getPreferedFinanceMethod() {
        return preferedFinanceMethod;
    }


    /**
     * Sets the preferedFinanceMethod value for this Vehicle.
     * 
     * @param preferedFinanceMethod
     */
    public void setPreferedFinanceMethod(com.arb.ws.clients.autobytel.FinanceMethod preferedFinanceMethod) {
        this.preferedFinanceMethod = preferedFinanceMethod;
    }


    /**
     * Gets the downPayment value for this Vehicle.
     * 
     * @return downPayment
     */
    public int getDownPayment() {
        return downPayment;
    }


    /**
     * Sets the downPayment value for this Vehicle.
     * 
     * @param downPayment
     */
    public void setDownPayment(int downPayment) {
        this.downPayment = downPayment;
    }


    /**
     * Gets the comments value for this Vehicle.
     * 
     * @return comments
     */
    public java.lang.String getComments() {
        return comments;
    }


    /**
     * Sets the comments value for this Vehicle.
     * 
     * @param comments
     */
    public void setComments(java.lang.String comments) {
        this.comments = comments;
    }


    /**
     * Gets the imageTag value for this Vehicle.
     * 
     * @return imageTag
     */
    public java.lang.String getImageTag() {
        return imageTag;
    }


    /**
     * Sets the imageTag value for this Vehicle.
     * 
     * @param imageTag
     */
    public void setImageTag(java.lang.String imageTag) {
        this.imageTag = imageTag;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Vehicle)) return false;
        Vehicle other = (Vehicle) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus()))) &&
            ((this.vehicleID==null && other.getVehicleID()==null) || 
             (this.vehicleID!=null &&
              this.vehicleID.equals(other.getVehicleID()))) &&
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
            ((this.interiorColor==null && other.getInteriorColor()==null) || 
             (this.interiorColor!=null &&
              this.interiorColor.equals(other.getInteriorColor()))) &&
            ((this.exteriorColor==null && other.getExteriorColor()==null) || 
             (this.exteriorColor!=null &&
              this.exteriorColor.equals(other.getExteriorColor()))) &&
            ((this.options==null && other.getOptions()==null) || 
             (this.options!=null &&
              java.util.Arrays.equals(this.options, other.getOptions()))) &&
            ((this.preferedFinanceMethod==null && other.getPreferedFinanceMethod()==null) || 
             (this.preferedFinanceMethod!=null &&
              this.preferedFinanceMethod.equals(other.getPreferedFinanceMethod()))) &&
            this.downPayment == other.getDownPayment() &&
            ((this.comments==null && other.getComments()==null) || 
             (this.comments!=null &&
              this.comments.equals(other.getComments()))) &&
            ((this.imageTag==null && other.getImageTag()==null) || 
             (this.imageTag!=null &&
              this.imageTag.equals(other.getImageTag())));
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
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        if (getVehicleID() != null) {
            _hashCode += getVehicleID().hashCode();
        }
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
        if (getInteriorColor() != null) {
            _hashCode += getInteriorColor().hashCode();
        }
        if (getExteriorColor() != null) {
            _hashCode += getExteriorColor().hashCode();
        }
        if (getOptions() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getOptions());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getOptions(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getPreferedFinanceMethod() != null) {
            _hashCode += getPreferedFinanceMethod().hashCode();
        }
        _hashCode += getDownPayment();
        if (getComments() != null) {
            _hashCode += getComments().hashCode();
        }
        if (getImageTag() != null) {
            _hashCode += getImageTag().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Vehicle.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.autobytel.com/", "Vehicle"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.autobytel.com/", "Status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.autobytel.com/", "VehicleStatus"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vehicleID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.autobytel.com/", "VehicleID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
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
        elemField.setFieldName("interiorColor");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.autobytel.com/", "InteriorColor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("exteriorColor");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.autobytel.com/", "ExteriorColor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("options");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.autobytel.com/", "Options"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://www.autobytel.com/", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("preferedFinanceMethod");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.autobytel.com/", "PreferedFinanceMethod"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.autobytel.com/", "FinanceMethod"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("downPayment");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.autobytel.com/", "DownPayment"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("comments");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.autobytel.com/", "Comments"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("imageTag");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.autobytel.com/", "ImageTag"));
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
