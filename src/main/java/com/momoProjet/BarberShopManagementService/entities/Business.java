package com.momoProjet.BarberShopManagementService.entities;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table
public class Business extends Utilisateur{
    @OneToMany
    private List<Employee> employees;

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> utilisateurs) {
        this.employees = utilisateurs;
    }
}
