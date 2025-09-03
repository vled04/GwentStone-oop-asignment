package org.poo.game.cards.specialMinions;


import org.poo.fileio.CardInput;
import org.poo.game.cards.Minion;

import static org.poo.game.cards.Constants.ZERO;

public final class TheCursedOne extends Minion {
    public TheCursedOne(final CardInput cardInput) {
        super(cardInput);
    }

    /**
     * @param minion
     */
    @Override
    public void useAbility(final Minion minion) {
        int aux = ZERO;
        aux = minion.getHealth();
        minion.setHealth(minion.getAttackDamage());
        minion.setAttackDamage(aux);
    }
}
