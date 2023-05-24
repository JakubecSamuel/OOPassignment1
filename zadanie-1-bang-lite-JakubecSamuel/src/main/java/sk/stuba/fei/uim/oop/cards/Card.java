package sk.stuba.fei.uim.oop.cards;

import sk.stuba.fei.uim.oop.player.Player;

import java.util.ArrayList;

public abstract class Card {
    protected String name;
    protected ArrayList<Card> deck;

    public Card(String name, ArrayList<Card> deck){
        this.name = name;
        this.deck = deck;
    }

    public void playCard(Player player) {
        System.out.println(player.getName() + " ZAHRAL KARTU: " + this.name);
    }
    public String getName() {
        return name;
    }
    public boolean canPlay(){
        return true;
    }

    public abstract void playCard(Player activePlayer, Player[] target, ArrayList<Card> deckDiscard);
    public abstract void playCard(Player player, Player target, ArrayList<Card> deckDiscard);
    public abstract void playCard(Player player, Player target);
    public abstract void playCard(Player player, Player[] target);
}
