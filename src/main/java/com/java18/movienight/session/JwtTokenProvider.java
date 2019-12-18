package com.java18.movienight.session;

import com.java18.movienight.configurations.MyUserDetailsService;
import com.java18.movienight.repositories.UserRepo;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
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
  private String secretKey = "wef:23R$32Lf32@L32LM£:f2?L34593%#¤%93945#¤%KepowjefWKEFmcke";
  private long validityInMilliseconds = 1800000; // 30min

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
    claims.put("auth", roles);

    Date now = new Date();
    Date validity = new Date(now.getTime() + validityInMilliseconds);

    return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(validity)
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact();
  }

//  public Authentication getAuthentication(String token) {
//    UserDetails details = userDetails.loadUserByUsername(getEmail(token));
//    return new UsernamePasswordAuthenticationToken(details, "password", details.getAuthorities());
//  }

  public String getEmail(String token) {
    return Jwts.parser().setSigningKey(secretKey).setAllowedClockSkewSeconds(10).parseClaimsJws(token).getBody().getSubject();
  }

  public List<String> getRoles(String token) {
    return Jwts.parser().setSigningKey(secretKey).setAllowedClockSkewSeconds(10).parseClaimsJws(token).getBody().get("auth", List.class);
  }

  public String resolveToken(HttpServletRequest req) {
    String bearerToken = req.getHeader("Authorization");
    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    return null;
  }

  public String validateToken(HttpServletRequest request, HttpServletResponse response, String token) {
    try {
      Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
      return token;
    } catch (JwtException | IllegalArgumentException e) {
      return refreshToken(request, response, token);
    }
  }

  private String refreshToken(HttpServletRequest request, HttpServletResponse response, String token) {

    Claims claims = null;
    try {
      claims = Jwts.parser().setSigningKey(secretKey).setAllowedClockSkewSeconds(604800).parseClaimsJws(token).getBody();
    } catch (Exception e) {
      return "invalid token";
    }
    // if jwt token expired in last 7 days, renew the token
    if(claims.getExpiration().getTime() + 604800000 > Instant.now().toEpochMilli()) {
      CookieUtils.removeJWTCookie(request, response);
      String jwtToken = createToken(claims.getSubject(), List.of("USER"));
      CookieUtils.addCookie(response, "JWT_SESSION", jwtToken);
      return jwtToken;
    }
    return token;
  }

}