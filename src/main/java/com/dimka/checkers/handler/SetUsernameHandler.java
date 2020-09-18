package com.dimka.checkers.handler;

import com.dimka.checkers.domain.User;
import com.dimka.checkers.dto.SetUsernameRequest;
import com.dimka.checkers.event.EventEmitter;
import com.dimka.checkers.repository.UserStorage;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@AllArgsConstructor
@Component("setUsername")
public class SetUsernameHandler implements Handler {

    private final UserStorage userStorage;
    private final EventEmitter emitter;
    private final ObjectMapper mapper;

    @Override
    public void dispatch(TextMessage message, WebSocketSession session) throws Exception {
        SetUsernameRequest request = mapper.readValue(message.getPayload(), SetUsernameRequest.class);
        User user = userStorage.getUser(session.getId());
        user.setName(request.getUsername());
        emitter.emmitUsernameChangedEvent(session);
    }
}
