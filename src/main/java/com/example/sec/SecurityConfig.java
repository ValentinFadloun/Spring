package com.example.sec;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

@Configuration @EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private DataSource dataSource;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		/*auth.inMemoryAuthentication().withUser("simm").password("{noop}123").roles("USER");
		auth.inMemoryAuthentication().withUser("admin").password("{noop}123").roles("USER", "ADMIN");*/
		auth.jdbcAuthentication().dataSource(dataSource)
			.usersByUsernameQuery("SELECT login as principal, pass as credentials, active FROM users WHERE login=?")
			.authoritiesByUsernameQuery("SELECT login as principal, role FROM usersroles WHERE login=?")
			.passwordEncoder(new BCryptPasswordEncoder())
			.rolePrefix("ROLE_");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin().loginPage("/login");
		http.csrf().disable();
		http.authorizeRequests().antMatchers("/user/*").hasRole("USER");
		http.authorizeRequests().antMatchers("/admin/*").hasRole("ADMIN");
		http.exceptionHandling().accessDeniedPage("/403");
	}
}
