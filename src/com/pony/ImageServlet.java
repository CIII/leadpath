package com.pony;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

/**
 * PonyLeads 2012.
 * User: martin
 * Date: 1/3/13
 * Time: 2:32 PM
 */
public class ImageServlet extends HttpServlet {
	private static final Log LOG = LogFactory.getLog(ImageServlet.class);

    private static final String IMAGE_PATH = "/var/www/images";
    private static final File IMAGE_FOLDER = new File(IMAGE_PATH);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //
        // if the image name is in the url path, render the image
        if (request.getPathInfo() != null && !request.getPathInfo().equals("")) {
            // it's one image
            //            PrintWriter out = response.getWriter();
            //            out.write("uri=" + request.getRequestURI());
            //            out.write("pathInfo=" + request.getPathInfo());
            //            out.write(new File(IMAGE_FOLDER, request.getRequestURI()).getPath());
            File file = new File(IMAGE_FOLDER, request.getPathInfo());
            String ext = request.getPathInfo().substring(request.getPathInfo().lastIndexOf(".") + 1);
            response.setContentType("image/" + ext);
            OutputStream out = response.getOutputStream();
            FileInputStream in = new FileInputStream(file);
            byte[] bytes = new byte[1024];
            int read;
            while ((read = in.read(bytes)) > 0) {
                out.write(bytes, 0, read);
            }
            in.close();
            out.flush();
        }
        else {
            // render file picker for upload and list of images (names) with a tag to view the image
            PrintWriter out = response.getWriter();
            // it's the list
            out.write(generateForm().toString());

            out.write(listImages());
        }
        response.setStatus(HttpServletResponse.SC_OK);
    }

    public static String listImages() {
        if (!IMAGE_FOLDER.exists()) {
            LOG.info("creating image folder [" + IMAGE_PATH + "]:" + IMAGE_FOLDER.mkdir());
        }

        StringBuilder img = new StringBuilder();
        img.append("<div id='imageList'><ul>");
        if (IMAGE_FOLDER.length() > 0) {
            for (File f : IMAGE_FOLDER.listFiles()) {
                if (!f.isDirectory()) {
                    img.append("<li class='imageLink'><a href='/images/").append(f.getName()).append("'>").append(f.getName()).append("</a></li>");
                }
            }
        }
        img.append("</ul></div>");

        return img.toString();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
        /*
        *Set the size threshold, above which content will be stored on disk.
        */
        fileItemFactory.setSizeThreshold(1 * 1024 * 1024); //1 MB
        /*
        * Set the temporary directory to store the uploaded files of size above threshold.
        */
        fileItemFactory.setRepository(IMAGE_FOLDER);

        ServletFileUpload uploadHandler = new ServletFileUpload(fileItemFactory);

        try {
            /*
            * Parse the request
            */
            List items = uploadHandler.parseRequest(request);
            Iterator itr = items.iterator();

            File file = null;

            while (itr.hasNext()) {
                FileItem item = (FileItem) itr.next();
                /*
                * Handle Form Fields.
                */
                if (!item.isFormField()) {
                    //Handle Uploaded files.
                    //                    PrintWriter out = response.getWriter();
                    //                    out.println("Field Name = " + item.getFieldName() +
                    //                                    ", File Name = " + item.getName() +
                    //                                    ", Content type = " + item.getContentType() +
                    //                                    ", File Size = " + item.getSize());
                    /*
                    * Write file to the ultimate location.
                    */
                    if (item.getName() != null && !item.getName().equals("") && item.getSize() > 0) {
                        file = new File(IMAGE_FOLDER, item.getName());
                        item.write(file);
                    }
                }
            }

            doGet(request, response);
        }
        catch (FileUploadException e) {
            LOG.error(e);
        }
        catch (Exception e) {
            LOG.error(e);
        }
    }

    private static StringBuilder generateForm() {
        StringBuilder html = new StringBuilder();


        html.append("<html><head><title>Upload Image</title></head><body>");
        html.append("<a href='/'>Home</a>");

        html.append("<div id='img_form'>");
        html.append("<form method='post' enctype='multipart/form-data'>");

        html.append("<div id='file_select'>");
        html.append("<label for='imageFile'>Select image file</label><input name='imageFile' id='imageFile' type='file'/>");
        html.append("</div>");

        html.append("<input type='submit' name='submit'/>");
        html.append("</form></div>").append("</body></html>");
        return html;
    }

    public static void main(String[] args) {
        LOG.debug(listImages());
    }
}
