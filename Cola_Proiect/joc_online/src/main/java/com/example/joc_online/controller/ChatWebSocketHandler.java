package com.example.joc_online.controller;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ChatWebSocketHandler extends TextWebSocketHandler {

    private static final Map<WebSocketSession, String> sessionUsernameMap = Collections.synchronizedMap(new HashMap<>());
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        URI uri = session.getUri();
        System.out.println("WebSocket URI: " + (uri != null ? uri.toString() : "null")); // Debugging URI

        if (uri != null) {
            String query = uri.getQuery();
            System.out.println("Query string: " + query); // Debugging query string
            Map<String, String> params = getQueryParams(query);
            String username = params.get("username");
            System.out.println("Username from query: " + username); // Debugging username

            if (username == null || username.isEmpty()) {
                username = "Anonim";
            }

            sessionUsernameMap.put(session, username);
            System.out.println("Final username stored: " + username); // Debugging stored username
            broadcastMessage("Server", username + " has joined the chat!");
        }
    }



    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String username = sessionUsernameMap.get(session);
        String formattedMessage = username + ": " + message.getPayload();
        broadcastMessage(username, formattedMessage);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String username = sessionUsernameMap.remove(session);
        if (username != null) {
            broadcastMessage("Server", username + " has left the chat!");
        }
    }

    private void broadcastMessage(String sender, String message) throws Exception {
        for (WebSocketSession session : sessionUsernameMap.keySet()) {
            if (session.isOpen()) {
                session.sendMessage(new TextMessage(message));
            }
        }
    }

    private Map<String, String> getQueryParams(String query) {
        if (query == null || query.isEmpty()) {
            return Collections.emptyMap();
        }
        Map<String, String> params = new HashMap<>();
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=", 2);
            if (keyValue.length == 2) {
                params.put(keyValue[0], keyValue[1]);
            }
        }
        return params;
    }
}
