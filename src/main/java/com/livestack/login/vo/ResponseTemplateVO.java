package com.livestack.login.vo;

import com.livestack.login.domain.User;

public class ResponseTemplateVO {
	private User user;
	private Farmer farmer;
	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
	/**
	 * @return the farmer
	 */
	public Farmer getFarmer() {
		return farmer;
	}
	/**
	 * @param farmer the farmer to set
	 */
	public void setFarmer(Farmer farmer) {
		this.farmer = farmer;
	}

}
