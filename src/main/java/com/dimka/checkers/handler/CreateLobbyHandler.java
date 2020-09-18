package com.dimka.checkers.handler;

import com.dimka.checkers.domain.Lobby;
import com.dimka.checkers.domain.User;
import com.dimka.checkers.dto.CreateLobbyRequest;
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
@Component("createLobby")
public class CreateLobbyHandler implements Handler {

    private final UserStorage userStorage;
    private final EventEmitter emitter;
    private final ObjectMapper mapper;

    @Override
    public void dispatch(TextMessage message, WebSocketSession session) throws Exception {
        try {
            CreateLobbyRequest request = mapper.readValue(message.getPayload(), CreateLobbyRequest.class);

            if (request.getGames() <= 0)
                throw new Exception("invalid number of games " + request.getGames());

            Lobby lobby = new Lobby();
            lobby.setHost(session.getId());
            lobby.setId(session.getId());
            lobby.setGames(request.getGames());
            User user = userStorage.getUser(session.getId());
            lobby.setHostName(user.getName());
            user.setLobby(lobby);

            emitter.emmitLobbyCreatedEvent(session);

            emitter.emmitLobbyMemberListChangedEvent(lobby.getId());

            emitter.emmitLobbyListChanged(session);
        } catch (Exception e) {
            log.debug("invalid request", e);
        }

    }
}
