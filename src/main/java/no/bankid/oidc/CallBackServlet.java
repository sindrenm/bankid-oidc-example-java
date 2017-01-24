package no.bankid.oidc; /**
 * Created by kristofferskaret on 20.01.2017.
 */

import no.bankid.oidc.BankIdOauthClient;
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

        String code = request.getParameter("code");

        User user = BankIdOauthClient.getInstance().endAuthentication(code);

        request.getSession().setAttribute("user", user);

        response.sendRedirect("/");
    }
}