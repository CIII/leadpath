/**
 * IPingService_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.arb.ws.clients.reply;

public interface IPingService_PortType extends java.rmi.Remote {
    public com.arb.ws.clients.reply.AutoResponse pingAuto(com.arb.ws.clients.reply.AutoRequest pingAutoRequest) throws java.rmi.RemoteException;
    public com.arb.ws.clients.reply.AutoResponse pingAutoOrganic(com.arb.ws.clients.reply.AutoRequest pingAutoRequest, java.lang.String[] directives) throws java.rmi.RemoteException;
    public com.arb.ws.clients.reply.RealEstateResponse pingRealEstate(com.arb.ws.clients.reply.RealEstateRequest pingRealEstateRequest) throws java.rmi.RemoteException;
    public com.arb.ws.clients.reply.AutoFinanceResponse pingAutoFinance(com.arb.ws.clients.reply.AutoFinanceRequest pingAutoFinanceRequest) throws java.rmi.RemoteException;
    public com.arb.ws.clients.reply.HomeImprovementResponse pingHomeImprovement(com.arb.ws.clients.reply.HomeImprovementRequest pingHomeImprovementRequest) throws java.rmi.RemoteException;
    public com.arb.ws.clients.reply.HomeImprovementResponse pingHomeImprovementQS(com.arb.ws.clients.reply.HomeImprovementRequest pingHomeImprovementRequest) throws java.rmi.RemoteException;
    public com.arb.ws.clients.reply.HomeInsuranceResponse pingHomeInsurance(com.arb.ws.clients.reply.HomeInsuranceRequest pingHomeInsuranceRequest) throws java.rmi.RemoteException;
    public com.arb.ws.clients.reply.AutoInsuranceResponse pingAutoInsurance(com.arb.ws.clients.reply.AutoInsuranceRequest pingAutoInsuranceRequest) throws java.rmi.RemoteException;
}
