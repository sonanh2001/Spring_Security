package org.aibles.jwtdemo.service;

import org.aibles.jwtdemo.dto.request.RegisterRequest;
import org.aibles.jwtdemo.dto.request.ProfileUserRequest;
import org.aibles.jwtdemo.dto.response.LoginResponse;
import org.aibles.jwtdemo.dto.response.UserResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

  /**
   * register an account
   * @param request - a request from client
   */
  void register(RegisterRequest request);

  /**
   * find a user by email
   * @param email - an email of a user
   * @return a response for user after login
   */
  LoginResponse getByEmail(String email);

  /**
   * get a user by an id
   * @param id - id sent from client
   * @return a response of user to client
   */
  UserResponse getById(long id);

  /**
   * update a user by id
   * @param id - id sent from client
   * @param request - a request from client
   * @return a response of an updated user
   */
  UserResponse update(long id, ProfileUserRequest request);
}
