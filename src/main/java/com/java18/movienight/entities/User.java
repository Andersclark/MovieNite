package com.java18.movienight.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.security.core.Authentication;

import java.security.Principal;
import java.util.Set;

public class User {
  @Id
  private ObjectId _id;
  private String email;
  private String username;
  private String pictureUrl;
  private Set<String> roles;
  private String accessToken;
  private String refreshToken;
  private long tokenExpires;

  public User() {}

  public User(ObjectId _id, String username, String email, String pictureUrl, String accessToken, String refreshToken, long tokenExpires, String ...roles) {
    this._id = _id;
    this.username = username;
    this.email = email;
    this.pictureUrl = pictureUrl;
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
    this.tokenExpires = tokenExpires;
    this.roles = roles.length > 0 ? Set.of(roles) : Set.of("USER");
  }

  public String get_id() {
    return _id.toHexString();
  }

  public void set_id(ObjectId _id) {
    this._id = _id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPictureUrl() {
    return pictureUrl;
  }

  public void setPictureUrl(String pictureUrl) {
    this.pictureUrl = pictureUrl;
  }
  @JsonIgnore
  public String getAccessToken() {
    return accessToken;
  }
  @JsonProperty
  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }
  @JsonIgnore
  public String getRefreshToken() {
    return refreshToken;
  }
  @JsonProperty
  public void setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }
  @JsonIgnore
  public long getTokenExpires() {
    return tokenExpires;
  }
  @JsonProperty
  public void setTokenExpires(long tokenExpires) {
    this.tokenExpires = tokenExpires;
  }

  public Set<String> getRoles() {
    return roles;
  }

  public void setRoles(Set<String> roles) {
    this.roles = roles;
  }

  public static boolean currentUserIsAdmin(Principal principal){
    org.springframework.security.core.userdetails.User u = (org.springframework.security.core.userdetails.User) ((Authentication) principal).getPrincipal();
    return u.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ADMIN"));
  }

  @Override
  public String toString() {
    return String.format("USER \nusername: %s \nemail: %s \npictureUrl: %s \naccessToken: %s \nrefreshToken: %s \ntokenExpires: %d", username, email, pictureUrl, accessToken, refreshToken, tokenExpires);
  }
}

