package com.dimka.checkers.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class JoinLobbyRequest extends MessageRequest {

    private String lobbyId;
}
