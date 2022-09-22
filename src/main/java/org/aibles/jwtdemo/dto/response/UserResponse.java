package org.aibles.jwtdemo.dto.response;

import org.aibles.jwtdemo.entity.User;
import org.aibles.jwtdemo.util.DateUtil;

public class UserResponse {

  private long userId;
  private String username;
  private String firstName;
  private String lastName;
  private String email;
  private String phone;
  private String dob;

  public UserResponse() {}

  public static UserResponse from(User user) {
    UserResponse response = new UserResponse();
    response.setUserId(user.getUserId());
    response.setUsername(user.getUsername());
    response.setFirstName(user.getFirstName());
    response.setLastName(user.getLastName());
    response.setEmail(user.getEmail());
    response.setPhone(user.getPhone());
    if(user.getDob() != null) {
      response.setDob(DateUtil.convertEpochToString(user.getDob()));
    }
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

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getDob() {
    return dob;
  }

  public void setDob(String dob) {
    this.dob = dob;
  }
}
