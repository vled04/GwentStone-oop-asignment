package org.poo.game.commands.debug;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.game.Match;
import org.poo.game.cards.Card;
import org.poo.game.cards.Minion;
import org.poo.game.commands.Command;

import java.util.ArrayList;

import static org.poo.game.cards.Constants.TWO;

public final class GetCardsInHand {
    /**
     * @param match
     * @param output
     * @param playerIndex
     */
    public void execute(final Match match, final ArrayNode output, final int playerIndex) {
        ArrayList<Minion> copy = new ArrayList<>();
        if (playerIndex == TWO) {
            for (Card card : match.getSecondPlayer().getHand()) {
                copy.add(new Minion((Minion) card));
            }
        } else {
            for (Card card : match.getFirstPlayer().getHand()) {
                copy.add(new Minion((Minion) card));
            }
        }
        output.addObject()
                .put(Command.command.name(), Command.getCardsInHand.name())
                .put(Command.playerIdx.name(), playerIndex)
                .putPOJO(Command.output.name(), copy);
    }
}
