package com.udacity.jwdnd.course1.cloudstorage.config;

import com.udacity.jwdnd.course1.cloudstorage.services.AuthenticationService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final AuthenticationService authenticationService;

    public SecurityConfig(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //signup
        http.authorizeRequests()
                .antMatchers("/signup", "/css/**", "/js/**", "/h2-console/**").permitAll()
                .anyRequest()
                .authenticated();

        http.csrf().disable();
        http.headers().frameOptions().disable();

        //login
        http.formLogin().loginPage("/login").permitAll();
        http.formLogin().defaultSuccessUrl("/home", true);

        //logout
        http.logout().logoutSuccessUrl("/login.html");
    }
}