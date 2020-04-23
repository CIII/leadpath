///**
// * PostServiceTestCase.java
// *
// * This file was auto-generated from WSDL
// * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
// */
//
//package com.arb.ws.clients.reply;
//
//public class PostServiceTestCase extends junit.framework.TestCase {
//    public PostServiceTestCase(java.lang.String name) {
//        super(name);
//    }
//
//    public void testIPostService1WSDL() throws Exception {
//        javax.xml.rpc.ServiceFactory serviceFactory = javax.xml.rpc.ServiceFactory.newInstance();
//        java.net.URL url = new java.net.URL(new com.arb.ws.clients.reply.PostServiceLocator().getIPostService1Address() + "?WSDL");
//        javax.xml.rpc.Service service = serviceFactory.createService(url, new com.arb.ws.clients.reply.PostServiceLocator().getServiceName());
//        assertTrue(service != null);
//    }
//
//    public void test1IPostService1DirectPostAutoOrganic() throws Exception {
//        com.arb.ws.clients.reply.IPostService1Stub binding;
//        try {
//            binding = (com.arb.ws.clients.reply.IPostService1Stub)
//                          new com.arb.ws.clients.reply.PostServiceLocator().getIPostService1();
//        }
//        catch (javax.xml.rpc.ServiceException jre) {
//            if(jre.getLinkedCause()!=null)
//                jre.getLinkedCause().printStackTrace();
//            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
//        }
//        assertNotNull("binding is null", binding);
//
//        // Time out after a minute
//        binding.setTimeout(60000);
//
//        // Test operation
//        com.arb.ws.clients.reply.AutoResponse value = null;
//        value = binding.directPostAutoOrganic(new com.arb.ws.clients.reply.OrganicAutoRequest());
//        // TBD - validate results
//    }
//
//    public void test2IPostService1DirectPostAuto() throws Exception {
//        com.arb.ws.clients.reply.IPostService1Stub binding;
//        try {
//            binding = (com.arb.ws.clients.reply.IPostService1Stub)
//                          new com.arb.ws.clients.reply.PostServiceLocator().getIPostService1();
//        }
//        catch (javax.xml.rpc.ServiceException jre) {
//            if(jre.getLinkedCause()!=null)
//                jre.getLinkedCause().printStackTrace();
//            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
//        }
//        assertNotNull("binding is null", binding);
//
//        // Time out after a minute
//        binding.setTimeout(60000);
//
//        // Test operation
//        com.arb.ws.clients.reply.AutoResponse value = null;
//        value = binding.directPostAuto(new com.arb.ws.clients.reply.AutoRequest());
//        // TBD - validate results
//    }
//
//    public void test3IPostService1DirectPostAutoFinanceOrganic() throws Exception {
//        com.arb.ws.clients.reply.IPostService1Stub binding;
//        try {
//            binding = (com.arb.ws.clients.reply.IPostService1Stub)
//                          new com.arb.ws.clients.reply.PostServiceLocator().getIPostService1();
//        }
//        catch (javax.xml.rpc.ServiceException jre) {
//            if(jre.getLinkedCause()!=null)
//                jre.getLinkedCause().printStackTrace();
//            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
//        }
//        assertNotNull("binding is null", binding);
//
//        // Time out after a minute
//        binding.setTimeout(60000);
//
//        // Test operation
//        com.arb.ws.clients.reply.AutoFinanceResponse value = null;
//        value = binding.directPostAutoFinanceOrganic(new com.arb.ws.clients.reply.OrganicAutoFinanceRequest());
//        // TBD - validate results
//    }
//
//    public void test4IPostService1DirectPostAutoFinance() throws Exception {
//        com.arb.ws.clients.reply.IPostService1Stub binding;
//        try {
//            binding = (com.arb.ws.clients.reply.IPostService1Stub)
//                          new com.arb.ws.clients.reply.PostServiceLocator().getIPostService1();
//        }
//        catch (javax.xml.rpc.ServiceException jre) {
//            if(jre.getLinkedCause()!=null)
//                jre.getLinkedCause().printStackTrace();
//            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
//        }
//        assertNotNull("binding is null", binding);
//
//        // Time out after a minute
//        binding.setTimeout(60000);
//
//        // Test operation
//        com.arb.ws.clients.reply.AutoFinanceResponse value = null;
//        value = binding.directPostAutoFinance(new com.arb.ws.clients.reply.AutoFinanceRequest());
//        // TBD - validate results
//    }
//
//    public void test5IPostService1DirectPostRealEstateOrganic() throws Exception {
//        com.arb.ws.clients.reply.IPostService1Stub binding;
//        try {
//            binding = (com.arb.ws.clients.reply.IPostService1Stub)
//                          new com.arb.ws.clients.reply.PostServiceLocator().getIPostService1();
//        }
//        catch (javax.xml.rpc.ServiceException jre) {
//            if(jre.getLinkedCause()!=null)
//                jre.getLinkedCause().printStackTrace();
//            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
//        }
//        assertNotNull("binding is null", binding);
//
//        // Time out after a minute
//        binding.setTimeout(60000);
//
//        // Test operation
//        com.arb.ws.clients.reply.RealEstateResponse value = null;
//        value = binding.directPostRealEstateOrganic(new com.arb.ws.clients.reply.OrganicRealEstateRequest());
//        // TBD - validate results
//    }
//
//    public void test6IPostService1DirectPostRealEstate() throws Exception {
//        com.arb.ws.clients.reply.IPostService1Stub binding;
//        try {
//            binding = (com.arb.ws.clients.reply.IPostService1Stub)
//                          new com.arb.ws.clients.reply.PostServiceLocator().getIPostService1();
//        }
//        catch (javax.xml.rpc.ServiceException jre) {
//            if(jre.getLinkedCause()!=null)
//                jre.getLinkedCause().printStackTrace();
//            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
//        }
//        assertNotNull("binding is null", binding);
//
//        // Time out after a minute
//        binding.setTimeout(60000);
//
//        // Test operation
//        com.arb.ws.clients.reply.RealEstateResponse value = null;
//        value = binding.directPostRealEstate(new com.arb.ws.clients.reply.RealEstateRequest());
//        // TBD - validate results
//    }
//
//    public void test7IPostService1DirectPostHomeImprovementOrganic() throws Exception {
//        com.arb.ws.clients.reply.IPostService1Stub binding;
//        try {
//            binding = (com.arb.ws.clients.reply.IPostService1Stub)
//                          new com.arb.ws.clients.reply.PostServiceLocator().getIPostService1();
//        }
//        catch (javax.xml.rpc.ServiceException jre) {
//            if(jre.getLinkedCause()!=null)
//                jre.getLinkedCause().printStackTrace();
//            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
//        }
//        assertNotNull("binding is null", binding);
//
//        // Time out after a minute
//        binding.setTimeout(60000);
//
//        // Test operation
//        com.arb.ws.clients.reply.HomeImprovementResponse value = null;
//        value = binding.directPostHomeImprovementOrganic(new com.arb.ws.clients.reply.OrganicHomeImprovementRequest(), new java.lang.String[0]);
//        // TBD - validate results
//    }
//
//    public void test8IPostService1DirectPostHomeImprovement() throws Exception {
//        com.arb.ws.clients.reply.IPostService1Stub binding;
//        try {
//            binding = (com.arb.ws.clients.reply.IPostService1Stub)
//                          new com.arb.ws.clients.reply.PostServiceLocator().getIPostService1();
//        }
//        catch (javax.xml.rpc.ServiceException jre) {
//            if(jre.getLinkedCause()!=null)
//                jre.getLinkedCause().printStackTrace();
//            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
//        }
//        assertNotNull("binding is null", binding);
//
//        // Time out after a minute
//        binding.setTimeout(60000);
//
//        // Test operation
//        com.arb.ws.clients.reply.HomeImprovementResponse value = null;
//        value = binding.directPostHomeImprovement(new com.arb.ws.clients.reply.HomeImprovementRequest());
//        // TBD - validate results
//    }
//
//    public void test9IPostService1DirectPostEducationOrganic() throws Exception {
//        com.arb.ws.clients.reply.IPostService1Stub binding;
//        try {
//            binding = (com.arb.ws.clients.reply.IPostService1Stub)
//                          new com.arb.ws.clients.reply.PostServiceLocator().getIPostService1();
//        }
//        catch (javax.xml.rpc.ServiceException jre) {
//            if(jre.getLinkedCause()!=null)
//                jre.getLinkedCause().printStackTrace();
//            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
//        }
//        assertNotNull("binding is null", binding);
//
//        // Time out after a minute
//        binding.setTimeout(60000);
//
//        // Test operation
//        com.arb.ws.clients.reply.EducationResponse value = null;
//        value = binding.directPostEducationOrganic(new com.arb.ws.clients.reply.OrganicEducationRequest());
//        // TBD - validate results
//    }
//
//    public void test10IPostService1DirectPostEducation() throws Exception {
//        com.arb.ws.clients.reply.IPostService1Stub binding;
//        try {
//            binding = (com.arb.ws.clients.reply.IPostService1Stub)
//                          new com.arb.ws.clients.reply.PostServiceLocator().getIPostService1();
//        }
//        catch (javax.xml.rpc.ServiceException jre) {
//            if(jre.getLinkedCause()!=null)
//                jre.getLinkedCause().printStackTrace();
//            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
//        }
//        assertNotNull("binding is null", binding);
//
//        // Time out after a minute
//        binding.setTimeout(60000);
//
//        // Test operation
//        com.arb.ws.clients.reply.EducationResponse value = null;
//        value = binding.directPostEducation(new com.arb.ws.clients.reply.EducationRequest());
//        // TBD - validate results
//    }
//
//    public void test11IPostService1DirectPostHomeInsuranceOrganic() throws Exception {
//        com.arb.ws.clients.reply.IPostService1Stub binding;
//        try {
//            binding = (com.arb.ws.clients.reply.IPostService1Stub)
//                          new com.arb.ws.clients.reply.PostServiceLocator().getIPostService1();
//        }
//        catch (javax.xml.rpc.ServiceException jre) {
//            if(jre.getLinkedCause()!=null)
//                jre.getLinkedCause().printStackTrace();
//            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
//        }
//        assertNotNull("binding is null", binding);
//
//        // Time out after a minute
//        binding.setTimeout(60000);
//
//        // Test operation
//        com.arb.ws.clients.reply.HomeInsuranceResponse value = null;
//        value = binding.directPostHomeInsuranceOrganic(new com.arb.ws.clients.reply.HomeInsuranceRequest());
//        // TBD - validate results
//    }
//
//    public void test12IPostService1DirectPostHomeInsurance() throws Exception {
//        com.arb.ws.clients.reply.IPostService1Stub binding;
//        try {
//            binding = (com.arb.ws.clients.reply.IPostService1Stub)
//                          new com.arb.ws.clients.reply.PostServiceLocator().getIPostService1();
//        }
//        catch (javax.xml.rpc.ServiceException jre) {
//            if(jre.getLinkedCause()!=null)
//                jre.getLinkedCause().printStackTrace();
//            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
//        }
//        assertNotNull("binding is null", binding);
//
//        // Time out after a minute
//        binding.setTimeout(60000);
//
//        // Test operation
//        com.arb.ws.clients.reply.HomeInsuranceResponse value = null;
//        value = binding.directPostHomeInsurance(new com.arb.ws.clients.reply.HomeInsuranceRequest());
//        // TBD - validate results
//    }
//
//    public void test13IPostService1DirectPostAutoInsuranceOrganic() throws Exception {
//        com.arb.ws.clients.reply.IPostService1Stub binding;
//        try {
//            binding = (com.arb.ws.clients.reply.IPostService1Stub)
//                          new com.arb.ws.clients.reply.PostServiceLocator().getIPostService1();
//        }
//        catch (javax.xml.rpc.ServiceException jre) {
//            if(jre.getLinkedCause()!=null)
//                jre.getLinkedCause().printStackTrace();
//            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
//        }
//        assertNotNull("binding is null", binding);
//
//        // Time out after a minute
//        binding.setTimeout(60000);
//
//        // Test operation
//        com.arb.ws.clients.reply.AutoInsuranceResponse value = null;
//        value = binding.directPostAutoInsuranceOrganic(new com.arb.ws.clients.reply.AutoInsuranceRequest());
//        // TBD - validate results
//    }
//
//    public void test14IPostService1DirectPostAutoInsurance() throws Exception {
//        com.arb.ws.clients.reply.IPostService1Stub binding;
//        try {
//            binding = (com.arb.ws.clients.reply.IPostService1Stub)
//                          new com.arb.ws.clients.reply.PostServiceLocator().getIPostService1();
//        }
//        catch (javax.xml.rpc.ServiceException jre) {
//            if(jre.getLinkedCause()!=null)
//                jre.getLinkedCause().printStackTrace();
//            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
//        }
//        assertNotNull("binding is null", binding);
//
//        // Time out after a minute
//        binding.setTimeout(60000);
//
//        // Test operation
//        com.arb.ws.clients.reply.AutoInsuranceResponse value = null;
//        value = binding.directPostAutoInsurance(new com.arb.ws.clients.reply.AutoInsuranceRequest());
//        // TBD - validate results
//    }
//
//    public void test15IPostService1PostAuto() throws Exception {
//        com.arb.ws.clients.reply.IPostService1Stub binding;
//        try {
//            binding = (com.arb.ws.clients.reply.IPostService1Stub)
//                          new com.arb.ws.clients.reply.PostServiceLocator().getIPostService1();
//        }
//        catch (javax.xml.rpc.ServiceException jre) {
//            if(jre.getLinkedCause()!=null)
//                jre.getLinkedCause().printStackTrace();
//            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
//        }
//        assertNotNull("binding is null", binding);
//
//        // Time out after a minute
//        binding.setTimeout(60000);
//
//        // Test operation
//        com.arb.ws.clients.reply.AutoResponse value = null;
//        value = binding.postAuto(new com.arb.ws.clients.reply.AutoRequest(), new java.lang.String[0]);
//        // TBD - validate results
//    }
//
//    public void test16IPostService1PostAutoFinance() throws Exception {
//        com.arb.ws.clients.reply.IPostService1Stub binding;
//        try {
//            binding = (com.arb.ws.clients.reply.IPostService1Stub)
//                          new com.arb.ws.clients.reply.PostServiceLocator().getIPostService1();
//        }
//        catch (javax.xml.rpc.ServiceException jre) {
//            if(jre.getLinkedCause()!=null)
//                jre.getLinkedCause().printStackTrace();
//            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
//        }
//        assertNotNull("binding is null", binding);
//
//        // Time out after a minute
//        binding.setTimeout(60000);
//
//        // Test operation
//        com.arb.ws.clients.reply.AutoFinanceResponse value = null;
//        value = binding.postAutoFinance(new com.arb.ws.clients.reply.AutoFinanceRequest());
//        // TBD - validate results
//    }
//
//    public void test17IPostService1PostHomeImprovement() throws Exception {
//        com.arb.ws.clients.reply.IPostService1Stub binding;
//        try {
//            binding = (com.arb.ws.clients.reply.IPostService1Stub)
//                          new com.arb.ws.clients.reply.PostServiceLocator().getIPostService1();
//        }
//        catch (javax.xml.rpc.ServiceException jre) {
//            if(jre.getLinkedCause()!=null)
//                jre.getLinkedCause().printStackTrace();
//            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
//        }
//        assertNotNull("binding is null", binding);
//
//        // Time out after a minute
//        binding.setTimeout(60000);
//
//        // Test operation
//        com.arb.ws.clients.reply.HomeImprovementResponse value = null;
//        value = binding.postHomeImprovement(new com.arb.ws.clients.reply.HomeImprovementRequest());
//        // TBD - validate results
//    }
//
//    public void test18IPostService1PostHomeImprovementQS() throws Exception {
//        com.arb.ws.clients.reply.IPostService1Stub binding;
//        try {
//            binding = (com.arb.ws.clients.reply.IPostService1Stub)
//                          new com.arb.ws.clients.reply.PostServiceLocator().getIPostService1();
//        }
//        catch (javax.xml.rpc.ServiceException jre) {
//            if(jre.getLinkedCause()!=null)
//                jre.getLinkedCause().printStackTrace();
//            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
//        }
//        assertNotNull("binding is null", binding);
//
//        // Time out after a minute
//        binding.setTimeout(60000);
//
//        // Test operation
//        com.arb.ws.clients.reply.HomeImprovementResponse value = null;
//        value = binding.postHomeImprovementQS(new com.arb.ws.clients.reply.HomeImprovementRequest());
//        // TBD - validate results
//    }
//
//    public void test19IPostService1PostHomeInsurance() throws Exception {
//        com.arb.ws.clients.reply.IPostService1Stub binding;
//        try {
//            binding = (com.arb.ws.clients.reply.IPostService1Stub)
//                          new com.arb.ws.clients.reply.PostServiceLocator().getIPostService1();
//        }
//        catch (javax.xml.rpc.ServiceException jre) {
//            if(jre.getLinkedCause()!=null)
//                jre.getLinkedCause().printStackTrace();
//            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
//        }
//        assertNotNull("binding is null", binding);
//
//        // Time out after a minute
//        binding.setTimeout(60000);
//
//        // Test operation
//        com.arb.ws.clients.reply.HomeInsuranceResponse value = null;
//        value = binding.postHomeInsurance(new com.arb.ws.clients.reply.HomeInsuranceRequest());
//        // TBD - validate results
//    }
//
//    public void test20IPostService1PostAutoInsurance() throws Exception {
//        com.arb.ws.clients.reply.IPostService1Stub binding;
//        try {
//            binding = (com.arb.ws.clients.reply.IPostService1Stub)
//                          new com.arb.ws.clients.reply.PostServiceLocator().getIPostService1();
//        }
//        catch (javax.xml.rpc.ServiceException jre) {
//            if(jre.getLinkedCause()!=null)
//                jre.getLinkedCause().printStackTrace();
//            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
//        }
//        assertNotNull("binding is null", binding);
//
//        // Time out after a minute
//        binding.setTimeout(60000);
//
//        // Test operation
//        com.arb.ws.clients.reply.AutoInsuranceResponse value = null;
//        value = binding.postAutoInsurance(new com.arb.ws.clients.reply.AutoInsuranceRequest());
//        // TBD - validate results
//    }
//
//    public void testIPostServiceWSDL() throws Exception {
//        javax.xml.rpc.ServiceFactory serviceFactory = javax.xml.rpc.ServiceFactory.newInstance();
//        java.net.URL url = new java.net.URL(new com.arb.ws.clients.reply.PostServiceLocator().getIPostServiceAddress() + "?WSDL");
//        javax.xml.rpc.Service service = serviceFactory.createService(url, new com.arb.ws.clients.reply.PostServiceLocator().getServiceName());
//        assertTrue(service != null);
//    }
//
//    public void test21IPostServiceDirectPostAutoOrganic() throws Exception {
//        com.arb.ws.clients.reply.IPostService_BindingStub binding;
//        try {
//            binding = (com.arb.ws.clients.reply.IPostService_BindingStub)
//                          new com.arb.ws.clients.reply.PostServiceLocator().getIPostService();
//        }
//        catch (javax.xml.rpc.ServiceException jre) {
//            if(jre.getLinkedCause()!=null)
//                jre.getLinkedCause().printStackTrace();
//            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
//        }
//        assertNotNull("binding is null", binding);
//
//        // Time out after a minute
//        binding.setTimeout(60000);
//
//        // Test operation
//        com.arb.ws.clients.reply.AutoResponse value = null;
//        value = binding.directPostAutoOrganic(new com.arb.ws.clients.reply.OrganicAutoRequest());
//        // TBD - validate results
//    }
//
//    public void test22IPostServiceDirectPostAuto() throws Exception {
//        com.arb.ws.clients.reply.IPostService_BindingStub binding;
//        try {
//            binding = (com.arb.ws.clients.reply.IPostService_BindingStub)
//                          new com.arb.ws.clients.reply.PostServiceLocator().getIPostService();
//        }
//        catch (javax.xml.rpc.ServiceException jre) {
//            if(jre.getLinkedCause()!=null)
//                jre.getLinkedCause().printStackTrace();
//            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
//        }
//        assertNotNull("binding is null", binding);
//
//        // Time out after a minute
//        binding.setTimeout(60000);
//
//        // Test operation
//        com.arb.ws.clients.reply.AutoResponse value = null;
//        value = binding.directPostAuto(new com.arb.ws.clients.reply.AutoRequest());
//        // TBD - validate results
//    }
//
//    public void test23IPostServiceDirectPostAutoFinanceOrganic() throws Exception {
//        com.arb.ws.clients.reply.IPostService_BindingStub binding;
//        try {
//            binding = (com.arb.ws.clients.reply.IPostService_BindingStub)
//                          new com.arb.ws.clients.reply.PostServiceLocator().getIPostService();
//        }
//        catch (javax.xml.rpc.ServiceException jre) {
//            if(jre.getLinkedCause()!=null)
//                jre.getLinkedCause().printStackTrace();
//            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
//        }
//        assertNotNull("binding is null", binding);
//
//        // Time out after a minute
//        binding.setTimeout(60000);
//
//        // Test operation
//        com.arb.ws.clients.reply.AutoFinanceResponse value = null;
//        value = binding.directPostAutoFinanceOrganic(new com.arb.ws.clients.reply.OrganicAutoFinanceRequest());
//        // TBD - validate results
//    }
//
//    public void test24IPostServiceDirectPostAutoFinance() throws Exception {
//        com.arb.ws.clients.reply.IPostService_BindingStub binding;
//        try {
//            binding = (com.arb.ws.clients.reply.IPostService_BindingStub)
//                          new com.arb.ws.clients.reply.PostServiceLocator().getIPostService();
//        }
//        catch (javax.xml.rpc.ServiceException jre) {
//            if(jre.getLinkedCause()!=null)
//                jre.getLinkedCause().printStackTrace();
//            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
//        }
//        assertNotNull("binding is null", binding);
//
//        // Time out after a minute
//        binding.setTimeout(60000);
//
//        // Test operation
//        com.arb.ws.clients.reply.AutoFinanceResponse value = null;
//        value = binding.directPostAutoFinance(new com.arb.ws.clients.reply.AutoFinanceRequest());
//        // TBD - validate results
//    }
//
//    public void test25IPostServiceDirectPostRealEstateOrganic() throws Exception {
//        com.arb.ws.clients.reply.IPostService_BindingStub binding;
//        try {
//            binding = (com.arb.ws.clients.reply.IPostService_BindingStub)
//                          new com.arb.ws.clients.reply.PostServiceLocator().getIPostService();
//        }
//        catch (javax.xml.rpc.ServiceException jre) {
//            if(jre.getLinkedCause()!=null)
//                jre.getLinkedCause().printStackTrace();
//            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
//        }
//        assertNotNull("binding is null", binding);
//
//        // Time out after a minute
//        binding.setTimeout(60000);
//
//        // Test operation
//        com.arb.ws.clients.reply.RealEstateResponse value = null;
//        value = binding.directPostRealEstateOrganic(new com.arb.ws.clients.reply.OrganicRealEstateRequest());
//        // TBD - validate results
//    }
//
//    public void test26IPostServiceDirectPostRealEstate() throws Exception {
//        com.arb.ws.clients.reply.IPostService_BindingStub binding;
//        try {
//            binding = (com.arb.ws.clients.reply.IPostService_BindingStub)
//                          new com.arb.ws.clients.reply.PostServiceLocator().getIPostService();
//        }
//        catch (javax.xml.rpc.ServiceException jre) {
//            if(jre.getLinkedCause()!=null)
//                jre.getLinkedCause().printStackTrace();
//            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
//        }
//        assertNotNull("binding is null", binding);
//
//        // Time out after a minute
//        binding.setTimeout(60000);
//
//        // Test operation
//        com.arb.ws.clients.reply.RealEstateResponse value = null;
//        value = binding.directPostRealEstate(new com.arb.ws.clients.reply.RealEstateRequest());
//        // TBD - validate results
//    }
//
//    public void test27IPostServiceDirectPostHomeImprovementOrganic() throws Exception {
//        com.arb.ws.clients.reply.IPostService_BindingStub binding;
//        try {
//            binding = (com.arb.ws.clients.reply.IPostService_BindingStub)
//                          new com.arb.ws.clients.reply.PostServiceLocator().getIPostService();
//        }
//        catch (javax.xml.rpc.ServiceException jre) {
//            if(jre.getLinkedCause()!=null)
//                jre.getLinkedCause().printStackTrace();
//            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
//        }
//        assertNotNull("binding is null", binding);
//
//        // Time out after a minute
//        binding.setTimeout(60000);
//
//        // Test operation
//        com.arb.ws.clients.reply.HomeImprovementResponse value = null;
//        value = binding.directPostHomeImprovementOrganic(new com.arb.ws.clients.reply.OrganicHomeImprovementRequest(), new java.lang.String[0]);
//        // TBD - validate results
//    }
//
//    public void test28IPostServiceDirectPostHomeImprovement() throws Exception {
//        com.arb.ws.clients.reply.IPostService_BindingStub binding;
//        try {
//            binding = (com.arb.ws.clients.reply.IPostService_BindingStub)
//                          new com.arb.ws.clients.reply.PostServiceLocator().getIPostService();
//        }
//        catch (javax.xml.rpc.ServiceException jre) {
//            if(jre.getLinkedCause()!=null)
//                jre.getLinkedCause().printStackTrace();
//            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
//        }
//        assertNotNull("binding is null", binding);
//
//        // Time out after a minute
//        binding.setTimeout(60000);
//
//        // Test operation
//        com.arb.ws.clients.reply.HomeImprovementResponse value = null;
//        value = binding.directPostHomeImprovement(new com.arb.ws.clients.reply.HomeImprovementRequest());
//        // TBD - validate results
//    }
//
//    public void test29IPostServiceDirectPostEducationOrganic() throws Exception {
//        com.arb.ws.clients.reply.IPostService_BindingStub binding;
//        try {
//            binding = (com.arb.ws.clients.reply.IPostService_BindingStub)
//                          new com.arb.ws.clients.reply.PostServiceLocator().getIPostService();
//        }
//        catch (javax.xml.rpc.ServiceException jre) {
//            if(jre.getLinkedCause()!=null)
//                jre.getLinkedCause().printStackTrace();
//            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
//        }
//        assertNotNull("binding is null", binding);
//
//        // Time out after a minute
//        binding.setTimeout(60000);
//
//        // Test operation
//        com.arb.ws.clients.reply.EducationResponse value = null;
//        value = binding.directPostEducationOrganic(new com.arb.ws.clients.reply.OrganicEducationRequest());
//        // TBD - validate results
//    }
//
//    public void test30IPostServiceDirectPostEducation() throws Exception {
//        com.arb.ws.clients.reply.IPostService_BindingStub binding;
//        try {
//            binding = (com.arb.ws.clients.reply.IPostService_BindingStub)
//                          new com.arb.ws.clients.reply.PostServiceLocator().getIPostService();
//        }
//        catch (javax.xml.rpc.ServiceException jre) {
//            if(jre.getLinkedCause()!=null)
//                jre.getLinkedCause().printStackTrace();
//            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
//        }
//        assertNotNull("binding is null", binding);
//
//        // Time out after a minute
//        binding.setTimeout(60000);
//
//        // Test operation
//        com.arb.ws.clients.reply.EducationResponse value = null;
//        value = binding.directPostEducation(new com.arb.ws.clients.reply.EducationRequest());
//        // TBD - validate results
//    }
//
//    public void test31IPostServiceDirectPostHomeInsuranceOrganic() throws Exception {
//        com.arb.ws.clients.reply.IPostService_BindingStub binding;
//        try {
//            binding = (com.arb.ws.clients.reply.IPostService_BindingStub)
//                          new com.arb.ws.clients.reply.PostServiceLocator().getIPostService();
//        }
//        catch (javax.xml.rpc.ServiceException jre) {
//            if(jre.getLinkedCause()!=null)
//                jre.getLinkedCause().printStackTrace();
//            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
//        }
//        assertNotNull("binding is null", binding);
//
//        // Time out after a minute
//        binding.setTimeout(60000);
//
//        // Test operation
//        com.arb.ws.clients.reply.HomeInsuranceResponse value = null;
//        value = binding.directPostHomeInsuranceOrganic(new com.arb.ws.clients.reply.HomeInsuranceRequest());
//        // TBD - validate results
//    }
//
//    public void test32IPostServiceDirectPostHomeInsurance() throws Exception {
//        com.arb.ws.clients.reply.IPostService_BindingStub binding;
//        try {
//            binding = (com.arb.ws.clients.reply.IPostService_BindingStub)
//                          new com.arb.ws.clients.reply.PostServiceLocator().getIPostService();
//        }
//        catch (javax.xml.rpc.ServiceException jre) {
//            if(jre.getLinkedCause()!=null)
//                jre.getLinkedCause().printStackTrace();
//            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
//        }
//        assertNotNull("binding is null", binding);
//
//        // Time out after a minute
//        binding.setTimeout(60000);
//
//        // Test operation
//        com.arb.ws.clients.reply.HomeInsuranceResponse value = null;
//        value = binding.directPostHomeInsurance(new com.arb.ws.clients.reply.HomeInsuranceRequest());
//        // TBD - validate results
//    }
//
//    public void test33IPostServiceDirectPostAutoInsuranceOrganic() throws Exception {
//        com.arb.ws.clients.reply.IPostService_BindingStub binding;
//        try {
//            binding = (com.arb.ws.clients.reply.IPostService_BindingStub)
//                          new com.arb.ws.clients.reply.PostServiceLocator().getIPostService();
//        }
//        catch (javax.xml.rpc.ServiceException jre) {
//            if(jre.getLinkedCause()!=null)
//                jre.getLinkedCause().printStackTrace();
//            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
//        }
//        assertNotNull("binding is null", binding);
//
//        // Time out after a minute
//        binding.setTimeout(60000);
//
//        // Test operation
//        com.arb.ws.clients.reply.AutoInsuranceResponse value = null;
//        value = binding.directPostAutoInsuranceOrganic(new com.arb.ws.clients.reply.AutoInsuranceRequest());
//        // TBD - validate results
//    }
//
//    public void test34IPostServiceDirectPostAutoInsurance() throws Exception {
//        com.arb.ws.clients.reply.IPostService_BindingStub binding;
//        try {
//            binding = (com.arb.ws.clients.reply.IPostService_BindingStub)
//                          new com.arb.ws.clients.reply.PostServiceLocator().getIPostService();
//        }
//        catch (javax.xml.rpc.ServiceException jre) {
//            if(jre.getLinkedCause()!=null)
//                jre.getLinkedCause().printStackTrace();
//            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
//        }
//        assertNotNull("binding is null", binding);
//
//        // Time out after a minute
//        binding.setTimeout(60000);
//
//        // Test operation
//        com.arb.ws.clients.reply.AutoInsuranceResponse value = null;
//        value = binding.directPostAutoInsurance(new com.arb.ws.clients.reply.AutoInsuranceRequest());
//        // TBD - validate results
//    }
//
//    public void test35IPostServicePostAuto() throws Exception {
//        com.arb.ws.clients.reply.IPostService_BindingStub binding;
//        try {
//            binding = (com.arb.ws.clients.reply.IPostService_BindingStub)
//                          new com.arb.ws.clients.reply.PostServiceLocator().getIPostService();
//        }
//        catch (javax.xml.rpc.ServiceException jre) {
//            if(jre.getLinkedCause()!=null)
//                jre.getLinkedCause().printStackTrace();
//            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
//        }
//        assertNotNull("binding is null", binding);
//
//        // Time out after a minute
//        binding.setTimeout(60000);
//
//        // Test operation
//        com.arb.ws.clients.reply.AutoResponse value = null;
//        value = binding.postAuto(new com.arb.ws.clients.reply.AutoRequest(), new java.lang.String[0]);
//        // TBD - validate results
//    }
//
//    public void test36IPostServicePostAutoFinance() throws Exception {
//        com.arb.ws.clients.reply.IPostService_BindingStub binding;
//        try {
//            binding = (com.arb.ws.clients.reply.IPostService_BindingStub)
//                          new com.arb.ws.clients.reply.PostServiceLocator().getIPostService();
//        }
//        catch (javax.xml.rpc.ServiceException jre) {
//            if(jre.getLinkedCause()!=null)
//                jre.getLinkedCause().printStackTrace();
//            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
//        }
//        assertNotNull("binding is null", binding);
//
//        // Time out after a minute
//        binding.setTimeout(60000);
//
//        // Test operation
//        com.arb.ws.clients.reply.AutoFinanceResponse value = null;
//        value = binding.postAutoFinance(new com.arb.ws.clients.reply.AutoFinanceRequest());
//        // TBD - validate results
//    }
//
//    public void test37IPostServicePostHomeImprovement() throws Exception {
//        com.arb.ws.clients.reply.IPostService_BindingStub binding;
//        try {
//            binding = (com.arb.ws.clients.reply.IPostService_BindingStub)
//                          new com.arb.ws.clients.reply.PostServiceLocator().getIPostService();
//        }
//        catch (javax.xml.rpc.ServiceException jre) {
//            if(jre.getLinkedCause()!=null)
//                jre.getLinkedCause().printStackTrace();
//            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
//        }
//        assertNotNull("binding is null", binding);
//
//        // Time out after a minute
//        binding.setTimeout(60000);
//
//        // Test operation
//        com.arb.ws.clients.reply.HomeImprovementResponse value = null;
//        value = binding.postHomeImprovement(new com.arb.ws.clients.reply.HomeImprovementRequest());
//        // TBD - validate results
//    }
//
//    public void test38IPostServicePostHomeImprovementQS() throws Exception {
//        com.arb.ws.clients.reply.IPostService_BindingStub binding;
//        try {
//            binding = (com.arb.ws.clients.reply.IPostService_BindingStub)
//                          new com.arb.ws.clients.reply.PostServiceLocator().getIPostService();
//        }
//        catch (javax.xml.rpc.ServiceException jre) {
//            if(jre.getLinkedCause()!=null)
//                jre.getLinkedCause().printStackTrace();
//            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
//        }
//        assertNotNull("binding is null", binding);
//
//        // Time out after a minute
//        binding.setTimeout(60000);
//
//        // Test operation
//        com.arb.ws.clients.reply.HomeImprovementResponse value = null;
//        value = binding.postHomeImprovementQS(new com.arb.ws.clients.reply.HomeImprovementRequest());
//        // TBD - validate results
//    }
//
//    public void test39IPostServicePostHomeInsurance() throws Exception {
//        com.arb.ws.clients.reply.IPostService_BindingStub binding;
//        try {
//            binding = (com.arb.ws.clients.reply.IPostService_BindingStub)
//                          new com.arb.ws.clients.reply.PostServiceLocator().getIPostService();
//        }
//        catch (javax.xml.rpc.ServiceException jre) {
//            if(jre.getLinkedCause()!=null)
//                jre.getLinkedCause().printStackTrace();
//            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
//        }
//        assertNotNull("binding is null", binding);
//
//        // Time out after a minute
//        binding.setTimeout(60000);
//
//        // Test operation
//        com.arb.ws.clients.reply.HomeInsuranceResponse value = null;
//        value = binding.postHomeInsurance(new com.arb.ws.clients.reply.HomeInsuranceRequest());
//        // TBD - validate results
//    }
//
//    public void test40IPostServicePostAutoInsurance() throws Exception {
//        com.arb.ws.clients.reply.IPostService_BindingStub binding;
//        try {
//            binding = (com.arb.ws.clients.reply.IPostService_BindingStub)
//                          new com.arb.ws.clients.reply.PostServiceLocator().getIPostService();
//        }
//        catch (javax.xml.rpc.ServiceException jre) {
//            if(jre.getLinkedCause()!=null)
//                jre.getLinkedCause().printStackTrace();
//            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
//        }
//        assertNotNull("binding is null", binding);
//
//        // Time out after a minute
//        binding.setTimeout(60000);
//
//        // Test operation
//        com.arb.ws.clients.reply.AutoInsuranceResponse value = null;
//        value = binding.postAutoInsurance(new com.arb.ws.clients.reply.AutoInsuranceRequest());
//        // TBD - validate results
//    }
//
//}
