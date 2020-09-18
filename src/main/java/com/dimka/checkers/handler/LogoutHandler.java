package com.dimka.checkers.handler;

import com.dimka.checkers.domain.Lobby;
import com.dimka.checkers.domain.User;
import com.dimka.checkers.event.EventEmitter;
import com.dimka.checkers.repository.UserStorage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@AllArgsConstructor
@Component("logout")
public class LogoutHandler implements Handler {

    private final UserStorage userStorage;
    private final EventEmitter emitter;

    @Override
    public void dispatch(TextMessage message, WebSocketSession session) throws Exception {
        User user = userStorage.getUser(session.getId());
        if (user.getLobby() != null) {
            Lobby lobby = user.getLobby();
            if (lobby.getHost().equals(user.getId())) {
                emitter.emmitLobbyDestroyedEvent(lobby.getId());
            } else {
                user.setLobby(null);
                emitter.emmitLobbyMemberListChangedEvent(lobby.getId());
            }
        }
        if (user.getGame() != null) {
            User enemy = userStorage.getGameEnemy(session.getId());
            user.setGame(null);
            enemy.setGame(null);
            emitter.emmitLeaveGameEvent(session, enemy.getSession());
        }
        userStorage.delete(session.getId());
    }
}
