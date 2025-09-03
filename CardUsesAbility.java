package org.poo.game.commands.gameplay;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.fileio.ActionsInput;
import org.poo.game.Board;
import org.poo.game.Match;
import org.poo.game.cards.Minion;

import static org.poo.game.cards.Constants.ONE;
import static org.poo.game.cards.Constants.TWO;
import static org.poo.game.cards.Constants.ZERO;



public final class CardUsesAbility {
    /**
     * @param output
     * @param actionsInput
     * @param board
     * @param match
     * @param playerIdx
     */
    public void execute(
            final ArrayNode output, final ActionsInput actionsInput, final Board board,
            final Match match, final int playerIdx) {

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("command", "cardUsesAbility");
        Minion minionAttacker = board.getCards().get(actionsInput.getCardAttacker().getX()).
                get(actionsInput.getCardAttacker().getY());
        Minion minionAttacked = board.getCards().get(actionsInput.getCardAttacked().getX()).
                get(actionsInput.getCardAttacked().getY());

        if (minionAttacker == null || minionAttacked == null) {
            return;
        }
        node.put("command", "cardUsesAbility");
        node.putPOJO("cardAttacker", actionsInput.getCardAttacker());
        node.putPOJO("cardAttacked", actionsInput.getCardAttacked());
        int minionAttackerX = actionsInput.getCardAttacker().getX();
        int minionAttackerY = actionsInput.getCardAttacker().getY();
        if (minionAttackerY > board.size(minionAttackerY)) {
            node.put("error", "Attacker card has already attacked this turn.");
            output.add(node);
            return;
        }
        int minionAttackedX = actionsInput.getCardAttacked().getX();
        int minionAttackedY = actionsInput.getCardAttacked().getY();
        if (minionAttackedY > board.size(minionAttackedY)) {
            node.put("error", "Attacker card has already attacked this turn.");
            output.add(node);
            return;
        }

        if (minionAttacker.checkFrozen()) {
            node.put("error", "Attacker card is frozen.");
            output.add(node);
            return;
        }
        if (minionAttacker.getAction()) {
            node.put("error", "Attacker card has already attacked this turn.");
            output.add(node);
            return;
        }
        int attackerIndex;
        int attackedIndex;
        if (minionAttackerX == ZERO || minionAttackerX == ONE) {
            attackerIndex = TWO;
        } else {
            attackerIndex = ONE;
        }
        if (minionAttackedX == ZERO || minionAttackedX == ONE) {
            attackedIndex = TWO;
        } else {
            attackedIndex = ONE;
        }
        boolean friends = attackedIndex == attackerIndex;

        if (minionAttacker.getName().equals("Disciple")) {
            if (!friends) {
                node.put("error", "Attacked card does not belong to the current player.");
                output.add(node);
                return;
            }
        }

        if ((friends) && !minionAttacker.getName().equals("Disciple")) {
            node.put("error", "Attacked card does not belong to the enemy.");
            output.add(node);
            return;
        }
        if (board.opponentHasTank(playerIdx) && !minionAttacked.checkTank() && !minionAttacker.getName().equals("Disciple")) {
            node.put("error", "Attacked card is not of type 'Tank'.");
            output.add(node);
            return;
        }
        minionAttacker.setAction(true);
        minionAttacker.useAbility(minionAttacked);
    }
}
