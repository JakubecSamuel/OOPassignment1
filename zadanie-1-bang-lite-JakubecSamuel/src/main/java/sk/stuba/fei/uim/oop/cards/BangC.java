package sk.stuba.fei.uim.oop.cards;

import sk.stuba.fei.uim.oop.player.Player;
import sk.stuba.fei.uim.oop.bang.Bang;
import java.lang.Math;
import java.util.ArrayList;

public class BangC extends Card{
    private static final String NAME = "Bang";

    public BangC(ArrayList<Card> deck){
        super(NAME, deck);
    }

    @Override
    public void playCard(Player activePlayer, Player[] target, ArrayList<Card> deckDiscard) {

    }

    @Override
    public void playCard(Player player, Player target, ArrayList<Card> deckDiscard) {
        super.playCard(player);
        double randomNum = Math.random();
        int index = target.hasTypeOfCard("Missed");

        if (target.barrelInFront && randomNum <= 0.25) {
            System.out.println("Hráča " + target.getName() + " zachránil Barrel. Životy ostávajú " + target.getHealth());
        }else if(index != -1){
            if(target.barrelInFront){
                System.out.println("Hráč " + target.getName() + " má vyložený Barrel ale nemal šťastie.");
            }
            System.out.println(target.getName() + " využíva kartu Missed. Životy ostávajú: " + target.getHealth());
            deckDiscard.add(target.cards.get(index));
            target.removeUsedCard(target.cards.get(index));
        }else{
            target.removeHealth(1);
            if (target.barrelInFront){
                System.out.println("Hráč " + target.getName() + " má vyložený Barrel ale nemal šťastie.");
            }
            System.out.println(target.getName() + " životy boli znížené na " + target.getHealth() + ".");
        }
    }

    @Override
    public void playCard(Player player, Player target) {

    }

    @Override
    public void playCard(Player player, Player[] target) {

    }
}
