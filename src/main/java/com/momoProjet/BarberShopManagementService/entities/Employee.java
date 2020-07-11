package com.momoProjet.BarberShopManagementService.entities;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
public class Employee extends Utilisateur_Humain{
    @ManyToOne
    @JoinColumn
    private Business boss;

    public Business getBoss() {
        return boss;
    }

    public void setBoss(Business boss) {
        this.boss = boss;
    }
}
