/**
 * PostServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.arb.ws.clients.reply;

public class PostServiceLocator extends org.apache.axis.client.Service implements com.arb.ws.clients.reply.PostService {

    public PostServiceLocator() {
    }


    public PostServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public PostServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for IPostService1
    private java.lang.String IPostService1_address = "http://services.reply.com/PostService/PostService.asmx";

    public java.lang.String getIPostService1Address() {
        return IPostService1_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String IPostService1WSDDServiceName = "IPostService1";

    public java.lang.String getIPostService1WSDDServiceName() {
        return IPostService1WSDDServiceName;
    }

    public void setIPostService1WSDDServiceName(java.lang.String name) {
        IPostService1WSDDServiceName = name;
    }

    public com.arb.ws.clients.reply.IPostService_PortType getIPostService1() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(IPostService1_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getIPostService1(endpoint);
    }

    public com.arb.ws.clients.reply.IPostService_PortType getIPostService1(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.arb.ws.clients.reply.IPostService1Stub _stub = new com.arb.ws.clients.reply.IPostService1Stub(portAddress, this);
            _stub.setPortName(getIPostService1WSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setIPostService1EndpointAddress(java.lang.String address) {
        IPostService1_address = address;
    }


    // Use to get a proxy class for IPostService
    private java.lang.String IPostService_address = "http://services.reply.com/PostService/PostService.asmx";

    public java.lang.String getIPostServiceAddress() {
        return IPostService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String IPostServiceWSDDServiceName = "IPostService";

    public java.lang.String getIPostServiceWSDDServiceName() {
        return IPostServiceWSDDServiceName;
    }

    public void setIPostServiceWSDDServiceName(java.lang.String name) {
        IPostServiceWSDDServiceName = name;
    }

    public com.arb.ws.clients.reply.IPostService_PortType getIPostService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(IPostService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getIPostService(endpoint);
    }

    public com.arb.ws.clients.reply.IPostService_PortType getIPostService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.arb.ws.clients.reply.IPostService_BindingStub _stub = new com.arb.ws.clients.reply.IPostService_BindingStub(portAddress, this);
            _stub.setPortName(getIPostServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setIPostServiceEndpointAddress(java.lang.String address) {
        IPostService_address = address;
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
            if (com.arb.ws.clients.reply.IPostService_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.arb.ws.clients.reply.IPostService1Stub _stub = new com.arb.ws.clients.reply.IPostService1Stub(new java.net.URL(IPostService1_address), this);
                _stub.setPortName(getIPostService1WSDDServiceName());
                return _stub;
            }
            if (com.arb.ws.clients.reply.IPostService_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.arb.ws.clients.reply.IPostService_BindingStub _stub = new com.arb.ws.clients.reply.IPostService_BindingStub(new java.net.URL(IPostService_address), this);
                _stub.setPortName(getIPostServiceWSDDServiceName());
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
        if ("IPostService1".equals(inputPortName)) {
            return getIPostService1();
        }
        else if ("IPostService".equals(inputPortName)) {
            return getIPostService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10", "PostService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10", "IPostService1"));
            ports.add(new javax.xml.namespace.QName("http://Reply.LeadMarketPlace.Services.Post.ServiceContracts/2007/10", "IPostService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("IPostService1".equals(portName)) {
            setIPostService1EndpointAddress(address);
        }
        else 
if ("IPostService".equals(portName)) {
            setIPostServiceEndpointAddress(address);
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
