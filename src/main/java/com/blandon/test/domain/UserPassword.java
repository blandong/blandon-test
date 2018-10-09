package com.blandon.test.domain;

import java.io.Serializable;

public class UserPassword implements Serializable{
	
	private static final long serialVersionUID = -5862387672926725716L;
	private String gblCovUserId;
	private String extSysUniqueId;
	private String realmCode;
	private String password;
	/**
	 * @return the gblCovUserId
	 */
	public String getGblCovUserId() {
		return gblCovUserId;
	}
	/**
	 * @param gblCovUserId the gblCovUserId to set
	 */
	public void setGblCovUserId(String gblCovUserId) {
		this.gblCovUserId = gblCovUserId;
	}
	/**
	 * @return the extSysUniqueId
	 */
	public String getExtSysUniqueId() {
		return extSysUniqueId;
	}
	/**
	 * @param extSysUniqueId the extSysUniqueId to set
	 */
	public void setExtSysUniqueId(String extSysUniqueId) {
		this.extSysUniqueId = extSysUniqueId;
	}
	/**
	 * @return the realmCode
	 */
	public String getRealmCode() {
		return realmCode;
	}
	/**
	 * @param realmCode the realmCode to set
	 */
	public void setRealmCode(String realmCode) {
		this.realmCode = realmCode;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UserPassword [gblCovUserId=" + gblCovUserId + ", extSysUniqueId=" + extSysUniqueId + ", realmCode="
				+ realmCode + ", password=" + password + "]";
	}
	
}
