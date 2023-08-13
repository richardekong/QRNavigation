package com.team1.qrnavigationproject.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ActiveProfiles;


@Configuration
@EnableWebSecurity
@ActiveProfiles("page-test")
public class QRNavigationSecurityTestConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        //disable for page tests
        http.authorizeRequests().anyRequest().permitAll();
        return http.build();
    }

}
