package org.aibles.jwtdemo.exception;

import java.util.HashMap;

public class BaseException extends RuntimeException {
  private int status;
  private String code;
  private HashMap<String, Object> params;

  public BaseException() {
    super();
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public HashMap<String, Object> getParams() {
    return params;
  }

  public void setParams(HashMap<String, Object> params) {
    this.params = params;
  }

  public void addParams(String key, Object value) {
    if(this.params == null) {
      params = new HashMap<>();
    }
    params.put(key, value);
  }
}
