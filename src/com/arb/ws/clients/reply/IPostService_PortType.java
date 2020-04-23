/**
 * IPostService_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.arb.ws.clients.reply;

public interface IPostService_PortType extends java.rmi.Remote {
    public com.arb.ws.clients.reply.AutoResponse directPostAutoOrganic(com.arb.ws.clients.reply.OrganicAutoRequest postOrganicAutoRequest) throws java.rmi.RemoteException;
    public com.arb.ws.clients.reply.AutoResponse directPostAuto(com.arb.ws.clients.reply.AutoRequest postAutoRequest) throws java.rmi.RemoteException;
    public com.arb.ws.clients.reply.AutoFinanceResponse directPostAutoFinanceOrganic(com.arb.ws.clients.reply.OrganicAutoFinanceRequest postOrganicAutoFinanceRequest) throws java.rmi.RemoteException;
    public com.arb.ws.clients.reply.AutoFinanceResponse directPostAutoFinance(com.arb.ws.clients.reply.AutoFinanceRequest postAutoFinanceRequest) throws java.rmi.RemoteException;
    public com.arb.ws.clients.reply.RealEstateResponse directPostRealEstateOrganic(com.arb.ws.clients.reply.OrganicRealEstateRequest postOrganicRealEstateRequest) throws java.rmi.RemoteException;
    public com.arb.ws.clients.reply.RealEstateResponse directPostRealEstate(com.arb.ws.clients.reply.RealEstateRequest postRealEstateRequest) throws java.rmi.RemoteException;
    public com.arb.ws.clients.reply.HomeImprovementResponse directPostHomeImprovementOrganic(com.arb.ws.clients.reply.OrganicHomeImprovementRequest postOrganicHomeImprovementRequest, java.lang.String[] directives) throws java.rmi.RemoteException;
    public com.arb.ws.clients.reply.HomeImprovementResponse directPostHomeImprovement(com.arb.ws.clients.reply.HomeImprovementRequest postHomeImprovementRequest) throws java.rmi.RemoteException;
    public com.arb.ws.clients.reply.EducationResponse directPostEducationOrganic(com.arb.ws.clients.reply.OrganicEducationRequest postOrganicEducationRequest) throws java.rmi.RemoteException;
    public com.arb.ws.clients.reply.EducationResponse directPostEducation(com.arb.ws.clients.reply.EducationRequest postEducationRequest) throws java.rmi.RemoteException;
    public com.arb.ws.clients.reply.HomeInsuranceResponse directPostHomeInsuranceOrganic(com.arb.ws.clients.reply.HomeInsuranceRequest postHomeInsuranceRequest) throws java.rmi.RemoteException;
    public com.arb.ws.clients.reply.HomeInsuranceResponse directPostHomeInsurance(com.arb.ws.clients.reply.HomeInsuranceRequest postHomeInsuranceRequest) throws java.rmi.RemoteException;
    public com.arb.ws.clients.reply.AutoInsuranceResponse directPostAutoInsuranceOrganic(com.arb.ws.clients.reply.AutoInsuranceRequest postAutoInsuranceRequest) throws java.rmi.RemoteException;
    public com.arb.ws.clients.reply.AutoInsuranceResponse directPostAutoInsurance(com.arb.ws.clients.reply.AutoInsuranceRequest postAutoInsuranceRequest) throws java.rmi.RemoteException;
    public com.arb.ws.clients.reply.AutoResponse postAuto(com.arb.ws.clients.reply.AutoRequest postAutoRequest, java.lang.String[] directives) throws java.rmi.RemoteException;
    public com.arb.ws.clients.reply.AutoFinanceResponse postAutoFinance(com.arb.ws.clients.reply.AutoFinanceRequest postAutoFinanceRequest) throws java.rmi.RemoteException;
    public com.arb.ws.clients.reply.HomeImprovementResponse postHomeImprovement(com.arb.ws.clients.reply.HomeImprovementRequest postHomeImprovementRequest) throws java.rmi.RemoteException;
    public com.arb.ws.clients.reply.HomeImprovementResponse postHomeImprovementQS(com.arb.ws.clients.reply.HomeImprovementRequest postHomeImprovementRequest) throws java.rmi.RemoteException;
    public com.arb.ws.clients.reply.HomeInsuranceResponse postHomeInsurance(com.arb.ws.clients.reply.HomeInsuranceRequest postHomeInsuranceRequest) throws java.rmi.RemoteException;
    public com.arb.ws.clients.reply.AutoInsuranceResponse postAutoInsurance(com.arb.ws.clients.reply.AutoInsuranceRequest postAutoInsuranceRequest) throws java.rmi.RemoteException;
}
