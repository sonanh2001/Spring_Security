package org.aibles.jwtdemo.configuration;

import static org.aibles.jwtdemo.constants.ApiConstants.LOGIN_URI;
import static org.aibles.jwtdemo.constants.ApiConstants.REFRESH_TOKEN_URI;
import static org.aibles.jwtdemo.constants.ApiConstants.USERS_API_URI;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

import org.aibles.jwtdemo.filter.CustomAuthenticationFilter;
import org.aibles.jwtdemo.filter.CustomAuthorizationFilter;
import org.aibles.jwtdemo.repository.RefreshTokenRepository;
import org.aibles.jwtdemo.repository.UserRepository;
import org.aibles.jwtdemo.service.RefreshTokenService;
import org.aibles.jwtdemo.service.UserService;
import org.aibles.jwtdemo.service.impl.RefreshTokenServiceImpl;
import org.aibles.jwtdemo.service.impl.UserServiceImpl;
import org.aibles.jwtdemo.util.JWTUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableJpaRepositories(basePackages = {"org.aibles.jwtdemo.repository"})
@ComponentScan(basePackages = {"org.aibles.jwtdemo.repository"})
public class OKRsConfiguration {

  private final JWTUtil jwtUtil;

  private final UserRepository userRepository;

  private final RefreshTokenRepository refreshTokenRepository;

  private final AuthenticationConfiguration authenticationConfiguration;

  public OKRsConfiguration(
      JWTUtil jwtUtil,
      UserRepository repository,
      RefreshTokenRepository refreshTokenRepository,
      AuthenticationConfiguration authenticationConfiguration) {
    this.jwtUtil = jwtUtil;
    this.userRepository = repository;
    this.refreshTokenRepository = refreshTokenRepository;
    this.authenticationConfiguration = authenticationConfiguration;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public RefreshTokenService refreshTokenService(
      RefreshTokenRepository repository, JWTUtil jwtUtil, UserService userService) {
    return new RefreshTokenServiceImpl(repository, jwtUtil, userService);
  }

  @Bean
  public UserService userService(UserRepository repository, PasswordEncoder passwordEncoder) {
    return new UserServiceImpl(repository, passwordEncoder);
  }

  @Bean
  public AuthenticationManager authenticationManager() throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
    daoAuthenticationProvider.setUserDetailsService(userService(userRepository, passwordEncoder()));
    daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
    return daoAuthenticationProvider;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    CustomAuthenticationFilter customAuthenticationFilter =
        new CustomAuthenticationFilter(
            jwtUtil,
            authenticationManager(),
            userService(userRepository, passwordEncoder()),
            refreshTokenService(
                refreshTokenRepository, jwtUtil, userService(userRepository, passwordEncoder())));
    customAuthenticationFilter.setFilterProcessesUrl(LOGIN_URI);
    http.csrf().disable();
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    http.authorizeRequests().antMatchers(POST, LOGIN_URI + "/**").permitAll();
    http.authorizeRequests().antMatchers(GET, REFRESH_TOKEN_URI + "/**").permitAll();
    http.authorizeRequests().antMatchers(POST, USERS_API_URI + "/**").permitAll();
    http.authorizeRequests().antMatchers(GET, USERS_API_URI + "/**").authenticated();
    http.authorizeRequests().antMatchers(PUT, USERS_API_URI + "/**").authenticated();
    http.addFilter(customAuthenticationFilter);
    http.addFilterBefore(
        new CustomAuthorizationFilter(jwtUtil, userService(userRepository, passwordEncoder())),
        UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }
}
