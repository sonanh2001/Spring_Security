package org.aibles.jwtdemo.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends BaseException {

  public UserNotFoundException(Object id) {
    setStatus(HttpStatus.NOT_FOUND.value());
    setCode("org.aibles.jwtdemo.exception.UserNotFoundException");
    addParams("id", id);
  }
}
