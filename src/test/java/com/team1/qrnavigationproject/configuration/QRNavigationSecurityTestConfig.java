package com.team1.qrnavigationproject.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;


@Configuration
@EnableWebSecurity
@Profile("test")
public class QRNavigationSecurityTestConfig extends QRNavigationSecurityConfiguration{


}
