package com.java18.movienight.controllers;

import com.java18.movienight.entities.User;
import com.java18.movienight.repositories.UserRepo;
import com.java18.movienight.services.OAuthService;
import com.java18.movienight.session.CookieUtils;
import com.java18.movienight.session.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;


@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

  @Resource(name="authenticationManager")
  private AuthenticationManager authManager;

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
  public ResponseEntity<User> whoAmI(HttpServletRequest request) {
    String token = null;
    try {
      token = CookieUtils.getCookie(request).get().getValue();
    } catch (Exception e) {}

    if (token == null) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No JWT cookie!");
    }
    String username = JwtTokenProvider.get().getEmail(token);
    return new ResponseEntity<>(userRepo.findByEmail(username), HttpStatus.OK);
  }

  @PostMapping("/storeauthcode") // @CookieValue(value = "username", defaultValue = "Atta") String username
  public ResponseEntity<User> storeauthcode(@RequestBody String code, @RequestHeader("X-Requested-With") String encoding) {
    if (encoding == null || encoding.isEmpty()) {
      // Without the `X-Requested-With` header, this request could be forged. Aborts.
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error, wrong headers") ;
    }
    return new ResponseEntity<>(oAuthService.authorizeWithGoogle(code), HttpStatus.OK);
  }

  @GetMapping("/logout")
  private ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response){
    CookieUtils.removeJWTCookie(request, response);
    try{
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      if (authentication != null) {
        new SecurityContextLogoutHandler().logout(request, response, authentication);
      }
    } catch (Exception err) {}
    return new ResponseEntity<>("Logged out",HttpStatus.RESET_CONTENT);
  }
}
