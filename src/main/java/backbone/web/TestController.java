package backbone.web;

import backbone.security.BearerToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@RestController
public class TestController {

    @RequestMapping(
            value = "/auth/a",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<BearerToken> protectedEndpoint() {

        return new ResponseEntity<>(new BearerToken("protege", LocalDateTime.now().toEpochSecond(ZoneOffset.of("+02")), "admin"), HttpStatus.OK);
    }

    @RequestMapping(
            value = "/b",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<BearerToken> publicEndpoint() {

        return new ResponseEntity<>(new BearerToken("public", LocalDateTime.now().toEpochSecond(ZoneOffset.of("+02")), "user"), HttpStatus.OK);
    }
}
