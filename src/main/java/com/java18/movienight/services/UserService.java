package com.java18.movienight.services;

import com.java18.movienight.entities.User;
import com.java18.movienight.repositories.UserRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private UserRepo userRepo;

  public User findBy_id(ObjectId _id) {
    return userRepo.findBy_id(_id);
  }

  public User findOneByEmail(String email) {
    return userRepo.findDistinctFirstByEmailIgnoreCase(email);
  }

  public User insertUser(User user) {
    return userRepo.save(user);
  }

  public void deleteUser(User user) {
    userRepo.delete(user);
  }
}