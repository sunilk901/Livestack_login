package com.livestack.login.security;

import com.livestack.login.domain.Role;
import com.livestack.login.domain.User;
import com.livestack.login.domain.UserLogin;
import com.livestack.login.repo.UserLoginRepo;
import com.livestack.login.repo.UserRepository;
import com.livestack.login.vo.Farmer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.security.core.userdetails.User.withUsername;

/**
 * Service to associate user with password and roles setup in the database.
 *
 * Created by Mary Ellen Bowman
 */
@Component
public class LivestackUserDetailsService implements UserDetailsService {
	private static final Logger LOGGER = LoggerFactory.getLogger(LivestackUserDetailsService.class);

	@Autowired
	JwtProvider jwtProvider;

	@Autowired
	private UserLoginRepo userLoginRepo;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
//    	Farmer user = restTemplate.getForObject("http://localhost:8080/farmers/" + s, Farmer.class);
//        List<GrantedAuthority> roles = new ArrayList<>();
//        roles.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		Optional<UserLogin> userlogin = userLoginRepo.findByUsername(s);
		Integer userId = userlogin.get().getUserId();
		Optional<User> user = userRepository.findById(userId);

		return withUsername(userlogin.get().getUsername()).password(userlogin.get().getPassword())
				.disabled(userlogin.get().isLoginDisabled()).authorities(user.get().getRoles()).accountExpired(false)
				.accountLocked(userlogin.get().isLoginDisabled()).credentialsExpired(false).disabled(false).build();
	}

	/**
	 * Extract username and roles from a validated jwt string.
	 *
	 * @param jwtToken jwt string
	 * @return UserDetails if valid, Empty otherwise
	 */
	public Optional<UserDetails> loadUserByJwtToken(String jwtToken) {
		if (jwtProvider.isValidToken(jwtToken)) {
			return Optional.of(withUsername(jwtProvider.getUsername(jwtToken))
					.authorities(jwtProvider.getRoles(jwtToken)).password("") // token does not have password but field
																				// may not be empty
					.accountExpired(false).accountLocked(false).credentialsExpired(false).disabled(false).build());
		}
		return Optional.empty();
	}

	/**
	 * Extract the username from the JWT then lookup the user in the database.
	 *
	 * @param jwtToken
	 * @return
	 */
	public Optional<UserDetails> loadUserByJwtTokenAndDatabase(String jwtToken) {
		if (jwtProvider.isValidToken(jwtToken)) {
			return Optional.of(loadUserByUsername(jwtProvider.getUsername(jwtToken)));
		} else {
			return Optional.empty();
		}
	}
}