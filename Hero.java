package org.poo.game.cards;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import org.poo.fileio.CardInput;
import org.poo.game.Board;

import static org.poo.game.cards.Constants.THIRTY;


@Getter
@Setter
public class Hero extends Card implements Ability {
    @JsonIgnore
    private String ability;
    @JsonIgnore
    private boolean action;

    /**
     * @param hero
     */
    public Hero(final Hero hero) {
        super(hero);
        this.ability = hero.ability;
        this.action = false;
    }

    /**
     * @param cardInput
     */
    public Hero(final CardInput cardInput) {
        super(cardInput);
        this.setHealth(THIRTY);
    }

    /**
     * @param cardInput
     * @return
     */
    public static Hero getHero(final CardInput cardInput) {
        return new Hero(cardInput);
    }

    /**
     * @return
     */
    public boolean getAction() {
        return action;
    }

    /**
     * @param damage
     */
    @Override
    public void takeDamage(final int damage) {
        super.setHealth(super.getHealth() - damage);
    }

    /**
     * @param row
     * @param board
     */
    public void useAbility(final int row, final Board board) {
    }

    @Override
    public void useAbility(final Minion minion) {

    }
}
