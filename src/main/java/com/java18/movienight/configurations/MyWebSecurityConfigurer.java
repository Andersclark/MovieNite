package com.java18.movienight.configurations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java18.movienight.entities.User;
import com.java18.movienight.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MyWebSecurityConfigurer extends WebSecurityConfigurerAdapter {

  @Autowired
  MyUserDetailsService myUserDetailsService;

  @Autowired
  UserService userService;

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Bean
  public RequestBodyReaderAuthenticationFilter authenticationFilter() throws Exception {
    RequestBodyReaderAuthenticationFilter authenticationFilter
            = new RequestBodyReaderAuthenticationFilter();
    authenticationFilter.setAuthenticationSuccessHandler(this::loginSuccessHandler);
    authenticationFilter.setAuthenticationFailureHandler(this::loginFailureHandler);
    authenticationFilter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/login", "POST"));
    authenticationFilter.setAuthenticationManager(authenticationManagerBean());
    return authenticationFilter;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers("/login").permitAll()
            .antMatchers("/auth**").permitAll()
            .antMatchers(HttpMethod.GET, "/api/users/*").hasRole("USER")
            .antMatchers(HttpMethod.GET, "/rest/**").permitAll()
            .antMatchers(HttpMethod.POST, "/api/register").permitAll()
            .antMatchers("/rest/**", "/api/**").hasRole("USER")
            .antMatchers("/rest/**", "/api/**").hasRole("ADMIN")
            .and()
            .addFilterBefore(
                    authenticationFilter(),
                    UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling().accessDeniedPage("/login")
            .and()
            .logout().permitAll().logoutSuccessUrl("/")
            .logoutSuccessHandler(this::logoutSuccessHandler)
            .and().csrf().disable()
    ;
  }

  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
    auth.setUserDetailsService(myUserDetailsService);
    auth.setPasswordEncoder(myUserDetailsService.getEncoder());
    return auth;
  }

  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(authenticationProvider());
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers("/assets/**");
  }

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth.inMemoryAuthentication()
            .withUser("user")
            .password(myUserDetailsService.getEncoder().encode("password"))
            .roles("USER");
  }

  @Bean("authenticationManager")
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  private void loginSuccessHandler(
          HttpServletRequest request,
          HttpServletResponse response,
          Authentication authentication) throws IOException {

    User loggedInUser = userService.findByUsername(authentication.getName());
    if(loggedInUser == null) throw new UsernameNotFoundException("User not found: " + authentication.getName());
    response.setStatus(HttpStatus.OK.value());
    objectMapper.writeValue(response.getWriter(), loggedInUser);
  }

  private void loginFailureHandler(
          HttpServletRequest request,
          HttpServletResponse response,
          AuthenticationException e) throws IOException {

    response.setStatus(HttpStatus.UNAUTHORIZED.value());
    objectMapper.writeValue(response.getWriter(), "Nopity nop!");
  }

  private void logoutSuccessHandler(
          HttpServletRequest request,
          HttpServletResponse response,
          Authentication authentication) throws IOException {

    // TODO: Remove cookie
    var b = Stream.of(request.getCookies())
            .filter(cookie -> cookie.getName().equals("OAuthCake"))
            .map(cookie -> cookie.getName() + " " + cookie.getValue())
            .collect(Collectors.toList());

    System.out.println("cookies: " + b);

    Cookie cookie = new Cookie("OAuthCake", null);
    cookie.setMaxAge(0);
    response.addCookie(cookie);

    response.setStatus(HttpStatus.OK.value());
    objectMapper.writeValue(response.getWriter(), "Bye!");
  }
}
