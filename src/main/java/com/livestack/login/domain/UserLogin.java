package com.livestack.login.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "user_login", schema = "public")
public class UserLogin implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "user_id")
	private Integer userId;
	
	@NotEmpty(message = "{username.not.null}")
	@Column
	@Size(max = 20, message = "{username.size}")
	private String username;

	@Size(min=8, max=20, message = "{password.size}")
	@Column
	private String password;
	
	@Column(name = "failed_attempts")
	private Integer failedAttempts;
	
	@Column(name = "login_disabled")
	private boolean loginDisabled;
	
	@Column(name = "lock_time")
    private Timestamp lockTime;
	
public UserLogin() {
	
}
	
	public UserLogin(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(username, password);
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserLogin userLogin = (UserLogin) o;
		return Objects.equals(username, userLogin.username)
				&& Objects.equals(password, userLogin.password);
	}
	
	@Override
	public String toString() {
		return "UserLogin{" + "username=" + username + ", password=" + password 
				+ '}';
	}

	
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
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
	
	/**
	 * @return the failedAttempts
	 */
	public Integer getFailedAttempts() {
		return failedAttempts;
	}

	/**
	 * @param failedAttempts the failedAttempts to set
	 */
	public void setFailedAttempts(Integer failedAttempts) {
		this.failedAttempts = failedAttempts;
	}

	/**
	 * @return the loginDisabled
	 */
	public boolean isLoginDisabled() {
		return loginDisabled;
	}

	/**
	 * @param loginDisabled the loginDisabled to set
	 */
	public void setLoginDisabled(boolean loginDisabled) {
		this.loginDisabled = loginDisabled;
	}

	/**
	 * @return the lockTime
	 */
	public Timestamp getLockTime() {
		return lockTime;
	}

	/**
	 * @param lockTime the lockTime to set
	 */
	public void setLockTime(Timestamp lockTime) {
		this.lockTime = lockTime;
	}

	/**
	 * @return the userId
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}
