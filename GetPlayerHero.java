package org.poo.game.commands.debug;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.game.Match;
import org.poo.game.cards.Hero;
import org.poo.game.commands.Command;

import static org.poo.game.cards.Constants.ONE;


public final class GetPlayerHero {
    /**
     * @param match
     * @param output
     * @param playerIndex
     */
    public void execute(final Match match, final ArrayNode output, final int playerIndex) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("command", "getPlayerHero");
        node.put("playerIdx", playerIndex);
        Hero copy;
        if (playerIndex == ONE) {
            copy = new Hero(match.getFirstPlayer().getHero());
            node.putPOJO(Command.output.name(), copy);
        } else {
            copy = new Hero(match.getSecondPlayer().getHero());
            node.putPOJO(Command.output.name(), copy);
        }
        output.add(node);
    }
}
