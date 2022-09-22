package org.aibles.jwtdemo.dto.response;

import org.aibles.jwtdemo.entity.User;

public class LoginResponse extends JwtResponse{
  private long userId;

  private String username;

  private String email;

  public LoginResponse() {
    super();
  }

  public static LoginResponse from(User user) {
    LoginResponse response = new LoginResponse();
    response.setUserId(user.getUserId());
    response.setEmail(user.getEmail());
    response.setUsername(user.getUsername());
    return response;
  }

  public long getUserId() {
    return userId;
  }
  public void setUserId(long userId) {
    this.userId = userId;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
