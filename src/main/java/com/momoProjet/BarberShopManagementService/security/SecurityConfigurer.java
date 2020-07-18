package com.momoProjet.BarberShopManagementService.security;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;


@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
@ConditionalOnProperty(name = "config.securite.active", havingValue = "true",matchIfMissing = true)
public class SecurityConfigurer extends WebSecurityConfigurerAdapter implements SecurityConfigurerImpl {



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers( "/createUser", "/h2-console/**").permitAll().
                and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER);

        http.headers().frameOptions().disable();
    }
}