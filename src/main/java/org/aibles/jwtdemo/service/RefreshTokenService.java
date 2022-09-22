package org.aibles.jwtdemo.service;

import org.aibles.jwtdemo.dto.response.JwtResponse;
import org.aibles.jwtdemo.entity.RefreshToken;

public interface RefreshTokenService {

  /**
   * create new refresh token
   * @param email - email of user
   * @param refreshToken - refresh token from filter
   */
  void create(String email, String refreshToken);

  /**
   * generate new access token
   * @param authorizationHeader - header request from client
   * @return a response of jwt
   */
  JwtResponse generateAccessToken(String authorizationHeader);
}
