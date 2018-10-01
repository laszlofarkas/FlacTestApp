package com.flac.wsservice.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketHandler.class);
  private static List<WebSocketSession> sessions = new ArrayList<>();

  private synchronized void addSession(WebSocketSession session) {
    sessions.add(session);
  }

  private synchronized void removeSession(WebSocketSession session) {
    sessions.remove(session);
  }

  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    addSession(session);
    LOGGER.info("Client connected");
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    removeSession(session);
    LOGGER.info("Client disconnected");
  }

  public void broadcast(String message) {
    TextMessage msg = new TextMessage(message);
    for (WebSocketSession session : sessions) {
      try {
        session.sendMessage(msg);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    LOGGER.info("Message were sent");
  }

  @Override
  protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    broadcast(message.getPayload());
  }
}
