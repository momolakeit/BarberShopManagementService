package com.momoProjet.BarberShopManagementService.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.momoProjet.BarberShopManagementService.BarberShopManagementServiceApplication;
import com.momoProjet.BarberShopManagementService.DTO.UserCreateDTO;
import com.momoProjet.BarberShopManagementService.DTO.UtilisateurDTO;
import com.momoProjet.BarberShopManagementService.entities.Business;
import com.momoProjet.BarberShopManagementService.models.AuthenticationRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,classes = BarberShopManagementServiceApplication.class)
public class UtilisateurControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void authenticationShouldReturnJwt(){
        AuthenticationRequest authenticationRequest=new AuthenticationRequest();
        authenticationRequest.setPassword("foo");
        authenticationRequest.setUsername("foo@gmail.com");
        String authenticationResponse= this.restTemplate.postForEntity("http://localhost:" +
                                                                                            port +
                                                                                            "/authenticate",
                                                                                            authenticationRequest,
                                                                                 String.class).getBody();

        assertNotNull(authenticationResponse);
    }
    @Test
    public void connectUserShouldReturnUtilisateurSubtype(){
        AuthenticationRequest authenticationRequest=new AuthenticationRequest();
        authenticationRequest.setPassword("foo");
        authenticationRequest.setUsername("foo@gmail.com");
        String authenticationResponse= this.restTemplate.postForEntity("http://localhost:" +
                        port +
                        "/authenticate",
                authenticationRequest,
                String.class)
                .getBody();

        assertNotNull(authenticationResponse);
    }
    @Test
    public void createUserBusinessShouldReturnUser(){

        UserCreateDTO  userCreateDTO =new UserCreateDTO();
        userCreateDTO.setEmail("business@gmail.com");
        userCreateDTO.setPassword("password");
        userCreateDTO.setRole("BUSINESS");
        UtilisateurDTO utilisateur= this.restTemplate.postForEntity("http://localhost:" +
                        port +
                        "/createUser",
                userCreateDTO,
                UtilisateurDTO.class).getBody();
        assertTrue(utilisateur.getEmail().contentEquals(userCreateDTO.getEmail()));
        assertNotNull(utilisateur);
    }
    @Test
    public void createUserClientShouldReturnUser(){

        UserCreateDTO  userCreateDTO =new UserCreateDTO();
        userCreateDTO.setEmail("client@gmail.com");
        userCreateDTO.setPassword("password");
        userCreateDTO.setRole("CLIENT");
            UtilisateurDTO utilisateur= this.restTemplate.postForEntity("http://localhost:" +
                        port +
                        "/createUser",
                userCreateDTO,
                UtilisateurDTO.class).getBody();
        assertTrue(utilisateur.getEmail().contentEquals(userCreateDTO.getEmail()));
        assertNotNull(utilisateur);
    }
    @Test
    public void createUserEmployeShouldReturnUser(){

        UserCreateDTO  userCreateDTO =new UserCreateDTO();
        userCreateDTO.setEmail("employee@gmail.com");
        userCreateDTO.setPassword("password");
        userCreateDTO.setRole("EMPLOYEE");
        UtilisateurDTO utilisateur= this.restTemplate.postForEntity("http://localhost:" +
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


        //authentification request
        JSONObject authentication=new JSONObject();
        authentication.put("username","businesstest@gmail.com");
        authentication.put("password","business");
        request=new HttpEntity<String>(authentication.toString(),httpHeaders);

        //getToken
        ResponseEntity<String> jwt= this.restTemplate.exchange("http://localhost:" +
                        port +
                        "/authenticate", HttpMethod.POST,
                request,
                new ParameterizedTypeReference<String>() {
                });
        httpHeaders.add("Authorization","bearer "+jwt.getBody().toString());
        request=new HttpEntity<String>(jsonArray.toString(),httpHeaders);

        ResponseEntity<String> responseAddEmployee= this.restTemplate.exchange("http://localhost:" +
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
        ResponseEntity<String> responseGetUser=this.restTemplate.exchange("http://localhost:" +
                        port +
                        "/getUser", HttpMethod.POST,
                request,
                new ParameterizedTypeReference<String>() {
                });

        Business business=new ObjectMapper().readValue(responseGetUser.getBody(),Business.class);

        assertEquals(jwt.getStatusCode(), HttpStatus.OK);
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

        //getToken
        ResponseEntity<String> jwt= this.restTemplate.exchange("http://localhost:" +
                        port +
                        "/authenticate", HttpMethod.POST,
                request,
                new ParameterizedTypeReference<String>() {
                });
        httpHeaders.add("Authorization","bearer "+jwt.getBody().toString());
        request=new HttpEntity<String>(jsonArray.toString(),httpHeaders);

        ResponseEntity<String> responseAddEmployee= this.restTemplate.exchange("http://localhost:" +
                        port +
                        "/addEmployee", HttpMethod.POST,
                request,
                new ParameterizedTypeReference<String>() {
                });



        assertEquals(jwt.getStatusCode(), HttpStatus.OK);
        assertEquals(responseAddEmployee.getStatusCode(), HttpStatus.FORBIDDEN);



    }


}