package com.finalproject.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

        jdbcUserDetailsManager.setUsersByUsernameQuery(
                "select email as username, password as pw, enabled from user_trial where email = ?");

        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
                "select user_trial.email as username, role_trial.role as role from user_trial " +
                        "inner join role_trial on user_trial.id = role_trial.user_id where user_trial.email = ?");

        return jdbcUserDetailsManager;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests(configurer ->
                        configurer
                                .requestMatchers("/", "/home").hasAnyRole("PETOWNER","DOCTOR","ADMIN")
                                .requestMatchers("/appointments/doctorAppointments").hasRole("DOCTOR")
                                .requestMatchers("/users/list").hasRole("ADMIN")
                                .requestMatchers("/showMyLoginPage", "/register", "/users/showFormForAdd", "/users/save").permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin(form ->
                        form
                                .loginPage("/showMyLoginPage")
                                .loginProcessingUrl("/authenticateTheUser")
                                .defaultSuccessUrl("/home", true)
                                .permitAll()
                )
                .logout(logout -> logout.permitAll()
                )
                .exceptionHandling(configurer ->
                        configurer.accessDeniedPage("/access-denied"));

        return http.build();
    }

}
