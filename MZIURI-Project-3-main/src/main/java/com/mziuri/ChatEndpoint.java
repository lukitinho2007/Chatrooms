package com.mziuri;


import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.RemoteEndpoint;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;


import javax.websocket.*;

@ServerEndpoint("/chat")
public class ChatEndpoint {
    private static final Set<javax.websocket.Session> sessions = Collections.synchronizedSet(new HashSet<>());

    @OnOpen
    public void onOpen(javax.websocket.Session session) {
        sessions.add(session);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        for (javax.websocket.Session s : sessions) {
            if (s != session) {
                try {
                    s.getBasicRemote().sendText(message);
                } catch (IOException e) {

                    e.printStackTrace();
                }
            }
        }
    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
    }
}