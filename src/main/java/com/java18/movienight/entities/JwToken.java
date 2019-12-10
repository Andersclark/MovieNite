package com.java18.movienight.entities;

public class JwToken {
  private String token;
  public JwToken(String token) {
    this.token = token;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }
}
