package com.java18.movienight.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class MyWebSecurityConfigurer extends WebSecurityConfigurerAdapter {

  @Autowired
  MyUserDetailsService myUserDetailsService;

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http
            .sessionManagement()
            .and()
            .authorizeRequests()
            .antMatchers("/auth**").permitAll()
            .antMatchers(HttpMethod.GET, "/api/users/*").hasRole("USER")
            .antMatchers(HttpMethod.GET, "/rest/**").permitAll()
            .antMatchers(HttpMethod.POST, "/api/register").permitAll()
            .antMatchers("/rest/**", "/api/**").hasRole("USER")
            .antMatchers("/rest/**", "/api/**").hasRole("ADMIN")
            .and()
            .oauth2Login()
            .authorizationEndpoint()
            .baseUri("/oauth2/authorize")
            .and()
//            .successHandler(oAuth2AuthenticationSuccessHandler)
//            .failureHandler(oAuth2AuthenticationFailureHandler)
            .and()
            .logout().permitAll().logoutSuccessUrl("/")
            .deleteCookies("JSESSIONID")
            .and()
            .rememberMe().key("ultraUberUniqueAndSecret")
            .tokenValiditySeconds(86400)
            .and().csrf().disable()
    ;
  }

  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
    auth
            .setUserDetailsService(myUserDetailsService);
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
}
