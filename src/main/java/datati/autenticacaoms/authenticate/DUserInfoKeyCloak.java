package datati.autenticacaoms.authenticate;

import java.io.Serializable;

public class DUserInfoKeyCloak implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sub;
	private Boolean email_verified;
	private String name;
	private String preferred_username;
	private String given_name;
	private String family_name;
	private String email;
	public String getSub() {
		return sub;
	}
	public void setSub(String sub) {
		this.sub = sub;
	}
	public Boolean getEmail_verified() {
		return email_verified;
	}
	public void setEmail_verified(Boolean email_verified) {
		this.email_verified = email_verified;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPreferred_username() {
		return preferred_username;
	}
	public void setPreferred_username(String preferred_username) {
		this.preferred_username = preferred_username;
	}
	public String getGiven_name() {
		return given_name;
	}
	public void setGiven_name(String given_name) {
		this.given_name = given_name;
	}
	public String getFamily_name() {
		return family_name;
	}
	public void setFamily_name(String family_name) {
		this.family_name = family_name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return "DUserInfo [sub=" + sub + ", email_verified=" + email_verified + ", name=" + name
				+ ", preferred_username=" + preferred_username + ", given_name=" + given_name + ", family_name="
				+ family_name + ", email=" + email + "]";
	}
	
	
}
