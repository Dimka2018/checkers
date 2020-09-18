package com.dimka.checkers.handler;

import com.dimka.checkers.domain.Lobby;
import com.dimka.checkers.domain.User;
import com.dimka.checkers.dto.JoinLobbyRequest;
import com.dimka.checkers.event.EventEmitter;
import com.dimka.checkers.repository.UserStorage;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Slf4j
@AllArgsConstructor
@Component("joinLobby")
public class JoinLobbyHandler implements Handler {

    private final UserStorage userStorage;
    private final EventEmitter emitter;
    private final ObjectMapper mapper;

    @Override
    public void dispatch(TextMessage message, WebSocketSession session) throws Exception {
        JoinLobbyRequest request = mapper.readValue(message.getPayload(), JoinLobbyRequest.class);

        Lobby lobby = userStorage.getLobby(request.getLobbyId());
        User user = userStorage.getUser(session.getId());
        user.setLobby(lobby);

        emitter.emmitJoinedToLobbyEvent(lobby.getId(), session);

        emitter.emmitLobbyMemberListChangedEvent(lobby.getId());
    }
}
