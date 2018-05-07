package no.bankid.oidc;

import org.json.JSONObject;

public class User {

    private final String accessToken;
    private final JSONObject idTokenPayload;

    public User(String accessToken, JSONObject idTokenPayload) {
        this.accessToken = accessToken;
        this.idTokenPayload = idTokenPayload;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public JSONObject getIdTokenPayload() {
        return idTokenPayload;
    }

    public String getPreferredUsername() { return idTokenPayload.getString("preferred_username"); }

}
