package in.indigenous.sso.model;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Deprecated
@Entity
@Table(name = "oa_role_mapping")
public class DomainApplicationRoleMapping {

	@Id
	@Column
	private BigInteger id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "oid")
	private Domain domain;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "aid")
	private Application application;

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

	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}

}
