package org.aibles.jwtdemo.filter;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.aibles.jwtdemo.dto.response.LoginResponse;
import org.aibles.jwtdemo.entity.RefreshToken;
import org.aibles.jwtdemo.service.RefreshTokenService;
import org.aibles.jwtdemo.service.UserService;
import org.aibles.jwtdemo.util.JWTUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final JWTUtil jwtUtil;
  private final AuthenticationManager authenticationManager;
  private final UserService userService;
  private final RefreshTokenService refreshTokenService;
  public CustomAuthenticationFilter(
      JWTUtil jwtUtil, AuthenticationManager authenticationManager, UserService userService,
      RefreshTokenService refreshTokenService) {
    this.jwtUtil = jwtUtil;
    this.authenticationManager = authenticationManager;
    this.userService = userService;
    this.refreshTokenService = refreshTokenService;
  }

  @Override
  public Authentication attemptAuthentication(
      HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    String email = request.getParameter("username");
    String password = request.getParameter("password");
    log.info("(attemptAuthentication)email : {}, password : {}", email, password);
    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
        new UsernamePasswordAuthenticationToken(email, password);
    return authenticationManager.authenticate(usernamePasswordAuthenticationToken);
  }

  @Override
  protected void successfulAuthentication(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain chain,
      Authentication authentication)
      throws IOException, ServletException {
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    log.info("(successfulAuthentication)email : {}", userDetails.getUsername());
    String accessToken = jwtUtil.generateAccessToken(userDetails);
    String refreshToken = jwtUtil.generateRefreshToken(userDetails);

    LoginResponse loginResponse = userService.getByEmail(userDetails.getUsername());
    refreshTokenService.create(loginResponse.getEmail(), refreshToken);
    loginResponse.setAccessToken(accessToken);
    loginResponse.setRefreshToken(refreshToken);
    response.setContentType(APPLICATION_JSON_VALUE);
    new ObjectMapper().writeValue(response.getOutputStream(), loginResponse);
  }

  @Override
  protected void unsuccessfulAuthentication(HttpServletRequest request,
      HttpServletResponse response, AuthenticationException failed)
      throws IOException, ServletException {
    log.info("(unsuccessfulAuthentication)");
    Map<String, Object> errorMap = new HashMap<>();
    errorMap.put("status", HttpStatus.UNAUTHORIZED.value());
    errorMap.put("code", failed.getClass().getName());
    errorMap.put("message", failed.getMessage());
    response.setContentType(APPLICATION_JSON_VALUE);
    response.setStatus(HttpStatus.UNAUTHORIZED.value());
    new ObjectMapper().writeValue(response.getOutputStream(), errorMap);
  }
}
