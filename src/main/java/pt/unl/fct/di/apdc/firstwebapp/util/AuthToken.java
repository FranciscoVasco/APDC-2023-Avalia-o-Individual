package pt.unl.fct.di.apdc.firstwebapp.util;

import java.security.SecureRandom;
import java.util.UUID;

public class AuthToken {
	
	public static final long EXPIRATION_TIME = 1000*60*60; //1h
	
	public String username;
	public String role;
	public String tokenID;
	public long validFrom;
	public long validTo;

	public AuthToken() {}

	public AuthToken(String username,String role) {
		this.role = role;
		this.username = username;
		this.tokenID = UUID.randomUUID().toString();
		this.validFrom = System.currentTimeMillis();
		this.validTo = this.validFrom + AuthToken.EXPIRATION_TIME;
	}

}
