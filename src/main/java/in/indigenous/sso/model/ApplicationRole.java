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
@Table(name = "aroles")
public class ApplicationRole extends SSOEntity {

	@Id
	@Column
	private BigInteger id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "aid")
	private Application application;

	@Column
	private String name;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
