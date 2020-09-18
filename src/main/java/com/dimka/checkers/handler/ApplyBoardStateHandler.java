package com.dimka.checkers.handler;

import com.dimka.checkers.domain.Game;
import com.dimka.checkers.domain.User;
import com.dimka.checkers.dto.ApplyBoardStateRequest;
import com.dimka.checkers.event.EventEmitter;
import com.dimka.checkers.repository.UserStorage;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;

@AllArgsConstructor
@Component("applyBoardState")
public class ApplyBoardStateHandler implements Handler {

    private final UserStorage userStorage;
    private final EventEmitter emitter;
    private final ObjectMapper mapper;
    

    @Override
    public void dispatch(TextMessage message, WebSocketSession session) throws Exception {
        ApplyBoardStateRequest request = mapper.readValue(message.getPayload(), ApplyBoardStateRequest.class);
        User user = userStorage.getUser(session.getId());
        Game game = user.getGame();
        if (game.getTurn().equals(session.getId())) {
            Integer[][] cells = request.getCells();
            User enemy = userStorage.getGameEnemy(session.getId());
            Integer[][] invertedCells = getInvertedCells(cells);

            emitter.emmitBoardStateChangedEvent(invertedCells, session);
            game.setTurn(enemy.getId());
            emitter.emmitEnemyTurnEvent(session);
            emitter.emmitMyTurnEvent(enemy.getSession());
        }
    }

    private Integer[][] getInvertedCells(Integer[][] cells) {
        Integer[][] invertedCells = new Integer[8][8];
        /*for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                if (cells[i][j] == null) {
                    invertedCells[i][j] = cells[i][j];
                } else if (cells[i][j] == 1) {
                    invertedCells[i][j] = 2;
                } else if (cells[i][j] == 2) {
                    invertedCells[i][j] = 1;
                } else {
                    invertedCells[i][j] = cells[i][j];
                }
            }
        }*/

        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                if (cells[i][j] == null) {
                    invertedCells[cells.length - i - 1][cells[i].length - j - 1] = cells[i][j];
                } else if (cells[i][j] == 1) {
                    invertedCells[cells.length - i - 1][cells[i].length - j - 1] = 2;
                } else if (cells[i][j] == 2) {
                    invertedCells[cells.length - i - 1][cells[i].length - j - 1] = 1;
                } else {
                    invertedCells[cells.length - i - 1][cells[i].length - j - 1] = cells[i][j];
                }

            }
        }
        return invertedCells;
    }
}
