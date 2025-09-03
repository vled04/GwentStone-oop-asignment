package org.poo.game.cards.specialMinions;


import org.poo.fileio.CardInput;
import org.poo.game.cards.Minion;

public final class Miraj extends Minion {
    public Miraj(final CardInput cardInput) {
        super(cardInput);
    }

    /**
     * @param minion
     */
    @Override
    public void useAbility(final Minion minion) {
        int aux = this.getHealth();
        this.setHealth(minion.getHealth());
        minion.setHealth(aux);
    }
}
