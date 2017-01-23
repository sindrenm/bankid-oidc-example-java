package no.bankid.oidc.service;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.json.JSONObject;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.UUID;

import static java.net.URLEncoder.encode;

/**
 * Created by kristofferskaret on 20.01.2017.
 */
public class BankIdOauthClient {

    public String startAuthentication() {
        String state = UUID.randomUUID().toString();

        final String url = String.format("%s?client_id=%s&redirect_uri=%s&response_type=code&scope=openid&state=%s&nonce=%s",
                "https://prototype.bankidnorge.no/bankid-oauth/oauth/authorize", "JavaClient", encoded("http://localhost:8080/callback"), encoded(state), "somecorrelationnonce");

        return url;
    }

    public static String encoded(String s) {
        try {
            return encode(s, Charset.forName("UTF-8").name());
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }
    }

    public static JSONObject endAuthentication(String code) {
        HttpAuthenticationFeature basicAuth = HttpAuthenticationFeature.
                basicBuilder()
                .nonPreemptive()
                .credentials("JavaClient", "1234")
                .build();

        ClientConfig clientConfig = new ClientConfig();
        clientConfig.register(basicAuth);

        Client client = ClientBuilder.newClient(clientConfig);

        WebTarget target = client.target("https://prototype.bankidnorge.no").path("bankid-oauth/oauth/token");

        MultivaluedMap<String, String> formData = new MultivaluedHashMap<String, String>();
        formData.add("code", code);
        formData.add("grant_type", "authorization_code");

        Response response = target.request().post(Entity.form(formData));

        JSONObject json = new JSONObject(response.readEntity(String.class));

        return json;
    }
}
