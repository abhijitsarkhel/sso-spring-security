package in.indigenous.sso.service.impl;

import org.springframework.data.authentication.UserCredentials;

import in.indigenous.sso.security.dto.UserDTO;
import in.indigenous.sso.security.dto.UserRole;
import in.indigenous.sso.service.UserService;

public class UserServiceImpl implements UserService {

	@Override
	public void registerDomain(String domain) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerSubDomain(String domain, String subDomain) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerApp(String domain, String subDomain, String app) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createRole(UserRole role) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createUser(UserCredentials credentials) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disableUser(UserCredentials credentials) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public UserDTO authenticate(UserCredentials credentials) {
		// TODO Auto-generated method stub
		return null;
	}

}
