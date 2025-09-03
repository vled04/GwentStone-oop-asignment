package org.poo.game.commands.gameplay;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.fileio.ActionsInput;
import org.poo.game.Board;
import org.poo.game.Match;
import org.poo.game.cards.Hero;
import org.poo.game.cards.Minion;
import org.poo.game.commands.Command;

import static org.poo.game.cards.Constants.ONE;
import static org.poo.game.cards.Constants.ZERO;


public final class UseAttackHero {
    /**
     * @param output
     * @param actionsInput
     * @param board
     * @param match
     * @param playerIndex
     */
    public void execute(
            final ArrayNode output, final ActionsInput actionsInput, final Board board,
            final Match match, final int playerIndex) {
        Minion minionAttacker = board.getCards().get(actionsInput.getCardAttacker().
                getX()).get(actionsInput.getCardAttacker().getY());
        Hero hero;
        if (playerIndex == ONE) {
            hero = match.getSecondPlayer().getHero();
        } else {
            hero = match.getFirstPlayer().getHero();
        }
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();

        node.put(Command.command.name(), Command.useAttackHero.name());
        node.putPOJO("cardAttacker", actionsInput.getCardAttacker());
        if (minionAttacker.checkFrozen()) {
            node.put("error", "Attacker card is frozen.");
            output.add(node);
            return;
        }
        if (board.opponentHasTank(playerIndex)) {
            node.put("error", "Attacked card is not of type 'Tank'.");
            output.add(node);
            return;
        }
        if (minionAttacker.getAction()) {
            node.put("error", "Attacker card has already attacked this turn.");
            output.add(node);
            return;
        }
        minionAttacker.setAction(true);
        minionAttacker.attack(hero);
        int dead = Math.max(hero.getHealth(), ZERO);
        if (dead == ZERO) {
            match.endMatch(playerIndex);
        }
    }
}
