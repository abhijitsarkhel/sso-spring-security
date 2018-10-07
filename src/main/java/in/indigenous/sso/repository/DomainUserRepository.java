package in.indigenous.sso.repository;

import java.math.BigInteger;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.indigenous.sso.model.DomainUser;

@Repository
@Transactional
public interface DomainUserRepository extends JpaRepository<DomainUser, BigInteger>{

}
