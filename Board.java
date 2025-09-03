package org.poo.game;


import lombok.Getter;
import lombok.Setter;
import org.poo.game.cards.Minion;

import java.util.ArrayList;

import static org.poo.game.cards.Constants.THREE;
import static org.poo.game.cards.Constants.ZERO;


@Getter
@Setter
public final class Board {
    private ArrayList<ArrayList<Minion>> cards;

    public Board() {
        cards = new ArrayList<>();
        cards.add(new ArrayList<>());
        cards.add(new ArrayList<>());
        cards.add(new ArrayList<>());
        cards.add(new ArrayList<>());
    }

    /**
     * @param card
     */
    public void removeMinionOnTable(final Minion card) {
        for (ArrayList<Minion> row : cards) {
            row.remove(card);
        }
    }

    /**
     * '
     *
     * @param currentPlayerIdx
     * @return
     */
    public boolean opponentHasTank(final int currentPlayerIdx) {
        for (Minion minion : cards.get(currentPlayerIdx)) {
            if (minion.checkTank()) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param row
     * @return
     */
    public int size(final int row) {
        if (row < ZERO || row > THREE) {
            return ZERO;
        }
        return cards.get(row).size();
    }
}
