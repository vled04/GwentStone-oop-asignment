package org.poo.game.cards.heroes;


import org.poo.fileio.CardInput;
import org.poo.game.Board;
import org.poo.game.cards.Hero;
import org.poo.game.cards.Minion;

import static org.poo.game.cards.Constants.ONE;

public final class GeneralKocioraw extends Hero {
    public GeneralKocioraw(final CardInput cardInput) {
        super(cardInput);
    }

    public GeneralKocioraw(final Hero hero) {
        super(hero);
    }

    @Override
    public void useAbility(final int row, final Board board) {
        for (Minion minion : board.getCards().get(row)) {
            minion.setAttackDamage(minion.getAttackDamage() + ONE);
        }
    }
}
