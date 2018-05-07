package no.bankid.oidc;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.List;

class JWTHandler {

    private final JWKSet publicKeys;

    public JWTHandler(String jwsKeysUri) {
        try {
            publicKeys = JWKSet.load(new URL(jwsKeysUri));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public JSONObject getPayload(String id_token) {
        try {
            List<JWK> keys = publicKeys.getKeys();

            JWSObject jwsObject = JWSObject.parse(id_token);
            boolean signatureIsOK = jwsObject.verify(new RSASSAVerifier((RSAKey) keys.get(0)));

            if (!signatureIsOK) {
                System.out.println("Signature in jwk could not be verified.");
                // TODO Should be handled
            }

            return new JSONObject(jwsObject.getPayload().toString());

        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }
}
