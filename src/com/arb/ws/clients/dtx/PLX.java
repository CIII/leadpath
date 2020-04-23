/**
 * PLX.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.arb.ws.clients.dtx;

public interface PLX extends javax.xml.rpc.Service {
    public java.lang.String getPLXSoap12Address();

    public com.arb.ws.clients.dtx.PLXSoap_PortType getPLXSoap12() throws javax.xml.rpc.ServiceException;

    public com.arb.ws.clients.dtx.PLXSoap_PortType getPLXSoap12(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
    public java.lang.String getPLXSoapAddress();

    public com.arb.ws.clients.dtx.PLXSoap_PortType getPLXSoap() throws javax.xml.rpc.ServiceException;

    public com.arb.ws.clients.dtx.PLXSoap_PortType getPLXSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
