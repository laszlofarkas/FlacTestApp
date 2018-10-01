package com.flac.wsservice.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RedisMessageHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(RedisMessageHandler.class);

  @Autowired
  private WebSocketHandler webSocketHandler;

  public void receiveMessage(String message) {
    LOGGER.info("Received <" + message + ">");
    webSocketHandler.broadcast(message);
  }
}
