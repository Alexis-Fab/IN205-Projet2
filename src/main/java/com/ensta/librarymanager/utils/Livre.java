package utils;

class Livre{
  private int id;
  private String author;
  private String title;
  private String isbn;

  public String toString() {
    return (title + " written by " + author);
  }
}
