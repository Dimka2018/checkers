package com.dimka.checkers.dto;

import com.dimka.checkers.event.Event;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MessageResponse {

    private Event type;
}
