package in.indigenous.sso.service;

import org.springframework.data.authentication.UserCredentials;

import in.indigenous.sso.security.dto.UserDTO;
import in.indigenous.sso.security.dto.UserRole;

public interface UserService {
	
	void registerDomain(String domain);
	
	void registerSubDomain(String domain, String subDomain);
	
	void registerApp(String domain, String subDomain, String app);
	
	void createRole(UserRole role);
	
	void createUser(UserCredentials credentials);
	
	void disableUser(UserCredentials credentials);
	
	UserDTO authenticate(UserCredentials credentials);
	
}
