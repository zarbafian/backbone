package backbone.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.util.Date;

public class JwtTest {

    static byte[] SECRET = { 124,-90,-14,-60,63,-125,-99,73,-6,-90,79,-50,78,-13,124,-100,107,-28,91,58,-121,-16,-68,38,-49,32,101,108,24,-99,-101,-1};
    static String TOKEN = "" +
            "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoidXNlciIsImlzcyI6ImJhY2tib25lIiwibG9naW4iOiJwb3VyaXlhIiwiZXhwIjoxNTY2NDI3NzQyfQ.oliAHJarFyblS8iuMIoYCr27g5Auo4udK2jQotlndsQ";

    public static void main(String[] args) {

        //create();

        validate();
    }

    private static void validate() {

        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("backbone")
                    .withClaim("login","pouriya")
                    .withClaim("role","user")
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(TOKEN);
        } catch (JWTVerificationException exception){
            //Invalid signature/claims
            exception.printStackTrace();
        }
    }

    private static void create() {

        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            String token = JWT.create()
                    .withIssuer("backbone")
                    .withClaim("login", "pouriya")
                    .withClaim("role", "user")
                    .withExpiresAt(Date.from(Instant.now().plus(30, ChronoUnit.SECONDS)))
                    .sign(algorithm);

            System.out.println("token: " + token);
        } catch (JWTCreationException exception){
            //Invalid Signing configuration / Couldn't convert Claims.
            exception.printStackTrace();
        }

    }
}
