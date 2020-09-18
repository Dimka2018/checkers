package com.dimka.checkers.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LobbyMember {

    private String id;
    private String name;
    private boolean isHost;
}
