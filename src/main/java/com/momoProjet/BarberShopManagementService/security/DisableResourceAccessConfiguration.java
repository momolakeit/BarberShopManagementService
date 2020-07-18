package com.momoProjet.BarberShopManagementService.security;

import net.bytebuddy.description.NamedElement;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@ConditionalOnProperty(name = "config.securite.active", havingValue = "false")
public class DisableResourceAccessConfiguration  extends ResourceServerConfigurerAdapter implements ResourcesAccessImpl {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/***").permitAll().anyRequest().anonymous();
    }
}
