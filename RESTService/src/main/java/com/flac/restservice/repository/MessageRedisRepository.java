package com.flac.restservice.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MessageRedisRepository {

  @Autowired
  private StringRedisTemplate template;

  /**
   * Send the given message to the "message" channel on Redis
   * @param message
   */
  public void send(Object message) {
    template.convertAndSend("message", message);
  }
}
