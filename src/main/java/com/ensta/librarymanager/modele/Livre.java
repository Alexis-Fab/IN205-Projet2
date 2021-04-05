package com.ensta.librarymanager.modele;

public class Livre{
  private int id;
  private String author;
  private String title;
  private String isbn;

  public int getId(){
      return this.id;
  }
  public void setId(Integer id){
      this.id = id;
  }
  public String getTitle() {
      return this.title;
  }
  public void setTitle(String title) {
      this.title = title;
  }
  public String getAuthor() {
      return this.author;
  }
  public void setAuthor(String author) {
      this.author = author;
  }
  public String getIsbn() {
      return this.isbn;
  }
  public void setIsbn(String isbn) {
      this.isbn = isbn;
  }


  public Livre(int id, String author, String title, String isbn) {
    this.id=id; this.author=author; this.title=title; this.isbn=isbn;
  }

  public String toString() {
    return (title + " written by " + author);
  }
}
