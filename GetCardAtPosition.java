package org.poo.game.commands.debug;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.fileio.ActionsInput;
import org.poo.game.Board;
import org.poo.game.cards.Minion;
import org.poo.game.commands.Command;


public final class GetCardAtPosition {
    /**
     * @param output
     * @param actionsInput
     * @param board
     */
    public void execute(final ArrayNode output, final ActionsInput actionsInput,
                        final Board board) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();

        node.put("command", "getCardAtPosition");
        int x = actionsInput.getX();
        int y = actionsInput.getY();
        node.put("x", x);
        node.put("y", y);
        if (y > board.size(x)) {
            node.put(Command.output.name(), "No card available at that position.");
            output.add(node);
            return;
        }
        Minion copy = new Minion(board.getCards().get(x).get(y));
        node.putPOJO(Command.output.name(), copy);
        output.add(node);
    }
}
