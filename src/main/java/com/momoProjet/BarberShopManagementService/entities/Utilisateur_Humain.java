package com.momoProjet.BarberShopManagementService.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Entity
public abstract class Utilisateur_Humain extends Utilisateur {
    @Column(name ="cote" )
    private String cote;

    public String getCote() {
        return cote;
    }

    public void setCote(String cote) {
        this.cote = cote;
    }
}
