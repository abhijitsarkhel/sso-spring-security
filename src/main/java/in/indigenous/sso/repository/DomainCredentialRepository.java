package in.indigenous.sso.repository;

import java.math.BigInteger;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.indigenous.sso.model.Domain;
import in.indigenous.sso.model.DomainCredential;

@Repository
@Transactional
public interface DomainCredentialRepository extends JpaRepository<DomainCredential, BigInteger> {

	List<DomainCredential> findByEmail(String email);
	
	DomainCredential findByDomainAndEmail(Domain domain, String email);
}
