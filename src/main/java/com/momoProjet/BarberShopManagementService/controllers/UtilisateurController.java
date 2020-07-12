package com.momoProjet.BarberShopManagementService.controllers;


import com.momoProjet.BarberShopManagementService.DTO.UserCreateDTO;
import com.momoProjet.BarberShopManagementService.DTO.UtilisateurDTO;
import com.momoProjet.BarberShopManagementService.entities.Business;
import com.momoProjet.BarberShopManagementService.entities.Client;
import com.momoProjet.BarberShopManagementService.entities.Employee;
import com.momoProjet.BarberShopManagementService.entities.Utilisateur;

import com.momoProjet.BarberShopManagementService.models.AuthenticationRequest;
import com.momoProjet.BarberShopManagementService.models.AuthenticationResponse;
import com.momoProjet.BarberShopManagementService.service.UtilisateurService;
import com.momoProjet.BarberShopManagementService.util.JwtUtil;
import com.momoProjet.BarberShopManagementService.util.springUtil.CustomUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


@RestController
public class UtilisateurController {
    private final String BUSINESS_ROLE= "BUSINESS";
    private final String CLIENT_ROLE= "CLIENT";
    private final String EMPLOYEE_ROLE= "EMPLOYEE";
    @Autowired
    private AuthenticationManager authenticationManager;

    @Qualifier("myUserDetailsService")
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UtilisateurService utilisateurService;




    @RequestMapping(path ="/authenticate",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest authenticationRequest)throws Exception{
        try {
                 authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));

        }catch (BadCredentialsException  e ){
            throw  new Exception("incorrect username and password",e);
        }

        final UserDetails userDetails= userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt =jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(jwt);
    }
    @RequestMapping(path ="/generateToken",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> generateToken(@RequestBody AuthenticationRequest authenticationRequest)throws Exception{
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));

        }catch (BadCredentialsException  e ){
            throw  new Exception("incorrect username and password",e);
        }

        final UserDetails userDetails= userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt =jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(jwt);
    }


    @RequestMapping(path ="/createUser",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> createUser(@RequestBody UserCreateDTO user)throws Exception{
        Utilisateur utilisateur=null;
        switch(user.getRole()) {
            case BUSINESS_ROLE:
                utilisateur=utilisateurService.createUtilsateurSubtype(new Business(),user);
                break;
            case CLIENT_ROLE:
                utilisateur=utilisateurService.createUtilsateurSubtype(new Client(),user);
                break;
            case EMPLOYEE_ROLE:
                utilisateur=utilisateurService.createUtilsateurSubtype(new Employee(),user);

                break;

        }
        UtilisateurDTO createdUtilisateur=utilisateurService.createUser(utilisateur);
        final UserDetails userDetails= userDetailsService.loadUserByUsername(createdUtilisateur.getEmail());
        final String jwt =jwtUtil.generateToken(userDetails);
        createdUtilisateur.setJwt(jwt);
       return ResponseEntity.ok(createdUtilisateur);

    }
    @PreAuthorize("hasAuthority('BUSINESS')")
    @RequestMapping(path ="/addEmployee",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> addEmployee(@RequestBody List<UtilisateurDTO> bossAndEmployee)throws Exception{
        utilisateurService.addEmployee(bossAndEmployee);
        return  ResponseEntity.ok().build();

    }
    @RequestMapping(path ="/getUser",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> getUser(@RequestBody UtilisateurDTO user)throws Exception{
        return  ResponseEntity.ok(utilisateurService.retrieveUser(user.getEmail()));

    }



}
