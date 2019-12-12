package com.java18.movienight.session;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class SessionAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

  @Override
  protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
    // throw new BadCredentialsException if mismatch
  }

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    return new SessionAuthentication(authentication.getName());
  }

  @Override
  protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
    System.out.println("SessionAuthenticationProvider retrieveUser ");
    return null;
//        return new User("a", "b", List.of(new SimpleGrantedAuthority("USER")));
  }

  @Override
  public boolean supports(Class<? extends Object> authentication) {
    return (SessionAuthentication.class.isAssignableFrom(authentication));
  }
}