package com.pony.email.validation;

import com.pony.validation.ValidationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;

import javax.net.ssl.SSLHandshakeException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * PonyLeads 2013.
 * User: martin
 * Date: 3/13/13
 * Time: 11:57 AM
 */
public class BriteVerifyEmailValidator {
	private static final Log LOG = LogFactory.getLog(BriteVerifyEmailValidator.class);
	
    private static final String API_KEY = "ca976f2f-f823-43e4-92f5-44a33823d1dd";
    private static final String URL = "https://bpi.briteverify.com/emails.json";
    private static final String EMAIL_TOKEN = "address";
    private static final String API_TOKEN = "apikey";

    public static void main(String[] args) {
        BriteVerifyEmailValidator v = new BriteVerifyEmailValidator();
        try {
            LOG.info("" + v.isValid("jerkinjeepr@ymail.com"));
//            System.out.println("" + v.isValid("robertb_120@hotmail.com"));
//            System.out.println("" + v.isValid("danellxxd@aim.com"));
//            System.out.println("" + v.isValid("kylehays79@yahoo.com"));
//            System.out.println("" + v.isValid("reddsw@google.com"));
//            System.out.println("" + v.isValid("mtaallabouttwheels@yahoo.com"));
//            System.out.println("" + v.isValid("tlnelson1360@gmail.com"));

//            System.out.println("" + v.isValid("duplissec@gmail.com"));
//            System.out.println("" + v.isValid("alkaaah1@gmail.com"));
//            System.out.println("" + v.isValid("jeanfarm4@yahoo.com"));
//            System.out.println("" + v.isValid("angelica214@yahoo.com"));
//            System.out.println("" + v.isValid("azziesimpson@yahoo.com"));
//            System.out.println("" + v.isValid("theloniousnwk@yahoo.com"));
//            System.out.println("" + v.isValid("fatguy481@gmail.com"));
//            System.out.println("" + v.isValid("hallearll@yahoo.com"));
//            System.out.println("" + v.isValid("snowanglejay@yahoo.com"));
//            System.out.println("" + v.isValid("wowisk3wl@yahoo.com"));
//            System.out.println("" + v.isValid("rashae_12_1994@yahoo.com"));
//            System.out.println("" + v.isValid("kathyhollembeak1@yahoo.com"));
//            System.out.println("" + v.isValid("sakelady1964@gmail.com"));
//            System.out.println("" + v.isValid("labrats393730@yahoo.com"));
//            System.out.println("" + v.isValid("kdx7236nc@gmail.com"));
//            System.out.println("" + v.isValid("sherclitsmoore@yahoo.com"));
//            System.out.println("" + v.isValid("peace.love@yahoo.com"));
//            System.out.println("" + v.isValid("vannessaowens34@yahoo.com"));
//            System.out.println("" + v.isValid("robesssharon@gmail.com"));
//            System.out.println("" + v.isValid("ginaluciat8456@gmail.com"));
//            System.out.println("" + v.isValid("fdsafdgsa@gmail.com"));
//            System.out.println("" + v.isValid("donnabrazell853@yahoo.com"));
//            System.out.println("" + v.isValid("cathy1966126@gmail.com"));
//            System.out.println("" + v.isValid("kyliexo14@yahoo.com"));
//            System.out.println("" + v.isValid("Jackson5@yahoo.com"));
//            System.out.println("" + v.isValid("leahpasson@yahoo.com"));
//            System.out.println("" + v.isValid("marydeo48@yahoo.com"));
//            System.out.println("" + v.isValid("narp@yahoo.com"));
//            System.out.println("" + v.isValid("raymercer6412@ymail.com"));
//            System.out.println("" + v.isValid("cytnthiahorton94@yahoo.com"));
//            System.out.println("" + v.isValid("belcher1010@gmail.com"));
//            System.out.println("" + v.isValid("dakotabs.24@gmail.com"));
//            System.out.println("" + v.isValid("ericjamesmonroe@yahoo.com"));
//            System.out.println("" + v.isValid("chanekka83@yahoo.com"));
//            System.out.println("" + v.isValid("brndacrops@yahoo.com"));
//            System.out.println("" + v.isValid("lewisgail134@yahoo.com"));
//            System.out.println("" + v.isValid("enewerowski@yahoo.com"));
//            System.out.println("" + v.isValid("lynn5610@gmail.com"));
//            System.out.println("" + v.isValid("alholland@yahoo.com"));
//            System.out.println("" + v.isValid("micheemh4@gmail.com"));
//            System.out.println("" + v.isValid("KISHA.COFFEY@GMAIL.com"));
//            System.out.println("" + v.isValid("congtudaicaT_1508@yahoo.com"));
//            System.out.println("" + v.isValid("Sherrybhanji@yahoo.com"));
//            System.out.println("" + v.isValid("Grace12_1127@yahoo.com"));
//            System.out.println("" + v.isValid("jhrguthyt@gmail.com"));
//            System.out.println("" + v.isValid("lineout10@gmail.com"));
//            System.out.println("" + v.isValid("tracya01@yahoo.com"));
//            System.out.println("" + v.isValid("doron.dodd@gmail.com"));
//            System.out.println("" + v.isValid("untease21@yahoo.com"));
//            System.out.println("" + v.isValid("kre8384@yahoo.com"));
//            System.out.println("" + v.isValid("nooodledoodle91@gmail.com"));
//            System.out.println("" + v.isValid("toddanderson89@yahoo.com"));
//            System.out.println("" + v.isValid("Yoyo14@yahoo.com"));
//            System.out.println("" + v.isValid("Kerrymurphy1315@gmail.com"));
//            System.out.println("" + v.isValid("thomasau@yahoo.com"));
//            System.out.println("" + v.isValid("malorie_garibay@yahoo.com"));
//            System.out.println("" + v.isValid("collins.debbie75@yahoo.com"));
//            System.out.println("" + v.isValid("1234567890@yahoo.com"));
//            System.out.println("" + v.isValid("jeannieeasley2010@yahoo.com"));
//            System.out.println("" + v.isValid("sanchezmonica7608@yahoo.com"));
//            System.out.println("" + v.isValid("Deligirl85@yahoo.com"));
//            System.out.println("" + v.isValid("blakcgoldadizzle@yahoo.com"));
//            System.out.println("" + v.isValid("iioechandi@yahoo.com"));
//            System.out.println("" + v.isValid("apphk@yahoo.com"));
//            System.out.println("" + v.isValid("betsyknapp@yahoo.com"));
//            System.out.println("" + v.isValid("tbushmcduffie@yahoo.com"));
//            System.out.println("" + v.isValid("sliceb2000@yahoo.com"));
//            System.out.println("" + v.isValid("wardrachel@yahoo.com"));
//            System.out.println("" + v.isValid("joefefghali303@gmail.com"));
//            System.out.println("" + v.isValid("Criatallee1988@yahoo.com"));
//            System.out.println("" + v.isValid("aldjgjg@yahoo.com"));
//            System.out.println("" + v.isValid("tamekajohnsoon40@ymail.com"));
//            System.out.println("" + v.isValid("ramos.gerardo195562@yahoo.com"));
//            System.out.println("" + v.isValid("savdella@yahoo.com"));
//            System.out.println("" + v.isValid("ryanmsmith@yahoo.com"));
//            System.out.println("" + v.isValid("tammyfaner@ymail.com"));
//            System.out.println("" + v.isValid("djovana4ever@yahoo.com"));
//            System.out.println("" + v.isValid("miriamtelly@yahoo.com"));
//            System.out.println("" + v.isValid("JrenKtetjvg@yahoo.com"));
//            System.out.println("" + v.isValid("kattech20009@gmail.com"));
//            System.out.println("" + v.isValid("Rrosemarie76@yahoo.com"));
//            System.out.println("" + v.isValid("Daniellewilson@yahoo.com"));
//            System.out.println("" + v.isValid("marahalltammy72@yahoo.com"));
//            System.out.println("" + v.isValid("jjjggf@yahoo.com"));
//            System.out.println("" + v.isValid("AIRBUS971@LAVA.net"));
//            System.out.println("" + v.isValid("onesweetwvguy@yahoo.com"));
//            System.out.println("" + v.isValid("laura.myers116@yahoo.com"));
//            System.out.println("" + v.isValid("mernfarian@yahoo.com"));
//            System.out.println("" + v.isValid("Lakeishamatthews1143@yahoo.com"));
//            System.out.println("" + v.isValid("josejlatra25@yahoo.com"));
//            System.out.println("" + v.isValid("jodylaw@yahoo.com"));
//            System.out.println("" + v.isValid("ajdgooffy@ymail.com"));
//            System.out.println("" + v.isValid("JonJon@yahoo.com"));
//            System.out.println("" + v.isValid("corburnerice@yahoo.com"));
//            System.out.println("" + v.isValid("ett@yahoo.com"));
//            System.out.println("" + v.isValid("lauren.koloff@yahoo.com"));
//            System.out.println("" + v.isValid("ronpeterson67@yahoo.com"));
//            System.out.println("" + v.isValid("iaminhim23@gmail.com"));
//            System.out.println("" + v.isValid("jeweslart1@yahoo.com"));
//            System.out.println("" + v.isValid("tcwilliams5838@yahoo.com"));
        }


        catch (ValidationException e) {
            LOG.error(e);
        }
    }

    public static boolean isValid(String email) throws ValidationException {
        // https://bpi.briteverify.com/emails.json?address=martinholzner@gmail.com&apikey=ca976f2f-f823-43e4-92f5-44a33823d1dd

        // &verify_connected=true  --> very slow! (adds about 7 seconds per call

        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();

            HttpRequestRetryHandler myRetryHandler = new HttpRequestRetryHandler() {

                public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
                    if (executionCount >= 3) {
                        // Do not retry if over max retry count
                        return false;
                    }
                    if (exception instanceof NoHttpResponseException) {
                        // Retry if the server dropped connection on us
                        return true;
                    }
                    if (exception instanceof SSLHandshakeException) {
                        // Do not retry on SSL handshake exception
                        return false;
                    }
                    HttpRequest request = (HttpRequest) context.getAttribute(ExecutionContext.HTTP_REQUEST);
                    return !(request instanceof HttpEntityEnclosingRequest);
                }

            };

            httpClient.setHttpRequestRetryHandler(myRetryHandler);

            String resp = httpSend(httpClient, email);
            return resp != null && processResponse(email, resp);
        }
        catch (IOException e) {
            throw new ValidationException(e);
        }
    }

    private static boolean processResponse(String email, String response) {
/*
    example response: {"address":"martinholzner@gmail.com","account":"martinholzner","domain":"gmail.com","status":"valid","connected":null,"disposable":false,"role_address":false,"duration":10.026600634}

    address: the email that was passed
    account: the inbox or account parsed from the email
    domain: the domain parsed from the email
    status: the status of the given email address
    error: the error message if the email is invalid
    error_code: a code representation of error
    connected: whether or not a valid email is connected to active online networks
    disposable: is the email a temporary or "disposable" email address
    duration: the time it took to process your request
*/
        LOG.info("BriteVerify Response:" + response);

        String[] tokens = response.split(",");
        if (tokens != null && tokens.length > 0) {
            for (String t : tokens) {
                String t1 = t.replace("\"", "");
                String[] keyValue = t1.split(":");
                if (keyValue.length != 2) {
                    LOG.info("skipping invalid token:" + t);
                    continue;
                }

                if ("address".equals(keyValue[0])) {
                    if (!keyValue[1].equals(email)) {
                        return false;
                    }
                }
                else if ("status".equals(keyValue[0])) {
                    if ("invalid".equals(keyValue[1])) {
                        return false; // there is also valid and unknown
                    }
                }
                else if ("connected".equals(keyValue[0])) {
                    // TODO: doesn't seem to be filled in often
                }
                else if ("disposable".equals(keyValue[0])) {
                    if ("true".equals(keyValue[1])) {
                        return false;
                    }
                }
                else if ("role_address".equals(keyValue[0])) {
                    if ("true".equals(keyValue[1])) {
                        return false;
                    }
                }
                else if ("error_code".equals(keyValue[0])) {
                    //todo
                }
                else if ("error".equals(keyValue[0])) {
                    //todo
                }
            }
        }

        return true;
    }


    /*
   // response attributes:

   address: the email that was passed
   account: the inbox or account parsed from the email
   domain: the domain parsed from the email
   status: the status of the given email address
   error: the error message if the email is invalid
   error_code: a code representation of error
   connected: whether or not a valid email is connected to active online networks
   disposable: is the email a temporary or "disposable" email address
   duration: the time it took to process your request


    */

    //https://bpi.briteverify.com/emails.json?address=martinholzner@gmail.com&apikey=ca976f2f-f823-43e4-92f5-44a33823d1dd
    // response: {"address":"martinholzner@gmail.com","account":"martinholzner","domain":"gmail.com","status":"valid","connected":null,"disposable":false,"role_address":false,"duration":0.018429102}

    /*
    https://bpi.briteverify.com/emails.json?address=james@briteverify.com&apikey=your-api-key
{
  "address":"james@briteverify.com",
  "account":"james",
  "domain":"briteverify.com",
  "status":"valid",
  "connected":true,
  "disposable":false,
  "role_address":false,
  "duration":0.104516605
}


{
  "address":"james@yahoo.com",
  "account":"james",
  "domain":"yahoo.com",
  "status":"invalid",
  "error_code":"email_account_invalid",
  "error":"Email account invalid",
  "disposable":false,
  "role_address":false,
  "duration":0.141539548
}

     */

    /*
    response examples:

https://bpi.briteverify.com/emails.json?address=james@briteverify.com&verify_connected=true&apikey=your-api-key
{
  "address":"james@briteverify.com",
  "account":"james",
  "domain":"briteverify.com",
  "status":"valid",
  "connected":true,
  "disposable":false,
  "role_address":false,
  "duration":0.236341246
}


{
  "address":"james@veryhidden.com",
  "account":"james",
  "domain":"veryhidden.com",
  "status":"valid",
  "disposable":true
}


    {
  "address":"sales@briteverify.com",
  "account":"sales",
  "domain":"briteverify.com",
  "status":"valid",
  "role_address":true
}

     */

    //    private void httpSend(HttpClient httpClient, List<NameValuePair> formParams) throws IOException, NamingException, SQLException {
    private static String httpSend(HttpClient httpClient, String email) throws IOException {
        // https://bpi.briteverify.com/emails.json?address=martinholzner@gmail.com&apikey=ca976f2f-f823-43e4-92f5-44a33823d1dd
        // &verify_connected=true

//        UrlEncodedFormEntity requestEntity = new UrlEncodedFormEntity(formParams, "UTF-8");
        HttpGet httpGet = new HttpGet(URL + "?" + EMAIL_TOKEN + "=" + email + "&" + API_TOKEN + "=" + API_KEY);
//        HttpGet httpGet = new HttpGet(URL + "?" + EMAIL_TOKEN + "=" + email + "&" + API_TOKEN + "=" + API_KEY + "&verify_connected=true");

        // Execute the request
        HttpResponse response = httpClient.execute(httpGet);

        int httpStatus = response.getStatusLine().getStatusCode();

        // Get hold of the response entity and read out the response to clear the socket
        HttpEntity entity = response.getEntity();

        BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
        // read the remote message id, created for this new message (if one is returned)
        // and update the local message.external_id with it
        String line, resp = null;

        while ((line = reader.readLine()) != null) {
//            System.out.println("read:" + line + "\r\n");
            if (resp == null) {
                // store the first line as response
                resp = line.trim();
            }
        }
        reader.close();

//        httpclient.getConnectionManager().shutdown();

        return resp;
    }
}
