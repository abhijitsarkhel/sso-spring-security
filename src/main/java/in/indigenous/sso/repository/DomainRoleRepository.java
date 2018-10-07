package in.indigenous.sso.repository;

import java.math.BigInteger;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.indigenous.sso.model.Domain;
import in.indigenous.sso.model.DomainRole;

@Repository
@Transactional
public interface DomainRoleRepository extends JpaRepository<DomainRole, BigInteger> {

	DomainRole findByDomainAndName(Domain domain, String name);
	
	List<DomainRole> findByDomain(Domain domain);
}
