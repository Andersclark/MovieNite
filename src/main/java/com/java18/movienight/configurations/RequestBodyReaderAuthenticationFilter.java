package com.java18.movienight.configurations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java18.movienight.entities.LoginRequest;
import com.java18.movienight.session.CookieUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class RequestBodyReaderAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private static final Log LOG = LogFactory.getLog(RequestBodyReaderAuthenticationFilter.class);

  private static final String ERROR_MESSAGE = "Something went wrong while parsing the custom /login request body";

  private final ObjectMapper objectMapper = new ObjectMapper();

  public RequestBodyReaderAuthenticationFilter() {}

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    LoginRequest authRequest = null;
    try {
      final String requestBody = request.getReader().readLine();

      // Handle Spring LoginForm (Optional)
      if (requestBody.contains(getUsernameParameter() + "=") && requestBody.contains(getPasswordParameter() + "=") && requestBody.contains("&")) {
        String email = requestBody.substring(requestBody.indexOf("=") + 1, requestBody.indexOf("&"));
        String password = requestBody.substring(requestBody.lastIndexOf("=") + 1);
        System.out.println("SPRING FORM: " + email);
        System.out.println("SPRING FORM: " + password);
        if (!email.isBlank()) {
          authRequest = new LoginRequest(email, "password");
        }
      } else {
        // Handle Custom POST to /login with a JSON Request Body
        System.out.println("JSON: " + requestBody);
        authRequest = objectMapper.readValue(requestBody, LoginRequest.class);
      }

      if(authRequest.username == null) {
        throw new InternalAuthenticationServiceException(ERROR_MESSAGE);
      }

      UsernamePasswordAuthenticationToken token
              = new UsernamePasswordAuthenticationToken(authRequest.username, "password", List.of(new SimpleGrantedAuthority("USER")));

      // Allow subclasses to set the "details" property
      setDetails(request, token);

      CookieUtils.addJWTCookie(response, authRequest.username);

      return this.getAuthenticationManager().authenticate(token);
    } catch(IOException e) {
      LOG.error(ERROR_MESSAGE, e);
      throw new InternalAuthenticationServiceException(ERROR_MESSAGE, e);
    }
  }
}
