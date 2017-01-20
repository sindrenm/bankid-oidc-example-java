package no.bankid.oidc.web; /**
 * Created by kristofferskaret on 20.01.2017.
 */

import no.bankid.oidc.service.BankIdOauthClient;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/login"}, loadOnStartup = 1)
public class LoginServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        StringBuilder responseHtml = new StringBuilder();

        String redirectUrl = new BankIdOauthClient().startAuthentication();

        response.sendRedirect(redirectUrl);
    }
}