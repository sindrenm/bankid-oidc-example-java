# BankID Test Application for BankID Norge AS
This is a Java based test application for authenticating a user with BankID OpenID Connect Server (BID OIDC) developed for BankID Norge AS.
When the user is authenticated you will see the result from a call to UserInfo RESTful service and the contents of the id_token which was returned from BID OIDC.

## Configuration
To be able to run the application you will need to edit the client_id and client_password in the source code.
Contact BankID Norge (developer@bankid.no) to retrieve the appropriate settings.

```
public class ClientDefinition {

    /**
     * client_id og passord må legges inn her for at applikasjonen skal fungere.
     * Ta kontakt med BankID Norge (developer@kantega.no) for å ta i bruk eksempelet.
     */
    public static final String CLIENT_ID = "<insert client_id>";
    public static final String CLIENT_PWD = "<insert client password>";
}
```

## Build and run
With maven and jetty
```
mvn clean install
mvn jetty:run
```

## Dependencies
This example application uses the following libraries

**javax.servlet-api** for web gui

**jersey-client** used in all integration

**oauth2-client** oauth2 support in jersey

**org.json.json** for json parsing

**nimbus-jose-jwt** for handling the json web token

**jetty-maven-plugin** for running the application locally



