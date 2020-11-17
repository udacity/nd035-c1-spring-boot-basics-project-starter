package com.udacity.jwdnd.course1.cloudstorage.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(authenticationService);
	}
	
	@Override 
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		    .antMatchers("/signup", "/css/**", "/js/**")
		    .permitAll()
		    .anyRequest()
		    .authenticated();
		
		http.formLogin()
		    .loginPage("/login")
		    .permitAll();
		
		http.formLogin()
		    .defaultSuccessUrl("/home", true);
		
		http.logout()
	    	.logoutSuccessUrl("/login")
	    	.invalidateHttpSession(true)
	    	.deleteCookies("JSESSIONID");
	}
}
