package com.java18.movienight.session;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

public class SessionAuthentication implements Authentication {

  private String cookie = "";
  private boolean authenticated = false;

  public SessionAuthentication(String cookie){
    this.cookie = cookie;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority("USER"));
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
    return null;
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
    return null;
  }
}