package org.poo.game.cards.specialMinions;


import org.poo.fileio.CardInput;
import org.poo.game.cards.Minion;

import static org.poo.game.cards.Constants.TWO;

public final class Disciple extends Minion {
    public Disciple(final CardInput cardInput) {
        super(cardInput);
    }

    /**
     * @param minion
     */
    @Override
    public void useAbility(final Minion minion) {
        minion.setHealth((minion.getHealth()) + TWO);
    }
}
