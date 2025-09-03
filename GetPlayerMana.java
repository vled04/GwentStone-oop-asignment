package org.poo.game.commands.debug;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.game.Match;
import org.poo.game.commands.Command;

import static org.poo.game.cards.Constants.TWO;


public final class GetPlayerMana {
    /**
     * @param match
     * @param output
     * @param playerIndex
     */
    public void execute(final Match match, final ArrayNode output, final int playerIndex) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("command", "getPlayerMana");
        node.put("playerIdx", playerIndex);
        if (playerIndex == TWO) {
            node.put(Command.output.name(), match.getSecondPlayer().getMana());
        } else {
            node.put(Command.output.name(), match.getFirstPlayer().getMana());
        }
        output.add(node);
    }
}