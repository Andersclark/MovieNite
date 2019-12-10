package com.java18.movienight.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class User {
  @Id
  private ObjectId _id;
  private String email;
  private String username;
  private String password;
  private Set<String> roles;

  public User(ObjectId _id, String email, String password, String ...roles) {
    this._id = _id;
    this.email = email;
    this.password = password;
    this.roles = roles != null ? Set.of(roles) : Set.of("USER");
  }

  public String get_id() {
    return _id.toHexString();
  }

  public void set_id(ObjectId _id) {
    this._id = _id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
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

  public List<String> getRoles() {
    return new ArrayList<>(roles);
  }

  public void setRoles(Set<String> roles) {
    this.roles = roles;
  }

  @Override
  public String toString() {
    return String.format("USER email: %s, password: %s", email, password);
  }
}

