package org.poo.game.commands.debug;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.game.Board;
import org.poo.game.cards.Minion;


import java.util.ArrayList;


import static org.poo.game.cards.Constants.FOUR;
import static org.poo.game.cards.Constants.ZERO;

public final class GetCardsOnTable {
    /**
     * @param output
     * @param board
     */
    public void execute(final ArrayNode output, final Board board) {
        ArrayList<ArrayList<Minion>> copy = new ArrayList<>();
        copy.add(new ArrayList<>());
        copy.add(new ArrayList<>());
        copy.add(new ArrayList<>());
        copy.add(new ArrayList<>());
        for (int i = ZERO; i < FOUR; i++) {
            for (Minion minion : board.getCards().get(i)) {
                copy.get(i).add(new Minion(minion));
            }
        }

        output.addObject().put("command", "getCardsOnTable").putPOJO("output", copy);
    }
}
