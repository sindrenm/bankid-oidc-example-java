package no.bankid.oidc;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.json.JSONArray;
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

    public static final String CONFIG_URL = "https://prototype.bankidnorge.no/bankid-oauth/oauth/.well-known/openid-configuration";

    private final String authorizationEndpoint;
    private final String issuer;
    private final String token_endpoint;
    private final String userinfo_endpoint;

    private static BankIdOauthClient bankIdOauthClient;
    private final String jwkKeyType;
    private final String jwkKey;

    public static BankIdOauthClient getInstance() {
        if (bankIdOauthClient == null) {
            bankIdOauthClient = new BankIdOauthClient();
        }
        return bankIdOauthClient;
    }

    private BankIdOauthClient() {
        JSONObject configuration = getJsonResponse(CONFIG_URL);

        this.authorizationEndpoint = configuration.getString("authorization_endpoint");
        this.issuer = configuration.getString("issuer");
        this.token_endpoint = configuration.getString("token_endpoint");
        this.userinfo_endpoint = configuration.getString("userinfo_endpoint");

        String jwks_uri = configuration.getString("jwks_uri");

        JSONObject jwksConfig = getJsonResponse(jwks_uri);

        JSONArray keys = jwksConfig.getJSONArray("keys");
        JSONObject key = keys.getJSONObject(0);
        this.jwkKeyType = key.getString("kty");
        this.jwkKey = key.getString("n");
    }

    private JSONObject getJsonResponse(String url) {
        Client client = ClientBuilder.newClient();
        Response response = client.target(url).request().get();
        return new JSONObject(response.readEntity(String.class));
    }


    public String createAuthenticationUrl() {
        String state = UUID.randomUUID().toString();

        return String.format("%s?client_id=%s&redirect_uri=%s&response_type=code&scope=openid&state=%s&nonce=%s",
                authorizationEndpoint, "JavaClient", encoded("http://localhost:8080/callback"), encoded(state), "somecorrelationnonce");
    }

    public static String encoded(String s) {
        try {
            return encode(s, Charset.forName("UTF-8").name());
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }
    }

    public JSONObject endAuthentication(String code) {
        HttpAuthenticationFeature basicAuth = HttpAuthenticationFeature.
                basicBuilder()
                .nonPreemptive()
                .credentials("JavaClient", "1234")
                .build();

        ClientConfig clientConfig = new ClientConfig();
        clientConfig.register(basicAuth);

        Client client = ClientBuilder.newClient(clientConfig);

        WebTarget target = client.target(token_endpoint);

        MultivaluedMap<String, String> formData = new MultivaluedHashMap<String, String>();
        formData.add("code", code);
        formData.add("grant_type", "authorization_code");

        Response response = target.request().post(Entity.form(formData));

        return new JSONObject(response.readEntity(String.class));
    }
}
