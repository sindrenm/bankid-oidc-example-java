package no.bankid.oidc.service;

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
                "https://preprod.bankidapis.no/oidc/oauth/authorize", "Postman", "localhost:8080/redirect", encoded(state), "somecorrelationnonce");

        return url;
    }

    public static String encoded(String s) {
        try {
            return encode(s, Charset.forName("UTF-8").name());
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }
    }
}
