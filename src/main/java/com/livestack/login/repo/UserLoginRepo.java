package com.livestack.login.repo;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.livestack.login.domain.UserLogin;

@Repository
public interface UserLoginRepo extends JpaRepository<UserLogin, Integer> {
	Optional<UserLogin> findByUsername(String username);
	
	@Query("UPDATE UserLogin u SET u.loginDisabled = ?1, u.lockTime = ?2 WHERE u.userId = ?3")
    @Modifying
    public void loginDisabledToggle(Boolean loginDisabled, Timestamp lockTime, Integer userId);
	
	@Query("UPDATE UserLogin u SET u.loginDisabled = ?1, u.failedAttempts = ?2, u.lockTime = ?3 WHERE u.userId = ?4")
    @Modifying
    public void unlockUser(Boolean loginDisabled, Integer failedAttempts, Timestamp lockTime, Integer userId);
	
	 @Query("UPDATE UserLogin u SET u.failedAttempts = ?1 WHERE u.userId = ?2")
    @Modifying
    public void updateFailedAttempts(Integer failAttempts, Integer userId);
	
}
