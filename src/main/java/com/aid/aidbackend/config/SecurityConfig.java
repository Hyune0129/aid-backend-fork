package com.aid.aidbackend.config;

import jakarta.servlet.DispatcherType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(matcherRegistry -> matcherRegistry
                        .dispatcherTypeMatchers(DispatcherType.ERROR).permitAll() // Error dispatch 인가 예외
                        .requestMatchers(
                                "/api/v1/hello",
                                "/api/v1/members/signup",
                                "/api/v1/auth/login"
                        ).permitAll()
                        .anyRequest().authenticated()
                );

        return httpSecurity.build();
    }
}
