package com.team1.qrnavigationproject.configuration;


import com.team1.qrnavigationproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static com.team1.qrnavigationproject.configuration.QRNavigationPaths.*;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class QRNavigationSecurityConfiguration {

    protected UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public AppAuthenticationSuccessHandler successHandler() {
        return new AppAuthenticationSuccessHandler(userService);
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChainForAdmin(HttpSecurity http) throws Exception {
        // Allow unauthorized access to an array of paths
        // Access to other addresses requires authentication permissions
        http
                .authorizeRequests()
                .antMatchers(PERMITTED_PATHS).permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                // Username and password parameter names
                .passwordParameter("password")
                .usernameParameter("username")
                .loginPage("/login")
                .successHandler(successHandler())
                // Login error
                .failureUrl(LOGIN_ERROR)
                .permitAll()
                .and()
                // Set the logout URL and jump page after successful logout
                .logout()
                .logoutUrl(LOGOUT)
                .logoutSuccessUrl(LOGIN);


        return http.build();
    }
}
