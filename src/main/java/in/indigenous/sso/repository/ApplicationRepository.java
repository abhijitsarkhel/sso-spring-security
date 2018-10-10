package in.indigenous.sso.repository;

import java.math.BigInteger;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.indigenous.sso.model.Application;
import in.indigenous.sso.model.SubDomain;

@Repository
@Transactional
public interface ApplicationRepository extends JpaRepository<Application, BigInteger>{

	Application findBySubDomainAndName(SubDomain subDomain, String name);
	
	List<Application> findBySubDomain(SubDomain subDomain);
}
