package com.usc.security;

import com.usc.handler.AccessDeniedHandlerImpl;
import com.usc.handler.AuthenticationEntryPointImpl;
import com.usc.handler.AuthenticationFailureHandlerImpl;
import com.usc.handler.AuthenticationSuccessHandlerImpl;
import com.usc.handler.LogoutSuccessHandlerImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	AuthenticationEntryPointImpl authenticationEntryPointImpl;

	@Autowired
	AccessDeniedHandlerImpl accessDeniedHandlerImpl;

	@Autowired
	AuthenticationSuccessHandlerImpl authenticationSuccessHandlerImpl;

	@Autowired
	AuthenticationFailureHandlerImpl authenticationFailureHandlerImpl;

	@Autowired
	LogoutSuccessHandlerImpl logoutSuccessHandlerImpl;

	@Autowired
	UserDetailsService userDetailsService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().cors() // cors config.
				.and().authorizeRequests().antMatchers("/index.html").hasAnyRole("ROLE_ADMIN").and()
				.exceptionHandling().authenticationEntryPoint(authenticationEntryPointImpl).and().exceptionHandling()
				.accessDeniedHandler(accessDeniedHandlerImpl).and().formLogin().permitAll().loginProcessingUrl("/login")
				.successHandler(authenticationSuccessHandlerImpl).failureHandler(authenticationFailureHandlerImpl)
				.usernameParameter("username").passwordParameter("password").and().logout().permitAll()
				.logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandlerImpl).and().rememberMe();
	}

	/*
	 * cors support
	 */
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.addAllowedOrigin("*"); // should only set trusted site here.
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS"));
		configuration.addAllowedHeader("*");
		configuration.setAllowCredentials(true);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		PasswordEncoder encoder = new BCryptPasswordEncoder(11);
		return encoder;
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
}

