package com.flac.restservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flac.restservice.repository.MessageMongoRepository;
import com.flac.restservice.repository.MessageRedisRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

  private static final Logger LOGGER = LoggerFactory.getLogger(MessageService.class);

  @Autowired
  private MessageRedisRepository messageRedisRepository;

  @Autowired
  private MessageMongoRepository messageMongoRepository;

  private ObjectMapper mapper = new ObjectMapper();

  /**
   * Process the given message. Save it into MongoDB and send to Redis as a message
   *
   * @param message to be processed
   * @throws JsonProcessingException when not able to process the given message
   */
  public void processMessage(Object message) throws JsonProcessingException {
    messageRedisRepository.send(mapper.writeValueAsString(message));
    messageMongoRepository.insert(message);
    LOGGER.info("Message has been saved");
  }

  /**
   * Get all stored message from MongoDB
   *
   * @return list of the stored message
   */
  public List<Object> getAllmessage() {
    return messageMongoRepository.findAll();
  }
}
