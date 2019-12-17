package com.java18.movienight.session;

import com.java18.movienight.configurations.MyUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

  private String secretKey = "SuperUniqueSecretKey";

  private long validityInMilliseconds = 3600000; // 1h

  @Autowired
  private MyUserDetailsService userDetails;

  @PostConstruct
  protected void init() {
    secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
  }

  public String createToken(String email, List<String> roles) {

    System.out.println("CREATE TOKEN: " + email);

    Claims claims = Jwts.claims().setSubject(email);
    claims.put("auth", roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));

    Date now = new Date();
    Date validity = new Date(now.getTime() + validityInMilliseconds);

    return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(validity)
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact();
  }

  public Authentication getAuthentication(String token) {
    UserDetails details = userDetails.loadUserByUsername(getEmail(token));
    return new UsernamePasswordAuthenticationToken(details, "password", details.getAuthorities());
  }

  public String getEmail(String token) {
    System.err.println("JWT expiration: " + Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getExpiration());

    return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
  }

  public String resolveToken(HttpServletRequest req) {
    String bearerToken = req.getHeader("Authorization");
    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    return null;
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
      return true;
    } catch (JwtException | IllegalArgumentException e) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Expired or invalid JWT token");
    }
  }

}