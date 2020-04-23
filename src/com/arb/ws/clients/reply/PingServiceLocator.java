/**
 * PingServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.arb.ws.clients.reply;

public class PingServiceLocator extends org.apache.axis.client.Service implements com.arb.ws.clients.reply.PingService {

    public PingServiceLocator() {
    }


    public PingServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public PingServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for IPingService
    private java.lang.String IPingService_address = "http://services.reply.com/PingService/PingService.asmx";

    public java.lang.String getIPingServiceAddress() {
        return IPingService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String IPingServiceWSDDServiceName = "IPingService";

    public java.lang.String getIPingServiceWSDDServiceName() {
        return IPingServiceWSDDServiceName;
    }

    public void setIPingServiceWSDDServiceName(java.lang.String name) {
        IPingServiceWSDDServiceName = name;
    }

    public com.arb.ws.clients.reply.IPingService_PortType getIPingService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(IPingService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getIPingService(endpoint);
    }

    public com.arb.ws.clients.reply.IPingService_PortType getIPingService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.arb.ws.clients.reply.IPingService_BindingStub _stub = new com.arb.ws.clients.reply.IPingService_BindingStub(portAddress, this);
            _stub.setPortName(getIPingServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setIPingServiceEndpointAddress(java.lang.String address) {
        IPingService_address = address;
    }


    // Use to get a proxy class for IPingService1
    private java.lang.String IPingService1_address = "http://services.reply.com/PingService/PingService.asmx";

    public java.lang.String getIPingService1Address() {
        return IPingService1_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String IPingService1WSDDServiceName = "IPingService1";

    public java.lang.String getIPingService1WSDDServiceName() {
        return IPingService1WSDDServiceName;
    }

    public void setIPingService1WSDDServiceName(java.lang.String name) {
        IPingService1WSDDServiceName = name;
    }

    public com.arb.ws.clients.reply.IPingService_PortType getIPingService1() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(IPingService1_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getIPingService1(endpoint);
    }

    public com.arb.ws.clients.reply.IPingService_PortType getIPingService1(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.arb.ws.clients.reply.IPingService1Stub _stub = new com.arb.ws.clients.reply.IPingService1Stub(portAddress, this);
            _stub.setPortName(getIPingService1WSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setIPingService1EndpointAddress(java.lang.String address) {
        IPingService1_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     * This service has multiple ports for a given interface;
     * the proxy implementation returned may be indeterminate.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.arb.ws.clients.reply.IPingService_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.arb.ws.clients.reply.IPingService_BindingStub _stub = new com.arb.ws.clients.reply.IPingService_BindingStub(new java.net.URL(IPingService_address), this);
                _stub.setPortName(getIPingServiceWSDDServiceName());
                return _stub;
            }
            if (com.arb.ws.clients.reply.IPingService_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.arb.ws.clients.reply.IPingService1Stub _stub = new com.arb.ws.clients.reply.IPingService1Stub(new java.net.URL(IPingService1_address), this);
                _stub.setPortName(getIPingService1WSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("IPingService".equals(inputPortName)) {
            return getIPingService();
        }
        else if ("IPingService1".equals(inputPortName)) {
            return getIPingService1();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10", "PingService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10", "IPingService"));
            ports.add(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Ping.ServiceContracts/2007/10", "IPingService1"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("IPingService".equals(portName)) {
            setIPingServiceEndpointAddress(address);
        }
        else 
if ("IPingService1".equals(portName)) {
            setIPingService1EndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
