package org.poo.game.cards;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.poo.fileio.CardInput;
import org.poo.game.Board;


public class Minion extends Card implements Ability {
    @JsonProperty("attackDamage")
    private int attackDamage;
    private boolean frozen;
    @JsonIgnore
    private boolean action;

    /**
     * @param minion
     */
    public Minion(final Minion minion) {
        super(minion);
        this.attackDamage = minion.attackDamage;
        this.action = minion.action;
        this.frozen = minion.frozen;
    }

    /**
     * @param cardInput
     */
    public Minion(final CardInput cardInput) {
        super(cardInput);
        this.attackDamage = cardInput.getAttackDamage();
        this.action = false;
        this.frozen = false;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    /**
     * @param attackDamage
     */
    public void setAttackDamage(final int attackDamage) {
        this.attackDamage = attackDamage;
    }

    /**
     * @param freeze
     */
    public void setFreeze(final boolean freeze) {
        this.frozen = freeze;
    }

    /**
     *
     * @return
     */
    public boolean checkFrozen() {
        return this.frozen;
    }

    /**
     * @param action
     */
    public void setAction(final boolean action) {
        this.action = action;
    }

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

    public void attack(final Card card) {
        if (card == null) {
            return;
        }
        card.takeDamage(this.attackDamage);
        card.setHealth(Math.max(card.getHealth(), 0));
    }

    /**
     *
     * @return
     */
    public boolean checkTank() {
        return this.getName().equals("Goliath") || this.getName().equals("Warden");
    }


    /**
     * @param minion
     */
    @Override
    public void useAbility(final Minion minion) {
    }

    @Override
    public void useAbility(final int row, final Board board) {
    }

}
