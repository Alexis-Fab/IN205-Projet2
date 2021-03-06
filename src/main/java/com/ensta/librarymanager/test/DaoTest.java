package com.ensta.librarymanager.test;

import com.ensta.librarymanager.utils.*;
import com.ensta.librarymanager.persistence.*;
import com.ensta.librarymanager.modele.*;
import com.ensta.librarymanager.dao.*;

import java.time.LocalDate;

public class DaoTest{
  public static void main(String[] args) {
    MembreDao membreImplTest = MembreDaoImpl.getInstance();
    LivreDao livreImplTest = LivreDaoImpl.getInstance();
    EmpruntDao empruntImplTest = EmpruntDaoImpl.getInstance();
    try {
      int id_membre = membreImplTest.create("Fabrigoule", "Alexis", "Allée des Techniques Avancées", "alexis.fabrigoule@ensta-paris.fr", "0600112233");
      Membre alexis = membreImplTest.getById(id_membre);
      alexis.setAdress("bat E");
      alexis.setSubscription(Abonnement.VIP);
      membreImplTest.update(alexis);

      int id_origin = livreImplTest.create("Origin", "Dan Brown", "0001");
      Livre origin = livreImplTest.getById(id_origin);
      origin.setTitle("The Origin");
      origin.setIsbn("0002");
      livreImplTest.update(origin);

      LocalDate date_retour = LocalDate.of(2021,04,04);
      empruntImplTest.create(id_membre, id_origin, date_retour);

    }
    catch (Exception e) {
      e.printStackTrace();
    };
  };
}
