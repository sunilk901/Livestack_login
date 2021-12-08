package com.livestack.login.advice;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.livestack.login.custom.exception.EmptyInputException;
import com.livestack.login.domain.LoginException;

@ControllerAdvice
public class LoginControllerAdvice {
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	String nowDate = dateFormat.format(timestamp);
	
	@Autowired
	private Environment env;
	
	@ExceptionHandler(EmptyInputException.class)
	public ResponseEntity<LoginException> handleEmptyInput(EmptyInputException exception, WebRequest request) {
		LoginException loginException = new LoginException(nowDate, env.getProperty("empty.input.exception.msg"),
				request.getDescription(false), HttpStatus.BAD_REQUEST.value());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(loginException);
	}
	
	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<LoginException> noSuchUsernameExists(NoSuchElementException exception, WebRequest request) {
		LoginException loginException = new LoginException(nowDate, env.getProperty("no.record.exists.msg"),
				request.getDescription(false), HttpStatus.NOT_FOUND.value());
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(loginException);
	}
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<LoginException> illegalArgumentException(IllegalArgumentException exception, WebRequest request) {
		LoginException loginException = new LoginException(nowDate, env.getProperty("no.invalid.args.msg"),
				request.getDescription(false), HttpStatus.BAD_REQUEST.value());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(loginException);
	}
	
//	@ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
//	public ResponseEntity<LoginException> credentialsNotMatched(AuthenticationCredentialsNotFoundException exception, WebRequest request) {
//		LoginException loginException = new LoginException(nowDate, env.getProperty("authentication.failed.msg"),
//				request.getDescription(false), HttpStatus.BAD_REQUEST.value());
//		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                .body(loginException); 
//	}
	
//	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
//	public ResponseEntity<LoginException> methodNotAllowedHandling(HttpRequestMethodNotSupportedException exception, WebRequest request) {
//		LoginException loginException = new LoginException(nowDate,
//				env.getProperty("no.such.method.allowed.msg"), request.getDescription(false),
//				HttpStatus.METHOD_NOT_ALLOWED.value());
//		return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(loginException);
//	}

}
