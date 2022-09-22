package org.aibles.jwtdemo.constants;

public class ApiConstants {
  public static final String API_SOURCES = "api";
  public static final String VERSION = "v1";
  public static final String USER_RESOURCE = "users";

  public static final String REFRESH_TOKEN_SOURCES = "refreshTokens";
  public static final String LOGIN_URI = '/' + API_SOURCES + '/' + VERSION + '/' + "login";
  public static final String USERS_API_URI =
      '/' + API_SOURCES + '/' + VERSION + '/' + USER_RESOURCE;

  public static final String REFRESH_TOKEN_URI =
      '/' + API_SOURCES + '/' + VERSION + '/' + REFRESH_TOKEN_SOURCES;
}
