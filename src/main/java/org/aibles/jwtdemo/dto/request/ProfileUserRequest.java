package org.aibles.jwtdemo.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.aibles.jwtdemo.entity.User;
import org.aibles.jwtdemo.util.DateUtil;

public class ProfileUserRequest {
  @NotNull(message = "an updated user must have an id")
  private Long userId;
  @NotBlank(message = "username must not be blank")
  private String username;
  private String firstName;
  private String lastName;
  @Pattern(regexp = "([+]84|0[3|5|7|8|9])+([0-9]{8})", message = "invalid phone format")
  private String phone;

  @Pattern(regexp =
      "(?:(?:0[1-9]|1\\d|2[0-8])/(?:0[1-9]|1[0-2])|"
          + "(?:29|30)/(?:0[13-9]|1[0-2])|31/(?:0[13578]"
          + "|1[02]))/[1-9]\\d{3}|29/02(?:/[1-9]\\d(?:0[48]|[2468][048]|"
          + "[13579][26])|(?:[2468][048]|[13579][26])00)",
      message = "a date must have format dd/MM/yyyy")
  private String dob;

  public ProfileUserRequest() {
  }

  public User toUser(User user) {
    User userUpdated = new User();
    userUpdated.setUserId(user.getUserId());
    userUpdated.setEmail(user.getEmail());
    userUpdated.setPassword(user.getPassword());
    userUpdated.setUsername(this.getUsername());
    userUpdated.setFirstName(this.getFirstName());
    userUpdated.setLastName(this.getLastName());
    userUpdated.setPhone(this.getPhone());
    if(this.getDob() != null) {
      userUpdated.setDob(DateUtil.convertStringToEpoch(this.getDob()));
    }
    return userUpdated;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
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
