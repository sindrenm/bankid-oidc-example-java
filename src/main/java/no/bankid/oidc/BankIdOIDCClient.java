package no.bankid.oidc;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.client.oauth2.OAuth2ClientSupport;
import org.json.JSONObject;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Feature;
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
public class BankIdOIDCClient {

    public static final String CONFIG_URL = "https://prototype.bankidnorge.no/bankid-oauth/oauth/.well-known/openid-configuration";
    public static final String CALLBACK_URL = "http://localhost:8080/callback";
    public static final String CLIENT_ID = "JavaClient";
    public static final String CLIENT_PWD = "1234";

    private final String authorizationEndpoint;
    private final String token_endpoint;
    private final String userinfo_endpoint;
    private final JWTHandler JWTHandler;

    private static BankIdOIDCClient bankIdOIDCClient;

    public static BankIdOIDCClient getInstance() {
        if (bankIdOIDCClient == null) {
            bankIdOIDCClient = new BankIdOIDCClient();
        }
        return bankIdOIDCClient;
    }

    private BankIdOIDCClient() {
        Client client = ClientBuilder.newClient();
        Response response = client.target(CONFIG_URL).request().get();
        JSONObject configuration = new JSONObject(response.readEntity(String.class));

        this.authorizationEndpoint = configuration.getString("authorization_endpoint");
        this.token_endpoint = configuration.getString("token_endpoint");
        this.userinfo_endpoint = configuration.getString("userinfo_endpoint");

        JWTHandler = new JWTHandler(configuration.getString("jwks_uri"));
    }

    public String createAuthenticationUrl() {
        String state = UUID.randomUUID().toString();

        return String.format("%s?client_id=%s&redirect_uri=%s&response_type=code&scope=openid&state=%s&nonce=%s",
                authorizationEndpoint, CLIENT_ID, encoded(CALLBACK_URL), encoded(state), "somecorrelationnonce");
    }

    public static String encoded(String s) {
        try {
            return encode(s, Charset.forName("UTF-8").name());
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }
    }

    public User endAuthentication(String code) {
        HttpAuthenticationFeature basicAuth = HttpAuthenticationFeature.
                basicBuilder()
                .nonPreemptive()
                .credentials(CLIENT_ID, CLIENT_PWD)
                .build();

        ClientConfig clientConfig = new ClientConfig();
        clientConfig.register(basicAuth);

        Client client = ClientBuilder.newClient(clientConfig);

        WebTarget target = client.target(token_endpoint);

        MultivaluedMap<String, String> formData = new MultivaluedHashMap<String, String>();
        formData.add("code", code);
        formData.add("grant_type", "authorization_code");

        Response response = target.request().post(Entity.form(formData));

        JSONObject json = new JSONObject(response.readEntity(String.class));

        String access_token = json.getString("access_token");
        String id_token = json.getString("id_token");

        String jwsPayload = JWTHandler.getPayload(id_token);
        return new User(access_token, jwsPayload);
    }

    public String getUserInfo(User user) {
        Client client = ClientBuilder.newClient();

        Feature feature = OAuth2ClientSupport.feature(user.getAccessToken());
        client.register(feature);

        Response response = client.target(userinfo_endpoint).request().get();

        return response.readEntity(String.class);
    }
}
