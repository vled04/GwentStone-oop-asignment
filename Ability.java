package org.poo.game.cards;


import org.poo.game.Board;

public interface Ability {
    public void useAbility(Minion minion);

    public void useAbility(int row, Board board);
}
