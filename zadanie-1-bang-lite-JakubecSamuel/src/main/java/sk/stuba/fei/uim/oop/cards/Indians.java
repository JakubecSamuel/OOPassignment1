package sk.stuba.fei.uim.oop.cards;

import sk.stuba.fei.uim.oop.player.Player;

import java.util.ArrayList;

public class Indians extends Card{
    private static final String NAME = "Indians";

    public Indians(ArrayList<Card> deck){
        super(NAME, deck);
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

    @Override
    public void playCard(Player activePlayer, Player[] target, ArrayList<Card> deckDiscard) {
        super.playCard(activePlayer);
        int index = 0;
        for (Player players : target){
            if((players.hasTypeOfCard("Bang") != -1) && players != activePlayer && players.isActive()){
                index = players.hasTypeOfCard("Bang");
                System.out.println(players.getName() + " má kartu Bang. Jeho životy ostávajú " + players.getHealth() + ".");
                deckDiscard.add(players.cards.get(index));
                players.removeUsedCard(players.cards.get(index));
            }
            else if(players != activePlayer && players.isActive()){
                players.removeHealth(1);
                System.out.println(players.getName() + " nemá kartu Bang. Tým pádom životy boli znížené na " + players.getHealth() + ".");
            }
        }
    }
}