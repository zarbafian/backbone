package backbone.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @RequestMapping(
            value = "/api/a",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<String> protectedEndpoint() {

        return new ResponseEntity<>("This is endpoint is protected", HttpStatus.OK);
    }

    @RequestMapping(
            value = "/b",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<String> publicEndpoint() {

        return new ResponseEntity<>("This is endpoint is public", HttpStatus.OK);
    }
}
