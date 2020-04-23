/**
 * Incident.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.arb.ws.clients.reply;

public class Incident  implements java.io.Serializable {
    private com.arb.ws.clients.reply.IncidentType incidentType;

    private short driverId;

    private short year;

    private short month;

    private com.arb.ws.clients.reply.DUI DUI;

    private com.arb.ws.clients.reply.Ticket ticket;

    private com.arb.ws.clients.reply.Accident accident;

    private com.arb.ws.clients.reply.AutoClaimInfo claim;

    public Incident() {
    }

    public Incident(
           com.arb.ws.clients.reply.IncidentType incidentType,
           short driverId,
           short year,
           short month,
           com.arb.ws.clients.reply.DUI DUI,
           com.arb.ws.clients.reply.Ticket ticket,
           com.arb.ws.clients.reply.Accident accident,
           com.arb.ws.clients.reply.AutoClaimInfo claim) {
           this.incidentType = incidentType;
           this.driverId = driverId;
           this.year = year;
           this.month = month;
           this.DUI = DUI;
           this.ticket = ticket;
           this.accident = accident;
           this.claim = claim;
    }


    /**
     * Gets the incidentType value for this Incident.
     * 
     * @return incidentType
     */
    public com.arb.ws.clients.reply.IncidentType getIncidentType() {
        return incidentType;
    }


    /**
     * Sets the incidentType value for this Incident.
     * 
     * @param incidentType
     */
    public void setIncidentType(com.arb.ws.clients.reply.IncidentType incidentType) {
        this.incidentType = incidentType;
    }


    /**
     * Gets the driverId value for this Incident.
     * 
     * @return driverId
     */
    public short getDriverId() {
        return driverId;
    }


    /**
     * Sets the driverId value for this Incident.
     * 
     * @param driverId
     */
    public void setDriverId(short driverId) {
        this.driverId = driverId;
    }


    /**
     * Gets the year value for this Incident.
     * 
     * @return year
     */
    public short getYear() {
        return year;
    }


    /**
     * Sets the year value for this Incident.
     * 
     * @param year
     */
    public void setYear(short year) {
        this.year = year;
    }


    /**
     * Gets the month value for this Incident.
     * 
     * @return month
     */
    public short getMonth() {
        return month;
    }


    /**
     * Sets the month value for this Incident.
     * 
     * @param month
     */
    public void setMonth(short month) {
        this.month = month;
    }


    /**
     * Gets the DUI value for this Incident.
     * 
     * @return DUI
     */
    public com.arb.ws.clients.reply.DUI getDUI() {
        return DUI;
    }


    /**
     * Sets the DUI value for this Incident.
     * 
     * @param DUI
     */
    public void setDUI(com.arb.ws.clients.reply.DUI DUI) {
        this.DUI = DUI;
    }


    /**
     * Gets the ticket value for this Incident.
     * 
     * @return ticket
     */
    public com.arb.ws.clients.reply.Ticket getTicket() {
        return ticket;
    }


    /**
     * Sets the ticket value for this Incident.
     * 
     * @param ticket
     */
    public void setTicket(com.arb.ws.clients.reply.Ticket ticket) {
        this.ticket = ticket;
    }


    /**
     * Gets the accident value for this Incident.
     * 
     * @return accident
     */
    public com.arb.ws.clients.reply.Accident getAccident() {
        return accident;
    }


    /**
     * Sets the accident value for this Incident.
     * 
     * @param accident
     */
    public void setAccident(com.arb.ws.clients.reply.Accident accident) {
        this.accident = accident;
    }


    /**
     * Gets the claim value for this Incident.
     * 
     * @return claim
     */
    public com.arb.ws.clients.reply.AutoClaimInfo getClaim() {
        return claim;
    }


    /**
     * Sets the claim value for this Incident.
     * 
     * @param claim
     */
    public void setClaim(com.arb.ws.clients.reply.AutoClaimInfo claim) {
        this.claim = claim;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Incident)) return false;
        Incident other = (Incident) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.incidentType==null && other.getIncidentType()==null) || 
             (this.incidentType!=null &&
              this.incidentType.equals(other.getIncidentType()))) &&
            this.driverId == other.getDriverId() &&
            this.year == other.getYear() &&
            this.month == other.getMonth() &&
            ((this.DUI==null && other.getDUI()==null) || 
             (this.DUI!=null &&
              this.DUI.equals(other.getDUI()))) &&
            ((this.ticket==null && other.getTicket()==null) || 
             (this.ticket!=null &&
              this.ticket.equals(other.getTicket()))) &&
            ((this.accident==null && other.getAccident()==null) || 
             (this.accident!=null &&
              this.accident.equals(other.getAccident()))) &&
            ((this.claim==null && other.getClaim()==null) || 
             (this.claim!=null &&
              this.claim.equals(other.getClaim())));
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
        if (getIncidentType() != null) {
            _hashCode += getIncidentType().hashCode();
        }
        _hashCode += getDriverId();
        _hashCode += getYear();
        _hashCode += getMonth();
        if (getDUI() != null) {
            _hashCode += getDUI().hashCode();
        }
        if (getTicket() != null) {
            _hashCode += getTicket().hashCode();
        }
        if (getAccident() != null) {
            _hashCode += getAccident().hashCode();
        }
        if (getClaim() != null) {
            _hashCode += getClaim().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Incident.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "Incident"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("incidentType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "IncidentType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "IncidentType"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("driverId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "DriverId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "short"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("year");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "Year"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "short"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("month");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "Month"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "short"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("DUI");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "DUI"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "DUI"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ticket");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "Ticket"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "Ticket"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accident");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "Accident"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "Accident"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("claim");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "Claim"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "AutoClaimInfo"));
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
