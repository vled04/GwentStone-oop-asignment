package org.poo.game;

import lombok.Getter;
import lombok.Setter;
import org.poo.fileio.CardInput;
import org.poo.fileio.DecksInput;
import org.poo.game.cards.Card;
import org.poo.game.cards.Hero;
import org.poo.game.cards.Minion;
import org.poo.game.cards.heroes.EmpressThorina;
import org.poo.game.cards.heroes.GeneralKocioraw;
import org.poo.game.cards.heroes.KingMudface;
import org.poo.game.cards.heroes.LordRoyce;
import org.poo.game.cards.specialMinions.Disciple;
import org.poo.game.cards.specialMinions.Miraj;
import org.poo.game.cards.specialMinions.TheCursedOne;
import org.poo.game.cards.specialMinions.TheRipper;

import java.util.ArrayList;

@Getter
@Setter
public final class Player {
    private int mana;
    private Hero hero;
    private int wins;
    private int plays;
    private ArrayList<Card> deck;
    private ArrayList<Card> hand;

    /**
     *
     * @param hero
     */
    public void setHero(final Hero hero) {
        if (hero.getName().equals("Empress Thorina")) {
            this.hero = new EmpressThorina(hero);
        }
        if (hero.getName().equals("General Kocioraw")) {
            this.hero = new GeneralKocioraw(hero);
        }
        if (hero.getName().equals("King Mudface")) {
            this.hero = new KingMudface(hero);
        }
        if (hero.getName().equals("Lord Royce")) {
            this.hero = new LordRoyce(hero);
        }
    }
    /**
     *
     */
    public void getCardInHand() {
        if (!deck.isEmpty()) {
            hand.add(deck.remove(0));
        }
    }

    /**
     *
     * @param decksInput
     * @param deckIdx
     * @return
     */
    public static ArrayList<Card> getDeck(final DecksInput decksInput, final int deckIdx) {
        ArrayList<Card> deck = new ArrayList<>();
        for (CardInput cardInput : decksInput.getDecks().get(deckIdx)) {
            if (cardInput.getName().equals("The Ripper")) {
                deck.add(new TheRipper(cardInput));
            } else if (cardInput.getName().equals("Miraj")) {
                deck.add(new Miraj(cardInput));
            } else if (cardInput.getName().equals("The Cursed One")) {
                deck.add(new TheCursedOne(cardInput));
            } else if (cardInput.getName().equals("Disciple")) {
                deck.add(new Disciple(cardInput));
            } else {
                deck.add(new Minion(cardInput));
            }
        }
        return deck;
    }
}

