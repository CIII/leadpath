/**
 * DropZoneTestCase.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.arb.ws.clients.autobytel;

public class DropZoneTestCase
//        extends junit.framework.TestCase
{
    public DropZoneTestCase(java.lang.String name) {
//        super(name);
    }

//    public void testDropZoneSoap12WSDL() throws Exception {
//        javax.xml.rpc.ServiceFactory serviceFactory = javax.xml.rpc.ServiceFactory.newInstance();
//        java.net.URL url = new java.net.URL(new com.arb.ws.clients.autobytel.DropZoneLocator().getDropZoneSoap12Address() + "?WSDL");
//        javax.xml.rpc.Service service = serviceFactory.createService(url, new com.arb.ws.clients.autobytel.DropZoneLocator().getServiceName());
//        assertTrue(service != null);
//    }
//
//    public void test1DropZoneSoap12Ping() throws Exception {
//        com.arb.ws.clients.autobytel.DropZoneSoap12Stub binding;
//        try {
//            binding = (com.arb.ws.clients.autobytel.DropZoneSoap12Stub)
//                          new com.arb.ws.clients.autobytel.DropZoneLocator().getDropZoneSoap12();
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
//        com.arb.ws.clients.autobytel.PingResult value = null;
//        value = binding.ping(0, 0, new java.lang.String(), new java.lang.String(), new java.lang.String(), new java.lang.String());
//        // TBD - validate results
//    }
//
//    public void test2DropZoneSoap12PingEx() throws Exception {
//        com.arb.ws.clients.autobytel.DropZoneSoap12Stub binding;
//        try {
//            binding = (com.arb.ws.clients.autobytel.DropZoneSoap12Stub)
//                          new com.arb.ws.clients.autobytel.DropZoneLocator().getDropZoneSoap12();
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
//        com.arb.ws.clients.autobytel.PingResult value = null;
//        value = binding.pingEx(0, 0, new java.lang.String(), new java.lang.String(), new java.lang.String(), new java.lang.String(), 0);
//        // TBD - validate results
//    }
//
//    public void test3DropZoneSoap12Post() throws Exception {
//        com.arb.ws.clients.autobytel.DropZoneSoap12Stub binding;
//        try {
//            binding = (com.arb.ws.clients.autobytel.DropZoneSoap12Stub)
//                          new com.arb.ws.clients.autobytel.DropZoneLocator().getDropZoneSoap12();
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
//        com.arb.ws.clients.autobytel.PostResult value = null;
//        value = binding.post(new com.arb.ws.clients.autobytel.Lead());
//        // TBD - validate results
//    }
//
//    public void test4DropZoneSoap12PostCommOptFlag() throws Exception {
//        com.arb.ws.clients.autobytel.DropZoneSoap12Stub binding;
//        try {
//            binding = (com.arb.ws.clients.autobytel.DropZoneSoap12Stub)
//                          new com.arb.ws.clients.autobytel.DropZoneLocator().getDropZoneSoap12();
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
//        com.arb.ws.clients.autobytel.PostResult value = null;
//        value = binding.postCommOptFlag(new com.arb.ws.clients.autobytel.Lead(), true);
//        // TBD - validate results
//    }
//
//    public void test5DropZoneSoap12PostCommOpt() throws Exception {
//        com.arb.ws.clients.autobytel.DropZoneSoap12Stub binding;
//        try {
//            binding = (com.arb.ws.clients.autobytel.DropZoneSoap12Stub)
//                          new com.arb.ws.clients.autobytel.DropZoneLocator().getDropZoneSoap12();
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
//        com.arb.ws.clients.autobytel.PostResult value = null;
//        value = binding.postCommOpt(new com.arb.ws.clients.autobytel.Lead(), new com.arb.ws.clients.autobytel.Option[0]);
//        // TBD - validate results
//    }
//
//    public void test6DropZoneSoap12Disposition() throws Exception {
//        com.arb.ws.clients.autobytel.DropZoneSoap12Stub binding;
//        try {
//            binding = (com.arb.ws.clients.autobytel.DropZoneSoap12Stub)
//                          new com.arb.ws.clients.autobytel.DropZoneLocator().getDropZoneSoap12();
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
//        com.arb.ws.clients.autobytel.DispositionResult value = null;
//        value = binding.disposition(0, 0);
//        // TBD - validate results
//    }
//
//    public void test7DropZoneSoap12Errors() throws Exception {
//        com.arb.ws.clients.autobytel.DropZoneSoap12Stub binding;
//        try {
//            binding = (com.arb.ws.clients.autobytel.DropZoneSoap12Stub)
//                          new com.arb.ws.clients.autobytel.DropZoneLocator().getDropZoneSoap12();
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
//        com.arb.ws.clients.autobytel.Error[] value = null;
//        value = binding.errors(0);
//        // TBD - validate results
//    }
//
//    public void testDropZoneSoapWSDL() throws Exception {
//        javax.xml.rpc.ServiceFactory serviceFactory = javax.xml.rpc.ServiceFactory.newInstance();
//        java.net.URL url = new java.net.URL(new com.arb.ws.clients.autobytel.DropZoneLocator().getDropZoneSoapAddress() + "?WSDL");
//        javax.xml.rpc.Service service = serviceFactory.createService(url, new com.arb.ws.clients.autobytel.DropZoneLocator().getServiceName());
//        assertTrue(service != null);
//    }
//
//    public void test8DropZoneSoapPing() throws Exception {
//        com.arb.ws.clients.autobytel.DropZoneSoap_BindingStub binding;
//        try {
//            binding = (com.arb.ws.clients.autobytel.DropZoneSoap_BindingStub)
//                          new com.arb.ws.clients.autobytel.DropZoneLocator().getDropZoneSoap();
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
//        com.arb.ws.clients.autobytel.PingResult value = null;
//        value = binding.ping(0, 0, new java.lang.String(), new java.lang.String(), new java.lang.String(), new java.lang.String());
//        // TBD - validate results
//    }
//
//    public void test9DropZoneSoapPingEx() throws Exception {
//        com.arb.ws.clients.autobytel.DropZoneSoap_BindingStub binding;
//        try {
//            binding = (com.arb.ws.clients.autobytel.DropZoneSoap_BindingStub)
//                          new com.arb.ws.clients.autobytel.DropZoneLocator().getDropZoneSoap();
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
//        com.arb.ws.clients.autobytel.PingResult value = null;
//        value = binding.pingEx(0, 0, new java.lang.String(), new java.lang.String(), new java.lang.String(), new java.lang.String(), 0);
//        // TBD - validate results
//    }
//
//    public void test10DropZoneSoapPost() throws Exception {
//        com.arb.ws.clients.autobytel.DropZoneSoap_BindingStub binding;
//        try {
//            binding = (com.arb.ws.clients.autobytel.DropZoneSoap_BindingStub)
//                          new com.arb.ws.clients.autobytel.DropZoneLocator().getDropZoneSoap();
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
//        com.arb.ws.clients.autobytel.PostResult value = null;
//        value = binding.post(new com.arb.ws.clients.autobytel.Lead());
//        // TBD - validate results
//    }
//
//    public void test11DropZoneSoapPostCommOptFlag() throws Exception {
//        com.arb.ws.clients.autobytel.DropZoneSoap_BindingStub binding;
//        try {
//            binding = (com.arb.ws.clients.autobytel.DropZoneSoap_BindingStub)
//                          new com.arb.ws.clients.autobytel.DropZoneLocator().getDropZoneSoap();
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
//        com.arb.ws.clients.autobytel.PostResult value = null;
//        value = binding.postCommOptFlag(new com.arb.ws.clients.autobytel.Lead(), true);
//        // TBD - validate results
//    }
//
//    public void test12DropZoneSoapPostCommOpt() throws Exception {
//        com.arb.ws.clients.autobytel.DropZoneSoap_BindingStub binding;
//        try {
//            binding = (com.arb.ws.clients.autobytel.DropZoneSoap_BindingStub)
//                          new com.arb.ws.clients.autobytel.DropZoneLocator().getDropZoneSoap();
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
//        com.arb.ws.clients.autobytel.PostResult value = null;
//        value = binding.postCommOpt(new com.arb.ws.clients.autobytel.Lead(), new com.arb.ws.clients.autobytel.Option[0]);
//        // TBD - validate results
//    }
//
//    public void test13DropZoneSoapDisposition() throws Exception {
//        com.arb.ws.clients.autobytel.DropZoneSoap_BindingStub binding;
//        try {
//            binding = (com.arb.ws.clients.autobytel.DropZoneSoap_BindingStub)
//                          new com.arb.ws.clients.autobytel.DropZoneLocator().getDropZoneSoap();
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
//        com.arb.ws.clients.autobytel.DispositionResult value = null;
//        value = binding.disposition(0, 0);
//        // TBD - validate results
//    }
//
//    public void test14DropZoneSoapErrors() throws Exception {
//        com.arb.ws.clients.autobytel.DropZoneSoap_BindingStub binding;
//        try {
//            binding = (com.arb.ws.clients.autobytel.DropZoneSoap_BindingStub)
//                          new com.arb.ws.clients.autobytel.DropZoneLocator().getDropZoneSoap();
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
//        com.arb.ws.clients.autobytel.Error[] value = null;
//        value = binding.errors(0);
//        // TBD - validate results
//    }

}
