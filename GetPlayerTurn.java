package org.poo.game.commands.debug;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.game.Match;
import org.poo.game.commands.Command;


public final class GetPlayerTurn {
    /**
     * @param match
     * @param output
     */
    public void execute(final Match match, final ArrayNode output) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();

        node.put(Command.command.name(), Command.getPlayerTurn.name());
        node.put(Command.output.name(), match.getPlayerTurn());

        output.add(node);
    }
}
