package com.ensta.librarymanager.modele;

import com.ensta.librarymanager.modele.Abonnement;

public class Membre {

  private int id;
  private String forename;
  private String surname;
  private String adress;
  private String mail;
  private String phone;
  private Abonnement subscription;

  public void setId(int id){
    this.id=id;
  }
  public void setForename(String forename){
    this.forename=forename;
  }
  public void setSurname(String surname){
    this.surname=surname;
  }
  public void setAdress(String adress){
    this.adress=adress;
  }
  public void setMail(String mail){
    this.mail=mail;
  }
  public void setPhone(String phone){
    this.phone=phone;
  }
  public void setSubscription(Abonnement subscription){
    this.subscription=subscription;
  }

  public int getId(){
    return id;
  }
  public String getForename(){
    return forename;
  }
  public String getSurname(){
    return surname;
  }
  public String getAdress(){
    return adress;
  }
  public String getMail(){
    return mail;
  }
  public String getPhone(){
    return phone;
  }
  public Abonnement getSubscription(){
    return subscription;
  }

  public Membre() {
    id=-1; forename=""; surname=""; adress=""; mail=""; phone=""; subscription=Abonnement.BASIC;
  }

  public Membre(int myId, String myForename, String mySurname, String myAdress, String myMail, String myPhone, Abonnement mySubscription) {
    id=myId; forename=myForename; surname=mySurname; adress=myAdress; mail=myMail; phone=myPhone; subscription=mySubscription;
  }

  public String toString() {
		return ("{"	+ "forename:" + forename + ", "
				        + "surname: " + surname + ", "
        				+ "adress: " + adress + ", "
        				+ "mail: " + mail + ", "
        				+ "phone: " + phone + ", "
        				+ "subscription: " + subscription + "}");
	}
}
