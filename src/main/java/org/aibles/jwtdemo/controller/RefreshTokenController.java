package org.aibles.jwtdemo.controller;

import static org.aibles.jwtdemo.constants.ApiConstants.REFRESH_TOKEN_URI;

import lombok.extern.slf4j.Slf4j;
import org.aibles.jwtdemo.dto.response.JwtResponse;
import org.aibles.jwtdemo.service.RefreshTokenService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(REFRESH_TOKEN_URI)
@Slf4j
public class RefreshTokenController {

  private final RefreshTokenService service;

  public RefreshTokenController(RefreshTokenService service) {
    this.service = service;
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public JwtResponse refreshToken(
      @RequestHeader(name = "Authorization", required = false) String authorizationHeader) {
    log.info("(refreshToken)header : {}", authorizationHeader);
    return service.generateAccessToken(authorizationHeader);
  }
}
