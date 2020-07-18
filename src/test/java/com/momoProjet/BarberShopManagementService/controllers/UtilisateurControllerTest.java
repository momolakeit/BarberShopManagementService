package com.momoProjet.BarberShopManagementService.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.momoProjet.BarberShopManagementService.BarberShopManagementServiceApplication;
import com.momoProjet.BarberShopManagementService.DTO.UserCreateDTO;
import com.momoProjet.BarberShopManagementService.DTO.UtilisateurDTO;
import com.momoProjet.BarberShopManagementService.entities.Business;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

import static org.mockito.ArgumentMatchers.any;


import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,classes = BarberShopManagementServiceApplication.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
public class UtilisateurControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;
    @MockBean
    private RestTemplate restTemplate;



    @Test
    public void createUserBusinessShouldReturnUser() throws URISyntaxException {
        URI uri = new URI("https://www.google.com/");
        ResponseEntity <String> responseEntity= ResponseEntity.created(uri).header("MyResponseHeader", "MyValue").body("Hello World");
        when( restTemplate.exchange(ArgumentMatchers.anyString(),any(HttpMethod.class), any(HttpEntity.class), Mockito.eq(String.class))).thenReturn(responseEntity);

        UserCreateDTO  userCreateDTO =new UserCreateDTO();
        userCreateDTO.setEmail("testUserCreateBusiness@gmail.com");
        userCreateDTO.setPassword("password");
        userCreateDTO.setRole("BUSINESS");
        UtilisateurDTO utilisateur= this.testRestTemplate.postForEntity("http://localhost:" +
                        port +
                        "/createUser",
                userCreateDTO,
                UtilisateurDTO.class).getBody();
        assertTrue(utilisateur.getEmail().contentEquals(userCreateDTO.getEmail()));
        assertNotNull(utilisateur);
    }
    @Test
    public void createUserClientShouldReturnUser() throws URISyntaxException {
        URI uri = new URI("https://www.google.com/");
        ResponseEntity <String> responseEntity= ResponseEntity.created(uri).header("MyResponseHeader", "MyValue").body("Hello World");
        when( restTemplate.exchange(ArgumentMatchers.anyString(),any(HttpMethod.class), any(HttpEntity.class), Mockito.eq(String.class))).thenReturn(responseEntity);
        UserCreateDTO  userCreateDTO =new UserCreateDTO();
        userCreateDTO.setEmail("testUserCreateClient@gmail.com");
        userCreateDTO.setPassword("password");
        userCreateDTO.setRole("CLIENT");
            UtilisateurDTO utilisateur= this.testRestTemplate.postForEntity("http://localhost:" +
                        port +
                        "/createUser",
                userCreateDTO,
                UtilisateurDTO.class).getBody();
        assertTrue(utilisateur.getEmail().contentEquals(userCreateDTO.getEmail()));
        assertNotNull(utilisateur);
    }

    @Test
    public void createUserEmployeShouldReturnUser() throws URISyntaxException {
        URI uri = new URI("https://www.google.com/");
        ResponseEntity <String> responseEntity= ResponseEntity.created(uri).header("MyResponseHeader", "MyValue").body("Hello World");
        when( restTemplate.exchange(ArgumentMatchers.anyString(),any(HttpMethod.class), any(HttpEntity.class), Mockito.eq(String.class))).thenReturn(responseEntity);
        UserCreateDTO  userCreateDTO =new UserCreateDTO();
        userCreateDTO.setEmail("testUserCreateEmploye@gmail.com");
        userCreateDTO.setPassword("password");
        userCreateDTO.setRole("EMPLOYEE");
        UtilisateurDTO utilisateur= this.testRestTemplate.postForEntity("http://localhost:" +
                        port +
                        "/createUser",
                userCreateDTO,
                UtilisateurDTO.class).getBody();
        assertTrue(utilisateur.getEmail().contentEquals(userCreateDTO.getEmail()));
        assertNotNull(utilisateur);
    }

    @Test
    public void addEmployee() throws JSONException, JsonProcessingException {


        JSONArray jsonArray=new JSONArray();
        JSONObject boss=new JSONObject();
        boss.put("email","businesstest@gmail.com");
        boss.put("password","password");
        boss.put("role","business");
        JSONObject employee=new JSONObject();
        employee.put("email","employeetest@gmail.com");
        employee.put("password","employee");
        employee.put("role","employee");
        jsonArray.put(boss);
        jsonArray.put(employee);
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request;



        request=new HttpEntity<String>(jsonArray.toString(),httpHeaders);

        ResponseEntity<String> responseAddEmployee= this.testRestTemplate.exchange("http://localhost:" +
                        port +
                        "/addEmployee", HttpMethod.POST,
                request,
                new ParameterizedTypeReference<String>() {
                });
        //get business and see result
        JSONObject emailJSON=new JSONObject();
        emailJSON.put("email","businesstest@gmail.com");
        emailJSON.put("password","business");
        request=new HttpEntity<String>(emailJSON.toString(),httpHeaders);
        ResponseEntity<String> responseGetUser=this.testRestTemplate.exchange("http://localhost:" +
                        port +
                        "/getUser", HttpMethod.POST,
                request,
                new ParameterizedTypeReference<String>() {
                });

        Business business=new ObjectMapper().readValue(responseGetUser.getBody(),Business.class);


        assertEquals(responseAddEmployee.getStatusCode(), HttpStatus.OK);
        assertEquals(responseGetUser.getStatusCode(), HttpStatus.OK);
        assertTrue(business.getEmployees().get(0).getEmail().contentEquals(employee.get("email").toString()));


    }
    @Test
    public void addEmployeeNotInRoleDecline() throws JSONException, JsonProcessingException {


        JSONArray jsonArray=new JSONArray();
        JSONObject employee=new JSONObject();
        employee.put("email","employeetest@gmail.com");
        employee.put("password","employee");
        jsonArray.put(employee);
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request;


        //authentification request
        JSONObject authentication=new JSONObject();
        authentication.put("username","employeetest@gmail.com");
        authentication.put("password","employee");
        request=new HttpEntity<String>(authentication.toString(),httpHeaders);

        request=new HttpEntity<String>(jsonArray.toString(),httpHeaders);

        ResponseEntity<String> responseAddEmployee= this.testRestTemplate.exchange("http://localhost:" +
                        port +
                        "/addEmployee", HttpMethod.POST,
                request,
                new ParameterizedTypeReference<String>() {
                });




        assertEquals(responseAddEmployee.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);



    }


}