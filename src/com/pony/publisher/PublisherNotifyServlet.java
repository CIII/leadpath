package com.pony.publisher;

import com.pony.advertiser.Disposition;
import com.pony.advertiser.Io;
import com.pony.advertiser.writers.PaydayTestWriter;
import com.pony.lead.Arrival;
import com.pony.lead.LeadType;
import com.pony.leadtypes.Payday;
import com.pony.models.ArrivalModelImpl;
import com.pony.models.IoModel;
import com.pony.models.PaydayLeadModel;
import com.pony.rules.PaydayRule;
import com.pony.rules.Rule;
import com.pony.validation.ValidationResponse;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Copyright 2011 PonyMash, LLC
 * User: Martin
 * Date: 11/12/11
 * Time: 6:07 PM
 */
public class PublisherNotifyServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //doPost(req, resp);

        if (!"146.115.68.207".equals(req.getRemoteAddr())) {
            super.doGet(req, resp);
            return;
        }

        resp.setContentType("text/html");

        Writer out = resp.getWriter();
        out.write("<html><head><title>Test</title></head><body><form method='post'><input type='text' name='lead_id' /><br><input type='text' name='account_number' /><br/><input type='text' name='social_security_number'></input><br/><input type='text' name='code'></input></br><input type='submit' name='submit' /></form></body></html>");
        out.flush();

        resp.setStatus(HttpServletResponse.SC_OK); //200
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        response.setContentType("text/plain");
//        String leadId = request.getParameter("lead_id");
//
//        System.out.println("received lead_id=" + leadId);
//
//        try {
//            // get a lead id to post
//            // and post it
//
//            Long paydayLeadId;
//            PaydayLeadModel leadModel = null;
//            Arrival arrival = null;
//
//            if (leadId != null) {
//                paydayLeadId = Long.valueOf(leadId);
//                leadModel = PaydayLeadModel.find(paydayLeadId);
//                if (leadModel != null) {
//                    arrival = ArrivalModel.find(leadModel.getLead().getArrivalId());
//                }
//            }
//
//            if (leadModel == null) {
//                response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE); // 406
//                writeResponse(response);
//                return;
//            }
//
//            // backfill 'unstored data' (we do not store ssn anywhere!)
//            String ssn = request.getParameter("social_security_number");
//            if (ssn != null && !"".equals(ssn)) {
//                leadModel.getLead().setSocialSecurityNumber(ssn);
//            }
//            else {
//                System.out.println("ERROR! No ssn provided!");
//            }
//
//            String account = request.getParameter("account_number");
//            if (account != null && !"".equals(account)) {
//                leadModel.getLead().setAccountNumber(account);
//            }
//            else {
//                System.out.println("ERROR! No account provided!");
//            }
//
//            // validate the lead
//            //TODO abstract this correctly
//            Payday leadType = (Payday) LeadType.find("Payday");
//            ValidationResponse vResponse = leadType.validate(leadModel.getLead());

//            if (vResponse.isValid()) {
//                // findByEmail ios
//                List<IoModel> ios = new ArrayList<IoModel>();
//
//                // if a specific code is provided, only look for that io
//                if (request.getParameter("code") != null) {
//                    IoModel io = IoModel.findByCode(request.getParameter("code"), leadType);
//                    if (io != null) {
//                        ios.add(io);
//                    }
//                }
//                else {
//                    ios.addAll(IoModel.findAll(leadType));
//                }
//                IoModel.applyCaps(ios);
//
//                // sort
//                Rule rule = new PaydayRule();
//                Stack<Io> ioStack = rule.sortOrders(ios);
//
////                AdvertiserService advertiserService = new AdvertiserService();
////
////                advertiserService.post(publisherContext, )
////                // try to post (stop after the first accepted one)
////                //LinkedList<Disposition> dispositions = new LinkedList<Disposition>();
////                Disposition disposition = null;
////                //TODO: either get rid of this entire class, or fix the io vs candidate
////                AdvertiserPostContext ctx = AdvertiserPostContext.create(arrival, leadType, leadModel.getLead(), 1, null);
////
////                while (!ioStack.empty()) {
////                    Io io = ioStack.pop();
////                    RoutingCandidate candidate = RoutingCandidate.create(io);
////                    disposition = advertiserService.post(ctx, candidate);
////                    System.out.println("post route: lead " + leadId + ": " + io + ": " + disposition);
////
////                    if (disposition.isAccepted()) {
////                        // we only allow one successful post per lead (for now)
////                        break;
////                    }
////                }
//
//                response.setStatus(HttpServletResponse.SC_OK); //200
//
////                writeResponse(leadId, response, vResponse, disposition);
//            }
//            else {
//                response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE); // 406
//                writeResponse(leadId, response, vResponse);
//            }
//        }
//        catch (SQLException e) {
//            e.printStackTrace();
//            writeResponse(leadId, response, e);
//        }
//        catch (NamingException e) {
//            e.printStackTrace();
//            writeResponse(leadId, response, e);
//        }
//        catch (Throwable e) {
//            e.printStackTrace();
//            writeResponse(leadId, response, e);
//        }
    }

    private void writeResponse(HttpServletResponse response) throws IOException {
        Writer out = response.getWriter();
        out.write("fail;invalid-lead-id");
        out.flush();
    }

    private void writeResponse(String leadId, HttpServletResponse response, Throwable e) throws IOException {
        Writer out = response.getWriter();
        out.write("fail;lead-id=" + leadId + ":");

        if (e != null) {
            out.write(e.getMessage());
        }
        else {
            out.write("unknown error occurred");
        }
    }

    private void writeResponse(String leadId, HttpServletResponse response, ValidationResponse vResponse, Disposition disposition)
            throws IOException {
        Writer out = response.getWriter();
        if (disposition != null) {
            out.write(disposition.isAccepted() ? "success" : "fail");
            String comment = disposition.getComment();
            //TODO: improve this: if the last disposition isn't a redirect url, use the default url
            String url = comment.startsWith("http") ? comment : PaydayTestWriter.URL;
            out.write(";" + url);
        }
        else {
            out.write("fail;no ios found");
        }
    }

    private void writeResponse(String leadId, HttpServletResponse response, ValidationResponse vResponse)
            throws IOException {
        Writer out = response.getWriter();

        if (vResponse != null) {
            out.write((vResponse.isValid() ? "success" : "fail") + ";");
        }
        else {
            //todo
        }

    }
}
