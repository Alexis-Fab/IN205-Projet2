package com.ensta.librarymanager.exception;

import com.ensta.librarymanager.persistence.*;

public class DaoException extends Exception {
  public DaoException() {
    super();
  }

  public DaoException(String message, Throwable cause) {
    super(message, cause);
  }

  public DaoException(String message) {
    super(message);
  }
}
