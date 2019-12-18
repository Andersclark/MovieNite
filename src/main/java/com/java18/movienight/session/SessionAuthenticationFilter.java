package com.java18.movienight.session;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class SessionAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

  public SessionAuthenticationFilter() {
    super(new AntPathRequestMatcher("**"));
  }

  private String authenticateCookieValue(HttpServletRequest request, HttpServletResponse response, String cookie) {
    return JwtTokenProvider.get().validateToken(request, response, cookie);
  }

  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
          throws IOException, ServletException {

    HttpServletRequest request = (HttpServletRequest) req;
    HttpServletResponse response = (HttpServletResponse) res;

    // Only run once per request
    Object alreadyDidAuthenticate = request.getAttribute("ALREADY_SUCCESSFUL_SESSION_JWT");
    if (alreadyDidAuthenticate != null) {
      chain.doFilter(request, response);
      return;
    }

    Authentication authResult;
    try {
      authResult = attemptAuthentication(request, response);
      if (authResult == null) {
        chain.doFilter(request, response);
        return;
      }
    } catch (AuthenticationException failed) {
      unsuccessfulAuthentication(request, response, failed);
      return;
    }

    // Authentication success
    request.setAttribute("ALREADY_SUCCESSFUL_SESSION_JWT", Boolean.TRUE);
    SecurityContextHolder.getContext().setAuthentication(authResult);
    chain.doFilter(request, response);
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    Optional<Cookie> cookie = CookieUtils.getCookie(request);

    if (cookie.isPresent()) {
      String cookieVal = cookie.get().getValue();

      cookieVal = authenticateCookieValue(request, response, cookieVal);
      if (!cookieVal.equals("invalid token")) {
        Authentication authentication = new SessionAuthentication(cookieVal);
        authentication.setAuthenticated(true);

        return this.getAuthenticationManager().authenticate(authentication);
      } else {
        // if cookie is invalid, user needs to re-authenticate
        CookieUtils.removeJWTCookie(request, response);
      }
    }
    return null;
  }
}