package org.poo.game.commands.gameplay;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.fileio.ActionsInput;
import org.poo.game.Board;
import org.poo.game.Match;
import org.poo.game.cards.Card;
import org.poo.game.cards.Minion;
import org.poo.game.commands.Command;
import java.util.ArrayList;
import static org.poo.game.cards.Constants.*;
public final class PlaceCard {
    /**
     * @param match
     * @param output
     * @param actionsInput
     * @param board
     */
    public void execute(
            final Match match, final ArrayNode output,
            final ActionsInput actionsInput, final Board board) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put(Command.command.name(), Command.placeCard.name());
        node.put("handIdx", actionsInput.getHandIdx());
        if (match.getPlayerTurn() == ONE
                && match.getFirstPlayer().getHand().size() > actionsInput.getHandIdx()) {
            Card card = match.getFirstPlayer().getHand().get(actionsInput.getHandIdx());
            int manaPlayer = match.getFirstPlayer().getMana();
            int manaCard = card.getMana();

            if (manaPlayer < manaCard) {
                node.put("error", "Not enough mana to place card on table.");
                output.add(node);
                return;
            }

            String cardName = card.getName();
            ArrayList<Card> currentHand = match.getFirstPlayer().getHand();
            if (cardName.equals("The Ripper") || cardName.equals("Miraj")
                    || cardName.equals("Goliath") || cardName.equals("Warden")) {
                if (board.getCards().get(TWO).size() == FIVE) {
                    node.put("error", "Cannot place card on table since row is full.");
                    output.add(node);
                    return;
                }
                board.getCards().get(TWO)
                        .add((Minion) currentHand.remove(actionsInput.getHandIdx()));
            } else if (cardName.equals("Sentinel") || cardName.equals("Berserker")
                    || cardName.equals("The Cursed One") || cardName.equals("Disciple")) {
                if (board.getCards().get(THREE).size() == FIVE) {
                    node.put("error", "Cannot place card on table since row is full.");
                    output.add(node);
                    return;
                }
                board.getCards().get(THREE).add((Minion) currentHand.
                        remove(actionsInput.getHandIdx()));
            }
            match.getFirstPlayer().setMana(manaPlayer - manaCard);
        } else if (match.getPlayerTurn() == TWO
                && match.getSecondPlayer().getHand().size() > actionsInput.getHandIdx()) {
            Card card = match.getSecondPlayer().getHand().get(actionsInput.getHandIdx());
            int manaPlayer = match.getSecondPlayer().getMana();
            int manaCard = card.getMana();

            if (manaPlayer < manaCard) {
                node.put("error", "Not enough mana to place card on table.");
                output.add(node);
                return;
            }
            String cardName = card.getName();
            ArrayList<Card> currentHand = match.getSecondPlayer().getHand();

            if (cardName.equals("The Ripper") || cardName.equals("Miraj")
                    || cardName.equals("Goliath") || cardName.equals("Warden")) {
                if (board.getCards().get(ONE).size() == FIVE) {
                    node.put("error", "Cannot place card on table " + "since row is full.");
                    output.add(node);
                    return;
                }
                board.getCards().get(ONE)
                        .add((Minion) currentHand.remove(actionsInput.getHandIdx()));
            } else if (cardName.equals("Sentinel") || cardName.equals("Berserker")
                    || cardName.equals("The Cursed One") || cardName.equals("Disciple")) {
                if (board.getCards().get(ZERO).size() == FIVE) {
                    node.put("error", "Cannot place card on table since row is full.");
                    output.add(node);
                    return;
                }
                board.getCards().get(ZERO)
                        .add((Minion) currentHand.remove(actionsInput.getHandIdx()));
            }

            match.getSecondPlayer().setMana(manaPlayer - manaCard);
        }
    }
}
