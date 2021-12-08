package com.livestack.login.restController;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.livestack.login.domain.User;
import com.livestack.login.domain.UserLogin;
import com.livestack.login.service.LoginService;
import com.livestack.login.vo.Farmer;
import com.livestack.login.vo.ResponseTemplateVO;

@RestController
@CrossOrigin(origins = "http://localhost:8084")
public class LoginController {
	
	@Autowired
	private LoginService loginService;
	
	@PostMapping(path="/users/signin", consumes = "application/json", produces = "application/json")
	public User login(@RequestBody @Valid UserLogin userLogin) {
		return loginService.signin(userLogin.getUsername(), userLogin.getPassword());
	}
	
	@PutMapping(path="/users/increaseAttempts", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> increaseFailedAttempts(@RequestBody UserLogin userLogin) {
		loginService.increaseFailedAttempts(userLogin);
		return new ResponseEntity<>("Attempts increased", HttpStatus.OK);
	}
	
	@PutMapping(path="/users/resetAttempts", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> resetFailedAttempts(@RequestBody UserLogin userLogin) {
		loginService.resetFailedAttempts(userLogin);
		return new ResponseEntity<>("Attempts has been reset", HttpStatus.OK);
	}
	
	@PutMapping(path="/users/lockAccount", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> lock(@RequestBody UserLogin userLogin) {
		loginService.lock(userLogin);
		return new ResponseEntity<>("Account has been locked", HttpStatus.OK);
	}
	
	@PutMapping(path="/users/unlockAccount", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> unlockAccount(@RequestBody UserLogin userLogin) {
		loginService.unlockWhenTimeExpired(userLogin);
		 return new ResponseEntity<>("Account has been unlocked", HttpStatus.OK);
	}

}
