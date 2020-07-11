package com.momoProjet.BarberShopManagementService.DTO;

import org.springframework.stereotype.Service;

@Service
public class EmployeeDTO extends UtilisateurDTO {
    BusinessDTO boss;

    public BusinessDTO getBoss() {
        return boss;
    }

    public void setBoss(BusinessDTO boss) {
        this.boss = boss;
    }
}
