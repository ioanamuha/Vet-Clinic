package com.finalproject.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

        jdbcUserDetailsManager.setUsersByUsernameQuery(
                "select email as username, password as pw, enabled from user where email = ?");

        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
                "select user.email as username, role.role as role from user " +
                        "inner join role on user.id = role.user_id where user.email = ?");

        return jdbcUserDetailsManager;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests(configurer ->
                        configurer
                                .requestMatchers("/", "/home", "/account/**", "/logout").hasAnyRole("PETOWNER","DOCTOR","ADMIN")
                                .requestMatchers("/doctor/medicalFile","/doctor/medicalFileUpdate","/doctor/update","/doctor/cancel").hasAnyRole("DOCTOR","ADMIN")
                                .requestMatchers("/pets/save","/pets/update","/pets/delete").hasAnyRole("PETOWNER","ADMIN")
                                .requestMatchers("/appointments/**","/pets/add","/pets","/pets/medicalFiles").hasRole("PETOWNER")
                                .requestMatchers("/doctor/appointments").hasRole("DOCTOR")
                                .requestMatchers("/admin/**","/doctor/medicalFile","/doctor/medicalFileUpdate").hasRole("ADMIN")
                                .requestMatchers("/login", "/register", "/users/save").permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin(form ->
                        form
                                .loginPage("/login")
                                .loginProcessingUrl("/authenticateTheUser")
                                .defaultSuccessUrl("/home", true)
                                .permitAll()
                )
                .logout(logout -> logout.permitAll()
                )
                .exceptionHandling(configurer ->
                        configurer.accessDeniedPage("/access-denied")
                );

        return http.build();
    }

}
