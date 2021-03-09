package utils;

import java.time.*;

class Emprunt{
  private int id;
  private Membre member;
  private Livre book;
  private LocalDate borrowDate;
  private LocalDate returnDate;

  public String toString() {
    return( member + "borrowed " + book + " the " + borrowDate + " and should return it on " + returnDate);
  }
}
