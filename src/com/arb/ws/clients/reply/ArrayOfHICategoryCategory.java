/**
 * ArrayOfHICategoryCategory.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.arb.ws.clients.reply;

public class ArrayOfHICategoryCategory  implements java.io.Serializable {
    private com.arb.ws.clients.reply.ArrayOfHICategoryCategoryProjectInfo projectInfo;

    private com.arb.ws.clients.reply.CategoryField[] fields;

    private long categoryId;  // attribute

    private java.lang.String name;  // attribute

    private long parentID;  // attribute

    private java.lang.String parentName;  // attribute

    public ArrayOfHICategoryCategory() {
    }

    public ArrayOfHICategoryCategory(
           com.arb.ws.clients.reply.ArrayOfHICategoryCategoryProjectInfo projectInfo,
           com.arb.ws.clients.reply.CategoryField[] fields,
           long categoryId,
           java.lang.String name,
           long parentID,
           java.lang.String parentName) {
           this.projectInfo = projectInfo;
           this.fields = fields;
           this.categoryId = categoryId;
           this.name = name;
           this.parentID = parentID;
           this.parentName = parentName;
    }


    /**
     * Gets the projectInfo value for this ArrayOfHICategoryCategory.
     * 
     * @return projectInfo
     */
    public com.arb.ws.clients.reply.ArrayOfHICategoryCategoryProjectInfo getProjectInfo() {
        return projectInfo;
    }


    /**
     * Sets the projectInfo value for this ArrayOfHICategoryCategory.
     * 
     * @param projectInfo
     */
    public void setProjectInfo(com.arb.ws.clients.reply.ArrayOfHICategoryCategoryProjectInfo projectInfo) {
        this.projectInfo = projectInfo;
    }


    /**
     * Gets the fields value for this ArrayOfHICategoryCategory.
     * 
     * @return fields
     */
    public com.arb.ws.clients.reply.CategoryField[] getFields() {
        return fields;
    }


    /**
     * Sets the fields value for this ArrayOfHICategoryCategory.
     * 
     * @param fields
     */
    public void setFields(com.arb.ws.clients.reply.CategoryField[] fields) {
        this.fields = fields;
    }


    /**
     * Gets the categoryId value for this ArrayOfHICategoryCategory.
     * 
     * @return categoryId
     */
    public long getCategoryId() {
        return categoryId;
    }


    /**
     * Sets the categoryId value for this ArrayOfHICategoryCategory.
     * 
     * @param categoryId
     */
    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }


    /**
     * Gets the name value for this ArrayOfHICategoryCategory.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this ArrayOfHICategoryCategory.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the parentID value for this ArrayOfHICategoryCategory.
     * 
     * @return parentID
     */
    public long getParentID() {
        return parentID;
    }


    /**
     * Sets the parentID value for this ArrayOfHICategoryCategory.
     * 
     * @param parentID
     */
    public void setParentID(long parentID) {
        this.parentID = parentID;
    }


    /**
     * Gets the parentName value for this ArrayOfHICategoryCategory.
     * 
     * @return parentName
     */
    public java.lang.String getParentName() {
        return parentName;
    }


    /**
     * Sets the parentName value for this ArrayOfHICategoryCategory.
     * 
     * @param parentName
     */
    public void setParentName(java.lang.String parentName) {
        this.parentName = parentName;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ArrayOfHICategoryCategory)) return false;
        ArrayOfHICategoryCategory other = (ArrayOfHICategoryCategory) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.projectInfo==null && other.getProjectInfo()==null) || 
             (this.projectInfo!=null &&
              this.projectInfo.equals(other.getProjectInfo()))) &&
            ((this.fields==null && other.getFields()==null) || 
             (this.fields!=null &&
              java.util.Arrays.equals(this.fields, other.getFields()))) &&
            this.categoryId == other.getCategoryId() &&
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            this.parentID == other.getParentID() &&
            ((this.parentName==null && other.getParentName()==null) || 
             (this.parentName!=null &&
              this.parentName.equals(other.getParentName())));
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
        if (getProjectInfo() != null) {
            _hashCode += getProjectInfo().hashCode();
        }
        if (getFields() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getFields());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getFields(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        _hashCode += new Long(getCategoryId()).hashCode();
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        _hashCode += new Long(getParentID()).hashCode();
        if (getParentName() != null) {
            _hashCode += getParentName().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ArrayOfHICategoryCategory.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", ">ArrayOfHICategory>Category"));
        org.apache.axis.description.AttributeDesc attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("categoryId");
        attrField.setXmlName(new javax.xml.namespace.QName("", "CategoryId"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("name");
        attrField.setXmlName(new javax.xml.namespace.QName("", "Name"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("parentID");
        attrField.setXmlName(new javax.xml.namespace.QName("", "ParentID"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("parentName");
        attrField.setXmlName(new javax.xml.namespace.QName("", "ParentName"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(attrField);
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("projectInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "ProjectInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", ">>ArrayOfHICategory>Category>ProjectInfo"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fields");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "Fields"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "CategoryField"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "CategoryField"));
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
