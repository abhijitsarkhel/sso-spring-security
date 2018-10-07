package in.indigenous.sso.repository;

import java.math.BigInteger;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.indigenous.sso.model.Application;
import in.indigenous.sso.model.ApplicationRole;

@Repository
@Transactional
public interface ApplicationRoleRepository extends JpaRepository<ApplicationRole, BigInteger>{

	ApplicationRole findByApplicationAndName(Application application, String name);
}
