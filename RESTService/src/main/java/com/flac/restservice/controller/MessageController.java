package com.flac.restservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.flac.restservice.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MessageController {

  private static final Logger LOGGER = LoggerFactory.getLogger(MessageController.class);

  @Autowired
  private MessageService messageService;

  @RequestMapping(value = "/", method = RequestMethod.GET)
  public ResponseEntity<List<Object>> getMessages() {
    return new ResponseEntity<>(messageService.getAllmessage(), HttpStatus.OK);
  }

  @RequestMapping(value = "/", method = RequestMethod.PUT)
  public ResponseEntity<?> saveMessage(@RequestBody Object message) {
    try {
      messageService.processMessage(message);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (JsonProcessingException e) {
      LOGGER.error("Unable to save message", e);
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }
}
