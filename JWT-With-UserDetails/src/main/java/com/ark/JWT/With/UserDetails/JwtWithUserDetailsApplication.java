package com.ark.JWT.With.UserDetails;

import com.ark.JWT.With.UserDetails.security.jwt_algorith.JwtGeneration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class JwtWithUserDetailsApplication {

	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}
	@Bean
	JwtGeneration jwtGeneration(){
		return new JwtGeneration();
	}

	public static void main(String[] args) {
		SpringApplication.run(JwtWithUserDetailsApplication.class, args);
	}

}
