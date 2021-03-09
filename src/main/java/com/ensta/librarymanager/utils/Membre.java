package utils;

class Membre {
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

  public String toString() {
    return( forename + " " + surname);
  }
}
