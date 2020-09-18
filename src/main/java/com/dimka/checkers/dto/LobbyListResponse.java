package com.dimka.checkers.dto;

import com.dimka.checkers.domain.Lobby;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
public class LobbyListResponse extends MessageResponse {

    private Set<Lobby> lobbies;
}
