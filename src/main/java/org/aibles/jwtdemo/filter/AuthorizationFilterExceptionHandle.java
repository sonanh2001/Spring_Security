package org.aibles.jwtdemo.filter;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
public class AuthorizationFilterExceptionHandle {
  public static void handle(HttpServletResponse response, Exception e) throws IOException {
    log.info("(handle)exception : {}", e.getClass().getName());
    Map<String, Object> errorMap = new HashMap<>();
    errorMap.put("status", HttpStatus.FORBIDDEN.value());
    errorMap.put("code", e.getClass().getName());
    response.setStatus(HttpStatus.FORBIDDEN.value());
    response.setContentType(APPLICATION_JSON_VALUE);
    new ObjectMapper().writeValue(response.getOutputStream(), errorMap);
  }
}
