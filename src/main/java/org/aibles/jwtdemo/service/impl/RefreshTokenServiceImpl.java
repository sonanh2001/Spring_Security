package org.aibles.jwtdemo.service.impl;

import static org.aibles.jwtdemo.constants.CommonConstants.START_OF_BEARER_TOKEN;
import static org.aibles.jwtdemo.constants.CommonConstants.START_POSITION_OF_CONTENT_IN_BEARER_TOKEN;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.aibles.jwtdemo.dto.response.JwtResponse;
import org.aibles.jwtdemo.entity.RefreshToken;
import org.aibles.jwtdemo.exception.InvalidOrExpiredToken;
import org.aibles.jwtdemo.repository.RefreshTokenRepository;
import org.aibles.jwtdemo.service.RefreshTokenService;
import org.aibles.jwtdemo.service.UserService;
import org.aibles.jwtdemo.util.JWTUtil;
import org.springframework.security.core.userdetails.UserDetails;

@Slf4j
public class RefreshTokenServiceImpl implements RefreshTokenService {

  private final RefreshTokenRepository repository;

  private final JWTUtil jwtUtil;

  private final UserService userService;

  public RefreshTokenServiceImpl(
      RefreshTokenRepository repository, JWTUtil jwtUtil, UserService userService) {
    this.repository = repository;
    this.jwtUtil = jwtUtil;
    this.userService = userService;
  }

  @Override
  public void create(String email, String refreshToken) {
    log.info("(create)email : {}, refreshToken : {}", email, refreshToken);
    if (repository.existsById(email)) {
      repository.deleteById(email);
    }
    repository.save(new RefreshToken(email, refreshToken));
  }

  @Override
  public JwtResponse generateAccessToken(String authorizationHeader) {
    log.info("(generateAccessToken)header : {}", authorizationHeader);
    try {
      if (authorizationHeader != null && authorizationHeader.startsWith(START_OF_BEARER_TOKEN)) {
        String refreshToken =
            authorizationHeader.substring(START_POSITION_OF_CONTENT_IN_BEARER_TOKEN);

        String email = jwtUtil.getEmailFromToken(refreshToken);
        UserDetails userDetails = userService.loadUserByUsername(email);
        if (jwtUtil.validateToken(refreshToken, userDetails)) {
          return new JwtResponse(refreshToken, jwtUtil.generateAccessToken(userDetails));
        }
      }
    } catch (Exception e) {
      throw new InvalidOrExpiredToken();
    }
    return new JwtResponse();
  }
}
