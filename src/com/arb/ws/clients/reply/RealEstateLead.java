/**
 * RealEstateLead.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.arb.ws.clients.reply;

public class RealEstateLead  extends com.arb.ws.clients.reply.Lead  implements java.io.Serializable {
    private com.arb.ws.clients.reply.RealEstateLeadLeadType leadType;

    private com.arb.ws.clients.reply.RealEstateLeadPropertyType propertyType;

    private int timeFrame;

    private java.lang.String buyZip1;

    private java.lang.String buyZip2;

    private java.lang.String buyZip3;

    private java.math.BigDecimal priceRangeStart;

    private java.math.BigDecimal priceRangeEnd;

    private int squareFootage;

    private int bedroomCount;

    private int bathroomCount;

    private java.lang.String creditHistory;

    private boolean hasAgent;

    private boolean foundHome;

    private java.lang.String whySelling;

    private int intent;

    public RealEstateLead() {
    }

    public RealEstateLead(
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
           com.arb.ws.clients.reply.RealEstateLeadLeadType leadType,
           com.arb.ws.clients.reply.RealEstateLeadPropertyType propertyType,
           int timeFrame,
           java.lang.String buyZip1,
           java.lang.String buyZip2,
           java.lang.String buyZip3,
           java.math.BigDecimal priceRangeStart,
           java.math.BigDecimal priceRangeEnd,
           int squareFootage,
           int bedroomCount,
           int bathroomCount,
           java.lang.String creditHistory,
           boolean hasAgent,
           boolean foundHome,
           java.lang.String whySelling,
           int intent) {
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
        this.propertyType = propertyType;
        this.timeFrame = timeFrame;
        this.buyZip1 = buyZip1;
        this.buyZip2 = buyZip2;
        this.buyZip3 = buyZip3;
        this.priceRangeStart = priceRangeStart;
        this.priceRangeEnd = priceRangeEnd;
        this.squareFootage = squareFootage;
        this.bedroomCount = bedroomCount;
        this.bathroomCount = bathroomCount;
        this.creditHistory = creditHistory;
        this.hasAgent = hasAgent;
        this.foundHome = foundHome;
        this.whySelling = whySelling;
        this.intent = intent;
    }


    /**
     * Gets the leadType value for this RealEstateLead.
     * 
     * @return leadType
     */
    public com.arb.ws.clients.reply.RealEstateLeadLeadType getLeadType() {
        return leadType;
    }


    /**
     * Sets the leadType value for this RealEstateLead.
     * 
     * @param leadType
     */
    public void setLeadType(com.arb.ws.clients.reply.RealEstateLeadLeadType leadType) {
        this.leadType = leadType;
    }


    /**
     * Gets the propertyType value for this RealEstateLead.
     * 
     * @return propertyType
     */
    public com.arb.ws.clients.reply.RealEstateLeadPropertyType getPropertyType() {
        return propertyType;
    }


    /**
     * Sets the propertyType value for this RealEstateLead.
     * 
     * @param propertyType
     */
    public void setPropertyType(com.arb.ws.clients.reply.RealEstateLeadPropertyType propertyType) {
        this.propertyType = propertyType;
    }


    /**
     * Gets the timeFrame value for this RealEstateLead.
     * 
     * @return timeFrame
     */
    public int getTimeFrame() {
        return timeFrame;
    }


    /**
     * Sets the timeFrame value for this RealEstateLead.
     * 
     * @param timeFrame
     */
    public void setTimeFrame(int timeFrame) {
        this.timeFrame = timeFrame;
    }


    /**
     * Gets the buyZip1 value for this RealEstateLead.
     * 
     * @return buyZip1
     */
    public java.lang.String getBuyZip1() {
        return buyZip1;
    }


    /**
     * Sets the buyZip1 value for this RealEstateLead.
     * 
     * @param buyZip1
     */
    public void setBuyZip1(java.lang.String buyZip1) {
        this.buyZip1 = buyZip1;
    }


    /**
     * Gets the buyZip2 value for this RealEstateLead.
     * 
     * @return buyZip2
     */
    public java.lang.String getBuyZip2() {
        return buyZip2;
    }


    /**
     * Sets the buyZip2 value for this RealEstateLead.
     * 
     * @param buyZip2
     */
    public void setBuyZip2(java.lang.String buyZip2) {
        this.buyZip2 = buyZip2;
    }


    /**
     * Gets the buyZip3 value for this RealEstateLead.
     * 
     * @return buyZip3
     */
    public java.lang.String getBuyZip3() {
        return buyZip3;
    }


    /**
     * Sets the buyZip3 value for this RealEstateLead.
     * 
     * @param buyZip3
     */
    public void setBuyZip3(java.lang.String buyZip3) {
        this.buyZip3 = buyZip3;
    }


    /**
     * Gets the priceRangeStart value for this RealEstateLead.
     * 
     * @return priceRangeStart
     */
    public java.math.BigDecimal getPriceRangeStart() {
        return priceRangeStart;
    }


    /**
     * Sets the priceRangeStart value for this RealEstateLead.
     * 
     * @param priceRangeStart
     */
    public void setPriceRangeStart(java.math.BigDecimal priceRangeStart) {
        this.priceRangeStart = priceRangeStart;
    }


    /**
     * Gets the priceRangeEnd value for this RealEstateLead.
     * 
     * @return priceRangeEnd
     */
    public java.math.BigDecimal getPriceRangeEnd() {
        return priceRangeEnd;
    }


    /**
     * Sets the priceRangeEnd value for this RealEstateLead.
     * 
     * @param priceRangeEnd
     */
    public void setPriceRangeEnd(java.math.BigDecimal priceRangeEnd) {
        this.priceRangeEnd = priceRangeEnd;
    }


    /**
     * Gets the squareFootage value for this RealEstateLead.
     * 
     * @return squareFootage
     */
    public int getSquareFootage() {
        return squareFootage;
    }


    /**
     * Sets the squareFootage value for this RealEstateLead.
     * 
     * @param squareFootage
     */
    public void setSquareFootage(int squareFootage) {
        this.squareFootage = squareFootage;
    }


    /**
     * Gets the bedroomCount value for this RealEstateLead.
     * 
     * @return bedroomCount
     */
    public int getBedroomCount() {
        return bedroomCount;
    }


    /**
     * Sets the bedroomCount value for this RealEstateLead.
     * 
     * @param bedroomCount
     */
    public void setBedroomCount(int bedroomCount) {
        this.bedroomCount = bedroomCount;
    }


    /**
     * Gets the bathroomCount value for this RealEstateLead.
     * 
     * @return bathroomCount
     */
    public int getBathroomCount() {
        return bathroomCount;
    }


    /**
     * Sets the bathroomCount value for this RealEstateLead.
     * 
     * @param bathroomCount
     */
    public void setBathroomCount(int bathroomCount) {
        this.bathroomCount = bathroomCount;
    }


    /**
     * Gets the creditHistory value for this RealEstateLead.
     * 
     * @return creditHistory
     */
    public java.lang.String getCreditHistory() {
        return creditHistory;
    }


    /**
     * Sets the creditHistory value for this RealEstateLead.
     * 
     * @param creditHistory
     */
    public void setCreditHistory(java.lang.String creditHistory) {
        this.creditHistory = creditHistory;
    }


    /**
     * Gets the hasAgent value for this RealEstateLead.
     * 
     * @return hasAgent
     */
    public boolean isHasAgent() {
        return hasAgent;
    }


    /**
     * Sets the hasAgent value for this RealEstateLead.
     * 
     * @param hasAgent
     */
    public void setHasAgent(boolean hasAgent) {
        this.hasAgent = hasAgent;
    }


    /**
     * Gets the foundHome value for this RealEstateLead.
     * 
     * @return foundHome
     */
    public boolean isFoundHome() {
        return foundHome;
    }


    /**
     * Sets the foundHome value for this RealEstateLead.
     * 
     * @param foundHome
     */
    public void setFoundHome(boolean foundHome) {
        this.foundHome = foundHome;
    }


    /**
     * Gets the whySelling value for this RealEstateLead.
     * 
     * @return whySelling
     */
    public java.lang.String getWhySelling() {
        return whySelling;
    }


    /**
     * Sets the whySelling value for this RealEstateLead.
     * 
     * @param whySelling
     */
    public void setWhySelling(java.lang.String whySelling) {
        this.whySelling = whySelling;
    }


    /**
     * Gets the intent value for this RealEstateLead.
     * 
     * @return intent
     */
    public int getIntent() {
        return intent;
    }


    /**
     * Sets the intent value for this RealEstateLead.
     * 
     * @param intent
     */
    public void setIntent(int intent) {
        this.intent = intent;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RealEstateLead)) return false;
        RealEstateLead other = (RealEstateLead) obj;
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
            ((this.propertyType==null && other.getPropertyType()==null) || 
             (this.propertyType!=null &&
              this.propertyType.equals(other.getPropertyType()))) &&
            this.timeFrame == other.getTimeFrame() &&
            ((this.buyZip1==null && other.getBuyZip1()==null) || 
             (this.buyZip1!=null &&
              this.buyZip1.equals(other.getBuyZip1()))) &&
            ((this.buyZip2==null && other.getBuyZip2()==null) || 
             (this.buyZip2!=null &&
              this.buyZip2.equals(other.getBuyZip2()))) &&
            ((this.buyZip3==null && other.getBuyZip3()==null) || 
             (this.buyZip3!=null &&
              this.buyZip3.equals(other.getBuyZip3()))) &&
            ((this.priceRangeStart==null && other.getPriceRangeStart()==null) || 
             (this.priceRangeStart!=null &&
              this.priceRangeStart.equals(other.getPriceRangeStart()))) &&
            ((this.priceRangeEnd==null && other.getPriceRangeEnd()==null) || 
             (this.priceRangeEnd!=null &&
              this.priceRangeEnd.equals(other.getPriceRangeEnd()))) &&
            this.squareFootage == other.getSquareFootage() &&
            this.bedroomCount == other.getBedroomCount() &&
            this.bathroomCount == other.getBathroomCount() &&
            ((this.creditHistory==null && other.getCreditHistory()==null) || 
             (this.creditHistory!=null &&
              this.creditHistory.equals(other.getCreditHistory()))) &&
            this.hasAgent == other.isHasAgent() &&
            this.foundHome == other.isFoundHome() &&
            ((this.whySelling==null && other.getWhySelling()==null) || 
             (this.whySelling!=null &&
              this.whySelling.equals(other.getWhySelling()))) &&
            this.intent == other.getIntent();
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
        if (getPropertyType() != null) {
            _hashCode += getPropertyType().hashCode();
        }
        _hashCode += getTimeFrame();
        if (getBuyZip1() != null) {
            _hashCode += getBuyZip1().hashCode();
        }
        if (getBuyZip2() != null) {
            _hashCode += getBuyZip2().hashCode();
        }
        if (getBuyZip3() != null) {
            _hashCode += getBuyZip3().hashCode();
        }
        if (getPriceRangeStart() != null) {
            _hashCode += getPriceRangeStart().hashCode();
        }
        if (getPriceRangeEnd() != null) {
            _hashCode += getPriceRangeEnd().hashCode();
        }
        _hashCode += getSquareFootage();
        _hashCode += getBedroomCount();
        _hashCode += getBathroomCount();
        if (getCreditHistory() != null) {
            _hashCode += getCreditHistory().hashCode();
        }
        _hashCode += (isHasAgent() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isFoundHome() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getWhySelling() != null) {
            _hashCode += getWhySelling().hashCode();
        }
        _hashCode += getIntent();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RealEstateLead.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "RealEstateLead"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("leadType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "LeadType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", ">RealEstateLead>LeadType"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("propertyType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "PropertyType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", ">RealEstateLead>PropertyType"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("timeFrame");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "TimeFrame"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("buyZip1");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "BuyZip1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("buyZip2");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "BuyZip2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("buyZip3");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "BuyZip3"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("priceRangeStart");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "PriceRangeStart"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("priceRangeEnd");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "PriceRangeEnd"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("squareFootage");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "SquareFootage"));
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
        elemField.setFieldName("bathroomCount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "BathroomCount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("creditHistory");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "CreditHistory"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hasAgent");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "HasAgent"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("foundHome");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "FoundHome"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("whySelling");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "WhySelling"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("intent");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "Intent"));
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
