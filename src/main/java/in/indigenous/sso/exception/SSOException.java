package in.indigenous.sso.exception;

import java.time.LocalDate;

public class SSOException extends Exception {

	/**
	 * Generated version UId.
	 */
	private static final long serialVersionUID = -7893191059050722995L;

	private String errorCode;

	private String summary;

	private String errorMessage;

	private LocalDate errorTime;

	public SSOException(String errorCode, String summary, String errorMessage, LocalDate errorTime) {
		this.errorCode = errorCode;
		this.summary = summary;
		this.errorMessage = errorMessage;
		this.errorTime = errorTime;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getSummary() {
		return summary;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public LocalDate getErrorTime() {
		return errorTime;
	}

}
