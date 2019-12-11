package com.java18.movienight.configurations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java18.movienight.entities.User;
import com.java18.movienight.services.UserService;
import com.java18.movienight.session.CookieUtils;
import com.java18.movienight.session.SessionAuthenticationFilter;
import com.java18.movienight.session.SessionAuthenticationProvider;
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
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
  public RequestBodyReaderAuthenticationFilter loginAuthenticationFilter() throws Exception {
    RequestBodyReaderAuthenticationFilter authenticationFilter = new RequestBodyReaderAuthenticationFilter();
//        authenticationFilter.setAuthenticationSuccessHandler(this::loginSuccessHandler); // Respond with "the user object"
    authenticationFilter.setAuthenticationFailureHandler(this::loginFailureHandler);
    authenticationFilter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/login", "POST"));
    authenticationFilter.setAuthenticationManager(authenticationManagerBean());
    return authenticationFilter;
  }

  @Bean
  public SessionAuthenticationProvider sessionAuthProvider() {
    SessionAuthenticationProvider authProvider = new SessionAuthenticationProvider();
    return authProvider;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers(HttpMethod.GET, "/").permitAll()
            .antMatchers("/login").permitAll()
            .antMatchers("/auth**").permitAll()
            .antMatchers(HttpMethod.GET, "/api/users/*").hasRole("USER")
            .antMatchers(HttpMethod.GET, "/rest/**", "/api/**").permitAll()
            .antMatchers(HttpMethod.POST, "/api/register").permitAll()
            .antMatchers("/rest/**", "/api/**").hasRole("USER")
            .antMatchers("/rest/**", "/api/**").hasRole("ADMIN")
            .and()
            .addFilterBefore(
                    sessionAuthenticationFilter(),
                    BasicAuthenticationFilter.class)
            .addFilterBefore(
                    loginAuthenticationFilter(),
                    UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling().accessDeniedPage("/login")
            .and()
            .logout().permitAll().logoutUrl("/logout").logoutSuccessUrl("/")
            .logoutSuccessHandler(this::logoutSuccessHandler)
            .and().csrf().disable()
    ;
  }

  @Bean
  public DaoAuthenticationProvider authProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(myUserDetailsService);
    authProvider.setPasswordEncoder(myUserDetailsService.getEncoder());
    return authProvider;
  }

  @Bean
  public SessionAuthenticationFilter sessionAuthenticationFilter() throws Exception {
    SessionAuthenticationFilter authenticationFilter  = new SessionAuthenticationFilter();
    authenticationFilter.setAuthenticationFailureHandler(this::loginFailureHandler);
    authenticationFilter.setAuthenticationManager(authenticationManagerBean());
    return authenticationFilter;
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

    CookieUtils.removeCookie(request, response, "OAuthCake");
    response.setStatus(HttpStatus.OK.value());
    objectMapper.writeValue(response.getWriter(), "Bye!");
  }
}
