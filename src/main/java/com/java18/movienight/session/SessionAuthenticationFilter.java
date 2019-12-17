package com.java18.movienight.session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
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
import java.util.stream.Stream;

public class SessionAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

  @Autowired
  JwtTokenProvider jwtTokenProvider;

  public SessionAuthenticationFilter() {
    super(new AntPathRequestMatcher("**"));
  }

  private boolean authenticateCookieValue(String cookie) {
    return jwtTokenProvider.validateToken(cookie);
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
//            System.out.println("CustomFilter Cookie found: " + cookie);
      if (authenticateCookieValue(cookieVal)) {
        Authentication authentication = new SessionAuthentication(cookieVal);
        authentication.setAuthenticated(true);

        return this.getAuthenticationManager().authenticate(authentication);
      } else {
        CookieUtils.removeJWTCookie(request, response);

        // TODO: Choose Option A or B then comment/remove the unnecessary code in this block.

        // Option A: FORCE LOGOUT (throw an error)
        throw new BadCredentialsException("Looks like you've been tampering with your JWT! Refresh (F5) to login.");

        // Option B: Do nothing
      }
    }

    return null;
  }
}