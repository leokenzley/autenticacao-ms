package datati.autenticacaoms.authenticate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authenticate")
public class AuthenticateController {
	@Autowired
	private KeycloakService keycloakService;
	
	@PostMapping("/token")
	public ResponseEntity<?> auth(@RequestBody Usuario usuario){
		try {
			return new ResponseEntity<String>(keycloakService.authenticate(usuario), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		}
		
	} 
	
}
