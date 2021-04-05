package com.ensta.librarymanager.test;

import com.ensta.librarymanager.utils.*;
import com.ensta.librarymanager.modele.*;
import java.io.*;

class ModeleTest{
  public static void main(String[] args) {
  System.out.println("ModeleTest is running\n");
  Membre membretest = new Membre(007, "Elon", "Musk", "topSecret", "elonmusk@gmail.com", "06????????", Abonnement.VIP);
  Livre origin = new Livre(1, "Origin","Dan Brown","4582");
  System.out.println(membretest);
  membretest.setPhone("0612345678");
  System.out.println(membretest);
  System.out.println(origin);
  System.out.println(origin.getIsbn());
  }
}
