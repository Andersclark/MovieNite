package com.java18.movienight.controllers;

import com.java18.movienight.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
  @Autowired
  private UserService userService;

}
