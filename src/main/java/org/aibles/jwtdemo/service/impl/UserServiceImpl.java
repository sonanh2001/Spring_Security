package org.aibles.jwtdemo.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.aibles.jwtdemo.dto.request.ProfileUserRequest;
import org.aibles.jwtdemo.dto.request.RegisterRequest;
import org.aibles.jwtdemo.dto.response.LoginResponse;
import org.aibles.jwtdemo.dto.response.UserResponse;
import org.aibles.jwtdemo.entity.User;
import org.aibles.jwtdemo.exception.EmailExistedException;
import org.aibles.jwtdemo.exception.UserNotFoundException;
import org.aibles.jwtdemo.exception.UsernameExistedException;
import org.aibles.jwtdemo.repository.UserRepository;
import org.aibles.jwtdemo.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
public class UserServiceImpl implements UserService {
  private final UserRepository repository;

  private final PasswordEncoder passwordEncoder;

  public UserServiceImpl(UserRepository repository, PasswordEncoder passwordEncoder) {
    this.repository = repository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  @Transactional
  public void register(RegisterRequest request) {
    log.info("(register)username: {}", request.getUsername());
    if (repository.existsByEmail(request.getEmail())) {
      throw new EmailExistedException(request.getEmail());
    }
    if (repository.existsByUsername(request.getUsername())) {
      throw new UsernameExistedException(request.getUsername());
    }
    User user = request.toUser();
    user.setPassword(passwordEncoder.encode(request.getPassword()));
    repository.save(user);
  }

  @Override
  @Transactional(readOnly = true)
  public LoginResponse getByEmail(String email) {
    log.info("(getByEmail)email : {}", email);
    return LoginResponse.from(
        repository
            .findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Invalid user info")));
  }

  @Override
  @Transactional(readOnly = true)
  public UserResponse getById(long id) {
    log.info("(getById)id : {}", id);
    return UserResponse.from(
        repository.findById(id).orElseThrow(() -> new UserNotFoundException(id)));
  }

  @Override
  @Transactional(readOnly = true)
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    log.info("(loadUserByUsername)email : {}", username);
    User user =
        repository
            .findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("Invalid user info"));
    return new UserDetailsImpl(user);
  }

  @Override
  @Transactional
  public UserResponse update(long id, ProfileUserRequest request) {
    log.info("(update)id : {}, username: {}", id, request.getUsername());
    User user =
        repository
            .findById(id)
            .map(request::toUser)
            .orElseThrow(() -> new UserNotFoundException(id));
    return UserResponse.from(repository.save(user));
  }
}
