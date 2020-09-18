package com.dimka.checkers.handler;

import com.dimka.checkers.domain.Lobby;
import com.dimka.checkers.domain.User;
import com.dimka.checkers.event.EventEmitter;
import com.dimka.checkers.repository.UserStorage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Slf4j
@AllArgsConstructor
@Component("leaveLobby")
public class LeaveLobbyHandler implements Handler {

    private final UserStorage userStorage;
    private final EventEmitter emitter;

    @Override
    public void dispatch(TextMessage message, WebSocketSession session) throws Exception {
        User user = userStorage.getUser(session.getId());
        Lobby lobby = user.getLobby();
        user.setLobby(null);
        emitter.emmitLobbyLeaveEvent(session);
        if (session.getId().equals(lobby.getHost())) {
            emitter.emmitLobbyDestroyedEvent(lobby.getId());
            emitter.emmitLobbyListChanged(session);
        } else {
            emitter.emmitLobbyMemberListChangedEvent(lobby.getId());
        }
    }
}
