/**
 * AreaOfStudy.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.arb.ws.clients.reply;

public class AreaOfStudy implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected AreaOfStudy(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _Unknown = "Unknown";
    public static final java.lang.String _ArtsAndDesign = "ArtsAndDesign";
    public static final java.lang.String _BusinessAndMBA = "BusinessAndMBA";
    public static final java.lang.String _ComputersAndIT = "ComputersAndIT";
    public static final java.lang.String _CriminalJustice = "CriminalJustice";
    public static final java.lang.String _EducationAndTeaching = "EducationAndTeaching";
    public static final java.lang.String _Healthcare = "Healthcare";
    public static final java.lang.String _Legal = "Legal";
    public static final java.lang.String _LiberalArts = "LiberalArts";
    public static final java.lang.String _Nursing = "Nursing";
    public static final java.lang.String _ScienceAndEngineering = "ScienceAndEngineering";
    public static final java.lang.String _SocialSciences = "SocialSciences";
    public static final java.lang.String _TradesAndCareers = "TradesAndCareers";
    public static final AreaOfStudy Unknown = new AreaOfStudy(_Unknown);
    public static final AreaOfStudy ArtsAndDesign = new AreaOfStudy(_ArtsAndDesign);
    public static final AreaOfStudy BusinessAndMBA = new AreaOfStudy(_BusinessAndMBA);
    public static final AreaOfStudy ComputersAndIT = new AreaOfStudy(_ComputersAndIT);
    public static final AreaOfStudy CriminalJustice = new AreaOfStudy(_CriminalJustice);
    public static final AreaOfStudy EducationAndTeaching = new AreaOfStudy(_EducationAndTeaching);
    public static final AreaOfStudy Healthcare = new AreaOfStudy(_Healthcare);
    public static final AreaOfStudy Legal = new AreaOfStudy(_Legal);
    public static final AreaOfStudy LiberalArts = new AreaOfStudy(_LiberalArts);
    public static final AreaOfStudy Nursing = new AreaOfStudy(_Nursing);
    public static final AreaOfStudy ScienceAndEngineering = new AreaOfStudy(_ScienceAndEngineering);
    public static final AreaOfStudy SocialSciences = new AreaOfStudy(_SocialSciences);
    public static final AreaOfStudy TradesAndCareers = new AreaOfStudy(_TradesAndCareers);
    public java.lang.String getValue() { return _value_;}
    public static AreaOfStudy fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        AreaOfStudy enumeration = (AreaOfStudy)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static AreaOfStudy fromString(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumSerializer(
            _javaType, _xmlType);
    }
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumDeserializer(
            _javaType, _xmlType);
    }
    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AreaOfStudy.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10/PostMessageTypes", "AreaOfStudy"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
