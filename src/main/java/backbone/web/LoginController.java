package backbone.web;

import backbone.LoginRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@RestController
public class LoginController {

    @RequestMapping(
            value = "/login",
            method = RequestMethod.POST
    )
    public ResponseEntity<Void> login(@RequestBody LoginRequest request) {

        if(request.getUsername() == null || request.getPassword() == null || request.getUsername().isBlank() || request.getPassword().isBlank()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if("toto".equals(request.getUsername()) && "toto".equals(request.getPassword())) {
            HttpHeaders responseHeaders = new HttpHeaders();
            //responseHeaders.set("Set-Cookie", "idd=a3fWa; Expires=Wed, 21 Oct 2015 07:28:00 CEST; Secure; HttpOnly");

            String expiry = ZonedDateTime.now().plus(10, ChronoUnit.MINUTES).format(DateTimeFormatter.RFC_1123_DATE_TIME);
            responseHeaders.set("Set-Cookie", "sid=ABCD; Expires=" + expiry + "; HttpOnly"); // TODO: Secure
            return ResponseEntity.ok().headers(responseHeaders).build();
        }
        else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
