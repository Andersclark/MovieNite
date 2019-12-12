package com.java18.movienight.session;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.stream.Stream;

public class CookieUtils {

  /**
   * Meant to hold the JWT cookie
   */
  private static final String COOKIE_NAME = "JWT_SESSION";

  /**
   * Creates a cookie to hold the JWT
   */
  public static void addJWTCookie(HttpServletResponse response, String username) {
    // TODO: Create a JWT here using the username instead of just "ABCDEFGH"
    addCookie(response, COOKIE_NAME, "ABCDEFGH");
  }

  /**
   * Creates a custom cookie
   */
  public static void addCookie(HttpServletResponse response, String name, String value) {
    // TODO: add an Expiration date.
    response.addCookie(new Cookie(name, value));
  }

  /**
   * Returns the cookie named by the static String CookieUtils.COOKIE_NAME
   */
  public static Optional<Cookie> getCookie(HttpServletRequest request) {
    return getCookie(request, COOKIE_NAME);
  }

  /**
   * Returns the cookie with the specified name
   */
  public static Optional<Cookie> getCookie(HttpServletRequest request, String cookieName) {
    final Cookie[] allCookies = request.getCookies(); // Freaking stupid it returns "null OR an array" instead of just an "empty OR filled" array
    return allCookies != null ? Stream.of(allCookies).filter(cookie -> cookie.getName().equals(cookieName)).findFirst() : Optional.empty();
  }

  /**
   * Removes the cookie named by the static String CookieUtils.COOKIE_NAME
   */
  public static void removeJWTCookie(HttpServletRequest request, HttpServletResponse response) {
    removeCookie(request, response, COOKIE_NAME);
  }

  /**
   * Removes the cookie with the specified name
   */
  public static void removeCookie(HttpServletRequest request, HttpServletResponse response, String cookieName) {
    var cookie = getCookie(request, cookieName);
    removeCookie(response, cookie);
  }

  /**
   * Removes the cookie.
   */
  public static void removeCookie(HttpServletResponse response, Optional<Cookie> cookie) {
    if (cookie.isPresent()) {
      cookie.get().setMaxAge(0);
      response.addCookie(cookie.get());
    }
  }
}