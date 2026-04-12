package com.bank.bankaccount.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Value("${app.security.admin-user}")
    private String adminUsername;

    @Value("${app.security.admin-password}")
    private String adminPassword;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(csrf -> csrf.disable()) //CSRF is disabled to facilitate ease of testing via Swagger UI and Postman
                .authorizeHttpRequests(auth -> auth
                        // allow us to enter Swagger-ui and H2 Console
                        .requestMatchers( "/h2-console/**").permitAll()
                        .requestMatchers("/bank/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                ).httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults())
                .headers(header -> header.frameOptions(frame -> frame.disable()));
        return httpSecurity.build();

    }

    @Bean
    public InMemoryUserDetailsManager userDetailService(){
        UserDetails admin= User.withDefaultPasswordEncoder()
                .username(adminUsername)
                .password(adminPassword)
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(admin);
    }
}
