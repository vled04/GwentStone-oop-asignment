package org.poo.game.cards;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import org.poo.fileio.CardInput;

import java.util.ArrayList;

@Getter
@Setter
public abstract class Card {
    private int mana;
    private String name;
    private String description;
    @JsonProperty("colors")
    private ArrayList<String> colors;
    private int health;

    /**
     * @param card
     */
    public Card(final Card card) {
        this.description = card.description;
        this.colors = card.colors;
        this.name = card.name;
        this.health = card.health;
        this.mana = card.mana;
    }

    /**
     * @param cardInput
     */
    public Card(final CardInput cardInput) {
        this.description = cardInput.getDescription();
        this.colors = cardInput.getColors();
        this.name = cardInput.getName();
        this.mana = cardInput.getMana();
        this.health = cardInput.getHealth();
    }

    /**
     * @param damage
     */
    public void takeDamage(final int damage) {
        this.health = this.health - damage;
    }

}
