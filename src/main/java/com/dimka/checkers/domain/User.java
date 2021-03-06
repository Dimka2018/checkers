package com.dimka.checkers.domain;

import lombok.Data;
import org.springframework.web.socket.WebSocketSession;

@Data
public class User {

    private String id;
    private String name;
    private Lobby lobby;
    private Game game;
    private WebSocketSession session;
}
