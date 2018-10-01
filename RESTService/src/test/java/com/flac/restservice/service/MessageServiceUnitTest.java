package com.flac.restservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flac.restservice.repository.MessageMongoRepository;
import com.flac.restservice.repository.MessageRedisRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MessageServiceUnitTest {

  @MockBean
  private MessageRedisRepository redisRepository;

  @MockBean
  private MessageMongoRepository mongoRepository;

  @Autowired
  private MessageService messageService;

  private LinkedHashMap<Object, Object> message = new LinkedHashMap<>();

  @Before
  public void setup() throws IOException {
    this.message.clear();
    this.message.put("Greeting", "Hello Test!");
  }

  @Test
  public void processMessageShouldInsertAndSend() throws JsonProcessingException {
    // WHEN
    messageService.processMessage(this.message);

    // THEN
    ObjectMapper mapper = new ObjectMapper();
    // MessageRedisRepository should get the object's json representation
    verify(redisRepository, times(1)).send(mapper.writeValueAsString(this.message));
    // MessageMongoRepository should get the object itself
    verify(mongoRepository, times(1)).insert(this.message);
  }

  @Test
  public void getAllMessagesShouldReturn() {
    // GIVEN
    List<Object> stored = new ArrayList<>();
    stored.add(this.message);
    when(mongoRepository.findAll()).thenReturn(stored);

    // WHEN
    List<Object> result = messageService.getAllMessages();

    // THEN
    verify(mongoRepository, times(1)).findAll();
    assertThat(result).containsExactly(this.message);

  }

}
