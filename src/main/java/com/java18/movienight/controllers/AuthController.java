package com.java18.movienight.controllers;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
public class AuthController {
  private final String CLIENT_SECRET = "wz1bO_JUMqCgm2FJVDJv1BH3";
  private final String CLIENT_ID = "795907338321-t4bumeavl9g5b5k51itg3257eo95qdfq.apps.googleusercontent.com";

  @PostMapping("/storeauthcode")
  public @ResponseBody String storeauthcode(@RequestBody String code, @RequestHeader("X-Requested-With") String encoding) {
    if (encoding == null || encoding.isEmpty()) {
      // Without the `X-Requested-With` header, this request could be forged. Aborts.
      return "Error, wrong headers";
    }

    GoogleTokenResponse tokenResponse = null;
    try {
      tokenResponse = new GoogleAuthorizationCodeTokenRequest(
              new NetHttpTransport(),
              JacksonFactory.getDefaultInstance(),
              "https://www.googleapis.com/oauth2/v4/token",
              CLIENT_ID,
              CLIENT_SECRET,
              code,
              "http://localhost:8080") // Make sure you set the correct port
              .execute();
    } catch (IOException e) {
      e.printStackTrace();
    }

    // Store these 3 in your DB
    String accessToken = tokenResponse.getAccessToken();
    String refreshToken = tokenResponse.getRefreshToken();
    Long expiresAt = System.currentTimeMillis() + (tokenResponse.getExpiresInSeconds() * 1000);

    // Debug purpose only
    System.out.println("accessToken: " + accessToken);
    System.out.println("refreshToken: " + refreshToken);
    System.out.println("expiresAt: " + expiresAt);

    // Get profile info from ID token (Obtained at the last step of OAuth2)
    GoogleIdToken idToken = null;
    try {
      idToken = tokenResponse.parseIdToken();
    } catch (IOException e) {
      e.printStackTrace();
    }


    GoogleIdToken.Payload payload = idToken.getPayload();

    // Use THIS ID as a key to identify a google user-account.
    String userId = payload.getSubject();

    String email = payload.getEmail();
    boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
    String name = (String) payload.get("name");
    String pictureUrl = (String) payload.get("picture");
    String locale = (String) payload.get("locale");
    String familyName = (String) payload.get("family_name");
    String givenName = (String) payload.get("given_name");

    // Debugging purposes, should probably be stored in the database instead (At least "givenName").
    System.out.println("userId: " + userId);
    System.out.println("email: " + email);
    System.out.println("emailVerified: " + emailVerified);
    System.out.println("name: " + name);
    System.out.println("pictureUrl: " + pictureUrl);
    System.out.println("locale: " + locale);
    System.out.println("familyName: " + familyName);
    System.out.println("givenName: " + givenName);

    return "OK";
  }
}
