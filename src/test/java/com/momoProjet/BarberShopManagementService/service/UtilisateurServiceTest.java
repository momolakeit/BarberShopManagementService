package com.momoProjet.BarberShopManagementService.service;

import com.momoProjet.BarberShopManagementService.DTO.*;
import com.momoProjet.BarberShopManagementService.entities.Business;
import com.momoProjet.BarberShopManagementService.entities.Client;
import com.momoProjet.BarberShopManagementService.entities.Employee;
import com.momoProjet.BarberShopManagementService.entities.Utilisateur;
import com.momoProjet.BarberShopManagementService.repositories.UtilisteurBaseRepository;
import com.momoProjet.BarberShopManagementService.service.converter.UtilisateurToDTOGenericConverter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class UtilisateurServiceTest {
    private final String BUSINESS_ROLE= "BUSINESS";
    private final String CLIENT_ROLE= "CLIENT";
    private final String EMPLOYEE_ROLE= "EMPLOYEE";
    @Mock
    private UtilisteurBaseRepository utilisteurBaseRepository;



    UtilisateurService utilisateurService ;


    @Test
    public void createUserNewUserReturnUtilisateurBusiness() throws InvocationTargetException, IllegalAccessException {
        utilisateurService= new UtilisateurService(utilisteurBaseRepository,new UtilisateurToDTOGenericConverter(),new RestTemplate());
        Business business=new Business();
        business.setEmail("email@gmail.com");
        business.setPassword("password");
        when(utilisteurBaseRepository.findByEmail(anyString())).thenReturn(null);
        when(utilisteurBaseRepository.save(any(Utilisateur.class))).thenReturn(business);
        Utilisateur utilisateur=business;
        UtilisateurDTO user=utilisateurService.createUser(utilisateur);
        assertTrue (user instanceof BusinessDTO);
    }
    @Test
    public void createUserNewUserReturnUtilisateurClient() throws InvocationTargetException, IllegalAccessException {
        utilisateurService= new UtilisateurService(utilisteurBaseRepository,new UtilisateurToDTOGenericConverter(),new RestTemplate());;
        Client client=new Client();
        client.setEmail("email@gmail.com");
        client.setPassword("password");
        when(utilisteurBaseRepository.findByEmail(anyString())).thenReturn(null);
        when(utilisteurBaseRepository.save(any(Utilisateur.class))).thenReturn(client);
        Utilisateur utilisateur=client;
        UtilisateurDTO user=utilisateurService.createUser(utilisateur);
        assertTrue (user instanceof ClientDTO);

    }

    @Test
    public void createUserNewUserReturnUtilisateurEmploye() throws InvocationTargetException, IllegalAccessException {
        utilisateurService= new UtilisateurService(utilisteurBaseRepository,new UtilisateurToDTOGenericConverter(),new RestTemplate());;
        Employee employee=new Employee();
        employee.setEmail("email@gmail.com");
        employee.setPassword("password");
        when(utilisteurBaseRepository.findByEmail(anyString())).thenReturn(null);
        when(utilisteurBaseRepository.save(any(Utilisateur.class))).thenReturn(employee);
        Utilisateur utilisateur=employee;
        UtilisateurDTO user=utilisateurService.createUser(utilisateur);
        assertTrue (user instanceof EmployeeDTO );

    }
    @Test
    public void createUserExistingUserReturnUtilisateurBusiness() throws InvocationTargetException, IllegalAccessException {
        utilisateurService= new UtilisateurService(utilisteurBaseRepository,new UtilisateurToDTOGenericConverter(),new RestTemplate());;
        Business business=new Business();
        business.setEmail("email@gmail.com");
        business.setPassword("password");
        when(utilisteurBaseRepository.findByEmail(anyString())).thenReturn(business);
        Utilisateur utilisateur=business;
        UtilisateurDTO user=utilisateurService.createUser(utilisateur);
        assertNull(user);

    }
    @Test
    public void createUserExistingUserReturnUtilisateurClient() throws InvocationTargetException, IllegalAccessException {
        utilisateurService= new UtilisateurService(utilisteurBaseRepository,new UtilisateurToDTOGenericConverter(),new RestTemplate());;
        Client client=new Client();
        client.setEmail("email@gmail.com");
        client.setPassword("password");
        when(utilisteurBaseRepository.findByEmail(anyString())).thenReturn(client);
        Utilisateur utilisateur=client;
        UtilisateurDTO user=utilisateurService.createUser(utilisateur);
        assertNull(user);

    }
    @Test
    public void createUserExistingUserReturnUtilisateurEmployee() throws InvocationTargetException, IllegalAccessException {
        utilisateurService= new UtilisateurService(utilisteurBaseRepository,new UtilisateurToDTOGenericConverter(),new RestTemplate());;
        Employee employee=new Employee();
        employee.setEmail("email@gmail.com");
        employee.setPassword("password");
        when(utilisteurBaseRepository.findByEmail(anyString())).thenReturn(employee);
        Utilisateur utilisateur=employee;
        UtilisateurDTO user=utilisateurService.createUser(utilisateur);
        assertNull(user);

    }
    @Test
    public void connectUserUserFoundMatchingCredentials(){
        utilisateurService= new UtilisateurService(utilisteurBaseRepository,new UtilisateurToDTOGenericConverter(),new RestTemplate());;
        Business business=new Business();
        business.setEmail("email@gmail.com");
        business.setPassword("password");
        when(utilisteurBaseRepository.findByEmail(anyString())).thenReturn(business);
        Utilisateur user=utilisateurService.connectUser("email@gmail.com","password");
        assertTrue(user instanceof  Business);
        assertEquals(user.getEmail(),business.getEmail());
        assertEquals(user.getPassword(),business.getPassword());

    }
    @Test
    public void connectUserUserNotFoundReturnNull(){
        utilisateurService= new UtilisateurService(utilisteurBaseRepository,new UtilisateurToDTOGenericConverter(),new RestTemplate());;
        Business business=new Business();
        business.setEmail("email@gmail.com");
        business.setPassword("password");
        when(utilisteurBaseRepository.findByEmail(anyString())).thenReturn(business);
        Utilisateur user=utilisateurService.connectUser("email@gmail.com","password");
        assertEquals(user.getEmail(),business.getEmail());
        assertEquals(user.getPassword(),business.getPassword());

    }
    @Test
    public void createUtilisateurSubbTupeMemeTypeEtCredentialsRetourne(){
        UserCreateDTO userCreateDTO=new UserCreateDTO();
        userCreateDTO.setEmail("foo");
        userCreateDTO.setPassword("foo");
        utilisateurService=new UtilisateurService(utilisteurBaseRepository,new UtilisateurToDTOGenericConverter(),new RestTemplate());;
        Utilisateur utilisateur= utilisateurService.createUtilsateurSubtype(new Business(),userCreateDTO);
        assertTrue(utilisateur instanceof Business);
        assertEquals(utilisateur.getPassword(),userCreateDTO.getPassword());
        assertEquals(utilisateur.getEmail(),userCreateDTO.getEmail());
    }
    @Test
    public void returnEmployeesForBusiness(){
        Employee employee=new Employee();
        employee.setNom("emp");
        Business business=new Business();
        business.setNom("buss");
        List<Employee> employeesList=new ArrayList<>();
        employeesList.add(employee);
        business.setEmployees(employeesList);
        utilisateurService=new UtilisateurService(utilisteurBaseRepository,new UtilisateurToDTOGenericConverter(),new RestTemplate());;
        List<Employee> assertListEmp=utilisateurService.returnAllEmployees(business);
        assertEquals(1, ( assertListEmp).size());

    }
    @Test
    public void returnEmployeesNoEmployee(){
        Business business=new Business();
        business.setNom("buss");
        List<Employee> employeesList=new ArrayList<>();
        business.setEmployees(employeesList);
        utilisateurService=new UtilisateurService(utilisteurBaseRepository,new UtilisateurToDTOGenericConverter(),new RestTemplate());;
        List<Employee> assertListEmp=utilisateurService.returnAllEmployees(business);
        assertEquals(0,assertListEmp.size());

    }





}