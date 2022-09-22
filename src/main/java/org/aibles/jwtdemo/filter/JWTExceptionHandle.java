package org.aibles.jwtdemo.filter;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.aibles.jwtdemo.exception.InvalidOrExpiredToken;

@Slf4j
public class JWTExceptionHandle {

  public static void handle(HttpServletResponse response, InvalidOrExpiredToken e)
      throws IOException {
    log.info("(handle)exception : {}", e.getClass().getName());
    Map<String, Object> errorMap = new HashMap<>();
    errorMap.put("status", e.getStatus());
    errorMap.put("code", e.getCode());
    response.setStatus(e.getStatus());
    response.setContentType(APPLICATION_JSON_VALUE);
    new ObjectMapper().writeValue(response.getOutputStream(), errorMap);
  }
}
