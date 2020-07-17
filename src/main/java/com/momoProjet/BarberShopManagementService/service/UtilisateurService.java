package com.momoProjet.BarberShopManagementService.service;

import com.momoProjet.BarberShopManagementService.DTO.UserCreateDTO;
import com.momoProjet.BarberShopManagementService.DTO.UtilisateurDTO;
import com.momoProjet.BarberShopManagementService.entities.Business;
import com.momoProjet.BarberShopManagementService.entities.Employee;
import com.momoProjet.BarberShopManagementService.entities.Utilisateur;
import com.momoProjet.BarberShopManagementService.repositories.UtilisteurBaseRepository;
import com.momoProjet.BarberShopManagementService.service.converter.UtilisateurToDTOGenericConverter;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class UtilisateurService {

    private UtilisteurBaseRepository utilisteurBaseRepository;
    private UtilisateurToDTOGenericConverter utilisateurToDTOGenericConverter;

    private final String BUSINESS_ROLE= "BUSINESS";
    private final String CLIENT_ROLE= "CLIENT";
    private final String EMPLOYEE_ROLE= "EMPLOYEE";

    @Autowired
    public UtilisateurService(UtilisteurBaseRepository utilisteurBaseRepository,UtilisateurToDTOGenericConverter utilisateurToDTOGenericConverter) {
        this.utilisteurBaseRepository = utilisteurBaseRepository;
        this.utilisateurToDTOGenericConverter=utilisateurToDTOGenericConverter;
    }

    public  UtilisateurDTO createUser(Utilisateur  utilisateurGeneric) throws InvocationTargetException, IllegalAccessException {

        if(utilisteurBaseRepository.findByEmail(utilisateurGeneric.getEmail())== null){
           return  utilisateurToDTOGenericConverter.convert((Utilisateur) utilisteurBaseRepository.save(utilisateurGeneric));
        }

        return null;
    }
    public <T extends Utilisateur> T retrieveUser(String email){
        return (T)utilisteurBaseRepository.findByEmail(email);
    }
    public Utilisateur connectUser(String email,String password){
         Utilisateur user =retrieveUser(email);

         if(user!= null){
             if(user.getPassword().contentEquals(password)){
                 return user;
             }

         }
         return null;
    }
    private void deleteUser(Utilisateur utilisateur){
        utilisteurBaseRepository.delete(utilisateur);
    }

    public <T extends Utilisateur> T createUtilsateurSubtype(T  utilisateurGeneric,
                                                             UserCreateDTO userCreateDTO){

       utilisateurGeneric.setEmail(userCreateDTO.getEmail());
       utilisateurGeneric.setPassword(userCreateDTO.getPassword());

        return utilisateurGeneric;
    }
    public List<Employee> returnAllEmployees(Business business){
        return business.getEmployees();
    }

    public void addEmployee(Business business,Employee employee){
        if(Objects.isNull(employee.getBoss())){
            business.getEmployees().add(employee);
        }
    }
    
    public void addEmployee(List<UtilisateurDTO> bossAndEmployee){//test avec integration
        UtilisateurDTO boss= bossAndEmployee.stream().filter(x -> x.getRole().toUpperCase().
                                                                  contentEquals(BUSINESS_ROLE)).findFirst().get();
        UtilisateurDTO employe= bossAndEmployee.stream().filter(x -> x.getRole().toUpperCase().
                contentEquals(EMPLOYEE_ROLE)).findFirst().get();

        Business business= retrieveUser(boss.getEmail());
        if(Objects.nonNull(business)){
            Employee nouvelEngage =retrieveUser(employe.getEmail());
            addEmployee(business,nouvelEngage);
            utilisteurBaseRepository.save(nouvelEngage);
        }

    }
    public String returnAuthorizationServerToken(String username,String password){
        ResponseEntity<String> response = null;
        RestTemplate restTemplate = new RestTemplate();

        // According OAuth documentation we need to send the client id and secret key in the header for authentication
        String credentials = "fekoumbrek:fekoumbrek@123";
        String encodedCredentials = new String(Base64.encodeBase64(credentials.getBytes()));

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("Authorization", "Basic " + encodedCredentials);

        HttpEntity<String> request = new HttpEntity<String>(headers);

        String access_token_url = "http://localhost:8080/oauth/token";
        access_token_url += "?username=" + username ;
        access_token_url += "&password=" + password ;
        access_token_url += "&grant_type=password";


        response = restTemplate.exchange(access_token_url, HttpMethod.POST, request, String.class);

        return response.getBody();
    }
}

