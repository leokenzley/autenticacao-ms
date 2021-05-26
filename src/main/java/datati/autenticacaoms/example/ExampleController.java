package datati.autenticacaoms.example;


import javax.annotation.security.RolesAllowed;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class ExampleController {
    @RequestMapping(value = "/anonymous", method = RequestMethod.GET)
    public ResponseEntity<String> getAnonymous() {
        return ResponseEntity.ok("Hello Anonymous");
    }
    
    @RolesAllowed("user")
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseEntity<String> getUser() {
        return ResponseEntity.ok("Hello User");
    }
    
    @RolesAllowed("admin")
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public ResponseEntity<String> getAdmin() {
        return ResponseEntity.ok("Hello Admin");
    }
    
    @RolesAllowed({ "admin", "user" })
    @RequestMapping(value = "/all-user", method = RequestMethod.GET)
    public ResponseEntity<String> getAllUser() {
        return ResponseEntity.ok("Hello All User");
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<String> nada() {
        return ResponseEntity.ok("Hello nada!");
    }
}