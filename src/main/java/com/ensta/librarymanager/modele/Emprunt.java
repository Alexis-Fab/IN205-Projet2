package com.ensta.librarymanager.modele;

import java.time.*;

public class Emprunt{
  private int id;
  private Membre member;
  private Livre book;
  private LocalDate borrowDate;
  private LocalDate returnDate;

  public Emprunt(int id, Membre member, Livre book, LocalDate borrowDate, LocalDate returnDate) {
    this.id=id; this.member=member; this.book=book; this.borrowDate=borrowDate; this.returnDate=returnDate;
  }
  public String toString() {
    return( member + "borrowed " + book + " the " + borrowDate + " and should return it on " + returnDate);
  }
}
