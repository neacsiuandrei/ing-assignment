package com.ing.assignment.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    @Order(1)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.ignoringRequestMatchers("/products/*"))
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/products/add/**", "products/changePrice/**", "products/delete/**").hasRole("ADMIN")
                        .requestMatchers("/products/**").hasAnyRole("USER", "ADMIN"))
                .httpBasic(Customizer.withDefaults());
        return httpSecurity.build();
    }

    @Bean
    public SecurityFilterChain adminSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests((authorize) -> authorize.requestMatchers("/products/all").hasAnyAuthority("ADMIN").anyRequest().authenticated());
        return httpSecurity.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {

        UserDetails basicUser = User.withDefaultPasswordEncoder()
                .username("user")
                .password("userPass")
                .roles("USER")
                .build();

        UserDetails adminUser = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("adminPass")
                .roles("ADMIN")
                .build();


        return new InMemoryUserDetailsManager(basicUser, adminUser);
    }

}
