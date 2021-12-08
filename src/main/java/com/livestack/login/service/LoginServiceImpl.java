package com.livestack.login.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.livestack.login.security.JwtProvider;
import com.livestack.login.vo.Farmer;
import com.livestack.login.vo.ResponseTemplateVO;
import com.livestack.login.domain.UserLogin;
import com.livestack.login.repo.UserLoginRepo;
import com.livestack.login.repo.UserRepository;
import com.livestack.login.advice.RestTemplateResponseErrorHandler;
import com.livestack.login.custom.exception.EmptyInputException;
import com.livestack.login.domain.User;

@Service
@Transactional
public class LoginServiceImpl implements LoginService {
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginServiceImpl.class);
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserLoginRepo userLoginRepo;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtProvider jwtProvider;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	public LoginServiceImpl(RestTemplateBuilder restTemplateBuilder) {
		RestTemplate restTemplate = restTemplateBuilder.errorHandler(new RestTemplateResponseErrorHandler()).build();
	}

	/**
	 * Sign in a user into the application, with JWT-enabled authentication
	 *
	 * @param username username
	 * @param password password
	 * @return User of the Java Web Token, empty otherwise
	 */
	@Override
	public User signin(String username, String password) {
		// if credentials are empty
		if (username.isEmpty() || password.isEmpty()) {
			throw new EmptyInputException();
		}
		String token = "";
//		User user = new User();
		ResponseTemplateVO vo = new ResponseTemplateVO();
		/**
		 * Commenting in as we are using now common table for all kinds of users Farmer
		 * farmer = new Farmer();
		 * 
		 * // if username is not found try { farmer =
		 * restTemplate.getForObject("http://localhost:8080/farmers/" + username,
		 * Farmer.class); } catch (HttpClientErrorException e) { throw new
		 * NoSuchElementException(); }
		 **/
		Optional<UserLogin> userlogin = userLoginRepo.findByUsername(username);
		Integer userId = userlogin.get().getUserId();
		Optional<User> user = userRepository.findById(userId);

		User userResponse = new User();

		if (user != null) {
			try {
				authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
				token = jwtProvider.createToken(username, user.get().getRoles());
				userResponse.setName(user.get().getName());
				userResponse.setId(userId);

			} catch (AuthenticationException e) {
				// if authentication failed
				throw new AuthenticationCredentialsNotFoundException(token);
			}

		}

		userResponse.setAccessToken(token);
		return userResponse;
	}

	@Override
	public void increaseFailedAttempts(UserLogin userLogin) {
		Optional<UserLogin> user = userLoginRepo.findByUsername(userLogin.getUsername());
		int newFailAttempts = userLogin.getFailedAttempts() + 1;
		if (userLoginRepo.findByUsername(userLogin.getUsername()).isPresent()) {
			userLoginRepo.updateFailedAttempts(newFailAttempts, user.get().getUserId());
		}
	}

	@Override
	public void resetFailedAttempts(UserLogin userLogin) {
		Optional<UserLogin> user = userLoginRepo.findByUsername(userLogin.getUsername());
		if (userLoginRepo.findByUsername(userLogin.getUsername()).isPresent()) {
			userLoginRepo.updateFailedAttempts(0, user.get().getUserId());
		} else {
			throw new NoSuchElementException();
		}
	}

	@Override
	public void lock(UserLogin userLogin) {
		Optional<UserLogin> user = userLoginRepo.findByUsername(userLogin.getUsername());
		if (userLoginRepo.findByUsername(userLogin.getUsername()).isPresent()) {
			Date date = new Date();
			userLoginRepo.loginDisabledToggle(true, new Timestamp(date.getTime()), user.get().getUserId());
		} else {
			throw new NoSuchElementException();
		}
	}

	@Override
	public void unlockWhenTimeExpired(UserLogin userLogin) {
		Optional<UserLogin> user = userLoginRepo.findByUsername(userLogin.getUsername());
		if (userLoginRepo.findByUsername(userLogin.getUsername()).isPresent()) {
			userLoginRepo.unlockUser(false, 0, null, user.get().getUserId());
		} else {
			throw new NoSuchElementException();
		}

	}

}
