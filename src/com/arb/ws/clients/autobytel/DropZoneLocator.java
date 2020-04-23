/**
 * DropZoneLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.arb.ws.clients.autobytel;

public class DropZoneLocator extends org.apache.axis.client.Service implements com.arb.ws.clients.autobytel.DropZone {

    public DropZoneLocator() {
    }


    public DropZoneLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public DropZoneLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for DropZoneSoap12
    // test
    //private java.lang.String DropZoneSoap12_address = "http://leadengine.services.staging.myride.com/leadengine/DropZone.asmx";
    // production
    private java.lang.String DropZoneSoap12_address = "http://leadengine.services.myride.com/leadengine/DropZone.asmx";

    public java.lang.String getDropZoneSoap12Address() {
        return DropZoneSoap12_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String DropZoneSoap12WSDDServiceName = "DropZoneSoap12";

    public java.lang.String getDropZoneSoap12WSDDServiceName() {
        return DropZoneSoap12WSDDServiceName;
    }

    public void setDropZoneSoap12WSDDServiceName(java.lang.String name) {
        DropZoneSoap12WSDDServiceName = name;
    }

    public com.arb.ws.clients.autobytel.DropZoneSoap_PortType getDropZoneSoap12() throws javax.xml.rpc.ServiceException {
        java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(DropZoneSoap12_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getDropZoneSoap12(endpoint);
    }

    public com.arb.ws.clients.autobytel.DropZoneSoap_PortType getDropZoneSoap12(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.arb.ws.clients.autobytel.DropZoneSoap12Stub _stub = new com.arb.ws.clients.autobytel.DropZoneSoap12Stub(portAddress, this);
            _stub.setPortName(getDropZoneSoap12WSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setDropZoneSoap12EndpointAddress(java.lang.String address) {
        DropZoneSoap12_address = address;
    }


    // Use to get a proxy class for DropZoneSoap
    // test
//    private java.lang.String DropZoneSoap_address = "http://leadengine.services.staging.myride.com/leadengine/DropZone.asmx";
    // production
    private java.lang.String DropZoneSoap_address = "http://leadengine.services.myride.com/leadengine/DropZone.asmx";

    public java.lang.String getDropZoneSoapAddress() {
        return DropZoneSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String DropZoneSoapWSDDServiceName = "DropZoneSoap";

    public java.lang.String getDropZoneSoapWSDDServiceName() {
        return DropZoneSoapWSDDServiceName;
    }

    public void setDropZoneSoapWSDDServiceName(java.lang.String name) {
        DropZoneSoapWSDDServiceName = name;
    }

    public com.arb.ws.clients.autobytel.DropZoneSoap_PortType getDropZoneSoap() throws javax.xml.rpc.ServiceException {
        java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(DropZoneSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getDropZoneSoap(endpoint);
    }

    public com.arb.ws.clients.autobytel.DropZoneSoap_PortType getDropZoneSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.arb.ws.clients.autobytel.DropZoneSoap_BindingStub _stub = new com.arb.ws.clients.autobytel.DropZoneSoap_BindingStub(portAddress, this);
            _stub.setPortName(getDropZoneSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setDropZoneSoapEndpointAddress(java.lang.String address) {
        DropZoneSoap_address = address;
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
            if (com.arb.ws.clients.autobytel.DropZoneSoap_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.arb.ws.clients.autobytel.DropZoneSoap12Stub _stub = new com.arb.ws.clients.autobytel.DropZoneSoap12Stub(new java.net.URL(DropZoneSoap12_address), this);
                _stub.setPortName(getDropZoneSoap12WSDDServiceName());
                return _stub;
            }
            if (com.arb.ws.clients.autobytel.DropZoneSoap_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.arb.ws.clients.autobytel.DropZoneSoap_BindingStub _stub = new com.arb.ws.clients.autobytel.DropZoneSoap_BindingStub(new java.net.URL(DropZoneSoap_address), this);
                _stub.setPortName(getDropZoneSoapWSDDServiceName());
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
        if ("DropZoneSoap12".equals(inputPortName)) {
            return getDropZoneSoap12();
        }
        else if ("DropZoneSoap".equals(inputPortName)) {
            return getDropZoneSoap();
        }
        else {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://www.autobytel.com/", "DropZone");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://www.autobytel.com/", "DropZoneSoap12"));
            ports.add(new javax.xml.namespace.QName("http://www.autobytel.com/", "DropZoneSoap"));
        }
        return ports.iterator();
    }

    /**
     * Set the endpoint address for the specified port name.
     */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {

        if ("DropZoneSoap12".equals(portName)) {
            setDropZoneSoap12EndpointAddress(address);
        }
        else if ("DropZoneSoap".equals(portName)) {
            setDropZoneSoapEndpointAddress(address);
        }
        else { // Unknown Port Name
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
