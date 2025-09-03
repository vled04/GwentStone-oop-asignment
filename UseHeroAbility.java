package org.poo.game.commands.gameplay;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.fileio.ActionsInput;
import org.poo.game.Board;
import org.poo.game.Match;
import org.poo.game.Player;
import org.poo.game.cards.Hero;

import static org.poo.game.cards.Constants.*;


public final class UseHeroAbility {
    /**
     * @param match
     * @param output
     * @param actionsInput
     * @param board
     */
    public void execute(final Match match, final ArrayNode output,
                        final ActionsInput actionsInput,
                        final Board board) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("command", "useHeroAbility");
        node.put("affectedRow", actionsInput.getAffectedRow());
        Player attacker = null;
        Hero hero = null;
        if (match.getPlayerTurn() == ONE) {
            attacker = match.getFirstPlayer();
            hero = match.getFirstPlayer().getHero();
        } else {
            attacker = match.getSecondPlayer();
            hero = match.getSecondPlayer().getHero();
        }
        boolean friends = false;
        int affectedRow = actionsInput.getAffectedRow();
        if (match.getPlayerTurn() == ONE && (affectedRow == THREE || affectedRow == TWO)) {
            friends = true;
        }
        if (match.getPlayerTurn() == TWO && (affectedRow == ONE || affectedRow == ZERO)) {
            friends = true;
        }
        if (attacker.getMana() < hero.getMana()) {
            node.put("error", "Not enough mana to use hero's ability.");
            output.add(node);
            return;
        }

        if (hero.getAction()) {
            node.put("error", "Hero has already attacked this turn.");
            output.add(node);
            return;
        }
        if ((hero.getName().equals("General Kocioraw") || hero.getName().equals("King Mudface"))
                && (!friends)) {
            node.put("error", "Selected row does not belong to the current player.");
            output.add(node);
            return;
        }
        if ((hero.getName().equals("Lord Royce") || hero.getName().equals("Empress Thorina"))
                && (friends)) {
            node.put("error", "Selected row does not belong to the enemy.");
            output.add(node);
            return;
        }

        hero.useAbility(actionsInput.getAffectedRow(), board);
        hero.setAction(true);
        attacker.setMana(attacker.getMana() - hero.getMana());
    }
}
