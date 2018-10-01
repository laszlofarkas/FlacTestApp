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

  /**
   * Add new session to the connected session list
   * @param session new session
   */
  private synchronized void addSession(WebSocketSession session) {
    sessions.add(session);
  }

  /**
   * Remove a disconnected session
   * @param session which disconnected
   */
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

  /**
   * Broadcast message to all connected client
   * @param message to be broadcast
   */
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
}
