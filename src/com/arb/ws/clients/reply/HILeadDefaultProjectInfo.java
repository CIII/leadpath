/**
 * HILeadDefaultProjectInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.arb.ws.clients.reply;

public class HILeadDefaultProjectInfo  implements java.io.Serializable {
    private java.lang.String timeFrame;

    private java.lang.String budgetRange;

    private java.lang.String projectStatus;

    private java.lang.String additionalInfo;

    public HILeadDefaultProjectInfo() {
    }

    public HILeadDefaultProjectInfo(
           java.lang.String timeFrame,
           java.lang.String budgetRange,
           java.lang.String projectStatus,
           java.lang.String additionalInfo) {
           this.timeFrame = timeFrame;
           this.budgetRange = budgetRange;
           this.projectStatus = projectStatus;
           this.additionalInfo = additionalInfo;
    }


    /**
     * Gets the timeFrame value for this HILeadDefaultProjectInfo.
     * 
     * @return timeFrame
     */
    public java.lang.String getTimeFrame() {
        return timeFrame;
    }


    /**
     * Sets the timeFrame value for this HILeadDefaultProjectInfo.
     * 
     * @param timeFrame
     */
    public void setTimeFrame(java.lang.String timeFrame) {
        this.timeFrame = timeFrame;
    }


    /**
     * Gets the budgetRange value for this HILeadDefaultProjectInfo.
     * 
     * @return budgetRange
     */
    public java.lang.String getBudgetRange() {
        return budgetRange;
    }


    /**
     * Sets the budgetRange value for this HILeadDefaultProjectInfo.
     * 
     * @param budgetRange
     */
    public void setBudgetRange(java.lang.String budgetRange) {
        this.budgetRange = budgetRange;
    }


    /**
     * Gets the projectStatus value for this HILeadDefaultProjectInfo.
     * 
     * @return projectStatus
     */
    public java.lang.String getProjectStatus() {
        return projectStatus;
    }


    /**
     * Sets the projectStatus value for this HILeadDefaultProjectInfo.
     * 
     * @param projectStatus
     */
    public void setProjectStatus(java.lang.String projectStatus) {
        this.projectStatus = projectStatus;
    }


    /**
     * Gets the additionalInfo value for this HILeadDefaultProjectInfo.
     * 
     * @return additionalInfo
     */
    public java.lang.String getAdditionalInfo() {
        return additionalInfo;
    }


    /**
     * Sets the additionalInfo value for this HILeadDefaultProjectInfo.
     * 
     * @param additionalInfo
     */
    public void setAdditionalInfo(java.lang.String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof HILeadDefaultProjectInfo)) return false;
        HILeadDefaultProjectInfo other = (HILeadDefaultProjectInfo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.timeFrame==null && other.getTimeFrame()==null) || 
             (this.timeFrame!=null &&
              this.timeFrame.equals(other.getTimeFrame()))) &&
            ((this.budgetRange==null && other.getBudgetRange()==null) || 
             (this.budgetRange!=null &&
              this.budgetRange.equals(other.getBudgetRange()))) &&
            ((this.projectStatus==null && other.getProjectStatus()==null) || 
             (this.projectStatus!=null &&
              this.projectStatus.equals(other.getProjectStatus()))) &&
            ((this.additionalInfo==null && other.getAdditionalInfo()==null) || 
             (this.additionalInfo!=null &&
              this.additionalInfo.equals(other.getAdditionalInfo())));
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
        if (getTimeFrame() != null) {
            _hashCode += getTimeFrame().hashCode();
        }
        if (getBudgetRange() != null) {
            _hashCode += getBudgetRange().hashCode();
        }
        if (getProjectStatus() != null) {
            _hashCode += getProjectStatus().hashCode();
        }
        if (getAdditionalInfo() != null) {
            _hashCode += getAdditionalInfo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(HILeadDefaultProjectInfo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", ">HILead>DefaultProjectInfo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("timeFrame");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "TimeFrame"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("budgetRange");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "BudgetRange"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("projectStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "ProjectStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("additionalInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "AdditionalInfo"));
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
