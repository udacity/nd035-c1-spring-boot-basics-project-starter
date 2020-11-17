package com.udacity.jwdnd.course1.cloudstorage.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.udacity.jwdnd.course1.cloudstorage.services.AuthenticationService;

@Configuration 
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	private AuthenticationService authenticationService;
	
	public SecurityConfiguration(final AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}
}
