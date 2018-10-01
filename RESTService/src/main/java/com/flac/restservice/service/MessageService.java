package com.flac.restservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flac.restservice.repository.MessageMongoRepository;
import com.flac.restservice.repository.MessageRedisRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

  private static final Logger LOGGER = LoggerFactory.getLogger(MessageService.class);

  @Autowired
  private MessageRedisRepository messageRedisRepository;

  @Autowired
  private MongoTemplate mongoTemplate;

  @Autowired
  private MessageMongoRepository messageMongoRepository;

  private ObjectMapper mapper = new ObjectMapper();

  public void processMessage(Object message) throws JsonProcessingException {
    messageRedisRepository.send(mapper.writeValueAsString(message));
    mongoTemplate.insert(message, "message");
    LOGGER.info("Message has been saved");
  }

  public List<Object> getAllmessage() {
    return messageMongoRepository.findAll();
  }
}
