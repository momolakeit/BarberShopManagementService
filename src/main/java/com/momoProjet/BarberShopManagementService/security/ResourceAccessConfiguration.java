package com.momoProjet.BarberShopManagementService.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
public class ResourceAccessConfiguration extends ResourceServerConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/createUser").permitAll().anyRequest().anonymous();
        //http.authorizeRequests().antMatchers("/**").permitAll();<< also can
    }
}
