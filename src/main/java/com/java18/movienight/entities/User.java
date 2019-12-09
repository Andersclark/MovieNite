package com.java18.movienight.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.security.core.Authentication;

import java.security.Principal;
import java.util.List;
import java.util.Set;

public class User {
  @Id
  private ObjectId _id;
  private String username;
  private String password;
  private Set<String> roles;

  public User(ObjectId _id, String username, String password, String ...roles) {
    this._id = _id;
    this.username = username;
    this.password = password;
    this.roles = roles != null ? Set.of(roles) : Set.of("USER");
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

  @JsonIgnore
  public String getPassword() {
    return password;
  }

  @JsonProperty
  public void setPassword(String password) {
    this.password = password;
  }

  public Set<String> getRoles() {
    return roles;
  }

  public void setRoles(Set<String> roles) {
    this.roles = roles;
  }

  public static boolean currentUserIsAdmin(Principal principal){
    org.springframework.security.core.userdetails.User u = (org.springframework.security.core.userdetails.User) ((Authentication) principal).getPrincipal();
    return u.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
  }

  @Override
  public String toString() {
    return String.format("USER username: %s, password: %s", username, password);
  }
}

