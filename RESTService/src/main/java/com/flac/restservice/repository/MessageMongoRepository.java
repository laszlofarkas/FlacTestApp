package com.flac.restservice.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MessageMongoRepository {

  private static final String COLLECTION = "message";

  @Autowired
  private MongoTemplate template;

  public void insert(Object message) {
    template.insert(message, COLLECTION);
  }

  public List<Object> findAll() {
    Query q = new Query();
    q.fields().exclude("_id");
    return template.find(q, Object.class, COLLECTION);
  }

}
