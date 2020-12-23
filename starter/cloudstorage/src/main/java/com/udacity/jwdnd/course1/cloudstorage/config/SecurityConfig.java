package com.udacity.jwdnd.course1.cloudstorage.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Controller
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	PasswordEncoder passwordEncoder;

	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	 }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.authorizeRequests()
		.antMatchers("/","/api/*").permitAll()// only lets you view page with "/" or "/api"
		.and()
			.formLogin()
			.loginPage("/login") // login page url
			.defaultSuccessUrl("/home")
			//.failureUrl(authenticationFailureUrl)
			.permitAll()
		.and()
			.logout()
			.logoutSuccessUrl("/login?logout=true")
			//.logoutSuccessUrl("/login")
			.invalidateHttpSession(true)
			.permitAll()
		.and()
			.csrf()
			.disable();
	}
	
	 @Autowired
	 protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	     auth.inMemoryAuthentication()
	     .passwordEncoder(passwordEncoder)
	     .withUser("user").password(passwordEncoder.encode("123456")).roles("USER");
	 }
	
	
}
