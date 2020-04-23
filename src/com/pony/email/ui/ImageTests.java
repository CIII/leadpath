package com.pony.email.ui;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * image data url encoding tests: create urls like data:image/png;base64,<data>
 * <p/>
 * ArbVentures 2013.
 * User: martin
 * Date: 7/24/14
 * Time: 5:39 PM
 */
public class ImageTests {
	private static final Log LOG = LogFactory.getLog(ImageTests.class);
	
    public static void main(String[] args) throws IOException {
        // encode binary to String
        String imageDataString = readImageToBase64String("/work/slots/public/images/reel_strip.png");
        LOG.info("base64 encoded: " + imageDataString);


        writeBase64UrlStringToFile("image/png", imageDataString, "/work/temp/reels_base64_converted.txt");

//        String imageDataString2 = readImageBase64String("/work/temp/reels_base64.txt");
//        System.out.println((imageDataString.equals(imageDataString2)) + ": imgString=" + imageDataString2);

        // decode back to binary
        writeBase64ImageStringToImageFile(imageDataString, "/work/temp/reels_base64_converted.png");
    }

    private static String readImageBase64String(String url) throws IOException {
        StringBuilder content = new StringBuilder();
        char[] chars = new char[1024];
        BufferedReader reader = new BufferedReader(new FileReader(url));
        int l = 0;
        while ((l = reader.read(chars)) > 0) {
            content.append(chars, 0, l);
        }
        reader.close();

        return content.toString();
    }

    private static void writeBase64ImageStringToImageFile(String imageDataString, String imgUrl) throws IOException {
        // Converting a Base64 String into Image byte array
        byte[] imageByteArray = Base64.decodeBase64(imageDataString);

        // Write a image byte array into file system
        FileOutputStream imageOutFile = new FileOutputStream(imgUrl);
        imageOutFile.write(imageByteArray);
        imageOutFile.close();
    }

    /**
     * write string format that can be used for inlined image
     * example: <img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAYAAACNbyblAAAAHElEQVQI12P4//8/w38GIAXDIBKE0DHxgljNBAAO9TXL0Y4OHwAAAABJRU5ErkJggg==" />
     *
     * @param imageDataString
     * @param fileUrl
     * @throws IOException
     */
    private static void writeBase64UrlStringToFile(String imgType, String imageDataString, String fileUrl) throws IOException {
        // Converting a Base64 String into Image byte array

        // Write a String into file system
        FileWriter writer = new FileWriter(fileUrl);
        if (imgType == null) {
            imgType = "image/png";
        }
        writer.write("data:" + imgType + ";base64," + imageDataString);
        writer.close();
    }

    private static String readImageToBase64String(String url) throws IOException {
        // read image
        File file = new File(url);

        // Reading a Image file from file system
        FileInputStream imageInFile = new FileInputStream(file);
        byte imageData[] = new byte[(int) file.length()];
        imageInFile.read(imageData);
        imageInFile.close();

        // Converting Image byte array into Base64 String
//        return Base64.encodeBase64URLSafeString(imageData);
        return Base64.encodeBase64String(imageData);
    }
}
