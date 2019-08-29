package backbone.web;

import backbone.LoginRequest;
import backbone.entity.Account;
import backbone.security.AuthenticationManager;
import backbone.security.SessionManager;
import backbone.security.UserToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private SessionManager sessionManager;

    @RequestMapping(
            value = "/api/me",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<UserToken> getPrincipal(@CookieValue(name = "${security.header.name}", required = false) String token) {

        if(token == null || token.isBlank()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        UserToken userToken = sessionManager.getSession(token);

        if(userToken == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

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

            UserToken userToken = sessionManager.createSession(entity);

            return new ResponseEntity(userToken, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(
            value = "/logout",
            method = RequestMethod.POST
    )
    public ResponseEntity<Void> logout(@RequestHeader(name = "${security.header.name}", required = false) String token) {

        if(token == null || token.isBlank()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        sessionManager.invalidateSession(token);

        return new ResponseEntity(HttpStatus.OK);
    }
}
