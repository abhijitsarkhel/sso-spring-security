package in.indigenous.sso.model;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "sdroles")
public class SubDomainRole extends SSOEntity {

	@Id
	@Column
	private BigInteger id;

	@OneToOne
	@JoinColumn(name = "sid")
	private SubDomain subDomain;

	@Column
	private String name;

	@OneToMany
	@JoinTable(name = "oa_role_mapping", joinColumns = { @JoinColumn(name = "sid") }, inverseJoinColumns = {
			@JoinColumn(name = "aid") })
	private List<ApplicationRole> applicationRoles;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public SubDomain getSubDomain() {
		return subDomain;
	}

	public void setSubDomain(SubDomain subDomain) {
		this.subDomain = subDomain;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ApplicationRole> getApplicationRoles() {
		return applicationRoles;
	}

	public void setApplicationRoles(List<ApplicationRole> applicationRoles) {
		this.applicationRoles = applicationRoles;
	}

}
