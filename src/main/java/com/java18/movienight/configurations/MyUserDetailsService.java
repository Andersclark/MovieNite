package com.java18.movienight.configurations;

import com.java18.movienight.entities.User;
import com.java18.movienight.services.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class MyUserDetailsService implements UserDetailsService {

  private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

  public BCryptPasswordEncoder getEncoder() {
    return encoder;
  }

  @Autowired
  private UserService userService;

  public static UserDetails currentUserDetails(){
    SecurityContext securityContext = SecurityContextHolder.getContext();
    Authentication authentication = securityContext.getAuthentication();
    if (authentication != null) {
      Object principal = authentication.getPrincipal();
      return principal instanceof UserDetails ? (UserDetails) principal : null;
    }
    return null;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userService.findByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException("User not found by name: " + username);
    }
    return toUserDetails(user);
  }

  private UserDetails toUserDetails(User user) {
    return org.springframework.security.core.userdetails.User
            .withUsername(user.getUsername())
            .password("password")
            .roles(user.getRoles().toArray(new String[0])).build();
  }
}