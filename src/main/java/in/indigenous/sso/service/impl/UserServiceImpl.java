package in.indigenous.sso.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import in.indigenous.sso.model.Application;
import in.indigenous.sso.model.ApplicationCredential;
import in.indigenous.sso.model.ApplicationRole;
import in.indigenous.sso.model.ApplicationUser;
import in.indigenous.sso.model.Domain;
import in.indigenous.sso.model.DomainCredential;
import in.indigenous.sso.model.DomainRole;
import in.indigenous.sso.model.DomainUser;
import in.indigenous.sso.model.SSOEntity;
import in.indigenous.sso.model.SubDomain;
import in.indigenous.sso.repository.ApplicationRepository;
import in.indigenous.sso.repository.ApplicationRoleRepository;
import in.indigenous.sso.repository.ApplicationUserRepository;
import in.indigenous.sso.repository.DomainCredentialRepository;
import in.indigenous.sso.repository.DomainRepository;
import in.indigenous.sso.repository.DomainRoleRepository;
import in.indigenous.sso.repository.DomainUserRepository;
import in.indigenous.sso.repository.SubDomainRepository;
import in.indigenous.sso.security.crypto.EncoderDecoder;
import in.indigenous.sso.security.dto.UserCredentialsDTO;
import in.indigenous.sso.security.dto.UserDTO;
import in.indigenous.sso.security.dto.UserRole;
import in.indigenous.sso.service.UserService;

public class UserServiceImpl implements UserService {

	@Autowired
	private DomainRepository domainRepository;

	@Autowired
	private SubDomainRepository subDomainRepository;

	@Autowired
	private ApplicationRepository applicationRepository;

	@Autowired
	private DomainUserRepository domainUserRepository;

	@Autowired
	private DomainCredentialRepository domainCredentialRepository;

	@Autowired
	private DomainRoleRepository domainRoleRepository;

	@Autowired
	private ApplicationRoleRepository applicationRoleRepository;

	@Autowired
	private ApplicationUserRepository applicationUserRepository;
	
	@Autowired
	private EncoderDecoder passwordEncoder;

	@Override
	public void registerDomain(String domain) {
		Domain dom = domainRepository.findByName(domain);
		if (dom != null) {
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
		if (dom != null) {
			// TODO - throw error
		} else {
			SubDomain subDom = subDomainRepository.findByDomainAndName(dom, subDomain);
			if (subDom != null) {
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
		Domain dom = domainRepository.findByName(domain);
		if (dom != null) {
			// TODO - throw error
		} else {
			SubDomain subDom = subDomainRepository.findByDomainAndName(dom, subDomain);
			if (subDom != null) {
				// TODO - throw error
			} else {
				Application application = applicationRepository.findBySubDomainAndName(subDom, app);
				if (application != null) {
					// TODO - throw error
				} else {
					application = new Application();
					application.setName(app);
					application.setSubDomain(subDom);
					applicationRepository.save(application);
				}
			}
		}
	}

	@Override
	public void createRole(UserRole role) {
		String domainName = role.getDomain();
		if (StringUtils.isEmpty(domainName)) {
			// TODO throw error
		}
		String subDomainName = role.getSubDomain();
		String appName = role.getApplication();
		if ((StringUtils.isEmpty(appName) && StringUtils.isNotEmpty(appName))
				|| (StringUtils.isNotEmpty(subDomainName) && StringUtils.isEmpty(appName))) {
			// TODO throw error
		}
		if (StringUtils.isEmpty(subDomainName)) {
			createDomainRole(domainName, role.getRole(), role.getAppRoles());
		} else {
			createAppRole(domainName, subDomainName, appName, role.getRole());
		}
	}

	@Override
	public void createUser(UserCredentialsDTO credentials) {
		String domainName = credentials.getDomain();
		String subDomainName = credentials.getSubDomain();
		String applicationName = credentials.getApplication();
		String userName = credentials.getEmail();
		String password = credentials.getPassword();
		List<String> roles = credentials.getRoles();
		if (StringUtils.isEmpty(domainName)) {
			// TODO - throw error
		}
		if ((StringUtils.isEmpty(subDomainName) && StringUtils.isNotEmpty(applicationName))
				|| (StringUtils.isNotEmpty(subDomainName) && StringUtils.isEmpty(applicationName))) {
			// TODO - throw error
		}

		if (StringUtils.isEmpty(subDomainName)) {
			createDomainUser(domainName, userName, password, roles);
		} else {
			createApplicationUser(domainName, subDomainName, applicationName, userName, password, roles);
		}
	}

	@Override
	public void enableUser(UserCredentialsDTO credentials) {
		String domainName = credentials.getDomain();
		String subDomainName = credentials.getSubDomain();
		String applicationName = credentials.getApplication();
		String userName = credentials.getEmail();
		if (StringUtils.isEmpty(domainName)) {
			// TODO - throw error
		}
		if ((StringUtils.isEmpty(subDomainName) && StringUtils.isNotEmpty(applicationName))
				|| (StringUtils.isNotEmpty(subDomainName) && StringUtils.isEmpty(applicationName))) {
			// TODO - throw error
		}
		if (StringUtils.isEmpty(subDomainName)) {
			Domain domain = domainRepository.findByName(domainName);
			if(domain == null) {
				//TODO throw error
			}
			
			DomainCredential userCredential = domainCredentialRepository.findByDomainAndEmail(domain, userName);
			if(userCredential == null) {
				// TODO throw error
			}
			DomainUser user = userCredential.getDomainUser();
			user.setEnabled(true);
			domainUserRepository.save(user);
		} else {
			Domain domain = domainRepository.findByName(domainName);
			if(domain == null) {
				//TODO throw error
			}
			SubDomain subDomain = subDomainRepository.findByDomainAndName(domain, subDomainName);
			if(subDomain == null) {
				//TODO throw error
			}
			Application application = applicationRepository.findBySubDomainAndName(subDomain, applicationName);
			if(application == null) {
				//TODO throw error
			}
			DomainCredential userCredential = domainCredentialRepository.findByDomainAndEmail(domain, userName);
			if(userCredential == null) {
				// TODO throw error
			}
			DomainUser user = userCredential.getDomainUser();
			ApplicationUser appUser = applicationUserRepository.findByUserAndApplication(user, application);
			if(appUser == null) {
				// TODO throw error
			}
			appUser.setEnabled(true);
			applicationUserRepository.save(appUser);
		}
	}

	@Override
	public void disableUser(UserCredentialsDTO credentials) {
		String domainName = credentials.getDomain();
		String subDomainName = credentials.getSubDomain();
		String applicationName = credentials.getApplication();
		String userName = credentials.getEmail();
		if (StringUtils.isEmpty(domainName)) {
			// TODO - throw error
		}
		if ((StringUtils.isEmpty(subDomainName) && StringUtils.isNotEmpty(applicationName))
				|| (StringUtils.isNotEmpty(subDomainName) && StringUtils.isEmpty(applicationName))) {
			// TODO - throw error
		}
		if (StringUtils.isEmpty(subDomainName)) {
			Domain domain = domainRepository.findByName(domainName);
			if(domain == null) {
				//TODO throw error
			}
			
			DomainCredential userCredential = domainCredentialRepository.findByDomainAndEmail(domain, userName);
			if(userCredential == null) {
				// TODO throw error
			}
			DomainUser user = userCredential.getDomainUser();
			user.setEnabled(false);
			domainUserRepository.save(user);
		} else {
			Domain domain = domainRepository.findByName(domainName);
			if(domain == null) {
				//TODO throw error
			}
			SubDomain subDomain = subDomainRepository.findByDomainAndName(domain, subDomainName);
			if(subDomain == null) {
				//TODO throw error
			}
			Application application = applicationRepository.findBySubDomainAndName(subDomain, applicationName);
			if(application == null) {
				//TODO throw error
			}
			DomainCredential userCredential = domainCredentialRepository.findByDomainAndEmail(domain, userName);
			if(userCredential == null) {
				// TODO throw error
			}
			DomainUser user = userCredential.getDomainUser();
			ApplicationUser appUser = applicationUserRepository.findByUserAndApplication(user, application);
			if(appUser == null) {
				// TODO throw error
			}
			appUser.setEnabled(false);
			applicationUserRepository.save(appUser);
		}
	}

	@Override
	public UserDTO authenticate(UserCredentialsDTO credentials) {
		// TODO Auto-generated method stub
		return null;
	}

	private void createDomainRole(String domainName, String roleName,
			Map<String, List<Map<String, List<String>>>> appRoles) {
		Domain domain = domainRepository.findByName(domainName);
		if (domain == null) {
			// TODO - throw error.
		} else {
			DomainRole role = new DomainRole();
			role.setDomain(domain);
			role.setName(roleName);
			List<ApplicationRole> roles = new ArrayList<>();
			if (MapUtils.isNotEmpty(appRoles)) {
				for (String subDomName : appRoles.keySet()) {
					for (Map<String, List<String>> apps : appRoles.get(subDomName)) {
						for (String appName : apps.keySet()) {
							for (String appRole : apps.get(appName)) {
								SubDomain subDom = subDomainRepository.findByDomainAndName(domain, subDomName);
								Application application = applicationRepository.findBySubDomainAndName(subDom, appName);
								ApplicationRole applicationRole = applicationRoleRepository
										.findByApplicationAndName(application, appRole);
								roles.add(applicationRole);
							}
						}
					}
				}
			}
			role.setApplicationRoles(roles);
			domainRoleRepository.save(role);
		}
	}

	private void createAppRole(String domainName, String subDomainName, String appName, String roleName) {
		if (StringUtils.isAnyEmpty(domainName, subDomainName, appName, roleName)) {
			// TODO throw error
		} else {

			Domain domain = domainRepository.findByName(domainName);
			if (domain == null) {
				// TODO throw error
			} else {
				SubDomain subDomain = subDomainRepository.findByDomainAndName(domain, subDomainName);
				if (subDomain == null) {
					// TODO throw error
				} else {
					Application application = applicationRepository.findBySubDomainAndName(subDomain, appName);
					if (application == null) {
						// TODO throw error
					} else {
						ApplicationRole role = new ApplicationRole();
						role.setApplication(application);
						role.setName(roleName);
						applicationRoleRepository.save(role);
					}
				}
			}
		}
	}

	private void createDomainUser(String domainName, String userName, String password, List<String> roles) {
		DomainUser domainUser = new DomainUser();
		Domain domain = domainRepository.findByName(domainName);
		List<SubDomain> subDomains = subDomainRepository.findByDomain(domain);
		domainUser.setDomain(domain);
		domainUser.setSubDomains(toString(subDomains));
		domainUser.setEnabled(false);
		domainUserRepository.save(domainUser);
		DomainCredential domainCredential = new DomainCredential();
		domainCredential.setDomain(domain);
		domainCredential.setDomainUser(domainUser);
		domainCredential.setEmail(userName);
		domainCredential.setPassword(passwordEncoder.encode(password));
		List<DomainRole> domainRoles = new ArrayList<>();
		for (String roleName : roles) {
			DomainRole domainRole = domainRoleRepository.findByDomainAndName(domain, roleName);
			if (domainRole == null) {
				// TODO throw error
			}
			domainRoles.add(domainRole);
		}
		domainCredential.setRoles(toString(domainRoles));
		domainCredentialRepository.save(domainCredential);
	}

	private void createApplicationUser(String domainName, String subDomainName, String applicationName, String userName,
			String password, List<String> roles) {
		Domain domain = domainRepository.findByName(domainName);
		if (domain == null) {
			// TODO throw error
		}
		SubDomain subDomain = subDomainRepository.findByDomainAndName(domain, subDomainName);
		if (subDomain == null) {
			// TODO throw error
		}
		Application application = applicationRepository.findBySubDomainAndName(subDomain, applicationName);
		if (application == null) {
			// TODO throw error
		}

		ApplicationUser applicationUser = new ApplicationUser();
		applicationUser.setApplication(application);
		DomainCredential domainCredential = domainCredentialRepository.findByDomainAndEmail(domain, userName);
		if (domainCredential == null) {
			// TODO throw error
		}
		applicationUser.setDomainUser(domainCredential.getDomainUser());
		applicationUser.setEnabled(false);
		applicationUserRepository.save(applicationUser);
		ApplicationCredential applicationCredential = new ApplicationCredential();
		applicationCredential.setApplication(application);
		applicationCredential.setDomainUser(domainCredential.getDomainUser());
		applicationCredential.setPassword(passwordEncoder.encode(password));
		List<ApplicationRole> applicationRoles = new ArrayList<>();
		for (String role : roles) {
			ApplicationRole applicationRole = applicationRoleRepository.findByApplicationAndName(application, role);
			if (applicationRole == null) {
				// TODO throw error
			}
			applicationRoles.add(applicationRole);
		}
		applicationCredential.setRoles(toString(applicationRoles));
	}

	private <T extends SSOEntity> String toString(List<T> list) {
		StringBuilder builder = new StringBuilder();
		for (SSOEntity obj : list) {
			builder.append(obj.getId());
			if (list.indexOf(obj) < list.size() - 1) {
				builder.append(",");
			}
		}
		return builder.toString();
	}

}
