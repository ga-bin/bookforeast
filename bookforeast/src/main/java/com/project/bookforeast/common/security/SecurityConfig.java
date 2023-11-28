package com.project.bookforeast.common.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.FormLoginBeanDefinitionParser;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.concurrent.SuccessCallback;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private JwtUtil jwtUtil;
	private JwtAuthenticationSuccessHandler jwtAuthenticationSuccessHandler;

	
	@Autowired
	public SecurityConfig(JwtUtil jwtUtil, JwtAuthenticationSuccessHandler jwtAuthenticationSuccessHandler) {
		this.jwtUtil = jwtUtil;
		this.jwtAuthenticationSuccessHandler = jwtAuthenticationSuccessHandler;
	}
	
	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests(authorizeRequests ->
				authorizeRequests
					.requestMatchers("/", 
									 "/api/u/v1/users/social-login",
									 "/api/u/v1/users/signup",
									 "/api/u/v1/users/login"
									).permitAll()
			)
			.csrf(csrf -> {
				csrf.disable();
			})
			.cors(cors -> {
				cors.disable();
			})
			.formLogin(formLogin -> {
				formLogin
					.loginProcessingUrl("/api/u/v1/users/login")
					.usernameParameter("phoneNumber")
					.passwordParameter("password")
					.successHandler(jwtAuthenticationSuccessHandler);
			})
			.httpBasic(Customizer.withDefaults())
			.addFilterBefore(new JwtAuthorizationFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class)
			.logout(logout -> logout.logoutSuccessUrl("/").permitAll());
			
		
		return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
