package com.dimka.checkers.handler;

import com.dimka.checkers.domain.User;
import com.dimka.checkers.repository.UserStorage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@AllArgsConstructor
@Component("login")
public class LoginHandler implements Handler {

    private final UserStorage userStorage;

    @Override
    public void dispatch(TextMessage message, WebSocketSession session) {
        User user = new User();
        user.setSession(session);
        user.setId(session.getId());
        userStorage.save(user);
    }
}
