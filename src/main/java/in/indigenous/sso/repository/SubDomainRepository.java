package in.indigenous.sso.repository;

import java.math.BigInteger;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.indigenous.sso.model.Domain;
import in.indigenous.sso.model.SubDomain;

@Repository
@Transactional
public interface SubDomainRepository extends JpaRepository<SubDomain, BigInteger>{

	SubDomain findByDomainAndName(Domain domain, String name);
	
	List<SubDomain> findByDomain(Domain domain);
}
