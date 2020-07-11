package com.momoProjet.BarberShopManagementService.DTO;

import java.util.List;

public class BusinessDTO extends UtilisateurDTO {
    List<EmployeeDTO> employees;

    public List<EmployeeDTO> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeDTO> employees) {
        this.employees = employees;
    }
}
