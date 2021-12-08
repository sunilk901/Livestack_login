package com.livestack.login.service;

import com.livestack.login.domain.User;
import com.livestack.login.domain.UserLogin;
import com.livestack.login.vo.ResponseTemplateVO;


public interface LoginService {
	public User signin(String username, String password);
	public void increaseFailedAttempts(UserLogin userLogin);
	public void resetFailedAttempts(UserLogin userLogin);
	public void lock(UserLogin userLogin);
	public void unlockWhenTimeExpired(UserLogin userLogin);
}
