package in.indigenous.sso.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.authentication.UserCredentials;

import in.indigenous.sso.model.Domain;
import in.indigenous.sso.model.SubDomain;
import in.indigenous.sso.repository.DomainRepository;
import in.indigenous.sso.repository.SubDomainRepository;
import in.indigenous.sso.security.dto.UserDTO;
import in.indigenous.sso.security.dto.UserRole;
import in.indigenous.sso.service.UserService;

public class UserServiceImpl implements UserService {
	
	@Autowired
	private DomainRepository domainRepository;
	
	@Autowired
	private SubDomainRepository subDomainRepository;

	@Override
	public void registerDomain(String domain) {
		Domain dom = domainRepository.findByName(domain);
		if(dom == null) {
			// TODO - throw error
		} else {
			dom = new Domain();
			dom.setName(domain);
			domainRepository.save(dom);
		}
	}

	@Override
	public void registerSubDomain(String domain, String subDomain) {
		Domain dom = domainRepository.findByName(domain);
		if(dom == null) {
			// TODO - throw error
		} else {
			SubDomain subDom = subDomainRepository.findByDomainAndName(dom, subDomain);
			if(subDom == null) {
				// TODO - throw error
			} else {
				subDom = new SubDomain();
				subDom.setDomain(dom);
				subDom.setName(subDomain);
				subDomainRepository.save(subDom);
			}
		}
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
