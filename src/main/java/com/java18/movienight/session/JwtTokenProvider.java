package com.java18.movienight.session;

import com.java18.movienight.configurations.MyUserDetailsService;
import com.java18.movienight.repositories.UserRepo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

  private final static JwtTokenProvider singleton = new JwtTokenProvider();
  private String secretKey = "SuperUniqueSecretKey";
//  private long validityInMilliseconds = 3600000; // 1h
  private long validityInMilliseconds = 5000; // 1h

  @Autowired
  private MyUserDetailsService userDetails;

  @Autowired
  UserRepo userRepo;

  private JwtTokenProvider() {
    init();
  }

  @PostConstruct
  protected void init() {
    secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
  }

  public static JwtTokenProvider get() {
    return singleton;
  }

  public String createToken(String email, List<String> roles) {
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
    return Jwts.parser().setSigningKey(secretKey).setAllowedClockSkewSeconds(10).parseClaimsJws(token).getBody().getSubject();
  }

  public String resolveToken(HttpServletRequest req) {
    String bearerToken = req.getHeader("Authorization");
    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    return null;
  }

  public String validateToken(HttpServletResponse response, String token) {
    try {
      Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
      return token;
    } catch (JwtException | IllegalArgumentException e) {
      Claims claims = Jwts.parser().setSigningKey(secretKey).setAllowedClockSkewSeconds(604800).parseClaimsJws(token).getBody();

      // if jwt token expired in last 7 days, renew the token
      if(claims.getExpiration().getTime() + 604800000 > Instant.now().toEpochMilli()) {
        System.out.println("Renewing expired token: " + claims.getSubject());
        String jwtToken = createToken(claims.getSubject(), List.of("USER"));
        CookieUtils.addCookie(response, "JWT_SESSION", jwtToken);
        return jwtToken;
      }
      return "invalid cookie";
    }
  }

}