package org.poo.game.commands.gameplay;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.fileio.ActionsInput;
import org.poo.game.Board;
import org.poo.game.cards.Minion;
import org.poo.game.commands.Command;

import static org.poo.game.cards.Constants.*;


public final class CardUsesAttack {
    /**
     * @param output
     * @param actionsInput
     * @param board
     */
    public void execute(
            final ArrayNode output, final ActionsInput actionsInput, final Board board) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put(Command.command.name(), Command.cardUsesAttack.name());
        if (actionsInput.getCardAttacker().getY() > board.getCards().
                get(actionsInput.getCardAttacker().getX()).size()) {
            return;
        }
        if (actionsInput.getCardAttacked().getY() > board.getCards().
                get(actionsInput.getCardAttacked().getX()).size()) {
            return;
        }
        node.putPOJO("cardAttacker", actionsInput.getCardAttacker());
        node.putPOJO("cardAttacked", actionsInput.getCardAttacked());
        int minionAttackerX = actionsInput.getCardAttacker().getX();
        int minionAttackerY = actionsInput.getCardAttacker().getY();
        Minion attacker = board.getCards().get(minionAttackerX).get(minionAttackerY);
        if (attacker == null) {
            return;
        }
        int minionAttackedX = actionsInput.getCardAttacked().getX();
        int minionAttackedY = actionsInput.getCardAttacked().getY();
        Minion attacked = board.getCards().get(minionAttackedX).get(minionAttackedY);
        if (attacked == null) {
            return;
        }
        int attackerIndex = ZERO;
        if (minionAttackerX == ZERO || minionAttackerX == ONE) {
            attackerIndex = TWO;
        } else if (minionAttackerX == TWO || minionAttackerX == THREE) {
            attackerIndex = ONE;
        }
        int attackedIndex = ZERO;
        if (minionAttackedX == ZERO || minionAttackedX == ONE) {
            attackedIndex = TWO;
        } else if (minionAttackedX == TWO || minionAttackedX == THREE) {
            attackedIndex = ONE;
        }
        if (attacker.checkFrozen()) {
            node.put("error", "Attacker card is frozen.");
            output.add(node);
            return;
        }
        if (attacker.getAction()) {
            node.put("error", "Attacker card has already attacked this turn.");
            output.add(node);
            return;
        }
        if (attackerIndex == attackedIndex) {
            node.put("error", "Attacked card does not belong to the enemy.");
            output.add(node);
            return;
        }

        boolean checktank = false;
        for (Minion minion : board.getCards().get(attackerIndex)) {
            if (minion.checkTank()) {
                checktank = true;
            }
        }

        if (checktank && !attacked.checkTank()) {
            node.put("error", "Attacked card is not of type 'Tank'.");
            output.add(node);
            return;
        }
        attacker.attack(attacked);
        if (attacked.getHealth() <= ZERO) {
            board.removeMinionOnTable(attacked);
        }
        attacker.setAction(true);
    }
}
