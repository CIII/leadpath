/**
 * PingService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.arb.ws.clients.reply;

public interface PingService extends javax.xml.rpc.Service {
    public java.lang.String getIPingServiceAddress();

    public com.arb.ws.clients.reply.IPingService_PortType getIPingService() throws javax.xml.rpc.ServiceException;

    public com.arb.ws.clients.reply.IPingService_PortType getIPingService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
    public java.lang.String getIPingService1Address();

    public com.arb.ws.clients.reply.IPingService_PortType getIPingService1() throws javax.xml.rpc.ServiceException;

    public com.arb.ws.clients.reply.IPingService_PortType getIPingService1(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
