package com.momoProjet.BarberShopManagementService.service;

import com.momoProjet.BarberShopManagementService.DTO.UserCreateDTO;
import com.momoProjet.BarberShopManagementService.DTO.UtilisateurDTO;
import com.momoProjet.BarberShopManagementService.entities.Business;
import com.momoProjet.BarberShopManagementService.entities.Employee;
import com.momoProjet.BarberShopManagementService.entities.Utilisateur;
import com.momoProjet.BarberShopManagementService.repositories.UtilisteurBaseRepository;
import com.momoProjet.BarberShopManagementService.service.converter.UtilisateurToDTOGenericConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
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
}

