package no.bankid.oidc.web; /**
 * Created by kristofferskaret on 20.01.2017.
 */

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/"}, loadOnStartup = 1)
public class WelcomeServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        StringBuilder responseHtml = new StringBuilder();

        responseHtml.append("<html>")
                .append("<head></head><body>")
                .append("<h1>Welcome</h1>")
                .append("<p>Click login to login</p>")
                .append("<a href=\"/login\">Login</a>")
                .append("</body></html");

        response.getOutputStream().print(responseHtml.toString());
    }
}