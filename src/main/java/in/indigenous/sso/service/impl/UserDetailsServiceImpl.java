package in.indigenous.sso.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import in.indigenous.sso.model.DomainCredential;
import in.indigenous.sso.repository.DomainCredentialRepository;

@Service("ssoUserDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private DomainCredentialRepository domainCredentialRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		DomainCredential domainCredential = domainCredentialRepository.findByEmail(username);
		return null;
	}

}
