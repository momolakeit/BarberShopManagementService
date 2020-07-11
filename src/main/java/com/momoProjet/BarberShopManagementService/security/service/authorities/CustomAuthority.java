package com.momoProjet.BarberShopManagementService.security.service.authorities;

import org.springframework.security.core.GrantedAuthority;

public class CustomAuthority implements GrantedAuthority {
    public String Authority;

    public CustomAuthority(String authority) {
        Authority = authority;
    }

    public void setAuthority(String authority) {
        Authority = authority;
    }

    @Override
    public String getAuthority() {
        return this.Authority;
    }
}
