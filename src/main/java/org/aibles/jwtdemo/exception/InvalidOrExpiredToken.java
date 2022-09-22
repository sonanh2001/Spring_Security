package org.aibles.jwtdemo.exception;

import org.springframework.http.HttpStatus;

public class InvalidOrExpiredToken extends BaseException{

  public InvalidOrExpiredToken() {
    super();
    setCode("org.aibles.jwtdemo.exception.InvalidOrExpiredToken");
    setStatus(HttpStatus.UNAUTHORIZED.value());
  }
}
