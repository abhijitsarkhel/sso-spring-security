package in.indigenous.sso.service;

import java.util.List;

import in.indigenous.sso.security.dto.SSOUser;
import in.indigenous.sso.security.dto.UserCredentialsDTO;
import in.indigenous.sso.security.dto.UserRole;

public interface UserService {
	
	void registerDomain(String domain);
	
	void registerSubDomain(String domain, String subDomain);
	
	void registerApp(String domain, String subDomain, String app);
	
	void createRole(UserRole role);
	
	void createUser(UserCredentialsDTO credentials);
	
	void enableUser(UserCredentialsDTO credentials);
	
	void disableUser(UserCredentialsDTO credentials);
	
	SSOUser authenticate(UserCredentialsDTO credentials);
	
	List<SSOUser> getAllUsersForDomain(String app);
	
}
