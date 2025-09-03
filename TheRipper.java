package org.poo.game.cards.specialMinions;


import org.poo.fileio.CardInput;
import org.poo.game.cards.Minion;

import static org.poo.game.cards.Constants.TWO;
import static org.poo.game.cards.Constants.ZERO;

public final class TheRipper extends Minion {
    public TheRipper(final CardInput cardInput) {
        super(cardInput);
    }

    /**
     * @param minion
     */
    @Override
    public void useAbility(final Minion minion) {
        if (minion.getAttackDamage() < TWO) {
            minion.setAttackDamage(ZERO);
        } else
            minion.setAttackDamage(minion.getAttackDamage() - TWO);
    }
}
