package no.bankid.oidc.web; /**
 * Created by kristofferskaret on 20.01.2017.
 */

import no.bankid.oidc.service.BankIdOauthClient;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/callback"}, loadOnStartup = 1)
public class CallBackServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        StringBuilder responseHtml = new StringBuilder();

        responseHtml.append("<html>")
                .append("<head></head><body>")
                .append("<h1>Callback</h1>")
                .append("<p>A callback has been made! With a code.</p>")
                .append("</body></html");

        String code = request.getParameter("code");

        BankIdOauthClient.endAuthentication(code);

        response.getOutputStream().print(responseHtml.toString());
    }
}