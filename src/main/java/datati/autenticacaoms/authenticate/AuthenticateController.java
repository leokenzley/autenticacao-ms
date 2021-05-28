package datati.autenticacaoms.authenticate;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authenticate")
public class AuthenticateController {
	@Autowired
	private KeycloakService keycloakService;
	
	@PostMapping("/token")
	public ResponseEntity<?> auth(@RequestBody DUserToken usuario){
		try {
			return new ResponseEntity<String>(keycloakService.authenticate(usuario), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		}
		
	} 
	
	@RequestMapping(value = "/refresh-token", method = RequestMethod.POST,
	        consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, 
	        produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> refreshToken(DUserToken usuario){
		try {
			return new ResponseEntity<String>(keycloakService.refreshToken(usuario), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		}
		
	} 
	
	
	@RequestMapping(value = "/user-info", method = RequestMethod.POST,
	        consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, 
	        produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> userInfo(@RequestHeader(value="Authorization") String authorizationHeader){
		System.out.println(authorizationHeader);
		try {			
			return new ResponseEntity<String>(keycloakService.userInfo(authorizationHeader), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		}
		
	} 
	
	
	@RequestMapping(value = "/introspect", method = RequestMethod.POST,
	        consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, 
	        produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> introspect(DUserToken usuario){
		System.out.println(usuario);
		try {			
			return new ResponseEntity<String>(keycloakService.introspect(usuario), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		}
		
	} 
	
	
	
}
