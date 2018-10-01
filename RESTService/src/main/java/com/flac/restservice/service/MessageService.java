package com.flac.restservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

  private static final Logger LOGGER = LoggerFactory.getLogger(MessageService.class);

  @Autowired
  private StringRedisTemplate template;

  private ObjectMapper mapper = new ObjectMapper();

  public void processMessage(Object message) throws JsonProcessingException {
    template.convertAndSend("message", mapper.writeValueAsString(message));
    LOGGER.info("Message has been saved");
  }
}
