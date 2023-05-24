package sk.stuba.fei.uim.oop.player;

import sk.stuba.fei.uim.oop.cards.Card;
import sk.stuba.fei.uim.oop.utility.ZKlavesnice;

import java.util.ArrayList;
import java.util.Objects;

public class Player {
    private final String name;
    private int health;
    public ArrayList<Card> cards;
    public boolean barrelInFront = false;
    public boolean dynamiteInFront = false;
    public boolean imprisoned = false;

    public Player(String name) {
        this.cards = new ArrayList<>();
        this.name = name;
        this.health = 4;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }
    public void printCards(){
        for (Card i : cards) {
            System.out.println(cards.indexOf(i)+1 + " --- "+ i.getName());
        }
    }
    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public void removeHealth(int i) {
        this.health -= i;
    }

    public void addHealth() {
        this.health++;
    }

    public boolean isActive() {
        return this.health > 0;
    }
    public ArrayList<Card> removeCardsFromHand() {
        ArrayList<Card> removedCards = new ArrayList<>(this.cards);
        this.cards.clear();
        return removedCards;
    }
    public ArrayList<Card> getPlayableCards() {
        ArrayList<Card> cards = new ArrayList<>();
        for (Card card : this.cards) {
            if (card.canPlay()) {
                cards.add(card);
            }
        }
        return cards;
    }
    public void removeUsedCard(Card oldCard) {
        for (int i = 0; i < this.cards.size(); i++) {
            if (Objects.equals(this.cards.get(i), oldCard)) {
                this.cards.remove(i);
                break;
            }
        }
    }
    public void drawACard(ArrayList<Card> deck){
        cards.add(deck.remove(0));
    }
    public void healthCards(){
        while(this.health < cards.size()){
            System.out.println("Máš menej životov než kariet v ruke, ktoré karty chceš vyhodiť?");
            for (int i = 0; i < cards.size();i++) {
                System.out.println("KARTA " + (i+1) + ": " + cards.get(i).getName());
            }
            while (true) {
                int numberCard = ZKlavesnice.readInt("*** VYBER ČÍSLO KARTY KTORÚ CHCEŠ VYMAZAŤ: ") -1;
                if (numberCard < 0 || numberCard > cards.size() - 1) {
                    System.out.println("!!! ZADAL SI NESPRÁVNE ČÍSLO KARIET !!!");
                }else{
                    cards.remove(numberCard);
                    break;
                }
            }
        }
    }
    public int hasTypeOfCard(String cardType){
        int index = 0;
        for(Card card : cards){
            if(card.getName().equals(cardType)){
                return index;
            }
            index++;
        }
        return -1;
    }
}