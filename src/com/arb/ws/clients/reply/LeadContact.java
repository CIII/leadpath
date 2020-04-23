/**
 * LeadContact.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.arb.ws.clients.reply;

public class LeadContact  implements java.io.Serializable {
    private com.arb.ws.clients.reply.LeadContactName[] name;

    private java.lang.String consumerName;

    private com.arb.ws.clients.reply.LeadContactEmail email;

    private com.arb.ws.clients.reply.LeadContactPhone[] phone;

    private com.arb.ws.clients.reply.LeadContactAddress address;

    private java.lang.String IPAddress;

    public LeadContact() {
    }

    public LeadContact(
           com.arb.ws.clients.reply.LeadContactName[] name,
           java.lang.String consumerName,
           com.arb.ws.clients.reply.LeadContactEmail email,
           com.arb.ws.clients.reply.LeadContactPhone[] phone,
           com.arb.ws.clients.reply.LeadContactAddress address,
           java.lang.String IPAddress) {
           this.name = name;
           this.consumerName = consumerName;
           this.email = email;
           this.phone = phone;
           this.address = address;
           this.IPAddress = IPAddress;
    }


    /**
     * Gets the name value for this LeadContact.
     * 
     * @return name
     */
    public com.arb.ws.clients.reply.LeadContactName[] getName() {
        return name;
    }


    /**
     * Sets the name value for this LeadContact.
     * 
     * @param name
     */
    public void setName(com.arb.ws.clients.reply.LeadContactName[] name) {
        this.name = name;
    }

    public com.arb.ws.clients.reply.LeadContactName getName(int i) {
        return this.name[i];
    }

    public void setName(int i, com.arb.ws.clients.reply.LeadContactName _value) {
        this.name[i] = _value;
    }


    /**
     * Gets the consumerName value for this LeadContact.
     * 
     * @return consumerName
     */
    public java.lang.String getConsumerName() {
        return consumerName;
    }


    /**
     * Sets the consumerName value for this LeadContact.
     * 
     * @param consumerName
     */
    public void setConsumerName(java.lang.String consumerName) {
        this.consumerName = consumerName;
    }


    /**
     * Gets the email value for this LeadContact.
     * 
     * @return email
     */
    public com.arb.ws.clients.reply.LeadContactEmail getEmail() {
        return email;
    }


    /**
     * Sets the email value for this LeadContact.
     * 
     * @param email
     */
    public void setEmail(com.arb.ws.clients.reply.LeadContactEmail email) {
        this.email = email;
    }


    /**
     * Gets the phone value for this LeadContact.
     * 
     * @return phone
     */
    public com.arb.ws.clients.reply.LeadContactPhone[] getPhone() {
        return phone;
    }


    /**
     * Sets the phone value for this LeadContact.
     * 
     * @param phone
     */
    public void setPhone(com.arb.ws.clients.reply.LeadContactPhone[] phone) {
        this.phone = phone;
    }

    public com.arb.ws.clients.reply.LeadContactPhone getPhone(int i) {
        return this.phone[i];
    }

    public void setPhone(int i, com.arb.ws.clients.reply.LeadContactPhone _value) {
        this.phone[i] = _value;
    }


    /**
     * Gets the address value for this LeadContact.
     * 
     * @return address
     */
    public com.arb.ws.clients.reply.LeadContactAddress getAddress() {
        return address;
    }


    /**
     * Sets the address value for this LeadContact.
     * 
     * @param address
     */
    public void setAddress(com.arb.ws.clients.reply.LeadContactAddress address) {
        this.address = address;
    }


    /**
     * Gets the IPAddress value for this LeadContact.
     * 
     * @return IPAddress
     */
    public java.lang.String getIPAddress() {
        return IPAddress;
    }


    /**
     * Sets the IPAddress value for this LeadContact.
     * 
     * @param IPAddress
     */
    public void setIPAddress(java.lang.String IPAddress) {
        this.IPAddress = IPAddress;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof LeadContact)) return false;
        LeadContact other = (LeadContact) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              java.util.Arrays.equals(this.name, other.getName()))) &&
            ((this.consumerName==null && other.getConsumerName()==null) || 
             (this.consumerName!=null &&
              this.consumerName.equals(other.getConsumerName()))) &&
            ((this.email==null && other.getEmail()==null) || 
             (this.email!=null &&
              this.email.equals(other.getEmail()))) &&
            ((this.phone==null && other.getPhone()==null) || 
             (this.phone!=null &&
              java.util.Arrays.equals(this.phone, other.getPhone()))) &&
            ((this.address==null && other.getAddress()==null) || 
             (this.address!=null &&
              this.address.equals(other.getAddress()))) &&
            ((this.IPAddress==null && other.getIPAddress()==null) || 
             (this.IPAddress!=null &&
              this.IPAddress.equals(other.getIPAddress())));
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
        if (getName() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getName());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getName(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getConsumerName() != null) {
            _hashCode += getConsumerName().hashCode();
        }
        if (getEmail() != null) {
            _hashCode += getEmail().hashCode();
        }
        if (getPhone() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPhone());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPhone(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getAddress() != null) {
            _hashCode += getAddress().hashCode();
        }
        if (getIPAddress() != null) {
            _hashCode += getIPAddress().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(LeadContact.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", ">Lead>Contact"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("name");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "Name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", ">>Lead>Contact>Name"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("consumerName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "ConsumerName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("email");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "Email"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", ">>Lead>Contact>Email"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("phone");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "Phone"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", ">>Lead>Contact>Phone"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("address");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "Address"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", ">>Lead>Contact>Address"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("IPAddress");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "IPAddress"));
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
