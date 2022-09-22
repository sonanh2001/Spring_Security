package org.aibles.jwtdemo.controller;

import static org.aibles.jwtdemo.constants.ApiConstants.USERS_API_URI;

import java.security.Principal;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.aibles.jwtdemo.dto.request.RegisterRequest;
import org.aibles.jwtdemo.dto.request.ProfileUserRequest;
import org.aibles.jwtdemo.dto.response.UserResponse;
import org.aibles.jwtdemo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(USERS_API_URI)
public class UserController {

  private final UserService service;

  public UserController(UserService service) {
    this.service = service;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public String register(@RequestBody @Valid RegisterRequest request, Principal principal) {
    log.info("(register)username: {}", request.getUsername());
    service.register(request);
    return "Register successfully!!!";
  }
  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public UserResponse getById(@PathVariable long id) {
    log.info("(getById)id : {}", id);
    return service.getById(id);
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public UserResponse update(@PathVariable long id, @RequestBody @Valid ProfileUserRequest request) {
    log.info("(update)id : {}, username : {}", id, request.getUsername());
    return service.update(id, request);
  }

  @GetMapping("/welcome")
  @ResponseStatus(HttpStatus.OK)
  public String welcome() {
    return "Welcome to the system";
  }
}
