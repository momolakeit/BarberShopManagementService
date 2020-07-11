package com.momoProjet.BarberShopManagementService.service.converter;

import com.momoProjet.BarberShopManagementService.DTO.BusinessDTO;
import com.momoProjet.BarberShopManagementService.DTO.ClientDTO;
import com.momoProjet.BarberShopManagementService.DTO.EmployeeDTO;
import com.momoProjet.BarberShopManagementService.entities.Business;
import com.momoProjet.BarberShopManagementService.entities.Client;
import com.momoProjet.BarberShopManagementService.entities.Employee;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class UtilsateurToDTOGenericConverterTest {

        UtilisateurToDTOGenericConverter utilsateurToDTOGenericConverter;
        @Test
        public void convertBusinessToDTO() throws InvocationTargetException, IllegalAccessException {
            utilsateurToDTOGenericConverter=new UtilisateurToDTOGenericConverter();
            Business business=new Business();
            business.setEmail("Bizz@gmail.com");
            business.setPassword("Bizz@gmail.com");
            Employee employee=new Employee();
            employee.setEmail("emp@gmail.com");
            employee.setPassword("emp@gmail.com");
            List<Employee> employees=new ArrayList<>();
            employees.add(employee);
            business.setEmployees(employees);
            BusinessDTO utilisateurDTO= (BusinessDTO) utilsateurToDTOGenericConverter.convert(business);
            assertTrue(business.getEmail().contentEquals(utilisateurDTO.getEmail()));
            assertTrue(business.getEmployees().get(0).getEmail().contentEquals(utilisateurDTO.getEmployees().get(0).getEmail()));
        }
    @Test
    public void convertEmployeeToDTO() throws InvocationTargetException, IllegalAccessException {
        utilsateurToDTOGenericConverter=new UtilisateurToDTOGenericConverter();
        Employee employee=new Employee();
        employee.setEmail("emp@gmail.com");
        employee.setPassword("emp@gmail.com");
        EmployeeDTO utilisateurDTO= (EmployeeDTO) utilsateurToDTOGenericConverter.convert(employee);
        assertTrue(employee.getEmail().contentEquals(utilisateurDTO.getEmail()));

    }
    @Test
    public void convertClientToDTO() throws InvocationTargetException, IllegalAccessException {
        utilsateurToDTOGenericConverter=new UtilisateurToDTOGenericConverter();
        Client client=new Client();
        client.setEmail("emp@gmail.com");
        client.setPassword("emp@gmail.com");
        ClientDTO utilisateurDTO= (ClientDTO) utilsateurToDTOGenericConverter.convert(client);
        assertTrue(client.getEmail().contentEquals(utilisateurDTO.getEmail()));

    }
}