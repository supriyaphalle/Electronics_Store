package com.bikkadIt.electronic.store.config;

import com.bikkadIt.electronic.store.entities.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebMvcConfigurerAdapter {

    @Bean
    public UserDetailsService userDetailsService() {

        UserDetails user = (UserDetails) User.builder()
                .name("Supriya")
                .password(passwordEncoder().encode("supriya"))
                .roles("NORMAL")
                .build();

        UserDetails admin = (UserDetails) User.builder()
                .name("Abhi")
                .password(passwordEncoder().encode("abhi"))
                .roles("Admin")
                .build();

        return new InMemoryUserDetailsManager();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
