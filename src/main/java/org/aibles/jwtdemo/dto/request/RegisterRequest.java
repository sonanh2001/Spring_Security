package org.aibles.jwtdemo.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import org.aibles.jwtdemo.entity.User;

public class RegisterRequest {
  @NotBlank(message = "username must not be blank")
  private String username;

  @NotBlank(message = "password must not be blank")
  private String password;

  @Email(message = "invalid email")
  @NotBlank(message = "email must not be blank")
  private String email;

  public RegisterRequest() {}

  public User toUser() {
    User user = new User();
    user.setUsername(this.getUsername());
    user.setEmail(this.getEmail());
    return user;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
