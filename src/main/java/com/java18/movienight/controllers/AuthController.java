package com.java18.movienight.controllers;

import com.java18.movienight.entities.JwToken;
import com.java18.movienight.services.OAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class AuthController {

  @Autowired
  OAuthService oAuthService;

  @PostMapping("/storeauthcode")
  public ResponseEntity<JwToken> storeauthcode(@RequestBody String code, @RequestHeader("X-Requested-With") String encoding) {
    if (encoding == null || encoding.isEmpty()) {
      // Without the `X-Requested-With` header, this request could be forged. Aborts.
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong headers");
    }
    String email = oAuthService.authorizeWithGoogle(code);

    return new ResponseEntity<>(oAuthService.login(email), HttpStatus.OK);
  }
}
