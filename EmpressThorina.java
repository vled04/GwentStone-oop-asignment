package org.poo.game.cards.heroes;


import org.poo.fileio.CardInput;
import org.poo.game.Board;
import org.poo.game.cards.Hero;
import org.poo.game.cards.Minion;

import static org.poo.game.cards.Constants.ZERO;

public final class EmpressThorina extends Hero {
    public EmpressThorina(final CardInput cardInput) {
        super(cardInput);
    }

    public EmpressThorina(final Hero hero) {
        super(hero);
    }

    @Override
    public void useAbility(final int row, final Board board) {
        int max = ZERO;
        Minion card = null;
        for (Minion minion : board.getCards().get(row)) {
            if (minion.getHealth() > max) {
                max = minion.getHealth();
                card = minion;
            }
        }
        board.getCards().get(row).remove(card);
    }
}
