package com.java18.movienight.controllers;

import com.java18.movienight.entities.User;
import com.java18.movienight.repositories.UserRepo;
import com.java18.movienight.services.OAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.List;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

@RestController
@RequestMapping("/auth")
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
  public ResponseEntity<User> whoAmI() {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();

    System.err.println(username);
    if (username == null || username.equals("anonymousUser")) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Not signed in!");
    }

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
}
