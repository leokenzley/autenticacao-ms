package datati.autenticacaoms.authenticate;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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
    
    
    @Value("${keycloack.url}")
    private String keyCloackUrl;
    
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
    public String authenticate(DUserToken usuario) {
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
    		   System.out.println(keyCloackUrl+"/token");
    		   response = restTemplate.exchange(
    				   keyCloackUrl+"/token", HttpMethod.POST, entity, String.class);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
    	return response.getBody();
    }
    
    
	public String refreshToken(DUserToken usuario) {
		ResponseEntity<String> response  = null;
    	try {
    		 restTemplate = new RestTemplate();
    		   MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
    		   body.add("client_id", resource);
    		   body.add("client_secret", secret);
    		   body.add("grant_type", "refresh_token");
    		   body.add("refresh_token", usuario.getRefreshToken());
    		   HttpHeaders headers = new HttpHeaders();
    		   headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    		   HttpEntity<?> entity = new HttpEntity<Object>(body, headers);
    		   response = restTemplate.exchange(
    				   keyCloackUrl+"/token", HttpMethod.POST, entity, String.class);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
    	return response.getBody();
	}
	
	public DUserInfo userInfo(String token) {
		ResponseEntity<DUserInfoKeyCloak> response  = null;
    	try {
    		 restTemplate = new RestTemplate();
    		   MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
    		   HttpHeaders headers = new HttpHeaders();
    		   headers.set("Authorization", token);
    		   headers.set("Content-Type", "application/x-www-form-urlencoded");
    		   HttpEntity<?> entity = new HttpEntity<Object>(body, headers);
    		   System.out.println(keyCloackUrl+"/userinfo");
    		   response = restTemplate.exchange(
    				   keyCloackUrl+"/userinfo", HttpMethod.POST, entity, DUserInfoKeyCloak.class);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
    	
    	return parseUserInfo(response.getBody());
	}
	
	private DUserInfo parseUserInfo(DUserInfoKeyCloak userInfoKeycloak) {
		DUserInfo userInfo = new DUserInfo();
		userInfo.setId(userInfoKeycloak.getSub());
		userInfo.setName(userInfoKeycloak.getName());
		userInfo.setUserName(userInfoKeycloak.getPreferred_username());
		userInfo.setEmail(userInfoKeycloak.getEmail());
		return userInfo;
	}


	public String introspect(DUserToken usuario) {
		ResponseEntity<String> response  = null;
    	try {
    		 restTemplate = new RestTemplate();
    		   MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
    		   HttpHeaders headers = new HttpHeaders();
    		   headers.set("Content-Type", "application/x-www-form-urlencoded");
    		   body.add("username", usuario.getUsername());
    		   body.add("password", usuario.getPassword());
    		   body.add("token", usuario.getToken());
    		   body.add("grant_type", "password");
    		   body.add("client_id", resource);
    		   body.add("client_secret", secret);
    		   HttpEntity<?> entity = new HttpEntity<Object>(body, headers);
    		   System.out.println(keyCloackUrl+"/token/introspect");
    		   response = restTemplate.exchange(
    				   keyCloackUrl+"/token/introspect", HttpMethod.POST, entity, String.class);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
    	System.out.println(response);
    	return response.getBody();
	}
	
	
	
	public String logout(DUserToken usuario, String authorizationHeader) {
		ResponseEntity<String> response  = null;
    	try {
    		 restTemplate = new RestTemplate();
    		   MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
    		   HttpHeaders headers = new HttpHeaders();
    		   headers.set("Authorization", authorizationHeader);
    		   headers.set("Content-Type", "application/x-www-form-urlencoded");
    		   body.add("refresh_token", usuario.getRefreshToken());
    		   body.add("redirect_uri", usuario.getRedirectURI());
    		  
    		  
    		   body.add("client_id", resource);
    		   body.add("client_secret", secret);
    		   HttpEntity<?> entity = new HttpEntity<Object>(body, headers);
    		   System.out.println(keyCloackUrl+"/logout");
    		   response = restTemplate.exchange(
    				   keyCloackUrl+"/logout", HttpMethod.POST, entity, String.class);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
    	System.out.println(response);
    	return response.getBody();
	}
	
//    
//    public Object[] createUser(User user){
//        String message = new String();
//        int statusId = 0;
//         try {
//             UsersResource usersResource = getUsersResource();
//             UserRepresentation userRepresentation = new UserRepresentation();
//             userRepresentation.setUsername(user.getUsername());
//             userRepresentation.setEmail(user.getEmail());
//             userRepresentation.setFirstName(user.getFirstName());
//             userRepresentation.setLastName(user.getLastName());
//             userRepresentation.setEnabled(true);
//
//             Response result = usersResource.create(userRepresentation);
//             statusId = result.getStatus();
//
//             if(statusId == 201){
//                 String path = result.getLocation().getPath();
//                 String userId = path.substring(path.lastIndexOf("/") + 1);
//                 CredentialRepresentation passwordCredential = new CredentialRepresentation();
//                 passwordCredential.setTemporary(false);
//                 passwordCredential.setType(CredentialRepresentation.PASSWORD);
//                 passwordCredential.setValue(user.getPassword());
//                 usersResource.get(userId).resetPassword(passwordCredential);
//
//                 RealmResource realmResource = getRealmResource();
//                 RoleRepresentation roleRepresentation = realmResource.roles().get("realm-user").toRepresentation();
//                 realmResource.users().get(userId).roles().realmLevel().add(Arrays.asList(roleRepresentation));
//                 message = ("usuario creado con éxito");
//             }else if(statusId == 409){
//                 message = ("ese usuario ya existe");
//             }else{
//                 message = ("error creando el usuario");
//             }
//         }catch (Exception e){
//             e.printStackTrace();
//         }
//
//         return new Object[]{statusId, message};
//    }




    private RealmResource getRealmResource(){
        Keycloak kc = KeycloakBuilder.builder().serverUrl(server_url).realm(realm).username("admin")
                .password("admin").clientId(resource).resteasyClient(new ResteasyClientBuilder().connectionPoolSize(10).build())
                .build();
        return kc.realm(realm);
    }

    public UsersResource getUsersResource(){
        RealmResource realmResource = getRealmResource();
        return realmResource.users();
    }
}
