package no.bankid.oidc;

class Configuration {

    public static final String CONFIG_URL = "https://preprod.bankidapis.no/oidc/oauth/.well-known/openid-configuration";
    public static final String CALLBACK_URL = "http://localhost:8080/callback";

    /**
     * Client_id and password must be inserted here for the application to work.
     * <p>
     * Make contact with BankID Norge (developer@bankid.no) to retrieve the information needed.
     */
    public static final String CLIENT_ID = "<insert client_id>";
    public static final String CLIENT_PWD = "<insert client password>";
}
