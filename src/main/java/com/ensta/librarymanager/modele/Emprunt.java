package com.ensta.librarymanager.modele;

import java.time.*;

public class Emprunt{
  private int id;
  private Membre member;
  private Livre book;
  private LocalDate borrowDate;
  private LocalDate returnDate;

  public int getId() {
      return this.id;
  }
  public void setId(int id) {
      this.id = id;
  }
  public Livre getBook() {
      return book;
  }
  public void setBook(Integer book) {
      this.book = book;
  }
  public Membre getMember() {
      return member;
  }
  public void setMember(Membre member) {
      this.member = member;
  }
  public LocalDate getBorrowDate() {
      return this.borrowDate;
  }
  public void setBorrowDate(LocalDate borrowDate) {
      this.borrowDate = borrowDate;
  }
  public LocalDate getReturnDate() {
      return this.returnDate;
  }
  public void setReturnDate(LocalDate returnDate) {
      this.returnDate = returnDate;
  }


  public Emprunt(int id, Membre member, Livre book, LocalDate borrowDate, LocalDate returnDate) {
    this.id=id; this.member=member; this.book=book; this.borrowDate=borrowDate; this.returnDate=returnDate;
  }
  public String toString() {
    return( member + "borrowed " + book + " the " + borrowDate + " and should return it on " + returnDate);
  }
}
