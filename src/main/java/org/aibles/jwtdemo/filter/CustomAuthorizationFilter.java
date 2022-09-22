package org.aibles.jwtdemo.filter;

import static org.aibles.jwtdemo.constants.ApiConstants.LOGIN_URI;
import static org.aibles.jwtdemo.constants.ApiConstants.REFRESH_TOKEN_URI;
import static org.aibles.jwtdemo.constants.ApiConstants.USERS_API_URI;
import static org.aibles.jwtdemo.constants.CommonConstants.START_OF_BEARER_TOKEN;
import static org.aibles.jwtdemo.constants.CommonConstants.START_POSITION_OF_CONTENT_IN_BEARER_TOKEN;
import static org.aibles.jwtdemo.filter.AuthorizationFilterExceptionHandle.handle;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.aibles.jwtdemo.exception.InvalidOrExpiredToken;
import org.aibles.jwtdemo.service.UserService;
import org.aibles.jwtdemo.util.JWTUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
public class CustomAuthorizationFilter extends OncePerRequestFilter {

  private final JWTUtil jwtUtil;

  private final UserService userService;

  public CustomAuthorizationFilter(JWTUtil jwtUtil, UserService userService) {
    this.jwtUtil = jwtUtil;
    this.userService = userService;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    if (request.getServletPath().equals(LOGIN_URI)
        || (request.getMethod().equals("POST") && request.getServletPath().equals(USERS_API_URI))
        || request.getServletPath().equals(REFRESH_TOKEN_URI)) {
      filterChain.doFilter(request, response);
    } else {
      String authorizationHeader = request.getHeader("Authorization");
      String token = null;
      String email = null;
      try {
        if (authorizationHeader != null && authorizationHeader.startsWith(START_OF_BEARER_TOKEN)) {
          log.info("(doFilterInternal)header : {}", authorizationHeader);
          token = authorizationHeader.substring(START_POSITION_OF_CONTENT_IN_BEARER_TOKEN);
          email = jwtUtil.getEmailFromToken(token);
          if (email != null) {
            log.info("(doFilterInternal)email : {}", email);
            UserDetails userDetails = userService.loadUserByUsername(email);
            if (jwtUtil.validateToken(token, userDetails)) {
              UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                  new UsernamePasswordAuthenticationToken(email, null, userDetails.getAuthorities());

              usernamePasswordAuthenticationToken.setDetails(
                  new WebAuthenticationDetailsSource().buildDetails(request));
              SecurityContextHolder.getContext()
                  .setAuthentication(usernamePasswordAuthenticationToken);

              filterChain.doFilter(request, response);
            } else {
              throw new InvalidOrExpiredToken();
            }
          } else {
            throw new InvalidOrExpiredToken();
          }
        } else {
          throw new InvalidOrExpiredToken();
        }
      } catch (Exception e) {
        handle(response, e);
      }
    }
  }
}
