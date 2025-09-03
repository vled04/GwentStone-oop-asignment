package org.poo.game.commands.gameplay;


import org.poo.game.Board;
import org.poo.game.Match;

import static org.poo.game.cards.Constants.TWO;
import static org.poo.game.cards.Constants.ZERO;

public final class EndPlayerTurn {
    /**
     * @param match
     * @param board
     */
    public void execute(final Match match, final Board board) {
        int startingPoint;
        if (board != null) {
            if (match.getPlayerTurn() == TWO) {
                match.getSecondPlayer().getHero().setAction(false);
                startingPoint = ZERO;
            } else {
                match.getFirstPlayer().getHero().setAction(false);
                startingPoint = TWO;
            }
            for (int i = startingPoint; i < startingPoint + TWO; i++) {
                for (int j = ZERO; j < board.getCards().get(i).size(); j++) {
                    if (board.getCards().get(i).get(j).checkFrozen()) {
                        board.getCards().get(i).get(j).setFreeze(false);
                    }
                    if (board.getCards().get(i).get(j).getAction()) {
                        board.getCards().get(i).get(j).setAction(false);
                    }
                }
            }
        }
    }
}

