package org.aibles.jwtdemo.exception;

import org.springframework.http.HttpStatus;

public class UsernameExistedException extends BaseException {

  public UsernameExistedException(String username) {
    setStatus(HttpStatus.BAD_REQUEST.value());
    setCode("org.aibles.jwtdemo.exception.UserExistedException");
    addParams("username", username);
  }
}
