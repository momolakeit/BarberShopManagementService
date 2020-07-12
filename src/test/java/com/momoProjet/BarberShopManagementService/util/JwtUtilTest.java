package com.momoProjet.BarberShopManagementService.util;

import com.momoProjet.BarberShopManagementService.security.service.MyUserDetailsService;
import com.momoProjet.BarberShopManagementService.security.service.authorities.CustomAuthority;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtUtilTest {
    private String SECRET_KEY ="secret";

    JwtUtil jwtUtil=new JwtUtil();
    @Test
    public void generateTokenTestRecupererUserName(){
        UserDetails userDetails=createUserDetails();

        String token=jwtUtil.generateToken(userDetails);
        Claims claims= Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
        claims.getSubject();
        assertEquals(claims.getSubject(),"foo");

    }

    @Test
    public void extractUserName(){
        UserDetails userDetails=createUserDetails();
        String token=jwtUtil.generateToken(userDetails);

        assertEquals(jwtUtil.extractUsername(token),"foo");

    }
    @Test
    public void extractDate(){
        UserDetails userDetails=createUserDetails();
        String token=jwtUtil.generateToken(userDetails);
        Date date= Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getExpiration();
        assertEquals(jwtUtil.extractExpiration(token),date);

    }
    private UserDetails createUserDetails(){
        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                List<CustomAuthority> customAuthorities=new ArrayList<>();
                customAuthorities.add(new CustomAuthority("BUSINESS".toUpperCase()));
                return customAuthorities;
            }

            @Override
            public String getPassword() {
                return null;
            }

            @Override
            public String getUsername() {
                return "foo";
            }

            @Override
            public boolean isAccountNonExpired() {
                return false;
            }

            @Override
            public boolean isAccountNonLocked() {
                return false;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return false;
            }

            @Override
            public boolean isEnabled() {
                return false;
            }
        };

    }

}