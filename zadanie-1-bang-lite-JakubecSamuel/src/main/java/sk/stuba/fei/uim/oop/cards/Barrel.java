package sk.stuba.fei.uim.oop.cards;

import sk.stuba.fei.uim.oop.player.Player;

import java.util.ArrayList;

public class Barrel extends Card{
    private static final String NAME = "Barrel";

    public Barrel(ArrayList<Card> deck){
        super(NAME, deck);
    }

    @Override
    public void playCard(Player player) {
        super.playCard(player);
        player.barrelInFront = true;
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
