package com.dimka.checkers.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ScoreResponse extends MessageResponse {

    private int you;
    private int enemy;
}
