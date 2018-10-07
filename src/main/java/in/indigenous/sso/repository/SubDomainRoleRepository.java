package in.indigenous.sso.repository;

import java.math.BigInteger;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.indigenous.sso.model.SubDomain;
import in.indigenous.sso.model.SubDomainRole;

@Repository
@Transactional
public interface SubDomainRoleRepository extends JpaRepository<SubDomainRole, BigInteger> {

	SubDomainRole findBySubDomainAndName(SubDomain subDomain, String name); 
}
