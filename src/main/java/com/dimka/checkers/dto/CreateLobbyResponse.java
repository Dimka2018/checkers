package com.dimka.checkers.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CreateLobbyResponse extends MessageResponse {

    private String id;
    private String host;
}
