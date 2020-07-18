package com.momoProjet.BarberShopManagementService.controllers;

import com.momoProjet.BarberShopManagementService.DTO.UserCreateDTO;
import com.momoProjet.BarberShopManagementService.DTO.UtilisateurDTO;
import com.momoProjet.BarberShopManagementService.entities.Business;
import com.momoProjet.BarberShopManagementService.entities.Client;
import com.momoProjet.BarberShopManagementService.entities.Employee;
import com.momoProjet.BarberShopManagementService.entities.Utilisateur;
import com.momoProjet.BarberShopManagementService.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;



@RestController
public class UtilisateurController {
    private final String BUSINESS_ROLE= "BUSINESS";
    private final String CLIENT_ROLE= "CLIENT";
    private final String EMPLOYEE_ROLE= "EMPLOYEE";


    @Autowired
    private UtilisateurService utilisateurService;
    


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
       createdUtilisateur.setJwt(utilisateurService.returnAuthorizationServerToken(utilisateur.getEmail(),
                                                                                    utilisateur.getPassword()));
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
