package com.java18.movienight.controllers;

import com.java18.movienight.services.OAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

  @Autowired
  OAuthService oAuthService;

  @PostMapping("/storeauthcode")
  public String storeauthcode(@RequestBody String code, @RequestHeader("X-Requested-With") String encoding) {
    if (encoding == null || encoding.isEmpty()) {
      // Without the `X-Requested-With` header, this request could be forged. Aborts.
      return "Error, wrong headers";
    }

    oAuthService.authorizeWithGoogle(code);

    return "OK";
  }
}
