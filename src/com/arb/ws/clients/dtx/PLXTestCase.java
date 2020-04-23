///**
// * PLXTestCase.java
// *
// * This file was auto-generated from WSDL
// * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
// */
//
//package com.arb.ws.clients.dtx;
//
//public class PLXTestCase extends junit.framework.TestCase {
//    public PLXTestCase(java.lang.String name) {
//        super(name);
//    }
//
//    public void testPLXSoap12WSDL() throws Exception {
//        javax.xml.rpc.ServiceFactory serviceFactory = javax.xml.rpc.ServiceFactory.newInstance();
//        java.net.URL url = new java.net.URL(new com.arb.ws.clients.dtx.PLXLocator().getPLXSoap12Address() + "?WSDL");
//        javax.xml.rpc.Service service = serviceFactory.createService(url, new com.arb.ws.clients.dtx.PLXLocator().getServiceName());
//        assertTrue(service != null);
//    }
//
//    public void test1PLXSoap12NEWCAR_PingGX() throws Exception {
//        com.arb.ws.clients.dtx.PLXSoap12Stub binding;
//        try {
//            binding = (com.arb.ws.clients.dtx.PLXSoap12Stub)
//                          new com.arb.ws.clients.dtx.PLXLocator().getPLXSoap12();
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
//        java.lang.String value = null;
//        value = binding.NEWCAR_PingGX(new java.lang.String(), new java.lang.String(), new java.lang.String(), true);
//        // TBD - validate results
//    }
//
//    public void test2PLXSoap12NEWCAR_SellGX() throws Exception {
//        com.arb.ws.clients.dtx.PLXSoap12Stub binding;
//        try {
//            binding = (com.arb.ws.clients.dtx.PLXSoap12Stub)
//                          new com.arb.ws.clients.dtx.PLXLocator().getPLXSoap12();
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
//        java.lang.String value = null;
//        value = binding.NEWCAR_SellGX(new java.lang.String(), new java.lang.String(), new java.lang.String(), true);
//        // TBD - validate results
//    }
//
//    public void test3PLXSoap12NEWCAR_PostGX() throws Exception {
//        com.arb.ws.clients.dtx.PLXSoap12Stub binding;
//        try {
//            binding = (com.arb.ws.clients.dtx.PLXSoap12Stub)
//                          new com.arb.ws.clients.dtx.PLXLocator().getPLXSoap12();
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
//        java.lang.String value = null;
//        value = binding.NEWCAR_PostGX(new java.lang.String(), new java.lang.String(), new java.lang.String(), true);
//        // TBD - validate results
//    }
//
//    public void test4PLXSoap12NEWCAR_MultiDealer_PostGX() throws Exception {
//        com.arb.ws.clients.dtx.PLXSoap12Stub binding;
//        try {
//            binding = (com.arb.ws.clients.dtx.PLXSoap12Stub)
//                          new com.arb.ws.clients.dtx.PLXLocator().getPLXSoap12();
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
//        java.lang.String value = null;
//        value = binding.NEWCAR_MultiDealer_PostGX(new java.lang.String(), new java.lang.String(), new java.lang.String(), true);
//        // TBD - validate results
//    }
//
//    public void test5PLXSoap12Place() throws Exception {
//        com.arb.ws.clients.dtx.PLXSoap12Stub binding;
//        try {
//            binding = (com.arb.ws.clients.dtx.PLXSoap12Stub)
//                          new com.arb.ws.clients.dtx.PLXLocator().getPLXSoap12();
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
//        com.arb.ws.clients.dtx.PostResponse value = null;
//        value = binding.place(new java.lang.String(), new java.lang.String(), new java.lang.String(), new java.lang.String(), new java.lang.String(), new java.lang.String(), new java.lang.String(), new java.lang.String(), new java.lang.String(), new java.lang.String(), new java.lang.String(), new java.lang.String(), new java.lang.String(), new java.lang.String(), new java.lang.String(), new java.lang.String(), new java.lang.String(), new java.lang.String(), new java.lang.String(), new java.lang.String(), new java.lang.String(), new java.lang.String());
//        // TBD - validate results
//    }
//
//    public void test6PLXSoap12NEWCAR_SellGX_ADF() throws Exception {
//        com.arb.ws.clients.dtx.PLXSoap12Stub binding;
//        try {
//            binding = (com.arb.ws.clients.dtx.PLXSoap12Stub)
//                          new com.arb.ws.clients.dtx.PLXLocator().getPLXSoap12();
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
//        java.lang.String value = null;
//        value = binding.NEWCAR_SellGX_ADF(new java.lang.String(), new java.lang.String(), new java.lang.String(), true);
//        // TBD - validate results
//    }
//
//    public void testPLXSoapWSDL() throws Exception {
//        javax.xml.rpc.ServiceFactory serviceFactory = javax.xml.rpc.ServiceFactory.newInstance();
//        java.net.URL url = new java.net.URL(new com.arb.ws.clients.dtx.PLXLocator().getPLXSoapAddress() + "?WSDL");
//        javax.xml.rpc.Service service = serviceFactory.createService(url, new com.arb.ws.clients.dtx.PLXLocator().getServiceName());
//        assertTrue(service != null);
//    }
//
//    public void test7PLXSoapNEWCAR_PingGX() throws Exception {
//        com.arb.ws.clients.dtx.PLXSoap_BindingStub binding;
//        try {
//            binding = (com.arb.ws.clients.dtx.PLXSoap_BindingStub)
//                          new com.arb.ws.clients.dtx.PLXLocator().getPLXSoap();
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
//        java.lang.String value = null;
//        value = binding.NEWCAR_PingGX(new java.lang.String(), new java.lang.String(), new java.lang.String(), true);
//        // TBD - validate results
//    }
//
//    public void test8PLXSoapNEWCAR_SellGX() throws Exception {
//        com.arb.ws.clients.dtx.PLXSoap_BindingStub binding;
//        try {
//            binding = (com.arb.ws.clients.dtx.PLXSoap_BindingStub)
//                          new com.arb.ws.clients.dtx.PLXLocator().getPLXSoap();
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
//        java.lang.String value = null;
//        value = binding.NEWCAR_SellGX(new java.lang.String(), new java.lang.String(), new java.lang.String(), true);
//        // TBD - validate results
//    }
//
//    public void test9PLXSoapNEWCAR_PostGX() throws Exception {
//        com.arb.ws.clients.dtx.PLXSoap_BindingStub binding;
//        try {
//            binding = (com.arb.ws.clients.dtx.PLXSoap_BindingStub)
//                          new com.arb.ws.clients.dtx.PLXLocator().getPLXSoap();
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
//        java.lang.String value = null;
//        value = binding.NEWCAR_PostGX(new java.lang.String(), new java.lang.String(), new java.lang.String(), true);
//        // TBD - validate results
//    }
//
//    public void test10PLXSoapNEWCAR_MultiDealer_PostGX() throws Exception {
//        com.arb.ws.clients.dtx.PLXSoap_BindingStub binding;
//        try {
//            binding = (com.arb.ws.clients.dtx.PLXSoap_BindingStub)
//                          new com.arb.ws.clients.dtx.PLXLocator().getPLXSoap();
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
//        java.lang.String value = null;
//        value = binding.NEWCAR_MultiDealer_PostGX(new java.lang.String(), new java.lang.String(), new java.lang.String(), true);
//        // TBD - validate results
//    }
//
//    public void test11PLXSoapPlace() throws Exception {
//        com.arb.ws.clients.dtx.PLXSoap_BindingStub binding;
//        try {
//            binding = (com.arb.ws.clients.dtx.PLXSoap_BindingStub)
//                          new com.arb.ws.clients.dtx.PLXLocator().getPLXSoap();
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
//        com.arb.ws.clients.dtx.PostResponse value = null;
//        value = binding.place(new java.lang.String(), new java.lang.String(), new java.lang.String(), new java.lang.String(), new java.lang.String(), new java.lang.String(), new java.lang.String(), new java.lang.String(), new java.lang.String(), new java.lang.String(), new java.lang.String(), new java.lang.String(), new java.lang.String(), new java.lang.String(), new java.lang.String(), new java.lang.String(), new java.lang.String(), new java.lang.String(), new java.lang.String(), new java.lang.String(), new java.lang.String(), new java.lang.String());
//        // TBD - validate results
//    }
//
//    public void test12PLXSoapNEWCAR_SellGX_ADF() throws Exception {
//        com.arb.ws.clients.dtx.PLXSoap_BindingStub binding;
//        try {
//            binding = (com.arb.ws.clients.dtx.PLXSoap_BindingStub)
//                          new com.arb.ws.clients.dtx.PLXLocator().getPLXSoap();
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
//        java.lang.String value = null;
//        value = binding.NEWCAR_SellGX_ADF(new java.lang.String(), new java.lang.String(), new java.lang.String(), true);
//        // TBD - validate results
//    }
//
//}
