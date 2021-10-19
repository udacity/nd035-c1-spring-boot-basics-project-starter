package safwat.cloudstorage.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import safwat.cloudstorage.services.AuthenticationService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	AuthenticationService authenticationService;
	
	public SecurityConfig(AuthenticationService authenticationService) {
		// TODO Auto-generated constructor stub
		this.authenticationService = authenticationService;
	}
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		auth.authenticationProvider(this.authenticationService);
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		http.authorizeRequests()
			.antMatchers("/signup", "/css/**", "/js/**", "/h2-console/**").permitAll()
			.anyRequest().authenticated()
			.and()
			.logout().permitAll();
			
		
		http.formLogin()
			.loginPage("/login").permitAll();
		
		http.formLogin()
			.defaultSuccessUrl("/home", true);
			
		/*For debugging purposes*/
		 http.csrf().disable();
	     http.headers().frameOptions().disable();
///////////////////////////////////////////////////
		
	}
}
