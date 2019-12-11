package com.java18.movienight.services;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.java18.movienight.entities.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class OAuthService {

  @Autowired
  UserService userService;

  private final String CLIENT_SECRET = "wz1bO_JUMqCgm2FJVDJv1BH3";
  private final String CLIENT_ID = "795907338321-t4bumeavl9g5b5k51itg3257eo95qdfq.apps.googleusercontent.com";

  public User authorizeWithGoogle(String code) {
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

    User user = new User(ObjectId.get(), name, email, pictureUrl, accessToken, refreshToken, expiresAt);
    userService.insertUser(user);

    return user;
  }
}
