package com.livestack.login.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.livestack.login.filter.XssHttpServletRequestWrapper;

@Component
@Order(2)
/**
 * to log requests and responses In order for Spring to recognize a filter, we
 * need to define it as a bean with the @Component annotation.
 * Moreover, to have the filters fire in the right order, we need to use
 * the @Order annotation.
 **/
public class RequestResponseLoggingFilter implements Filter {
	private static final Logger LOGGER = LoggerFactory.getLogger(RequestResponseLoggingFilter.class);

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		filterChain.doFilter(new XssHttpServletRequestWrapper((HttpServletRequest) request), response);
		LOGGER.info("Logging Request: " + req.getMethod(), req.getRequestURI());
		LOGGER.info("Logging Response: " + resp.getContentType());
	}
	
}
