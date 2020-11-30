package com.github.saleco.exceptions;

public class RedirectException extends RuntimeException {

  private static final long serialVersionUID = -8083463767397175112L;
  public RedirectException(String location) {
    super(location);
  }
}
