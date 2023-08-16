package com.team1.qrnavigationproject.configuration;


import com.team1.qrnavigationproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static com.team1.qrnavigationproject.configuration.QRNavigationPaths.*;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class QRNavigationSecurityConfiguration {

    protected PasswordEncoder passwordEncoder;

    protected AppAuthenticationProvider appAuthenticationProvider;
    protected UserService userService;


    @Autowired
    private void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    @Autowired
    public void setAppAuthenticationProvider(AppAuthenticationProvider appAuthenticationProvider){
        this.appAuthenticationProvider = appAuthenticationProvider;
    }

    @Bean
    public AppAuthenticationSuccessHandler successHandler() {
        return new AppAuthenticationSuccessHandler(userService);
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return webSecurity -> {
            webSecurity.ignoring().antMatchers("/css/**");
            webSecurity.ignoring().antMatchers("/script/**");
            webSecurity.ignoring().antMatchers("/images/**");
            webSecurity.ignoring().antMatchers("/h2-console/**");
        };
    }

    @Bean
    public AuthenticationManager AuthenticationManager(HttpSecurity http, UserService userService) throws Exception {

        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userService)
                .passwordEncoder(passwordEncoder)
                .and()
                .authenticationProvider(appAuthenticationProvider)
                .build();
    }

    @Bean
    public SecurityFilterChain filterChainForAdmin(HttpSecurity http) throws Exception {
        // Allow unauthorized access to an array of paths

        // Access to other addresses requires authentication permissions
        http.authorizeRequests()
                .antMatchers(PERMITTED_PATHS).permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .successHandler(successHandler())
                // Username and password parameter names
                .passwordParameter("password")
                .usernameParameter("username")
                .loginPage("/login")
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
