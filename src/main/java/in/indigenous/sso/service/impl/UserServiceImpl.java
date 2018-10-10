package in.indigenous.sso.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import in.indigenous.sso.security.dto.SSOUser;
import in.indigenous.sso.security.dto.UserCredentialsDTO;
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
			if (domain == null) {
				// TODO throw error
			}

			DomainCredential userCredential = domainCredentialRepository.findByDomainAndEmail(domain, userName);
			if (userCredential == null) {
				// TODO throw error
			}
			DomainUser user = userCredential.getDomainUser();
			user.setEnabled(true);
			domainUserRepository.save(user);
		} else {
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
			DomainCredential userCredential = domainCredentialRepository.findByDomainAndEmail(domain, userName);
			if (userCredential == null) {
				// TODO throw error
			}
			DomainUser user = userCredential.getDomainUser();
			ApplicationUser appUser = applicationUserRepository.findByUserAndApplication(user, application);
			if (appUser == null) {
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
			if (domain == null) {
				// TODO throw error
			}

			DomainCredential userCredential = domainCredentialRepository.findByDomainAndEmail(domain, userName);
			if (userCredential == null) {
				// TODO throw error
			}
			DomainUser user = userCredential.getDomainUser();
			user.setEnabled(false);
			domainUserRepository.save(user);
		} else {
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
			DomainCredential userCredential = domainCredentialRepository.findByDomainAndEmail(domain, userName);
			if (userCredential == null) {
				// TODO throw error
			}
			DomainUser user = userCredential.getDomainUser();
			ApplicationUser appUser = applicationUserRepository.findByUserAndApplication(user, application);
			if (appUser == null) {
				// TODO throw error
			}
			appUser.setEnabled(false);
			applicationUserRepository.save(appUser);
		}
	}

	@Override
	public SSOUser authenticate(UserCredentialsDTO credentials) {
		String domainName = credentials.getDomain();
		Domain domain = domainRepository.findByName(domainName);
		String userName = credentials.getEmail();
		SSOUser user = new SSOUser();
		user.setDomain(domainName);
		user.setEmail(userName);
		DomainCredential credential = domainCredentialRepository.findByDomainAndEmail(domain, userName);
		user.setSubDomains(subDomainToList(domainName, credential.getDomainUser().getSubDomains()).stream().map(s -> {
			return s.getName();
		}).collect(Collectors.toList()));
		user.setDomainRoles(domainRoleRepository.findByDomain(domain).stream().map(r -> {
			return r.getName();
		}).collect(Collectors.toList()));
		user.setEnabled(credential.getDomainUser().isEnabled());
		user.setApps(getDomainApps(domain.getName(), credential.getEmail()));
		user.setAppRoles(getAppRoles(domain.getName(), credential.getEmail()));
		return user;
	}

	@Override
	public List<SSOUser> getAllUsersForDomain(String domain) {
		List<DomainCredential> credentials = domainCredentialRepository.findAll();
		List<SSOUser> users = new ArrayList<>();
		for (DomainCredential credential : credentials) {
			DomainUser user = credential.getDomainUser();
			SSOUser ssoUser = new SSOUser();
			ssoUser.setDomain(user.getDomain().getName());
			ssoUser.setEmail(credential.getEmail());
			ssoUser.setSubDomains(subDomainToList(user.getDomain().getName(), user.getSubDomains()).stream()
					.map(s -> s.getName()).collect(Collectors.toList()));
			ssoUser.setEnabled(user.isEnabled());
			ssoUser.setApps(getDomainApps(user.getDomain().getName(), credential.getEmail()));
			users.add(ssoUser);
		}
		return users;
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

	@SuppressWarnings("unused")
	private List<Domain> domainToList(String list) {
		List<String> items = Arrays.asList(StringUtils.split(list, ","));
		List<Domain> result = new ArrayList<>();
		for (String item : items) {
			Domain domain = domainRepository.findByName(item);
			if (domain == null) {
				// TODO throw error
			}
			result.add(domain);
		}
		return result;
	}

	private List<SubDomain> subDomainToList(String domainName, String list) {
		List<String> items = Arrays.asList(StringUtils.split(list, ","));
		Domain domain = domainRepository.findByName(domainName);
		if (domain == null) {
			// TODO throw error
		}
		List<SubDomain> result = new ArrayList<>();
		for (String item : items) {
			SubDomain subDomain = subDomainRepository.findByDomainAndName(domain, item);
			if (subDomain == null) {
				// TODO throw error
			}
			result.add(subDomain);
		}
		return result;
	}

	@SuppressWarnings("unused")
	private List<Application> applicationToList(String domainName, String subDomainName, String list) {
		List<String> items = Arrays.asList(StringUtils.split(list, ","));
		Domain domain = domainRepository.findByName(domainName);
		if (domain == null) {
			// TODO throw error
		}
		SubDomain subDomain = subDomainRepository.findByDomainAndName(domain, subDomainName);
		if (subDomain == null) {
			// TODO throw error
		}
		List<Application> result = new ArrayList<>();
		for (String item : items) {
			Application application = applicationRepository.findBySubDomainAndName(subDomain, item);
			if (application == null) {
				// TODO throw error
			}
			result.add(application);
		}
		return result;
	}

	private List<Map<String, List<String>>> getDomainApps(String domainName, String email) {
		List<Map<String, List<String>>> result = new ArrayList<>();
		Domain domain = domainRepository.findByName(domainName);
		DomainCredential credential = domainCredentialRepository.findByDomainAndEmail(domain, email);
		DomainUser user = credential.getDomainUser();
		String subDomainIds = user.getSubDomains();
		List<SubDomain> subDomainList = subDomainToList(domainName, subDomainIds);
		for (SubDomain subDomain : subDomainList) {
			Map<String, List<String>> subDomainApps = new HashMap<>();
			List<Application> applications = applicationRepository.findBySubDomain(subDomain);
			subDomainApps.put(subDomain.getName(), applications.stream().map(a -> {
				return a.getName();
			}).collect(Collectors.toList()));
			result.add(subDomainApps);
		}
		return result;
	}

	private List<Map<String, List<Map<String, List<String>>>>> getAppRoles(String domainName, String email) {
		List<Map<String, List<Map<String, List<String>>>>> result = new ArrayList<>();
		Domain domain = domainRepository.findByName(domainName);
		DomainCredential credential = domainCredentialRepository.findByDomainAndEmail(domain, email);
		DomainUser user = credential.getDomainUser();
		String subDomainIds = user.getSubDomains();
		List<SubDomain> subDomainList = subDomainToList(domainName, subDomainIds);
		Map<String, List<Map<String, List<String>>>> subDomainApps = new HashMap<>();
		for (SubDomain subDomain : subDomainList) {
			List<Application> applications = applicationRepository.findBySubDomain(subDomain);
			List<Map<String, List<String>>> appList = new ArrayList<>();
			for (Application application : applications) {
				Map<String, List<String>> appRoles = new HashMap<>();
				appRoles.put(application.getName(),
						applicationRoleRepository.findByApplication(application).stream().map(r -> {
							return r.getName();
						}).collect(Collectors.toList()));
				appList.add(appRoles);
			}
			subDomainApps.put(subDomain.getName(), appList);
			result.add(subDomainApps);
		}
		return result;
	}

}
