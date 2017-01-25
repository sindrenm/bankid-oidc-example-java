package no.bankid.oidc;

public class User {

    private String username;
    private final String accessToken;
    private final String idTokenPayload;

    public User(String username, String accessToken, String idTokenPayload) {
        this.username = username;
        this.accessToken = accessToken;
        this.idTokenPayload = idTokenPayload;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getIdTokenPayload() {
        return idTokenPayload;
    }

    public String getUsername() {
        return username;
    }
}
