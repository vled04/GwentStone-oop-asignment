package org.poo.game.cards.heroes;


import org.poo.fileio.CardInput;
import org.poo.game.Board;
import org.poo.game.cards.Hero;
import org.poo.game.cards.Minion;

import static org.poo.game.cards.Constants.ONE;

public final class KingMudface extends Hero {
    public KingMudface(final CardInput cardInput) {
        super(cardInput);
    }

    public KingMudface(final Hero hero) {
        super(hero);
    }

    @Override
    public void useAbility(final int row, final Board board) {
        for (Minion minion : board.getCards().get(row)) {
            minion.setHealth(minion.getHealth() + ONE);
        }
    }
}
