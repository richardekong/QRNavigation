package com.team1.qrnavigationproject.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Profile("page-test")
public class QRNavigationSecurityPageTestConfig extends QRNavigationSecurityConfiguration{

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        //disable for page tests
        http.authorizeRequests().anyRequest().permitAll();
        return http.build();
    }
}
