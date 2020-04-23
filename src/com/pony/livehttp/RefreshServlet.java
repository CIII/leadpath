package com.pony.livehttp;

import com.pony.core.MediaType;
import com.pony.models.ClickRuleModel;
import com.pony.models.ClickSourceModel;
import com.pony.models.RedirectModel;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.Writer;
import java.sql.SQLException;

/**
 * This servlet renders a page containing an automatic refresh link.
 * This can be used to effectively cause a redirect, BUT this redirect is not bringing the referrer url forward, but instead
 * will inject this servers domain as the referrer domain.
 * It will handle the differences between IE and other browsers in an attempt to acomplish this for all browsers
 * <p/>
 * <p/>
 * PonyLeads 2012.
 * User: martin
 * Date: 11/28/12
 * Time: 12:04 PM
 */
public class RefreshServlet extends HttpServlet {
	private static final Log LOG = LogFactory.getLog(RefreshServlet.class);
	
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            // who sent the click?
            ClickSource clickSource = null;
            String src = req.getParameter(ClickSource.SRC_TOKEN);
            if (src != null) {
                clickSource = ClickSourceModel.find(Long.valueOf(src));
            }

            // they did not identify themselves or we don't know them
            if (clickSource == null) {
                resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return;
            }

            // find the target to direct the click to
            ClickTarget clickTarget = ClickRuleModel.findClickTargetForSource(clickSource);

            // now record the event
            Redirect redirect = Redirect.create(clickSource, clickTarget, req);
            Long redirectId = RedirectModel.create(redirect);

            // and redirect to the final destination
            String redirectUrl = "http://www.acquisition-sciences.com?arrival_id={redirect_id}";

            if (clickTarget != null && clickTarget.getDestinationUrl() != null) {
                redirectUrl = clickTarget.getDestinationUrl();
            }

            redirectUrl = redirectUrl.replace("{redirect_id}", redirectId.toString());

            resp.setContentType(MediaType.HTML.toString());
            resp.setCharacterEncoding("utf8");

            Writer out = resp.getWriter();

            out.write("<!DOCTYPE HTML PUBLIC '-//W3C//Dtd HTML 4.0 Transitional//EN'>'");
            out.write("<html>");
            out.write("<head>");
            out.write("<!-- <meta http-equiv='refresh' content='0;url=<%= @uuu %>' /> -->");
            out.write("<title> Welcome to the redirect-R </title>");
            out.write("<script type='text/javascript'>");
            out.write("function redir()");
            out.write("{");
            out.write("  var fireOnThis = document.getElementById('clickX');");
            out.write("  if (document.createEvent)");
            out.write("  {");
            out.write("    window.location.href = fireOnThis.href");
            out.write("  }");
            out.write("  else if (document.createEventObject)");
            out.write("  {");
            out.write("    fireOnThis.click();");
            out.write("  }");
            out.write("}");
            out.write("</script >");
            out.write("</head >");
            out.write("<body >");
            out.write("<a id = 'clickX' href = '");
            out.write(redirectUrl);
            out.write("' / >");
            out.write("<script type = 'text/javascript' >");
            out.write("  window.onLoad = redir();");
            out.write("</script >");
            out.write("</body >");
            out.write("</html >");

            out.flush();
            resp.setStatus(HttpServletResponse.SC_OK);
        }
        catch (SQLException e) {
            LOG.error(e);
            throw new IOException(e);
        }
        catch (NamingException e) {
            LOG.error(e);
            throw new IOException(e);
        }
        catch (NumberFormatException e) {
            LOG.error(e);
            throw new IOException(e);
        }
    }
}
