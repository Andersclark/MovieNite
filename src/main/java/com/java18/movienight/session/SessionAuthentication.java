package com.java18.movienight.session;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collection;
import java.util.List;

public class SessionAuthentication implements Authentication {

  private String cookie = "";
  private boolean authenticated = false;
  private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
  private UserDetails userDetails;

  public SessionAuthentication(String cookie){
    this.cookie = cookie;
    userDetails = toUserDetails(JwtTokenProvider.get().getEmail(cookie));
  }

  private UserDetails toUserDetails(String email) {
    return org.springframework.security.core.userdetails.User
            .withUsername(email)
            .password(encoder.encode("password"))
            .roles(JwtTokenProvider.get().getRoles(cookie).toArray(new String[0])).build();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return userDetails.getAuthorities();
  }

  @Override
  public Object getCredentials() {
    return null;
  }

  @Override
  public Object getDetails() {
    return null;
  }

  @Override
  public Object getPrincipal() {
    return userDetails;
  }

  @Override
  public boolean isAuthenticated() {
    return authenticated;
  }

  public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
    authenticated = true;
  }

  @Override
  public String getName() {
    return userDetails.getUsername();
  }

  public String getCookie() {
    return cookie;
  }


}