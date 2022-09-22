package org.aibles.jwtdemo.exception;

import org.springframework.http.HttpStatus;

public class EmailExistedException extends BaseException{

  public EmailExistedException(String email) {
    setStatus(HttpStatus.BAD_REQUEST.value());
    setCode("org.aibles.jwtdemo.exception.EmailExistedException");
    addParams("email", email);
  }
}
