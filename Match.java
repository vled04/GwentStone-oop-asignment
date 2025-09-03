package org.poo.game;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import lombok.Setter;
import lombok.Getter;
import org.poo.fileio.GameInput;
import org.poo.fileio.Input;
import org.poo.game.cards.Hero;
import org.poo.game.commands.Command;
import org.poo.game.commands.debug.GetCardAtPosition;
import org.poo.game.commands.debug.GetCardsInHand;
import org.poo.game.commands.debug.GetCardsOnTable;
import org.poo.game.commands.debug.GetFrozenCardsOnTable;
import org.poo.game.commands.debug.GetPlayerDeck;
import org.poo.game.commands.debug.GetPlayerHero;
import org.poo.game.commands.debug.GetPlayerMana;
import org.poo.game.commands.debug.GetPlayerTurn;
import org.poo.game.commands.gameplay.CardUsesAbility;
import org.poo.game.commands.gameplay.CardUsesAttack;
import org.poo.game.commands.gameplay.PlaceCard;
import org.poo.game.commands.gameplay.UseAttackHero;
import org.poo.game.commands.gameplay.UseHeroAbility;
import org.poo.game.commands.gameplay.EndPlayerTurn;

import static org.poo.game.cards.Constants.ZERO;
import static org.poo.game.cards.Constants.ONE;
import static org.poo.game.cards.Constants.TWO;
import static org.poo.game.cards.Constants.TEN;



@Getter
@Setter
public final class Match {
    private Player firstPlayer;
    private Player secondPlayer;
    private Input input;
    private GameInput gameInput;
    private ArrayNode output;
    private Board board;
    private int playerTurn;
    private int initialTurn;
    private int roundNumber;

    /**
     * @param player1
     * @param player2
     * @param input
     * @param gameInput
     * @param output
     */
    public Match(final Player player1, final Player player2, final Input input,
                 final GameInput gameInput, final ArrayNode output) {
        this.firstPlayer = player1;
        this.secondPlayer = player2;
        this.input = input;
        this.gameInput = gameInput;
        this.output = output;
        this.board = new Board();
        setPlayerForNewRound(player1);
        setPlayerForNewRound(player2);
        this.firstPlayer.setDeck(Player.getDeck(input.getPlayerOneDecks(),
                gameInput.getStartGame().getPlayerOneDeckIdx()));
        this.secondPlayer.setDeck(Player.getDeck(input.getPlayerTwoDecks(),
                gameInput.getStartGame().getPlayerTwoDeckIdx()));
        this.firstPlayer.setHero(Hero.getHero(gameInput.getStartGame().getPlayerOneHero()));
        this.secondPlayer.setHero(Hero.getHero(gameInput.getStartGame().getPlayerTwoHero()));
        shuffleDecks(firstPlayer, secondPlayer);
        this.playerTurn = gameInput.getStartGame().getStartingPlayer();
        this.initialTurn = gameInput.getStartGame().getStartingPlayer();
        this.roundNumber = ONE;
        round();
    }

    /**
     * @param player
     */
    public void setPlayerForNewRound(final Player player) {
        player.setMana(ZERO);
        player.setHand(new ArrayList<>());

    }

    /**
     * @param firstPlayer
     * @param secondPlayer
     */
    public void shuffleDecks(final Player firstPlayer, final Player secondPlayer) {
        Random numberFirst = new Random(gameInput.getStartGame().getShuffleSeed());
        Random numberSecond = new Random(gameInput.getStartGame().getShuffleSeed());
        Collections.shuffle(firstPlayer.getDeck(), numberFirst);
        Collections.shuffle(secondPlayer.getDeck(), numberSecond);
    }

    /**
     *
     */
    public void round() {
        if (roundNumber < TEN) {
            firstPlayer.setMana(firstPlayer.getMana() + roundNumber);
            secondPlayer.setMana(secondPlayer.getMana() + roundNumber);
        } else {
            firstPlayer.setMana(firstPlayer.getMana() + TEN);
            secondPlayer.setMana(secondPlayer.getMana() + TEN);
        }
        firstPlayer.getCardInHand();
        secondPlayer.getCardInHand();
        roundNumber++;
    }

    /**
     * @param playersId
     */
    public void endMatch(final int playersId) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        if (playersId == ONE) {
            node.put(Command.gameEnded.name(), "Player one killed the enemy hero.");
            firstPlayer.setWins(firstPlayer.getWins() + ONE);
        } else {
            node.put(Command.gameEnded.name(), "Player two killed the enemy hero.");
            secondPlayer.setWins(secondPlayer.getWins() + ONE);
        }
        output.add(node);
        firstPlayer.setPlays(firstPlayer.getPlays() + ONE);
        secondPlayer.setPlays(secondPlayer.getPlays() + ONE);
        roundNumber++;
    }

    /**
     *
     */
    public void playGame() {
        gameInput.getActions().forEach(actionsInput -> {
            String command = actionsInput.getCommand();
            if (command.equals(Command.endPlayerTurn.name())) {
                EndPlayerTurn endPlayerTurn = new EndPlayerTurn();
                endPlayerTurn.execute(this, board);
                if (this.playerTurn == ONE) {
                    this.playerTurn = TWO;
                } else {
                    this.playerTurn = ONE;
                }
                if (this.playerTurn == this.initialTurn) {
                    round();
                }
            } else if (command.equals(Command.getCardAtPosition.name())) {
                GetCardAtPosition getCardAtPosition = new GetCardAtPosition();
                getCardAtPosition.execute(output, actionsInput, board);
            } else if (command.equals(Command.getCardsInHand.name())) {
                GetCardsInHand getCardsInHand = new GetCardsInHand();
                getCardsInHand.execute(this, output, actionsInput.getPlayerIdx());
            } else if (command.equals(Command.getCardsOnTable.name())) {
                GetCardsOnTable getCardsOnTable = new GetCardsOnTable();
                getCardsOnTable.execute(output, board);
            } else if (command.equals(Command.getFrozenCardsOnTable.name())) {
                GetFrozenCardsOnTable getFrozenCardsOnTable = new GetFrozenCardsOnTable();
                getFrozenCardsOnTable.execute(output, board);
            } else if (command.equals(Command.getPlayerDeck.name())) {
                GetPlayerDeck getPlayerDeck = new GetPlayerDeck();
                getPlayerDeck.execute(this, output, actionsInput.getPlayerIdx());
            } else if (command.equals(Command.getPlayerHero.name())) {
                GetPlayerHero getPlayerHero = new GetPlayerHero();
                getPlayerHero.execute(this, output, actionsInput.getPlayerIdx());
            } else if (command.equals(Command.getPlayerMana.name())) {
                GetPlayerMana getPlayerMana = new GetPlayerMana();
                getPlayerMana.execute(this, output, actionsInput.getPlayerIdx());
            } else if (command.equals(Command.getPlayerTurn.name())) {
                GetPlayerTurn getPlayerTurn = new GetPlayerTurn();
                getPlayerTurn.execute(this, output);
            } else if (command.equals(Command.cardUsesAbility.name())) {
                CardUsesAbility cardUsesAbility = new CardUsesAbility();
                cardUsesAbility.execute(output, actionsInput, board, this, getPlayerTurn());
            } else if (command.equals(Command.cardUsesAttack.name())) {
                CardUsesAttack cardUsesAttack = new CardUsesAttack();
                cardUsesAttack.execute(output, actionsInput, board);
            } else if (command.equals(Command.placeCard.name())) {
                PlaceCard placeCard = new PlaceCard();
                placeCard.execute(this, output, actionsInput, board);
            } else if (command.equals(Command.useAttackHero.name())) {
                UseAttackHero useAttackHero = new UseAttackHero();
                useAttackHero.execute(output, actionsInput, board, this, getPlayerTurn());
            } else if (command.equals(Command.useHeroAbility.name())) {
                UseHeroAbility useHeroAbility = new UseHeroAbility();
                useHeroAbility.execute(this, output, actionsInput, board);
            } else if (command.equals(Command.getTotalGamesPlayed.name())) {
                output.addObject()
                        .put(Command.command.name(), Command.getTotalGamesPlayed.name())
                        .put(Command.output.name(), getFirstPlayer().getPlays());
            } else if (command.equals(Command.getPlayerOneWins.name())) {
                output.addObject()
                        .put(Command.command.name(), Command.getPlayerOneWins.name())
                        .put(Command.output.name(), getFirstPlayer().getWins());

            } else if (command.equals(Command.getPlayerTwoWins.name())) {
                output.addObject()
                        .put(Command.command.name(), Command.getPlayerTwoWins.name())
                        .put(Command.output.name(), getSecondPlayer().getWins());
            }
        });
    }
}

