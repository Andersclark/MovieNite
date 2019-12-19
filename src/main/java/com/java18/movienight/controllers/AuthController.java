package com.java18.movienight.controllers;

import com.java18.movienight.entities.RequestLog;
import com.java18.movienight.entities.User;
import com.java18.movienight.repositories.RequestLogRepo;
import com.java18.movienight.repositories.UserRepo;
import com.java18.movienight.services.OAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  @Resource(name="authenticationManager")
  private AuthenticationManager authManager;

  @Autowired
  RequestLogRepo logRepo;

  @Autowired
  UserRepo userRepo;

  @Autowired
  OAuthService oAuthService;

  @PostConstruct
  void checkUsers() {
//    userRepo.deleteAll();

    List<User> users = userRepo.findAll();

    users.forEach(System.out::println);
  }

  @GetMapping("/whoami")
  public ResponseEntity<User> whoAmI() {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    RequestLog log = new RequestLog("/whoami", "GET");
    if (username == null || username.equals("anonymousUser")) {
      log.statusCode = "401";
      logRepo.save(log);
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Not signed in!");
    }
    log.statusCode = "200";
    log.user = username;
    logRepo.save(log);
    return new ResponseEntity<>(userRepo.findByEmail(username), HttpStatus.OK);
  }

  @PostMapping("/storeauthcode") // @CookieValue(value = "username", defaultValue = "Atta") String username
  public ResponseEntity<User> storeauthcode(@RequestBody String code, @RequestHeader("X-Requested-With") String encoding) {
    RequestLog log = new RequestLog("/storeauthcode", "POST");
    log.requestBody = code;
    if (encoding == null || encoding.isEmpty()) {
      log.statusCode = "400";
      logRepo.save(log);
      // Without the `X-Requested-With` header, this request could be forged. Aborts.
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error, wrong headers") ;
    }
    log.statusCode = "200";
    logRepo.save(log);
    return new ResponseEntity<>(oAuthService.authorizeWithGoogle(code), HttpStatus.OK);
  }
}
