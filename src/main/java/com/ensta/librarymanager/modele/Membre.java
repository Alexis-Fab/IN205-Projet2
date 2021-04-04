package com.ensta.librarymanager.modele;

public class Membre {
  public enum Abonnement {
    BASIC,
    PREMIUM,
    VIP,
  };
  private int id;
  private String forename;
  private String surname;
  private String adress;
  private String mail;
  private String phone;
  private Abonnement subscription;

  public Membre(int myId, String myForename, String mySurname, String myAdress, String myMail, String myPhone, Abonnement mySubscription) {
    id=myId; forename=myForename; surname=mySurname; adress=myAdress; mail=myMail; phone=myPhone; subscription=mySubscription;
  }

  public String toString() {
    return( forename + " " + surname);
  }
}
