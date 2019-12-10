package com.java18.movienight.repositories;

import com.java18.movienight.entities.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends MongoRepository<User, ObjectId> {
  public User findDistinctFirstByEmailIgnoreCase(String email);
  public User findBy_id(ObjectId _id);
}