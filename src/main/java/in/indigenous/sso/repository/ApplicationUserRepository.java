package in.indigenous.sso.repository;

import java.math.BigInteger;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.indigenous.sso.model.Application;
import in.indigenous.sso.model.ApplicationUser;
import in.indigenous.sso.model.DomainUser;

@Repository
@Transactional
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, BigInteger> {

	ApplicationUser findByDomainUserAndApplication(Application application, DomainUser domainUser);
	
	List<ApplicationUser> findByApplication(Application application);
}
