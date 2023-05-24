package sk.stuba.fei.uim.oop.cards;

import sk.stuba.fei.uim.oop.player.Player;
import sk.stuba.fei.uim.oop.utility.ZKlavesnice;

import java.util.ArrayList;

public class Prison extends Card{
    private static final String NAME = "Prison";

    public Prison(ArrayList<Card> deck){
        super(NAME, deck);
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
    public void playCard(Player player, Player[] target){
        int index = 0;
        System.out.println("Zoznam hráčov, ktorých môžeš uväzniť: ");
        for(Player players : target){
            if((players != player) && (!players.imprisoned)){
                System.out.println((index+1) + ". " + players.getName());
            }
            index++;
        }
        while (true){
            index = ZKlavesnice.readInt("Vyber hráča: ") -1;
            if((index < 0 || index >= target.length) || (target[index] == player) || target[index].imprisoned){
                System.out.println("!!! ZADAL SI NEPLATNÉHO HRÁČA !!!");
            }else{
                target[index].imprisoned = true;
                System.out.println("Uväznil si hráča " + target[index].getName() + ".");
                break;
            }
        }
    }
}
