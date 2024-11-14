package com.vivek.journalApp.config;

import com.vivek.journalApp.service.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(request -> request
                        .requestMatchers("/public/**").permitAll()
                        .requestMatchers("/journal/**", "/user/**").authenticated() // Authenticated requests for /journal/**
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }


    /**

     This setup allows you to customize how user authentication data is fetched and how passwords are handled,
     giving you flexibility to store user data in a database, LDAP, or any other storage mechanism you want, while also securing passwords by encoding them.
     */

    /**
     * This method essentially configures the authentication mechanism for your application:
     * <p>
     * When a user attempts to log in, Spring Security will use the userDetailService to load the user by their username.
     * <p>
     * The password provided by the user will be encoded using the configured PasswordEncoder.
     * <p>
     * The encoded password will be compared to the stored password to validate the user's credentials.
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
