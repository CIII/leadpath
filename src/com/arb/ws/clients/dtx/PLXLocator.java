/**
 * PLXLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.arb.ws.clients.dtx;

public class PLXLocator extends org.apache.axis.client.Service implements com.arb.ws.clients.dtx.PLX {

    public PLXLocator() {
    }


    public PLXLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public PLXLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for PLXSoap12
    private java.lang.String PLXSoap12_address = "http://production.detroittradingexchange.com/PremierLeadExchange.asmx";

    public java.lang.String getPLXSoap12Address() {
        return PLXSoap12_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String PLXSoap12WSDDServiceName = "PLXSoap12";

    public java.lang.String getPLXSoap12WSDDServiceName() {
        return PLXSoap12WSDDServiceName;
    }

    public void setPLXSoap12WSDDServiceName(java.lang.String name) {
        PLXSoap12WSDDServiceName = name;
    }

    public com.arb.ws.clients.dtx.PLXSoap_PortType getPLXSoap12() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(PLXSoap12_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getPLXSoap12(endpoint);
    }

    public com.arb.ws.clients.dtx.PLXSoap_PortType getPLXSoap12(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.arb.ws.clients.dtx.PLXSoap12Stub _stub = new com.arb.ws.clients.dtx.PLXSoap12Stub(portAddress, this);
            _stub.setPortName(getPLXSoap12WSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setPLXSoap12EndpointAddress(java.lang.String address) {
        PLXSoap12_address = address;
    }


    // Use to get a proxy class for PLXSoap
    private java.lang.String PLXSoap_address = "http://production.detroittradingexchange.com/PremierLeadExchange.asmx";

    public java.lang.String getPLXSoapAddress() {
        return PLXSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String PLXSoapWSDDServiceName = "PLXSoap";

    public java.lang.String getPLXSoapWSDDServiceName() {
        return PLXSoapWSDDServiceName;
    }

    public void setPLXSoapWSDDServiceName(java.lang.String name) {
        PLXSoapWSDDServiceName = name;
    }

    public com.arb.ws.clients.dtx.PLXSoap_PortType getPLXSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(PLXSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getPLXSoap(endpoint);
    }

    public com.arb.ws.clients.dtx.PLXSoap_PortType getPLXSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.arb.ws.clients.dtx.PLXSoap_BindingStub _stub = new com.arb.ws.clients.dtx.PLXSoap_BindingStub(portAddress, this);
            _stub.setPortName(getPLXSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setPLXSoapEndpointAddress(java.lang.String address) {
        PLXSoap_address = address;
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
            if (com.arb.ws.clients.dtx.PLXSoap_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.arb.ws.clients.dtx.PLXSoap12Stub _stub = new com.arb.ws.clients.dtx.PLXSoap12Stub(new java.net.URL(PLXSoap12_address), this);
                _stub.setPortName(getPLXSoap12WSDDServiceName());
                return _stub;
            }
            if (com.arb.ws.clients.dtx.PLXSoap_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.arb.ws.clients.dtx.PLXSoap_BindingStub _stub = new com.arb.ws.clients.dtx.PLXSoap_BindingStub(new java.net.URL(PLXSoap_address), this);
                _stub.setPortName(getPLXSoapWSDDServiceName());
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
        if ("PLXSoap12".equals(inputPortName)) {
            return getPLXSoap12();
        }
        else if ("PLXSoap".equals(inputPortName)) {
            return getPLXSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("www.detroittradingexchange.com/", "PLX");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("www.detroittradingexchange.com/", "PLXSoap12"));
            ports.add(new javax.xml.namespace.QName("www.detroittradingexchange.com/", "PLXSoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("PLXSoap12".equals(portName)) {
            setPLXSoap12EndpointAddress(address);
        }
        else 
if ("PLXSoap".equals(portName)) {
            setPLXSoapEndpointAddress(address);
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
