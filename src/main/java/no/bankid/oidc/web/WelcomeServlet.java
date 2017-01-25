package no.bankid.oidc.web;

import no.bankid.oidc.BankIdOIDCClient;
import no.bankid.oidc.User;
import org.json.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@WebServlet(urlPatterns = {"/"}, loadOnStartup = 1)
public class WelcomeServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        /**
         * Prøver å finne user på sesjonen. Hvis den ikke finnes presenteres lenke til login.
         *
         * Etter pålogging vil bruker redirrigeres tilbake hit med user på sesjon.
         */
        User user = (User) request.getSession().getAttribute("user");

        response.getWriter().append("<html>")
                .append("<head></head><body>")
                .append("<h1>Velkommen til OpenID Connect testapp i Java</h1>");

        if (user == null) {
            response.getWriter()
                    .append("<p>Du er ikke logget inn.</p>")
                    .append("<a href=\"/login\">Logg inn</a>");
        } else {

            JSONObject userInfo = BankIdOIDCClient.getInstance().getUserInfo(user);
            JSONObject idTokenPayload = user.getIdTokenPayload();

            response.getWriter()
                    .append(String.format("<p>Du er logget inn som</p><p>%s</p>", user.getUsername()))
                    .append("<h2>Access token</h2>")
                    .append(String.format("<p>%s</p>", user.getAccessToken()))
                    .append("<h2>Id token</h2>")
                    .append(prettifyJSON(idTokenPayload))
                    .append("<h2>User info</h2>")
                    .append(prettifyJSON(userInfo))
                    .append("<a href=\"/logout\">Logout</a>");
        }

        response.getWriter()
                .append("</body></html");
    }

    private String prettifyJSON(JSONObject json) {
        StringBuilder htmlString = new StringBuilder();
        htmlString.append("<p>{</p>");
        for(String key : json.keySet()) {
            htmlString.append(String.format("<p>&nbsp;&nbsp;&nbsp;&nbsp;<b>%s:</b> %s</p>", key, json.get(key)));
        }
        htmlString.append("<p>}</p>");
        return htmlString.toString();
    }
}