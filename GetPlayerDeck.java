package org.poo.game.commands.debug;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.game.Match;
import org.poo.game.commands.Command;

import static org.poo.game.cards.Constants.ONE;


public final class GetPlayerDeck {
    /**
     * @param match
     * @param output
     * @param playerIndex
     */

    public void execute(final Match match, final ArrayNode output, final int playerIndex) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("command", "getPlayerDeck");
        node.put("playerIdx", playerIndex);
        if (playerIndex == ONE) {
            node.putPOJO(Command.output.name(), match.getFirstPlayer().getDeck());
            output.add(node);
        } else {
            node.putPOJO(Command.output.name(), match.getSecondPlayer().getDeck());
            output.add(node);
        }
    }
}
