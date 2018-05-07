package no.bankid.oidc;

class Configuration {

    public static final String CONFIG_URL = "https://oidc-current.bankidapis.no/auth/realms/current/.well-known/openid-configuration";
    public static final String CALLBACK_URL = "https://localhost:8443/callback";

    public static final String SCOPE = "openid profile";

    /**
     * Client_id and secret must be inserted here for the application to work.
     * <p>
     * See https://confluence.bankidnorge.no/confluence/pdoidcl/release-notes/provisioning for details on how to receive this.
     */
    public static final String CLIENT_ID = "<insert client_id>";
    public static final String CLIENT_SECRET = "<insert client password>";
}
