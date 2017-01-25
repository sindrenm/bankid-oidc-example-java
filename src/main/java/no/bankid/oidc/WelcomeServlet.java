package no.bankid.oidc; /**
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

        User user = (User) request.getSession().getAttribute("user");

        StringBuilder responseHtml = new StringBuilder();

        if (user == null) {
            responseHtml.append("<html>")
                    .append("<head></head><body>")
                    .append("<h1>Welcome</h1>")
                    .append("<p>Click login to login</p>")
                    .append("<a href=\"/login\">Login</a>")
                    .append("</body></html");
        } else {

            String userInfo = BankIdOIDCClient.getInstance().getUserInfo(user);

            responseHtml.append("<html>")
                    .append("<head></head><body>")
                    .append("<h1>Welcome</h1>")
                    .append("<p>You are logged in.</p>")
                    .append("<h2>Access token</h2>")
                    .append(String.format("<p>%s</p>", user.getAccessToken()))
                    .append("<h2>Id token</h2>")
                    .append(String.format("<p>%s</p>", user.getIdTokenPayload()))
                    .append("<h2>User info</h2>")
                    .append(String.format("<p>%s</p>", userInfo))
                    .append("<a href=\"/logout\">Logout</a>")
                    .append("</body></html");
        }


        response.getOutputStream().print(responseHtml.toString());
    }
}