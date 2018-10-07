package in.indigenous.sso.model;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "oroles")
public class DomainRole extends SSOEntity {

	@Id
	@Column
	private BigInteger id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "oid")
	private Domain domain;

	@Column
	private String name;

	@OneToMany
	@JoinTable(name = "oa_role_mapping", joinColumns = { @JoinColumn(name = "oid") }, inverseJoinColumns = {
			@JoinColumn(name = "aid") })
	private List<ApplicationRole> applicationRoles;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public Domain getDomain() {
		return domain;
	}

	public void setDomain(Domain domain) {
		this.domain = domain;
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
