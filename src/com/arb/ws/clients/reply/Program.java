/**
 * Program.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.arb.ws.clients.reply;

public class Program  implements java.io.Serializable {
    private com.arb.ws.clients.reply.DegreeType degreeType;

    private com.arb.ws.clients.reply.Concentration concentration;

    private java.lang.String programDescription;

    private java.lang.String schoolProgramId;

    private int priority;

    public Program() {
    }

    public Program(
           com.arb.ws.clients.reply.DegreeType degreeType,
           com.arb.ws.clients.reply.Concentration concentration,
           java.lang.String programDescription,
           java.lang.String schoolProgramId,
           int priority) {
           this.degreeType = degreeType;
           this.concentration = concentration;
           this.programDescription = programDescription;
           this.schoolProgramId = schoolProgramId;
           this.priority = priority;
    }


    /**
     * Gets the degreeType value for this Program.
     * 
     * @return degreeType
     */
    public com.arb.ws.clients.reply.DegreeType getDegreeType() {
        return degreeType;
    }


    /**
     * Sets the degreeType value for this Program.
     * 
     * @param degreeType
     */
    public void setDegreeType(com.arb.ws.clients.reply.DegreeType degreeType) {
        this.degreeType = degreeType;
    }


    /**
     * Gets the concentration value for this Program.
     * 
     * @return concentration
     */
    public com.arb.ws.clients.reply.Concentration getConcentration() {
        return concentration;
    }


    /**
     * Sets the concentration value for this Program.
     * 
     * @param concentration
     */
    public void setConcentration(com.arb.ws.clients.reply.Concentration concentration) {
        this.concentration = concentration;
    }


    /**
     * Gets the programDescription value for this Program.
     * 
     * @return programDescription
     */
    public java.lang.String getProgramDescription() {
        return programDescription;
    }


    /**
     * Sets the programDescription value for this Program.
     * 
     * @param programDescription
     */
    public void setProgramDescription(java.lang.String programDescription) {
        this.programDescription = programDescription;
    }


    /**
     * Gets the schoolProgramId value for this Program.
     * 
     * @return schoolProgramId
     */
    public java.lang.String getSchoolProgramId() {
        return schoolProgramId;
    }


    /**
     * Sets the schoolProgramId value for this Program.
     * 
     * @param schoolProgramId
     */
    public void setSchoolProgramId(java.lang.String schoolProgramId) {
        this.schoolProgramId = schoolProgramId;
    }


    /**
     * Gets the priority value for this Program.
     * 
     * @return priority
     */
    public int getPriority() {
        return priority;
    }


    /**
     * Sets the priority value for this Program.
     * 
     * @param priority
     */
    public void setPriority(int priority) {
        this.priority = priority;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Program)) return false;
        Program other = (Program) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.degreeType==null && other.getDegreeType()==null) || 
             (this.degreeType!=null &&
              this.degreeType.equals(other.getDegreeType()))) &&
            ((this.concentration==null && other.getConcentration()==null) || 
             (this.concentration!=null &&
              this.concentration.equals(other.getConcentration()))) &&
            ((this.programDescription==null && other.getProgramDescription()==null) || 
             (this.programDescription!=null &&
              this.programDescription.equals(other.getProgramDescription()))) &&
            ((this.schoolProgramId==null && other.getSchoolProgramId()==null) || 
             (this.schoolProgramId!=null &&
              this.schoolProgramId.equals(other.getSchoolProgramId()))) &&
            this.priority == other.getPriority();
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
        if (getDegreeType() != null) {
            _hashCode += getDegreeType().hashCode();
        }
        if (getConcentration() != null) {
            _hashCode += getConcentration().hashCode();
        }
        if (getProgramDescription() != null) {
            _hashCode += getProgramDescription().hashCode();
        }
        if (getSchoolProgramId() != null) {
            _hashCode += getSchoolProgramId().hashCode();
        }
        _hashCode += getPriority();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Program.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "Program"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("degreeType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "DegreeType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "DegreeType"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("concentration");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "Concentration"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "Concentration"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("programDescription");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "ProgramDescription"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("schoolProgramId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "SchoolProgramId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("priority");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "Priority"));
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
