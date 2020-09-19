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

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

@AllArgsConstructor
@Component("applyBoardState")
public class ApplyBoardStateHandler implements Handler {

    private static final Integer[][] STARTING_BOARD = new Integer[][] {
            {null, 1, null, 1, null, 1, null, 1},
            {1, null, 1, null, 1, null, 1, null},
            {null, 1, null, 1, null, 1, null, 1},
            {0, null, 0, null, 0, null, 0, null},
            {null, 0, null, 0, null, 0, null, 0},
            {2, null, 2, null, 2, null, 2, null},
            {null, 2, null, 2, null, 2, null, 2},
            {2, null, 2, null, 2, null, 2, null}
    };

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
            if (isWinner(cells)) {
                if (game.getCurrentGameNum() < game.getTotalGames()) {
                    emitter.emmitGamesChangedEvent(game.getCurrentGameNum(), game.getTotalGames(), session, enemy.getSession());
                    Map<String, Integer> score = game.getScore();
                    int enemyScore = score.get(enemy.getId());
                    int myScore = score.get(session.getId()) + 1;
                    score.put(session.getId(), myScore);
                    emitter.emmitScoreChangedEvent(myScore, enemyScore, session);
                    emitter.emmitScoreChangedEvent(enemyScore, myScore, enemy.getSession());
                    emitter.emmitBoardStateChangedEvent(STARTING_BOARD, session);
                    emitter.emmitBoardStateChangedEvent(STARTING_BOARD, enemy.getSession());
                } else {
                    user.setGame(null);
                    enemy.setGame(null);
                    int myScore = game.getScore().get(session.getId()) + 1;
                    int enemyScore = game.getScore().get(enemy.getId());

                    if (myScore > enemyScore) {
                        emitter.emmitYouWinEvent(session);
                        emitter.emmitYouLoseEvent(enemy.getSession());
                    } else if (myScore == enemyScore) {
                        emitter.emmitDrawEvent(session, enemy.getSession());
                    } else {
                        emitter.emmitYouWinEvent(enemy.getSession());
                        emitter.emmitYouLoseEvent(session);
                    }
                }
                game.setCurrentGameNum(game.getCurrentGameNum() + 1);
                emitter.emmitGamesChangedEvent(game.getCurrentGameNum(), game.getTotalGames(), session, enemy.getSession());
            } else {
                Integer[][] invertedCells = getInvertedCells(cells);
                emitter.emmitBoardStateChangedEvent(invertedCells, session);
            }

            game.setTurn(enemy.getId());
            emitter.emmitEnemyTurnEvent(session);
            emitter.emmitMyTurnEvent(enemy.getSession());
        }
    }

    private Integer[][] getInvertedCells(Integer[][] cells) {
        Integer[][] invertedCells = new Integer[8][8];
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

    private boolean isWinner(Integer[][] cells) {
        return Arrays.stream(cells)
                .flatMap(Arrays::stream)
                .filter(Objects::nonNull)
                .allMatch(cell -> cell != 1);
    }
}
