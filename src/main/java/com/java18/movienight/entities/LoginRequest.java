package com.java18.movienight.entities;

public class LoginRequest {
  private String email;

  public LoginRequest(String email) {
    this.email = email;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
