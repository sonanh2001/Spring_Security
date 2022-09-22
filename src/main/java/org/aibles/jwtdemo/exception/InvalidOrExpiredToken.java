package org.aibles.jwtdemo.exception;

import org.springframework.http.HttpStatus;

public class InvalidOrExpiredToken extends BaseException{

  public InvalidOrExpiredToken() {
    setStatus(HttpStatus.FORBIDDEN.value());
    setCode("org.aibles.jwtdemo.exception.InvalidOrExpiredToken");
  }
}
