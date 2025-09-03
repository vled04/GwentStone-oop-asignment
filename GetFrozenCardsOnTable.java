package org.poo.game.commands.debug;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.game.Board;
import org.poo.game.cards.Minion;
import org.poo.game.commands.Command;

import java.util.ArrayList;

import static org.poo.game.cards.Constants.FOUR;
import static org.poo.game.cards.Constants.ZERO;

public final class GetFrozenCardsOnTable {
    /**
     * @param output
     * @param board
     */

    public void execute(final ArrayNode output, final Board board) {
        ArrayList<Minion> frozenCards = new ArrayList<>();
        for (int i = ZERO; i < FOUR; i++) {
            for (Minion minion : board.getCards().get(i)) {
                if (minion.checkFrozen() == true) {
                    frozenCards.add(new Minion(minion));
                }
            }
        }
        if (!frozenCards.isEmpty()) {
            output.addObject().put(Command.command.name(), Command.getFrozenCardsOnTable.name())
                    .putPOJO(Command.output.name(), frozenCards);
        } else {
            output.addObject().put(Command.command.name(), Command.getFrozenCardsOnTable.name())
                    .putPOJO(Command.output.name(), frozenCards);
        }
    }
}
