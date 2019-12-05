package com.java18.movienight.services;

import org.bson.types.ObjectId;

public class Util {
  static public ObjectId convertToObjectId(String _id) {
    try {
      return new ObjectId(_id);
    } catch (Exception e) {
      return null;
    }
  }
}