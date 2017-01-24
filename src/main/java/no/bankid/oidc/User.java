package no.bankid.oidc;

/**
 * Created by kristofferskaret on 24.01.2017.
 */
public class User {

    private final String accessToken;
    private final String idToken;

    public User(String accessToken, String idToken) {
        this.accessToken = accessToken;
        this.idToken = idToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getIdToken() {
        return idToken;
    }
}
