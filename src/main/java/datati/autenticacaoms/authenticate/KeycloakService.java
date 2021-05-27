package datati.autenticacaoms.authenticate;

import java.util.Arrays;

import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class KeycloakService {

    @Value("${keycloak.auth-server-url}")
    private String server_url;

    @Value("${keycloak.realm}")
    private String realm;
    
    
    @Value("${keycloack.authenticate.token.url}")
    private String apiAuthenticateTokenUrl;
    
    @Value("${keycloak.resource}")
    private String resource;
    
    
    @Value("${keycloak.credentials.secret}")
    private String secret;
    
    
    
    
    @Autowired
	RestTemplate restTemplate;

    /**
     * Get Access Token
     * @param usuario
     * @return
     */
    public String authenticate(Usuario usuario) {
    	ResponseEntity<String> response  = null;
    	try {
    		 restTemplate = new RestTemplate();
    		   MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
    		   body.add("client_id", resource);
    		   body.add("client_secret", secret);
    		   body.add("grant_type", "password");
    		   body.add("username", usuario.getUsername());
    		   body.add("password", usuario.getPassword());
    		   HttpHeaders headers = new HttpHeaders();
    		   headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    		   HttpEntity<?> entity = new HttpEntity<Object>(body, headers);
    		   response = restTemplate.exchange(
    				   apiAuthenticateTokenUrl, HttpMethod.POST, entity, String.class);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
    	System.out.println(response);
    	return response.getBody();
    }
    
    public Object[] createUser(User user){
        String message = new String();
        int statusId = 0;
         try {
             UsersResource usersResource = getUsersResource();
             UserRepresentation userRepresentation = new UserRepresentation();
             userRepresentation.setUsername(user.getUsername());
             userRepresentation.setEmail(user.getEmail());
             userRepresentation.setFirstName(user.getFirstName());
             userRepresentation.setLastName(user.getLastName());
             userRepresentation.setEnabled(true);

             Response result = usersResource.create(userRepresentation);
             statusId = result.getStatus();

             if(statusId == 201){
                 String path = result.getLocation().getPath();
                 String userId = path.substring(path.lastIndexOf("/") + 1);
                 CredentialRepresentation passwordCredential = new CredentialRepresentation();
                 passwordCredential.setTemporary(false);
                 passwordCredential.setType(CredentialRepresentation.PASSWORD);
                 passwordCredential.setValue(user.getPassword());
                 usersResource.get(userId).resetPassword(passwordCredential);

                 RealmResource realmResource = getRealmResource();
                 RoleRepresentation roleRepresentation = realmResource.roles().get("realm-user").toRepresentation();
                 realmResource.users().get(userId).roles().realmLevel().add(Arrays.asList(roleRepresentation));
                 message = ("usuario creado con éxito");
             }else if(statusId == 409){
                 message = ("ese usuario ya existe");
             }else{
                 message = ("error creando el usuario");
             }
         }catch (Exception e){
             e.printStackTrace();
         }

         return new Object[]{statusId, message};
    }

    private RealmResource getRealmResource(){
        Keycloak kc = KeycloakBuilder.builder().serverUrl(server_url).realm("master").username("admin")
                .password("admin").clientId("admin-cli").resteasyClient(new ResteasyClientBuilder().connectionPoolSize(10).build())
                .build();
        return kc.realm(realm);
    }

    private UsersResource getUsersResource(){
        RealmResource realmResource = getRealmResource();
        return realmResource.users();
    }
}
