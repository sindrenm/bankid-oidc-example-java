package no.bankid.oidc.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
}
