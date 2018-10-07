package in.indigenous.sso.repository;

import java.math.BigInteger;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.indigenous.sso.model.Domain;

@Repository
@Transactional
public interface DomainRepository extends JpaRepository<Domain, BigInteger> {

	Domain findByName(String name);
}
