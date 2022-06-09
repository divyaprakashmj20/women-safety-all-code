package com.tus.womensafety.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter{

	private static final String[] SWAGGER_WHITELIST = {
			"/swagger-resources/**",
			"/swagger-ui.html",
			"/v2/api-docs",
			"/webjars/**",
			"/swagger-ui/**"
	};

	@Autowired
	private ResourceServerProperties sso;

	@Override
	public void configure(HttpSecurity http) throws Exception
	{
		System.out.println(sso.getClientId());
		http.cors().and()
			.csrf().disable().
			authorizeRequests()
			.antMatchers(HttpMethod.POST, "/user").permitAll()
			.antMatchers(HttpMethod.GET, "/user").hasAuthority("ADMIN")
			.antMatchers(HttpMethod.GET, "/user/relatives").hasAuthority("ADMIN")
				.antMatchers(SWAGGER_WHITELIST).permitAll()

			.anyRequest().authenticated();
		http.headers().frameOptions().disable();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(11);
	}

}
