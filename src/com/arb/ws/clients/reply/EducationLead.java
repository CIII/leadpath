/**
 * EducationLead.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.arb.ws.clients.reply;

public class EducationLead  extends com.arb.ws.clients.reply.Lead  implements java.io.Serializable {
    private com.arb.ws.clients.reply.AreaOfStudy areaOfStudy;

    private com.arb.ws.clients.reply.DegreeType degreeType;

    private com.arb.ws.clients.reply.Concentration concentration;

    private boolean online;

    private boolean ground;

    private int age;

    private com.arb.ws.clients.reply.HighestDegree highestDegreeAttained;

    private boolean militaryAffiliated;

    private int yearGraduated;

    private boolean USCitizen;

    private com.arb.ws.clients.reply.MilitaryBranch militaryBranch;

    private com.arb.ws.clients.reply.MilitaryStatus militaryStatus;

    private com.arb.ws.clients.reply.EDUTimeFrame timeFrame;

    private com.arb.ws.clients.reply.School[] schools;

    public EducationLead() {
    }

    public EducationLead(
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
           com.arb.ws.clients.reply.AreaOfStudy areaOfStudy,
           com.arb.ws.clients.reply.DegreeType degreeType,
           com.arb.ws.clients.reply.Concentration concentration,
           boolean online,
           boolean ground,
           int age,
           com.arb.ws.clients.reply.HighestDegree highestDegreeAttained,
           boolean militaryAffiliated,
           int yearGraduated,
           boolean USCitizen,
           com.arb.ws.clients.reply.MilitaryBranch militaryBranch,
           com.arb.ws.clients.reply.MilitaryStatus militaryStatus,
           com.arb.ws.clients.reply.EDUTimeFrame timeFrame,
           com.arb.ws.clients.reply.School[] schools) {
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
        this.areaOfStudy = areaOfStudy;
        this.degreeType = degreeType;
        this.concentration = concentration;
        this.online = online;
        this.ground = ground;
        this.age = age;
        this.highestDegreeAttained = highestDegreeAttained;
        this.militaryAffiliated = militaryAffiliated;
        this.yearGraduated = yearGraduated;
        this.USCitizen = USCitizen;
        this.militaryBranch = militaryBranch;
        this.militaryStatus = militaryStatus;
        this.timeFrame = timeFrame;
        this.schools = schools;
    }


    /**
     * Gets the areaOfStudy value for this EducationLead.
     * 
     * @return areaOfStudy
     */
    public com.arb.ws.clients.reply.AreaOfStudy getAreaOfStudy() {
        return areaOfStudy;
    }


    /**
     * Sets the areaOfStudy value for this EducationLead.
     * 
     * @param areaOfStudy
     */
    public void setAreaOfStudy(com.arb.ws.clients.reply.AreaOfStudy areaOfStudy) {
        this.areaOfStudy = areaOfStudy;
    }


    /**
     * Gets the degreeType value for this EducationLead.
     * 
     * @return degreeType
     */
    public com.arb.ws.clients.reply.DegreeType getDegreeType() {
        return degreeType;
    }


    /**
     * Sets the degreeType value for this EducationLead.
     * 
     * @param degreeType
     */
    public void setDegreeType(com.arb.ws.clients.reply.DegreeType degreeType) {
        this.degreeType = degreeType;
    }


    /**
     * Gets the concentration value for this EducationLead.
     * 
     * @return concentration
     */
    public com.arb.ws.clients.reply.Concentration getConcentration() {
        return concentration;
    }


    /**
     * Sets the concentration value for this EducationLead.
     * 
     * @param concentration
     */
    public void setConcentration(com.arb.ws.clients.reply.Concentration concentration) {
        this.concentration = concentration;
    }


    /**
     * Gets the online value for this EducationLead.
     * 
     * @return online
     */
    public boolean isOnline() {
        return online;
    }


    /**
     * Sets the online value for this EducationLead.
     * 
     * @param online
     */
    public void setOnline(boolean online) {
        this.online = online;
    }


    /**
     * Gets the ground value for this EducationLead.
     * 
     * @return ground
     */
    public boolean isGround() {
        return ground;
    }


    /**
     * Sets the ground value for this EducationLead.
     * 
     * @param ground
     */
    public void setGround(boolean ground) {
        this.ground = ground;
    }


    /**
     * Gets the age value for this EducationLead.
     * 
     * @return age
     */
    public int getAge() {
        return age;
    }


    /**
     * Sets the age value for this EducationLead.
     * 
     * @param age
     */
    public void setAge(int age) {
        this.age = age;
    }


    /**
     * Gets the highestDegreeAttained value for this EducationLead.
     * 
     * @return highestDegreeAttained
     */
    public com.arb.ws.clients.reply.HighestDegree getHighestDegreeAttained() {
        return highestDegreeAttained;
    }


    /**
     * Sets the highestDegreeAttained value for this EducationLead.
     * 
     * @param highestDegreeAttained
     */
    public void setHighestDegreeAttained(com.arb.ws.clients.reply.HighestDegree highestDegreeAttained) {
        this.highestDegreeAttained = highestDegreeAttained;
    }


    /**
     * Gets the militaryAffiliated value for this EducationLead.
     * 
     * @return militaryAffiliated
     */
    public boolean isMilitaryAffiliated() {
        return militaryAffiliated;
    }


    /**
     * Sets the militaryAffiliated value for this EducationLead.
     * 
     * @param militaryAffiliated
     */
    public void setMilitaryAffiliated(boolean militaryAffiliated) {
        this.militaryAffiliated = militaryAffiliated;
    }


    /**
     * Gets the yearGraduated value for this EducationLead.
     * 
     * @return yearGraduated
     */
    public int getYearGraduated() {
        return yearGraduated;
    }


    /**
     * Sets the yearGraduated value for this EducationLead.
     * 
     * @param yearGraduated
     */
    public void setYearGraduated(int yearGraduated) {
        this.yearGraduated = yearGraduated;
    }


    /**
     * Gets the USCitizen value for this EducationLead.
     * 
     * @return USCitizen
     */
    public boolean isUSCitizen() {
        return USCitizen;
    }


    /**
     * Sets the USCitizen value for this EducationLead.
     * 
     * @param USCitizen
     */
    public void setUSCitizen(boolean USCitizen) {
        this.USCitizen = USCitizen;
    }


    /**
     * Gets the militaryBranch value for this EducationLead.
     * 
     * @return militaryBranch
     */
    public com.arb.ws.clients.reply.MilitaryBranch getMilitaryBranch() {
        return militaryBranch;
    }


    /**
     * Sets the militaryBranch value for this EducationLead.
     * 
     * @param militaryBranch
     */
    public void setMilitaryBranch(com.arb.ws.clients.reply.MilitaryBranch militaryBranch) {
        this.militaryBranch = militaryBranch;
    }


    /**
     * Gets the militaryStatus value for this EducationLead.
     * 
     * @return militaryStatus
     */
    public com.arb.ws.clients.reply.MilitaryStatus getMilitaryStatus() {
        return militaryStatus;
    }


    /**
     * Sets the militaryStatus value for this EducationLead.
     * 
     * @param militaryStatus
     */
    public void setMilitaryStatus(com.arb.ws.clients.reply.MilitaryStatus militaryStatus) {
        this.militaryStatus = militaryStatus;
    }


    /**
     * Gets the timeFrame value for this EducationLead.
     * 
     * @return timeFrame
     */
    public com.arb.ws.clients.reply.EDUTimeFrame getTimeFrame() {
        return timeFrame;
    }


    /**
     * Sets the timeFrame value for this EducationLead.
     * 
     * @param timeFrame
     */
    public void setTimeFrame(com.arb.ws.clients.reply.EDUTimeFrame timeFrame) {
        this.timeFrame = timeFrame;
    }


    /**
     * Gets the schools value for this EducationLead.
     * 
     * @return schools
     */
    public com.arb.ws.clients.reply.School[] getSchools() {
        return schools;
    }


    /**
     * Sets the schools value for this EducationLead.
     * 
     * @param schools
     */
    public void setSchools(com.arb.ws.clients.reply.School[] schools) {
        this.schools = schools;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof EducationLead)) return false;
        EducationLead other = (EducationLead) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.areaOfStudy==null && other.getAreaOfStudy()==null) || 
             (this.areaOfStudy!=null &&
              this.areaOfStudy.equals(other.getAreaOfStudy()))) &&
            ((this.degreeType==null && other.getDegreeType()==null) || 
             (this.degreeType!=null &&
              this.degreeType.equals(other.getDegreeType()))) &&
            ((this.concentration==null && other.getConcentration()==null) || 
             (this.concentration!=null &&
              this.concentration.equals(other.getConcentration()))) &&
            this.online == other.isOnline() &&
            this.ground == other.isGround() &&
            this.age == other.getAge() &&
            ((this.highestDegreeAttained==null && other.getHighestDegreeAttained()==null) || 
             (this.highestDegreeAttained!=null &&
              this.highestDegreeAttained.equals(other.getHighestDegreeAttained()))) &&
            this.militaryAffiliated == other.isMilitaryAffiliated() &&
            this.yearGraduated == other.getYearGraduated() &&
            this.USCitizen == other.isUSCitizen() &&
            ((this.militaryBranch==null && other.getMilitaryBranch()==null) || 
             (this.militaryBranch!=null &&
              this.militaryBranch.equals(other.getMilitaryBranch()))) &&
            ((this.militaryStatus==null && other.getMilitaryStatus()==null) || 
             (this.militaryStatus!=null &&
              this.militaryStatus.equals(other.getMilitaryStatus()))) &&
            ((this.timeFrame==null && other.getTimeFrame()==null) || 
             (this.timeFrame!=null &&
              this.timeFrame.equals(other.getTimeFrame()))) &&
            ((this.schools==null && other.getSchools()==null) || 
             (this.schools!=null &&
              java.util.Arrays.equals(this.schools, other.getSchools())));
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
        if (getAreaOfStudy() != null) {
            _hashCode += getAreaOfStudy().hashCode();
        }
        if (getDegreeType() != null) {
            _hashCode += getDegreeType().hashCode();
        }
        if (getConcentration() != null) {
            _hashCode += getConcentration().hashCode();
        }
        _hashCode += (isOnline() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isGround() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += getAge();
        if (getHighestDegreeAttained() != null) {
            _hashCode += getHighestDegreeAttained().hashCode();
        }
        _hashCode += (isMilitaryAffiliated() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += getYearGraduated();
        _hashCode += (isUSCitizen() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getMilitaryBranch() != null) {
            _hashCode += getMilitaryBranch().hashCode();
        }
        if (getMilitaryStatus() != null) {
            _hashCode += getMilitaryStatus().hashCode();
        }
        if (getTimeFrame() != null) {
            _hashCode += getTimeFrame().hashCode();
        }
        if (getSchools() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getSchools());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getSchools(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(EducationLead.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "EducationLead"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("areaOfStudy");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "AreaOfStudy"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "AreaOfStudy"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
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
        elemField.setFieldName("online");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "Online"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ground");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "Ground"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("age");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "Age"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("highestDegreeAttained");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "HighestDegreeAttained"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "HighestDegree"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("militaryAffiliated");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "MilitaryAffiliated"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("yearGraduated");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "YearGraduated"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("USCitizen");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "USCitizen"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("militaryBranch");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "MilitaryBranch"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "MilitaryBranch"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("militaryStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "MilitaryStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "MilitaryStatus"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("timeFrame");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "TimeFrame"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "EDUTimeFrame"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("schools");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "Schools"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "School"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "School"));
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
