package com.dimka.checkers.dto;

import com.dimka.checkers.domain.LobbyMember;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
public class LobbyMembersResponse extends MessageResponse {

    private Set<LobbyMember> members;
}
