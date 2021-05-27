package datati.autenticacaoms.authenticate;

import java.util.List;

import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticateToken{
	private String grantType;
	private String clientId;
	private String clientSecret;
	private String username;
	private String password;
	
	
}