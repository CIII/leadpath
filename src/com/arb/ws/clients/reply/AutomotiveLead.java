/**
 * AutomotiveLead.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.arb.ws.clients.reply;

public class AutomotiveLead  extends com.arb.ws.clients.reply.Lead  implements java.io.Serializable {
    private com.arb.ws.clients.reply.AutomotiveLeadLeadType leadType;

    private java.lang.String make;

    private java.lang.String model;

    private java.lang.String year;

    private java.lang.String trim;

    private java.lang.String exteriorColor;

    private java.lang.String interiorColor;

    private java.lang.String transmission;

    private boolean isDealerSelect;

    private boolean tradeIn;

    private java.lang.String[] zipbacks;

    private java.lang.String[] dealerSelect;

    private java.lang.String sequenceDupe;

    private java.lang.String timeframe;

    public AutomotiveLead() {
    }

    public AutomotiveLead(
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
           com.arb.ws.clients.reply.AutomotiveLeadLeadType leadType,
           java.lang.String make,
           java.lang.String model,
           java.lang.String year,
           java.lang.String trim,
           java.lang.String exteriorColor,
           java.lang.String interiorColor,
           java.lang.String transmission,
           boolean isDealerSelect,
           boolean tradeIn,
           java.lang.String[] zipbacks,
           java.lang.String[] dealerSelect,
           java.lang.String sequenceDupe,
           java.lang.String timeframe) {
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
        this.leadType = leadType;
        this.make = make;
        this.model = model;
        this.year = year;
        this.trim = trim;
        this.exteriorColor = exteriorColor;
        this.interiorColor = interiorColor;
        this.transmission = transmission;
        this.isDealerSelect = isDealerSelect;
        this.tradeIn = tradeIn;
        this.zipbacks = zipbacks;
        this.dealerSelect = dealerSelect;
        this.sequenceDupe = sequenceDupe;
        this.timeframe = timeframe;
    }


    /**
     * Gets the leadType value for this AutomotiveLead.
     * 
     * @return leadType
     */
    public com.arb.ws.clients.reply.AutomotiveLeadLeadType getLeadType() {
        return leadType;
    }


    /**
     * Sets the leadType value for this AutomotiveLead.
     * 
     * @param leadType
     */
    public void setLeadType(com.arb.ws.clients.reply.AutomotiveLeadLeadType leadType) {
        this.leadType = leadType;
    }


    /**
     * Gets the make value for this AutomotiveLead.
     * 
     * @return make
     */
    public java.lang.String getMake() {
        return make;
    }


    /**
     * Sets the make value for this AutomotiveLead.
     * 
     * @param make
     */
    public void setMake(java.lang.String make) {
        this.make = make;
    }


    /**
     * Gets the model value for this AutomotiveLead.
     * 
     * @return model
     */
    public java.lang.String getModel() {
        return model;
    }


    /**
     * Sets the model value for this AutomotiveLead.
     * 
     * @param model
     */
    public void setModel(java.lang.String model) {
        this.model = model;
    }


    /**
     * Gets the year value for this AutomotiveLead.
     * 
     * @return year
     */
    public java.lang.String getYear() {
        return year;
    }


    /**
     * Sets the year value for this AutomotiveLead.
     * 
     * @param year
     */
    public void setYear(java.lang.String year) {
        this.year = year;
    }


    /**
     * Gets the trim value for this AutomotiveLead.
     * 
     * @return trim
     */
    public java.lang.String getTrim() {
        return trim;
    }


    /**
     * Sets the trim value for this AutomotiveLead.
     * 
     * @param trim
     */
    public void setTrim(java.lang.String trim) {
        this.trim = trim;
    }


    /**
     * Gets the exteriorColor value for this AutomotiveLead.
     * 
     * @return exteriorColor
     */
    public java.lang.String getExteriorColor() {
        return exteriorColor;
    }


    /**
     * Sets the exteriorColor value for this AutomotiveLead.
     * 
     * @param exteriorColor
     */
    public void setExteriorColor(java.lang.String exteriorColor) {
        this.exteriorColor = exteriorColor;
    }


    /**
     * Gets the interiorColor value for this AutomotiveLead.
     * 
     * @return interiorColor
     */
    public java.lang.String getInteriorColor() {
        return interiorColor;
    }


    /**
     * Sets the interiorColor value for this AutomotiveLead.
     * 
     * @param interiorColor
     */
    public void setInteriorColor(java.lang.String interiorColor) {
        this.interiorColor = interiorColor;
    }


    /**
     * Gets the transmission value for this AutomotiveLead.
     * 
     * @return transmission
     */
    public java.lang.String getTransmission() {
        return transmission;
    }


    /**
     * Sets the transmission value for this AutomotiveLead.
     * 
     * @param transmission
     */
    public void setTransmission(java.lang.String transmission) {
        this.transmission = transmission;
    }


    /**
     * Gets the isDealerSelect value for this AutomotiveLead.
     * 
     * @return isDealerSelect
     */
    public boolean isIsDealerSelect() {
        return isDealerSelect;
    }


    /**
     * Sets the isDealerSelect value for this AutomotiveLead.
     * 
     * @param isDealerSelect
     */
    public void setIsDealerSelect(boolean isDealerSelect) {
        this.isDealerSelect = isDealerSelect;
    }


    /**
     * Gets the tradeIn value for this AutomotiveLead.
     * 
     * @return tradeIn
     */
    public boolean isTradeIn() {
        return tradeIn;
    }


    /**
     * Sets the tradeIn value for this AutomotiveLead.
     * 
     * @param tradeIn
     */
    public void setTradeIn(boolean tradeIn) {
        this.tradeIn = tradeIn;
    }


    /**
     * Gets the zipbacks value for this AutomotiveLead.
     * 
     * @return zipbacks
     */
    public java.lang.String[] getZipbacks() {
        return zipbacks;
    }


    /**
     * Sets the zipbacks value for this AutomotiveLead.
     * 
     * @param zipbacks
     */
    public void setZipbacks(java.lang.String[] zipbacks) {
        this.zipbacks = zipbacks;
    }


    /**
     * Gets the dealerSelect value for this AutomotiveLead.
     * 
     * @return dealerSelect
     */
    public java.lang.String[] getDealerSelect() {
        return dealerSelect;
    }


    /**
     * Sets the dealerSelect value for this AutomotiveLead.
     * 
     * @param dealerSelect
     */
    public void setDealerSelect(java.lang.String[] dealerSelect) {
        this.dealerSelect = dealerSelect;
    }


    /**
     * Gets the sequenceDupe value for this AutomotiveLead.
     * 
     * @return sequenceDupe
     */
    public java.lang.String getSequenceDupe() {
        return sequenceDupe;
    }


    /**
     * Sets the sequenceDupe value for this AutomotiveLead.
     * 
     * @param sequenceDupe
     */
    public void setSequenceDupe(java.lang.String sequenceDupe) {
        this.sequenceDupe = sequenceDupe;
    }


    /**
     * Gets the timeframe value for this AutomotiveLead.
     * 
     * @return timeframe
     */
    public java.lang.String getTimeframe() {
        return timeframe;
    }


    /**
     * Sets the timeframe value for this AutomotiveLead.
     * 
     * @param timeframe
     */
    public void setTimeframe(java.lang.String timeframe) {
        this.timeframe = timeframe;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AutomotiveLead)) return false;
        AutomotiveLead other = (AutomotiveLead) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.leadType==null && other.getLeadType()==null) || 
             (this.leadType!=null &&
              this.leadType.equals(other.getLeadType()))) &&
            ((this.make==null && other.getMake()==null) || 
             (this.make!=null &&
              this.make.equals(other.getMake()))) &&
            ((this.model==null && other.getModel()==null) || 
             (this.model!=null &&
              this.model.equals(other.getModel()))) &&
            ((this.year==null && other.getYear()==null) || 
             (this.year!=null &&
              this.year.equals(other.getYear()))) &&
            ((this.trim==null && other.getTrim()==null) || 
             (this.trim!=null &&
              this.trim.equals(other.getTrim()))) &&
            ((this.exteriorColor==null && other.getExteriorColor()==null) || 
             (this.exteriorColor!=null &&
              this.exteriorColor.equals(other.getExteriorColor()))) &&
            ((this.interiorColor==null && other.getInteriorColor()==null) || 
             (this.interiorColor!=null &&
              this.interiorColor.equals(other.getInteriorColor()))) &&
            ((this.transmission==null && other.getTransmission()==null) || 
             (this.transmission!=null &&
              this.transmission.equals(other.getTransmission()))) &&
            this.isDealerSelect == other.isIsDealerSelect() &&
            this.tradeIn == other.isTradeIn() &&
            ((this.zipbacks==null && other.getZipbacks()==null) || 
             (this.zipbacks!=null &&
              java.util.Arrays.equals(this.zipbacks, other.getZipbacks()))) &&
            ((this.dealerSelect==null && other.getDealerSelect()==null) || 
             (this.dealerSelect!=null &&
              java.util.Arrays.equals(this.dealerSelect, other.getDealerSelect()))) &&
            ((this.sequenceDupe==null && other.getSequenceDupe()==null) || 
             (this.sequenceDupe!=null &&
              this.sequenceDupe.equals(other.getSequenceDupe()))) &&
            ((this.timeframe==null && other.getTimeframe()==null) || 
             (this.timeframe!=null &&
              this.timeframe.equals(other.getTimeframe())));
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
        if (getLeadType() != null) {
            _hashCode += getLeadType().hashCode();
        }
        if (getMake() != null) {
            _hashCode += getMake().hashCode();
        }
        if (getModel() != null) {
            _hashCode += getModel().hashCode();
        }
        if (getYear() != null) {
            _hashCode += getYear().hashCode();
        }
        if (getTrim() != null) {
            _hashCode += getTrim().hashCode();
        }
        if (getExteriorColor() != null) {
            _hashCode += getExteriorColor().hashCode();
        }
        if (getInteriorColor() != null) {
            _hashCode += getInteriorColor().hashCode();
        }
        if (getTransmission() != null) {
            _hashCode += getTransmission().hashCode();
        }
        _hashCode += (isIsDealerSelect() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isTradeIn() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getZipbacks() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getZipbacks());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getZipbacks(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getDealerSelect() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDealerSelect());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDealerSelect(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getSequenceDupe() != null) {
            _hashCode += getSequenceDupe().hashCode();
        }
        if (getTimeframe() != null) {
            _hashCode += getTimeframe().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AutomotiveLead.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "AutomotiveLead"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("leadType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "LeadType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", ">AutomotiveLead>LeadType"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("make");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "Make"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("model");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "Model"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("year");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "Year"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("trim");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "Trim"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("exteriorColor");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "ExteriorColor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("interiorColor");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "InteriorColor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("transmission");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "Transmission"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isDealerSelect");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "IsDealerSelect"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tradeIn");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "TradeIn"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("zipbacks");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "Zipbacks"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "Zipcode"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dealerSelect");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "DealerSelect"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "Zipcode"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sequenceDupe");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "SequenceDupe"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("timeframe");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "Timeframe"));
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
