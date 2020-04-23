package com.pony.email.ui;

import com.pony.core.MediaType;
import com.pony.email.MessageTemplate;
import com.pony.models.CreativeModel;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.sql.SQLException;

/**
 * ArbVentures 2013.
 * User: martin
 * Date: 12/2/13
 * Time: 9:12 PM
 */
public class TemplateServlet extends HttpServlet {

// list, create, edit, delete, view

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // list, edit, view

        String[] args = req.getRequestURI().split("/");
        if (args.length < 2) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String cmd = null;
        if (args.length > 2) {
            cmd = args[2];
        }
        else {
            cmd = "list";
        }

        String id = null;

        if (args.length > 3) {
            id = args[3];
        }

        resp.setContentType(MediaType.HTML.toString());
        resp.setCharacterEncoding("utf8");
        Writer out = resp.getWriter();

        try {
            if ("list".equalsIgnoreCase(cmd)) {
                list(req, out);
            }
            else if ("view".equalsIgnoreCase(cmd)) {

                if (id == null) {
                    list(req, out);
                }
                else {
                    view(id, req, out);
                }
            }
            else if ("create".equalsIgnoreCase(cmd)) {
                create(req, out);
            }
            else if ("delete".equalsIgnoreCase(cmd)) {
                delete(id, req, out);
            }
            else if ("edit".equalsIgnoreCase(cmd)) {
                edit(id, req, out);
            }
        }
        catch (SQLException e) {
            throw new ServletException(e);
        }
        catch (NamingException e) {
            throw new ServletException(e);
        }

        resp.setStatus(HttpServletResponse.SC_OK);
    }

    private void edit(String id, HttpServletRequest req, Writer writer) throws NamingException, SQLException, IOException {
        // TODO:
        // if get -> view. -> render form
        StringBuilder html = new StringBuilder();

        // read the template and display it
        MessageTemplate template = CreativeModel.findTemplate(Long.valueOf(id));
        if (template != null) {

            if ("get".equalsIgnoreCase(req.getMethod())) {
                // render form
                html.append("<html><head><title>Create template</title></head><body>");
                html.append("<a href='/template/'>back</a>");
                html.append("<div id='template_form'>");
                html.append("<form method='post'>");

                html.append("<table>");
                html.append("<tr id='name-template'><td><label for='name'>Email Template Name</label></td></tr><tr><td><input type='text' id='name' name='name' value='").append(template.getName()).append("'>").append("</></td></tr>");
                html.append("<tr id='html-template'><td><label for='html'>Email Template (html)</label></td></tr><tr><td><textarea id='html' rows='40' cols='90' name='html'>").append(template.getHtml()).append("</textarea></td></tr>");
                html.append("<tr id='text-template'><td><label for='txt'>Email Template (text)</label></td></tr><tr><td><textarea id='txt' rows='40' cols='90' name='txt'>").append(template.getText()).append("</textarea></td></tr>");

                html.append("<tr><td><input type='submit' id='submitMe' name='templateSubmit' value='update'/></td></tr>");

                html.append("</table></form></div>");

                html.append("</body></html>");
            }
            else {
                // post -> update
                String name = req.getParameter("name");
                String htm = req.getParameter("html");
                String txt = req.getParameter("txt");

                if (CreativeModel.updateTemplate(template.getId(), name, htm, txt)) {
                    html.append("<html><head><title>New template</title></head><body>");
                    html.append("<span><a href='/template/view/").append(id).append("'>").append(id).append("</a></span>");
                    html.append("<br/><a href='/template/'>back</a>");
                    html.append("</body></html>");
                }
                else {
                    html.append("ERROR");
                }
            }
        }
        else {
            html.append("NOT FOUND");
        }

        writer.write(html.toString());


        // if post -> update


    }

    private void delete(String id, HttpServletRequest req, Writer out) {

    }

    private void view(String id, HttpServletRequest req, Writer writer) throws NamingException, SQLException, IOException {
        // render the details of the template

        StringBuilder html = new StringBuilder();

        // read the template and display it
        MessageTemplate template = CreativeModel.findTemplate(Long.valueOf(id));
        if (template != null) {
//            if (!template.getHtml().contains("<html")) {
//                html.append("<html>");
//            }

            html.append(template.getHtml());
            html.append("<br/><br/><a href='/template/'>List</a>");

        }
        else {
            html.append("NOT FOUND");
        }

        writer.write(html.toString());
    }

    private void create(HttpServletRequest req, Writer writer) throws IOException, NamingException, SQLException {

        if ("get".equalsIgnoreCase(req.getMethod())) {
            // render form
            StringBuilder html = new StringBuilder();
            html.append("<html><head><title>Create template</title></head><body>");
            html.append("<a href='/template/'>back</a>");
            html.append("<div id='template_form'>");
            html.append("<form method='post'>");

            html.append("<table>");
            html.append("<tr id='name-template'><td><label for='name'>Email Template Name</label></td></tr><tr><td><input type='text' id='name' name='name'>").append("</></td></tr>");
            html.append("<tr id='html-template'><td><label for='html'>Email Template (html)</label></td></tr><tr><td><textarea id='html' rows='40' cols='90' name='html'>").append("markup here").append("</textarea></td></tr>");
            html.append("<tr id='text-template'><td><label for='txt'>Email Template (text)</label></td></tr><tr><td><textarea id='txt' rows='40' cols='90' name='txt'>").append("markup here").append("</textarea></td></tr>");

            html.append("<tr><td><input type='submit' id='submitMe' name='templateSubmit' value='create'/></td></tr>");

            html.append("</table></form></div>");

            html.append("</body></html>");
            writer.write(html.toString());
        }
        else {
            StringBuilder html = new StringBuilder();

            String name = req.getParameter("name");
            String htm = req.getParameter("html");
            String txt = req.getParameter("txt");

            Long id = CreativeModel.createTemplate(name, htm, txt);

            html.append("<html><head><title>New template</title></head><body>");
            html.append("<span><a href='/template/view/").append(id.toString()).append("'>").append(id).append("</a></span>");
            html.append("<br/><a href='/template/'>back</a>");
            html.append("</body></html>");

            writer.write(html.toString());
        }
    }

    private void list(HttpServletRequest req, Writer writer) throws NamingException, SQLException, IOException {

        StringBuilder html = new StringBuilder();

        html.append("<html><head><title>Email Template Management</title></head><body>");
//        html.append("<a href='/template/'>List</a>");
        html.append("<div><a href='/template/create'>Create</a></div>");
        html.append("<div id='campaign_form'>");
//        html.append("<form method='post'>");

        html.append("<table>");

        for (MessageTemplate template : CreativeModel.findTemplates(false)) {
            html.append("<tr><td><a href='/template/view/").append(template.getId()).append("'>").append(template.getName()).append("</a></td>").append("<td><a href='/template/edit/").append(template.getId()).append("'>edit</a></td>").append("</tr>");
        }

        html.append("</table>");
//        html.append("<input type='submit' name='submit'/>");
//        html.append("</form>");
        html.append("</div></body></html>");

        writer.write(html.toString());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // create, delete

        String[] args = req.getRequestURI().split("/");
        if (args.length < 2) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String cmd = null;
        if (args.length > 2) {
            cmd = args[2];
        }
        else {
            cmd = "list";
        }

        String id = null;

        if (args.length > 3) {
            id = args[3];
        }

        resp.setContentType(MediaType.HTML.toString());
        resp.setCharacterEncoding("utf8");
        Writer out = resp.getWriter();

        try {
            if ("create".equalsIgnoreCase(cmd)) {
                create(req, out);
            }
            else if ("delete".equalsIgnoreCase(cmd)) {
                delete(id, req, out);
            }
            else if ("edit".equalsIgnoreCase(cmd)) {
                edit(id, req, out);
            }
        }
        catch (SQLException e) {
            throw new ServletException(e);
        }
        catch (NamingException e) {
            throw new ServletException(e);
        }

        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
