package com.pony.core;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.net.QuotedPrintableCodec;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.mail.MessagingException;
import java.io.IOException;

/**
 * PonyLeads 2012.
 * User: martin
 * Date: 1/16/13
 * Time: 3:34 PM
 */
public class Encoding {
	private static final Log LOG = LogFactory.getLog(Encoding.class);

    public static void main(String[] args) throws IOException, MessagingException, DecoderException {
        String subject = "&#9737; Hello @@fname@@: check this out. &#9788;";
//        String subject2 = "=?utf-8?Q?=e2=98=9e Hello @@fname@@: check this out.";
        String subject3 = "=e2=98=9e Hello @@fname@@ =e2=98=89: check this out. (=e2=98=bc)[=e2=98=ba]";

//        String subject4 = "=?UTF-8?B?4piF4piFVG9wLXJhdGVkIHNob2VzIGZvciBzcHJpbmcgYW5kIGJl?=
// =?UTF-8?B?eW9uZOKYheKYhQ==?=";
        String subject4 = "4piF4piFVG9wLXJhdGVkIHNob2VzIGZvciBzcHJpbmcgYW5kIGJl";
        String sub2 = "4piF4piF";
        String sub3 = "B?4piF4piF";

        String sub = "Top-rated shoes for spring and beyond";

//        System.out.println("" + MimeUtility.encodeText(subject, "utf8", "quoted-printable"));
//        System.out.println("" + MimeUtility.encodeText(subject));
//
//        System.out.println("" + MimeUtility.encodeWord(subject, "utf8", "quoted-printable"));
//        System.out.println("" + MimeUtility.encodeWord(subject));
//
////        byte[] bytes = new byte[1024];
//
//        ByteArrayOutputStream bout = new ByteArrayOutputStream();
//
//        OutputStream out = MimeUtility.encode(bout, "quoted-printable");
//        out.write(subject.getBytes("iso-8859-1"));
//
//        byte[] bytes = bout.toByteArray();
//        String s = new String(bytes, "iso-8859-1");
//        System.out.println(s);
//
        QuotedPrintableCodec encoder = new QuotedPrintableCodec();
//
//        bytes=encoder.encode(subject.getBytes("iso-8859-1"));
//        System.out.println(new String(bytes));
//

//        String encoded = "=E2=99=A5";
        //String encoded = "=e2=98=83";
        String encoded = "=e2=98=9e";
//        =e2=98=89
//        =e2=98=bc
//        String encoded = "";
        BASE64Encoder enc = new BASE64Encoder();
        BASE64Decoder dec = new BASE64Decoder();
        LOG.debug(enc.encode(sub.getBytes()));
        LOG.debug(dec.decodeBuffer(sub2));
        LOG.debug(dec.decodeBuffer(sub3));

//        System.out.println(dec.decodeBuffer(subject4));

//        System.out.println("(" + encoder.decode(subject4)+ ")");
    }


}
