/**
 * PLXSoap_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.arb.ws.clients.dtx;

public interface PLXSoap_PortType extends java.rmi.Remote {
    public java.lang.String NEWCAR_PingGX(java.lang.String user, java.lang.String password, java.lang.String message, boolean debugMode) throws java.rmi.RemoteException;
    public java.lang.String NEWCAR_SellGX(java.lang.String user, java.lang.String password, java.lang.String message, boolean debugMode) throws java.rmi.RemoteException;
    public java.lang.String NEWCAR_PostGX(java.lang.String user, java.lang.String password, java.lang.String message, boolean debugMode) throws java.rmi.RemoteException;
    public java.lang.String NEWCAR_MultiDealer_PostGX(java.lang.String user, java.lang.String password, java.lang.String message, boolean debugMode) throws java.rmi.RemoteException;

    /**
     * Place a lead to the highest ordered buyer in your ping tree.
     */
    public com.arb.ws.clients.dtx.PostResponse place(java.lang.String generatorID, java.lang.String password, java.lang.String leadID, java.lang.String firstName, java.lang.String lastName, java.lang.String address, java.lang.String city, java.lang.String state, java.lang.String zipCode, java.lang.String email, java.lang.String homePhone, java.lang.String workPhone, java.lang.String workPhoneExt, java.lang.String year, java.lang.String make, java.lang.String model, java.lang.String trim, java.lang.String interiorColor, java.lang.String exteriorColor, java.lang.String financing, java.lang.String timeframe, java.lang.String contactTime) throws java.rmi.RemoteException;
    public java.lang.String NEWCAR_SellGX_ADF(java.lang.String user, java.lang.String password, java.lang.String message, boolean debugMode) throws java.rmi.RemoteException;
}
