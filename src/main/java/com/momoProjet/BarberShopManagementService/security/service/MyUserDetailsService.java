package com.momoProjet.BarberShopManagementService.security.service;

import com.momoProjet.BarberShopManagementService.entities.Utilisateur;
import com.momoProjet.BarberShopManagementService.security.service.authorities.CustomAuthority;
import com.momoProjet.BarberShopManagementService.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UtilisateurService utilisateurService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utilisateur utilisateur=utilisateurService.retrieveUser(username);
        List<CustomAuthority> customAuthorities=new ArrayList<>();
        customAuthorities.add(new CustomAuthority(utilisateur.getClass().getSimpleName().toUpperCase()));
        User user=new User(utilisateur.getEmail(),utilisateur.getPassword(),customAuthorities);
        return user;
    }
}
