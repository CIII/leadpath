/**
 * IPingService1Stub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.arb.ws.clients.reply;

public class IPingService1Stub extends org.apache.axis.client.Stub implements com.arb.ws.clients.reply.IPingService_PortType {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[8];
        _initOperationDesc1();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("PingAuto");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "PingAutoRequest"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "AutoRequest"), com.arb.ws.clients.reply.AutoRequest.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "AutoResponse"));
        oper.setReturnClass(com.arb.ws.clients.reply.AutoResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "PingAutoResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("PingAutoOrganic");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "PingAutoRequest"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "AutoRequest"), com.arb.ws.clients.reply.AutoRequest.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10", "Directives"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10", "ArrayOfString"), java.lang.String[].class, false, false);
        param.setItemQName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10", "string"));
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "AutoResponse"));
        oper.setReturnClass(com.arb.ws.clients.reply.AutoResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "PingAutoResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("PingRealEstate");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "PingRealEstateRequest"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "RealEstateRequest"), com.arb.ws.clients.reply.RealEstateRequest.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "RealEstateResponse"));
        oper.setReturnClass(com.arb.ws.clients.reply.RealEstateResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "PostRealEstateResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("PingAutoFinance");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "PingAutoFinanceRequest"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "AutoFinanceRequest"), com.arb.ws.clients.reply.AutoFinanceRequest.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "AutoFinanceResponse"));
        oper.setReturnClass(com.arb.ws.clients.reply.AutoFinanceResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "PingAutoFinanceResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("PingHomeImprovement");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "PingHomeImprovementRequest"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "HomeImprovementRequest"), com.arb.ws.clients.reply.HomeImprovementRequest.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "HomeImprovementResponse"));
        oper.setReturnClass(com.arb.ws.clients.reply.HomeImprovementResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "PingHomeImprovementResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[4] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("PingHomeImprovementQS");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "PingHomeImprovementRequest"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "HomeImprovementRequest"), com.arb.ws.clients.reply.HomeImprovementRequest.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "HomeImprovementResponse"));
        oper.setReturnClass(com.arb.ws.clients.reply.HomeImprovementResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "PingHomeImprovementResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[5] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("PingHomeInsurance");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "PingHomeInsuranceRequest"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "HomeInsuranceRequest"), com.arb.ws.clients.reply.HomeInsuranceRequest.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "HomeInsuranceResponse"));
        oper.setReturnClass(com.arb.ws.clients.reply.HomeInsuranceResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "PingHomeInsuranceResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[6] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("PingAutoInsurance");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "PingAutoInsuranceRequest"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "AutoInsuranceRequest"), com.arb.ws.clients.reply.AutoInsuranceRequest.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "AutoInsuranceResponse"));
        oper.setReturnClass(com.arb.ws.clients.reply.AutoInsuranceResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "PingAutoInsuranceResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[7] = oper;

    }

    public IPingService1Stub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public IPingService1Stub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public IPingService1Stub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        if (service == null) {
            super.service = new org.apache.axis.client.Service();
        } else {
            super.service = service;
        }
        ((org.apache.axis.client.Service)super.service).setTypeMappingVersion("1.2");
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
        addBindings0();
        addBindings1();
    }

    private void addBindings0() {
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("http://microsoft.com/wsdl/types/", "char");
            cachedSerQNames.add(qName);
            cls = org.apache.axis.types.UnsignedShort.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://microsoft.com/wsdl/types/", "guid");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", ">>>AutoFinanceLead>EmploymentInfo>EmployerPhone>PreferredContact");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.AutoFinanceLeadEmploymentInfoEmployerPhonePreferredContact.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", ">>>AutoFinanceLead>EmploymentInfo>EmployerPhone>Time");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.AutoFinanceLeadEmploymentInfoEmployerPhoneTime.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", ">>>AutoFinanceLead>EmploymentInfo>EmployerPhone>Type");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.AutoFinanceLeadEmploymentInfoEmployerPhoneType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", ">>>AutoFinanceLead>ResidenceInfo>Address>Street");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.AutoFinanceLeadResidenceInfoAddressStreet.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", ">>>AutoFinanceLead>ResidenceInfo>Address>Type");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.AutoFinanceLeadResidenceInfoAddressType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", ">>>Lead>Contact>Address>Street");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.LeadContactAddressStreet.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", ">>>Lead>Contact>Address>Type");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.LeadContactAddressType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", ">>>Lead>Contact>Email>PreferredContact");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.LeadContactEmailPreferredContact.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", ">>>Lead>Contact>Name>Part");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.LeadContactNamePart.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", ">>>Lead>Contact>Name>Type");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.LeadContactNameType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", ">>>Lead>Contact>Phone>PreferredContact");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.LeadContactPhonePreferredContact.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", ">>>Lead>Contact>Phone>Time");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.LeadContactPhoneTime.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", ">>>Lead>Contact>Phone>Type");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.LeadContactPhoneType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", ">>ArrayOfHICategory>Category>ProjectInfo");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.ArrayOfHICategoryCategoryProjectInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", ">>AutoFinanceLead>EmploymentInfo>EmployerPhone");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.AutoFinanceLeadEmploymentInfoEmployerPhone.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", ">>AutoFinanceLead>ResidenceInfo>Address");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.AutoFinanceLeadResidenceInfoAddress.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", ">>AutoFinanceLead>ResidenceInfo>Type");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.AutoFinanceLeadResidenceInfoType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", ">>Lead>Contact>Address");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.LeadContactAddress.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", ">>Lead>Contact>Email");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.LeadContactEmail.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", ">>Lead>Contact>Name");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.LeadContactName.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", ">>Lead>Contact>Phone");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.LeadContactPhone.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", ">>Property>Address>Street");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.PropertyAddressStreet.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", ">>Property>Address>Type");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.PropertyAddressType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", ">ArrayOfError>Error");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.ArrayOfErrorError.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", ">ArrayOfHICategory>Category");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.ArrayOfHICategoryCategory.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", ">AutoFinanceLead>AuthorizationsInfo");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.AutoFinanceLeadAuthorizationsInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", ">AutoFinanceLead>CarType");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.AutoFinanceLeadCarType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", ">AutoFinanceLead>EmploymentInfo");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.AutoFinanceLeadEmploymentInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", ">AutoFinanceLead>PersonalInfo");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.AutoFinanceLeadPersonalInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", ">AutoFinanceLead>ResidenceInfo");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.AutoFinanceLeadResidenceInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", ">AutomotiveLead>LeadType");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.AutomotiveLeadLeadType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", ">HILead>DefaultProjectInfo");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.HILeadDefaultProjectInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", ">HILead>HomeOwnership");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.HILeadHomeOwnership.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", ">HILead>Rating");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.HILeadRating.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", ">InsuranceProperty>PropertyType");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.InsurancePropertyPropertyType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", ">Lead>Contact");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.LeadContact.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", ">Property>Address");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.PropertyAddress.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", ">Property>Type");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.PropertyType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", ">RealEstateLead>LeadType");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.RealEstateLeadLeadType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", ">RealEstateLead>PropertyType");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.RealEstateLeadPropertyType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "Accident");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.Accident.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "AccidentType");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.AccidentType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "ArrayOfCategoryField");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.CategoryField[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "CategoryField");
            qName2 = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "CategoryField");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "ArrayOfDriver");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.Driver[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "Driver");
            qName2 = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "Driver");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "ArrayOfError");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.ArrayOfErrorError[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", ">ArrayOfError>Error");
            qName2 = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "Error");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "ArrayOfHash");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.Hash[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "Hash");
            qName2 = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "Hash");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "ArrayOfHICategory");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.ArrayOfHICategoryCategory[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", ">ArrayOfHICategory>Category");
            qName2 = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "Category");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "ArrayOfHomeClaimInfo");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.HomeClaimInfo[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "HomeClaimInfo");
            qName2 = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "HomeClaimInfo");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "ArrayOfIncident");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.Incident[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "Incident");
            qName2 = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "Incident");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "ArrayOfString");
            cachedSerQNames.add(qName);
            cls = java.lang.String[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string");
            qName2 = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "Zipcode");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "ArrayOfString1");
            cachedSerQNames.add(qName);
            cls = java.lang.String[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string");
            qName2 = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "Hashcode");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "ArrayOfVehicleCoverage");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.VehicleCoverage[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "VehicleCoverage");
            qName2 = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "VehicleCoverage");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "ArrayOfZip");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.Zip[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "Zip");
            qName2 = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "Zip");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "ArrayOfZipBack");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.ZipBack[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "ZipBack");
            qName2 = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "ZipBack");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "AutoClaimInfo");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.AutoClaimInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "AutoClaimType");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.AutoClaimType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "AutoCoverageType");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.AutoCoverageType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "AutoFinanceLead");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.AutoFinanceLead.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "AutoFinanceRequest");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.AutoFinanceRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "AutoFinanceResponse");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.AutoFinanceResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "AutoInsLeadType");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.AutoInsLeadType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "AutoInsuranceLead");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.AutoInsuranceLead.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "AutoInsurancePolicy");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.AutoInsurancePolicy.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "AutoInsuranceRequest");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.AutoInsuranceRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "AutoInsuranceResponse");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.AutoInsuranceResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "AutomotiveLead");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.AutomotiveLead.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "AutoRequest");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.AutoRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "AutoResponse");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.AutoResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "CarrierInformation");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.CarrierInformation.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "CarrierInformationAgentKeySelected");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.CarrierInformationAgentKeySelected.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "CarrierInformationAgentSelected");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.CarrierInformationAgentSelected.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "CategoryField");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.CategoryField.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "ClaimInfo");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.ClaimInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "CreditHistoryType");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.CreditHistoryType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "DamageType");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.DamageType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "DistributionIncludes");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.DistributionIncludes.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "DistributionIncludesAgentKeySelected");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.DistributionIncludesAgentKeySelected.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "DistributionIncludesAgentSelected");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.DistributionIncludesAgentSelected.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "Driver");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.Driver.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "DriversLicenseType");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.DriversLicenseType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "DUI");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.DUI.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "EducationLevel");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.EducationLevel.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "ExteriorWallMaterial");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.ExteriorWallMaterial.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "FireAlarmSystem");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.FireAlarmSystem.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "FireHydrantDistance");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.FireHydrantDistance.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "FireStationDistance");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.FireStationDistance.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "FloodWaterType");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.FloodWaterType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "FoundationType");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.FoundationType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "GarageType");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.GarageType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "Gender");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.Gender.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "Hash");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.Hash.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "HeatingCoolingSystem");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.HeatingCoolingSystem.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "HILead");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.HILead.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "HomeClaimInfo");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.HomeClaimInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "HomeClaimType");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.HomeClaimType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "HomeImprovementRequest");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.HomeImprovementRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "HomeImprovementResponse");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.HomeImprovementResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "HomeInsLeadType");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.HomeInsLeadType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

    }
    private void addBindings1() {
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "HomeInsuranceLead");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.HomeInsuranceLead.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "HomeInsurancePolicy");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.HomeInsurancePolicy.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "HomeInsuranceRequest");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.HomeInsuranceRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "HomeInsuranceResponse");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.HomeInsuranceResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "Incident");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.Incident.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "IncidentType");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.IncidentType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "InsuranceLead");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.InsuranceLead.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "InsuranceProperty");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.InsuranceProperty.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "Lead");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.Lead.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "MaritalStatus");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.MaritalStatus.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "NumberBathrooms");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.NumberBathrooms.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "NumberBedrooms");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.NumberBedrooms.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "NumberFireplaces");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.NumberFireplaces.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "NumberRooms");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.NumberRooms.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "NumberStories");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.NumberStories.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "NumberUnits");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.NumberUnits.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "Occupant");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.Occupant.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "Policy");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.Policy.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "Property");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.Property.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "PropertyQuality");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.PropertyQuality.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "PropertySafety");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.PropertySafety.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "PropertyStructure");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.PropertyStructure.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "RealEstateLead");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.RealEstateLead.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "RealEstateRequest");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.RealEstateRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "RealEstateResponse");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.RealEstateResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "RelationToApplicant");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.RelationToApplicant.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "RoofAge");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.RoofAge.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "RoofingMaterial");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.RoofingMaterial.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "SecuritySystem");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.SecuritySystem.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "ServicePanelType");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.ServicePanelType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "Ticket");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.Ticket.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "TicketType");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.TicketType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "Vehicle");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.Vehicle.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "VehicleCoverage");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.VehicleCoverage.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "VehicleOwnership");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.VehicleOwnership.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "VehiclePrimaryUse");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.VehiclePrimaryUse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "WiringType");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.WiringType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "Zip");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.Zip.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10/ServiceContracts", "ZipBack");
            cachedSerQNames.add(qName);
            cls = com.arb.ws.clients.reply.ZipBack.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10", "ArrayOfString");
            cachedSerQNames.add(qName);
            cls = java.lang.String[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string");
            qName2 = new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10", "string");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

    }

    protected org.apache.axis.client.Call createCall() throws java.rmi.RemoteException {
        try {
            org.apache.axis.client.Call _call = super._createCall();
            if (super.maintainSessionSet) {
                _call.setMaintainSession(super.maintainSession);
            }
            if (super.cachedUsername != null) {
                _call.setUsername(super.cachedUsername);
            }
            if (super.cachedPassword != null) {
                _call.setPassword(super.cachedPassword);
            }
            if (super.cachedEndpoint != null) {
                _call.setTargetEndpointAddress(super.cachedEndpoint);
            }
            if (super.cachedTimeout != null) {
                _call.setTimeout(super.cachedTimeout);
            }
            if (super.cachedPortName != null) {
                _call.setPortName(super.cachedPortName);
            }
            java.util.Enumeration keys = super.cachedProperties.keys();
            while (keys.hasMoreElements()) {
                java.lang.String key = (java.lang.String) keys.nextElement();
                _call.setProperty(key, super.cachedProperties.get(key));
            }
            // All the type mapping information is registered
            // when the first call is made.
            // The type mapping information is actually registered in
            // the TypeMappingRegistry of the service, which
            // is the reason why registration is only needed for the first call.
            synchronized (this) {
                if (firstCall()) {
                    // must set encoding style before registering serializers
                    _call.setEncodingStyle(null);
                    for (int i = 0; i < cachedSerFactories.size(); ++i) {
                        java.lang.Class cls = (java.lang.Class) cachedSerClasses.get(i);
                        javax.xml.namespace.QName qName =
                                (javax.xml.namespace.QName) cachedSerQNames.get(i);
                        java.lang.Object x = cachedSerFactories.get(i);
                        if (x instanceof Class) {
                            java.lang.Class sf = (java.lang.Class)
                                 cachedSerFactories.get(i);
                            java.lang.Class df = (java.lang.Class)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                        else if (x instanceof javax.xml.rpc.encoding.SerializerFactory) {
                            org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory)
                                 cachedSerFactories.get(i);
                            org.apache.axis.encoding.DeserializerFactory df = (org.apache.axis.encoding.DeserializerFactory)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                    }
                }
            }
            return _call;
        }
        catch (java.lang.Throwable _t) {
            throw new org.apache.axis.AxisFault("Failure trying to get the Call object", _t);
        }
    }

    public com.arb.ws.clients.reply.AutoResponse pingAuto(com.arb.ws.clients.reply.AutoRequest pingAutoRequest) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/11/PingAuto");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP12_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "PingAuto"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {pingAutoRequest});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.arb.ws.clients.reply.AutoResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.arb.ws.clients.reply.AutoResponse) org.apache.axis.utils.JavaUtils.convert(_resp, com.arb.ws.clients.reply.AutoResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.arb.ws.clients.reply.AutoResponse pingAutoOrganic(com.arb.ws.clients.reply.AutoRequest pingAutoRequest, java.lang.String[] directives) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/11/PingAutoOrganic");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP12_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "PingAutoOrganic"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {pingAutoRequest, directives});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.arb.ws.clients.reply.AutoResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.arb.ws.clients.reply.AutoResponse) org.apache.axis.utils.JavaUtils.convert(_resp, com.arb.ws.clients.reply.AutoResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.arb.ws.clients.reply.RealEstateResponse pingRealEstate(com.arb.ws.clients.reply.RealEstateRequest pingRealEstateRequest) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/11/PingRealEstate");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP12_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "PingRealEstate"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {pingRealEstateRequest});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.arb.ws.clients.reply.RealEstateResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.arb.ws.clients.reply.RealEstateResponse) org.apache.axis.utils.JavaUtils.convert(_resp, com.arb.ws.clients.reply.RealEstateResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.arb.ws.clients.reply.AutoFinanceResponse pingAutoFinance(com.arb.ws.clients.reply.AutoFinanceRequest pingAutoFinanceRequest) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/11/PingAutoFinance");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP12_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "PingAutoFinance"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {pingAutoFinanceRequest});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.arb.ws.clients.reply.AutoFinanceResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.arb.ws.clients.reply.AutoFinanceResponse) org.apache.axis.utils.JavaUtils.convert(_resp, com.arb.ws.clients.reply.AutoFinanceResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.arb.ws.clients.reply.HomeImprovementResponse pingHomeImprovement(com.arb.ws.clients.reply.HomeImprovementRequest pingHomeImprovementRequest) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[4]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/11/PingHomeImprovement");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP12_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "PingHomeImprovement"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {pingHomeImprovementRequest});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.arb.ws.clients.reply.HomeImprovementResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.arb.ws.clients.reply.HomeImprovementResponse) org.apache.axis.utils.JavaUtils.convert(_resp, com.arb.ws.clients.reply.HomeImprovementResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.arb.ws.clients.reply.HomeImprovementResponse pingHomeImprovementQS(com.arb.ws.clients.reply.HomeImprovementRequest pingHomeImprovementRequest) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[5]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/11/PingHomeImprovementQS");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP12_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "PingHomeImprovementQS"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {pingHomeImprovementRequest});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.arb.ws.clients.reply.HomeImprovementResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.arb.ws.clients.reply.HomeImprovementResponse) org.apache.axis.utils.JavaUtils.convert(_resp, com.arb.ws.clients.reply.HomeImprovementResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.arb.ws.clients.reply.HomeInsuranceResponse pingHomeInsurance(com.arb.ws.clients.reply.HomeInsuranceRequest pingHomeInsuranceRequest) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[6]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/11/PingHomeInsurance");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP12_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "PingHomeInsurance"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {pingHomeInsuranceRequest});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.arb.ws.clients.reply.HomeInsuranceResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.arb.ws.clients.reply.HomeInsuranceResponse) org.apache.axis.utils.JavaUtils.convert(_resp, com.arb.ws.clients.reply.HomeInsuranceResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.arb.ws.clients.reply.AutoInsuranceResponse pingAutoInsurance(com.arb.ws.clients.reply.AutoInsuranceRequest pingAutoInsuranceRequest) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[7]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/11/PingAutoInsurance");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP12_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "PingAutoInsurance"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {pingAutoInsuranceRequest});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.arb.ws.clients.reply.AutoInsuranceResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.arb.ws.clients.reply.AutoInsuranceResponse) org.apache.axis.utils.JavaUtils.convert(_resp, com.arb.ws.clients.reply.AutoInsuranceResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

}
