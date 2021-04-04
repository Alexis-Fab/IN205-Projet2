package com.ensta.librarymanager.modele;

public class Livre{
  private int id;
  private String author;
  private String title;
  private String isbn;

  public Livre(int id, String author, String title, String isbn) {
    this.id=id; this.author=author; this.title=title; this.isbn=isbn;
  }

  public String toString() {
    return (title + " written by " + author);
  }
}
