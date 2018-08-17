package no.bankid.oidc;

import org.json.JSONObject;

public class User {

    private final String accessToken;
    private final String idToken;
    private final JSONObject accessTokenPayload;
    private final JSONObject idTokenPayload;

    public User(String accessToken, JSONObject accessTokenPayload, String idToken, JSONObject idTokenPayload) {
        this.accessToken = accessToken;
        this.idToken = idToken;
        this.idTokenPayload = idTokenPayload;
        this.accessTokenPayload = accessTokenPayload;
    }

    public String getAccessToken() {
        return accessToken;
    }
    public String getIdToken() { return idToken; }
    public JSONObject getIdTokenPayload() { return idTokenPayload; }
    public JSONObject getAccessTokenPayload() { return accessTokenPayload; }

    public String getName() {
        try {
            return idTokenPayload.getString("name");
        } catch (Exception e) {
            return "No name (missing scope-access?)";
        }
   }

}
