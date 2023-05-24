package sk.stuba.fei.uim.oop.cards;

import sk.stuba.fei.uim.oop.player.Player;

import java.util.ArrayList;

public class Stagecoach extends Card{
    private static final String NAME = "Stagecoach";

    public Stagecoach(ArrayList<Card> deck){
        super(NAME, deck);
    }

    @Override
    public void playCard(Player player) {
        player.drawACard(this.deck);
    }

    @Override
    public void playCard(Player activePlayer, Player[] target, ArrayList<Card> deckDiscard) {

    }

    @Override
    public void playCard(Player player, Player target, ArrayList<Card> deckDiscard) {

    }

    @Override
    public void playCard(Player player, Player target) {

    }

    @Override
    public void playCard(Player player, Player[] target) {

    }
}
