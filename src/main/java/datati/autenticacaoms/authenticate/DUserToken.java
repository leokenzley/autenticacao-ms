package datati.autenticacaoms.authenticate;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DUserToken implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String grantType;
	private String clientId;
	private String clientSecret;
	private String token;
	private String refreshToken;
	private String resource;
	private String realm;
	private String username;
	private String password;
	
	
}
