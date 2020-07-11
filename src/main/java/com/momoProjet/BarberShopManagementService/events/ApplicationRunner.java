package com.momoProjet.BarberShopManagementService.events;

import com.momoProjet.BarberShopManagementService.entities.Business;
import com.momoProjet.BarberShopManagementService.entities.Employee;
import com.momoProjet.BarberShopManagementService.repositories.UtilisteurBaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ApplicationRunner implements CommandLineRunner {
    @Autowired
    UtilisteurBaseRepository utilisateurBaseRepository;
    @Override
    public void run(String... args) throws Exception {
        Business business =new Business();
        business.setEmail("businesstest@gmail.com");
        business.setPassword("business");
        Employee employee=new Employee();
        employee.setEmail("employeetest@gmail.com");
        employee.setPassword("employee");
        utilisateurBaseRepository.save(business);
        utilisateurBaseRepository.save(employee);


    }
}
