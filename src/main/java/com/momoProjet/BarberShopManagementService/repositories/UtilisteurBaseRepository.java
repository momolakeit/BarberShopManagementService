package com.momoProjet.BarberShopManagementService.repositories;

import com.momoProjet.BarberShopManagementService.entities.Utilisateur;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UtilisteurBaseRepository <T extends Utilisateur>  extends CrudRepository<T,Long> {
   public  <T extends Utilisateur> T findByEmail(String email);
}
