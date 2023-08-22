package com.team1.qrnavigationproject.configuration;


import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class DevQRNavigationSecurityConfiguration extends QRNavigationSecurityConfiguration{

}
