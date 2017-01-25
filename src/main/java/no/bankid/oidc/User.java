package no.bankid.oidc;

/**
 * Created by kristofferskaret on 24.01.2017.
 */
public class User {

    private final String accessToken;
    private final String idTokenPayload;

    public User(String accessToken, String idTokenPayload) {
        this.accessToken = accessToken;
        this.idTokenPayload = idTokenPayload;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getIdTokenPayload() {
        return idTokenPayload;
    }
}
