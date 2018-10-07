package in.indigenous.sso.model;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Deprecated
@Entity
@Table(name = "sda_role_mapping")
public class SubDomainApplicationRoleMapping {

	@Id
	@Column
	private BigInteger id;

	private SubDomain subDomain;

	private Application application;

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

	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}

}
