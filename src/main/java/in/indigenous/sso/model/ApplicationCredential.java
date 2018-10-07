package in.indigenous.sso.model;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "acredentials")
public class ApplicationCredential extends SSOEntity {

	@Id
	@Column
	private BigInteger id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "uid")
	private DomainUser domainUser;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "aid")
	private Application application;

	@Column(name = "passwd")
	private String password;

	@Column
	private String roles;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public DomainUser getDomainUser() {
		return domainUser;
	}

	public void setDomainUser(DomainUser domainUser) {
		this.domainUser = domainUser;
	}

	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

}
