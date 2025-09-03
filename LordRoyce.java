package org.poo.game.cards.heroes;


import org.poo.fileio.CardInput;
import org.poo.game.Board;
import org.poo.game.cards.Hero;
import org.poo.game.cards.Minion;

public final class LordRoyce extends Hero {
    public LordRoyce(final CardInput cardInput) {
        super(cardInput);
    }

    public LordRoyce(final Hero hero) {
        super(hero);
    }

    @Override
    public void useAbility(final int row, final Board board) {
        for (Minion minion : board.getCards().get(row)) {
            minion.setFreeze(true);
        }
    }
}
