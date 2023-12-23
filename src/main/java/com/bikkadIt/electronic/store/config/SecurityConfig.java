package com.bikkadIt.electronic.store.config;

import com.bikkadIt.electronic.store.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    //    @Bean
//    public UserDetailsService userDetailsService() {
//
//        UserDetails user = (UserDetails) User.builder()
//                .name("Supriya")
//                .password(passwordEncoder().encode("supriya"))
//                .roles("NORMAL")
//                .build();
//
//        UserDetails admin = (UserDetails) User.builder()
//                .name("Abhi")
//                .password(passwordEncoder().encode("abhi"))
//                .roles("Admin")
//                .build();
//
//        return new InMemoryUserDetailsManager();
//    }

    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


//        http.authorizeRequests()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .loginPage("login.html")
//                .loginProcessingUrl("/process-url")
//                .defaultSuccessUrl("/dashboard")
//                .failureUrl("error")
//                .and()
//                .logout()
//                .logoutUrl("/logout");
//
//        return http.build();

        http.

                csrf()
                .disable()
                .cors()
                .disable()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(this.userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
