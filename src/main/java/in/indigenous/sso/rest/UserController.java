package in.indigenous.sso.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import in.indigenous.sso.dto.UserCredentials;
import in.indigenous.sso.dto.UserDTO;
import in.indigenous.sso.dto.UserRole;
import in.indigenous.sso.service.UserService;

@RestController("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("register/domain/{app}")
	public void registerDomain(@PathVariable String app) {
		userService.registerDomain(app);
	}

	@PostMapping("register/domain/{dom}/subdomain/{app}")
	public void registerSubDomain(@PathVariable String dom, @PathVariable String app) {
		userService.registerSubDomain(dom, app);
	}

	@PostMapping("register/domain/{dom}/subdomain/{sdom}/app/{app}")
	public void registerApp(@PathVariable String dom, @PathVariable String sdom, @PathVariable String app) {
		userService.registerApp(dom, sdom, app);
	}

	@PostMapping("/create/role")
	public void createRole(UserRole role) {
		userService.createRole(role);
	}

	@PostMapping("/create")
	public void createUser(UserCredentials credentials) {
		userService.createUser(credentials);
	}

	@PostMapping("/disable")
	public void disableUser(UserCredentials credentials) {
		userService.disableUser(credentials);
	}

	@PostMapping("/enable")
	public void enableUser(UserCredentials credentials) {
		userService.disableUser(credentials);
	}

	@PostMapping("/authenticate")
	public UserDTO authenticate(UserCredentials credentials) {
		return userService.authenticate(credentials);
	}

}
