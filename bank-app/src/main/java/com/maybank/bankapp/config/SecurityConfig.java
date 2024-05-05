package com.maybank.bankapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * SecurityConfig class is responsible for configuring security settings for the application.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * Configures security settings for HTTP requests.
     *
     * @param http HttpSecurity object to configure security settings.
     * @throws Exception if an error occurs during configuration.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers().permitAll() // Allow access to H2 console without authentication
            //.antMatchers("/api/**").authenticated() // Require authentication for API endpoints
            .anyRequest().permitAll() // Allow anonymous access to all other URLs
            .and()
            .csrf().disable() // Disable CSRF protection
            .headers().frameOptions().disable(); // Disable frame options
    }

}

