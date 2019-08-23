package backbone.web;

import backbone.LoginRequest;
import backbone.entity.Account;
import backbone.security.AuthenticationManager;
import backbone.security.SessionManager;
import backbone.security.UserSession;
import backbone.security.UserToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@RestController
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private SessionManager sessionManager;

    @Value("${security.cookie.secure}")
    private boolean secure;

    @RequestMapping(
            value = "/api/me",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<UserToken> getPrincipal(@CookieValue(name = "${security.cookie.name}", required = false) String sessionId) {

        if(sessionId == null || sessionId.isBlank()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        UserToken userToken = sessionManager.getSession(sessionId);

        return new ResponseEntity<>(userToken, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/login",
            method = RequestMethod.POST
    )
    public ResponseEntity<UserToken> login(@RequestBody LoginRequest request) {

        if(request.getUsername() == null || request.getPassword() == null || request.getUsername().isBlank() || request.getPassword().isBlank()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Account entity = authenticationManager.authenticate(request.getUsername(), request.getPassword());

        if(entity != null) {

            UserSession userSession = sessionManager.createSession(entity);

            HttpHeaders responseHeaders = new HttpHeaders();
            String cookieValue = getCookie(userSession.getSessionId(), userSession.getUserToken().getExpiration());
            responseHeaders.set("Set-Cookie", cookieValue);

            return ResponseEntity.ok().headers(responseHeaders).body(userSession.getUserToken());
        }
        else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(
            value = "/logout",
            method = RequestMethod.POST
    )
    public ResponseEntity<Void> logout(@CookieValue(name = "${security.cookie.name}", required = false) String sessionId) {

        if(sessionId == null || sessionId.isBlank()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        sessionManager.invalidateSession(sessionId);

        HttpHeaders responseHeaders = new HttpHeaders();
        ZonedDateTime expiration = ZonedDateTime.of(1970, 1, 1, 0, 0, 0, 0, ZoneId.of("UTC"));
        String cookieValue = getCookie("", expiration);
        responseHeaders.set("Set-Cookie", cookieValue);

        return ResponseEntity.ok().headers(responseHeaders).build();
    }

    private String getCookie(String sid, ZonedDateTime expiration) {

        String expiry = expiration.format(DateTimeFormatter.RFC_1123_DATE_TIME);
        String value = "sid=" + sid + "; Expires=" + expiry + (secure ? "; Secure" : "") + "; HttpOnly";

        return value;
    }
}
