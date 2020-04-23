/**
 * DropZoneSoap_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.arb.ws.clients.autobytel;

public interface DropZoneSoap_PortType extends java.rmi.Remote {
    public com.arb.ws.clients.autobytel.PingResult ping(int providerID, int year, java.lang.String make, java.lang.String model, java.lang.String trim, java.lang.String zipCode) throws java.rmi.RemoteException;
    public com.arb.ws.clients.autobytel.PingResult pingEx(int providerID, int year, java.lang.String make, java.lang.String model, java.lang.String trim, java.lang.String zipCode, int ppcSourceGroupID) throws java.rmi.RemoteException;
    public com.arb.ws.clients.autobytel.PostResult post(com.arb.ws.clients.autobytel.Lead lead) throws java.rmi.RemoteException;
    public com.arb.ws.clients.autobytel.PostResult postCommOptFlag(com.arb.ws.clients.autobytel.Lead lead, boolean optionsFlag) throws java.rmi.RemoteException;
    public com.arb.ws.clients.autobytel.PostResult postCommOpt(com.arb.ws.clients.autobytel.Lead lead, com.arb.ws.clients.autobytel.Option[] options) throws java.rmi.RemoteException;
    public com.arb.ws.clients.autobytel.DispositionResult disposition(int providerID, int leadID) throws java.rmi.RemoteException;
    public com.arb.ws.clients.autobytel.Error[] errors(int providerID) throws java.rmi.RemoteException;
}
