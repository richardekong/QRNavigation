package com.team1.qrnavigationproject.configuration;


import com.team1.qrnavigationproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Profile("dev")
public class DevQRNavigationSecurityConfiguration extends QRNavigationSecurityConfiguration{

//    private AppAuthenticationSuccessHandler devSuccessHandler;
//    private WebSecurityCustomizer devWebSecurityCustomizer;
//    private AuthenticationManager devAuthenticationManager;
//    private SecurityFilterChain devFilterChainForAdmin;
//
//    @Autowired
//    public void setDevSuccessHandler(AppAuthenticationSuccessHandler devSuccessHandler) {
//        this.devSuccessHandler = devSuccessHandler;
//    }
//
//    @Autowired
//    public void setDevWebSecurityCustomizer(WebSecurityCustomizer devWebSecurityCustomizer) {
//        this.devWebSecurityCustomizer = devWebSecurityCustomizer;
//    }
//
//    @Autowired
//    public void setDevAuthenticationManager(AuthenticationManager devAuthenticationManager) throws Exception {
//        devAuthenticationManager = devAuthenticationManager;
//    }
//
//    @Autowired
//    public void setDevFilterChainForAdmin(SecurityFilterChain devFilterChainForAdmin) {
//        this.devFilterChainForAdmin = devFilterChainForAdmin;
//    }
//
//    @Bean(name = "devSuccessHandler")
//    @Override
//    public AppAuthenticationSuccessHandler successHandler() {
//        return devSuccessHandler;
//    }
//
//    @Bean(name = "devWebSecurityCustomizer")
//    @Override
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return devWebSecurityCustomizer;
//    }
//
//
//    @Bean(name="DevAuthenticationManager")
//    @Override
//    public AuthenticationManager AuthenticationManager(HttpSecurity http, UserService userService) throws Exception {
//        return devAuthenticationManager;
//    }
//
//    @Bean(name="DevFilterChainForAdmin")
//    @Override
//    public SecurityFilterChain filterChainForAdmin(HttpSecurity http) throws Exception {
//        return devFilterChainForAdmin;
//    }
}
