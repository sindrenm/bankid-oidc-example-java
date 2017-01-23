package no.bankid.oidc.web; /**
 * Created by kristofferskaret on 20.01.2017.
 */

import no.bankid.oidc.service.BankIdOauthClient;
import org.json.JSONObject;

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

        JSONObject json = BankIdOauthClient.endAuthentication(code);

        String access_token = json.getString("access_token");
        String id_token = json.getString("id_token");

        System.out.println("access_token: " + access_token);
        System.out.println("id_token: " + id_token);

        response.getOutputStream().print(responseHtml.toString());
    }
}