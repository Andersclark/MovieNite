package com.java18.movienight.services;

import com.google.api.client.googleapis.auth.oauth2.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.java18.movienight.entities.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;

@Service
public class OAuthService {

  @Autowired
  UserService userService;

  private final String CLIENT_SECRET = "wz1bO_JUMqCgm2FJVDJv1BH3";
  private final String CLIENT_ID = "795907338321-t4bumeavl9g5b5k51itg3257eo95qdfq.apps.googleusercontent.com";

  public GoogleCredential getRefreshedCredentials(String refreshCode) {
    try {
      GoogleTokenResponse response = new GoogleRefreshTokenRequest(
              new NetHttpTransport(), JacksonFactory.getDefaultInstance(), refreshCode, CLIENT_ID, CLIENT_SECRET)
              .execute();

      return new GoogleCredential().setAccessToken(response.getAccessToken());
    } catch (Exception ex) {
      ex.printStackTrace();
      return null;
    }
  }

  public void refreshAccessToken(User user) {
    GoogleCredential credential = getRefreshedCredentials(user.getRefreshToken());
    final long NOW = Instant.now().toEpochMilli();
    // refresh access token if it expires within 30 second
    if (user.getTokenExpires() + 1800000 < NOW) {
      long now = Instant.now().toEpochMilli();
      user.setAccessToken(credential.getAccessToken());
      user.setTokenExpires(now);
      userService.updateUser(user);
    }
  }

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

    // Get profile info from ID token (Obtained at the last step of OAuth2)
    GoogleIdToken idToken = null;
    try {
      idToken = tokenResponse.parseIdToken();
    } catch (IOException e) {
      e.printStackTrace();
    }
    GoogleIdToken.Payload payload = idToken.getPayload();

    String userId = payload.getSubject();
    String email = payload.getEmail();
    String name = (String) payload.get("name");
    String pictureUrl = (String) payload.get("picture");

    User user = new User(ObjectId.get(), userId, name, email, pictureUrl, accessToken, refreshToken, expiresAt, "USER");
    User dbUser = userService.findByEmail(email);
    System.out.println(dbUser);
    if (dbUser == null) {
      userService.insertUser(user);
    }

    return user;
  }
}
