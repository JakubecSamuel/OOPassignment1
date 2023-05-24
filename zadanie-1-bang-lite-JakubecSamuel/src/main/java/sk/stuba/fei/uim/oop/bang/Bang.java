package sk.stuba.fei.uim.oop.bang;

import sk.stuba.fei.uim.oop.cards.*;
import sk.stuba.fei.uim.oop.player.Player;
import sk.stuba.fei.uim.oop.utility.ZKlavesnice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Bang {
    private final Player[] players;
    private int currentPlayer;
    private ArrayList<Card> deck;
    private ArrayList<Card> deckDiscard;
    private boolean endTurn = false;

    public Bang() {
        System.out.println("--- Začína hra Bang ---");
        int numberPlayers = 0;
        while (numberPlayers < 2 || numberPlayers > 4) {
            numberPlayers = ZKlavesnice.readInt("*** Zadaj koľko hráčov bude hrať: ***");
            if (numberPlayers < 2 || numberPlayers > 4) {
                System.out.println(" !!! Zadal si zle číslo! !!!");
            }
        }
        this.players = new Player[numberPlayers];
        for (int i = 0; i < numberPlayers; i++) {
            this.players[i] = new Player(ZKlavesnice.readString("*** Zadaj meno hráčovi " + (i + 1) + " : ***"));
        }
        this.deck = new ArrayList<>();
        this.deckDiscard = new ArrayList<>();
        initializeCards(this.deck);
        this.startGame();
    }



    private void startGame() {
        System.out.println("--- HRA ZAČÍNA ---");
        Player lastPlayer = this.players[getNumberOfActivePlayers()-1];
        while (this.getNumberOfActivePlayers() > 1) {
            Player activePlayer = this.players[this.currentPlayer];
            if (!activePlayer.isActive()) {
                ArrayList<Card> returnCards = activePlayer.removeCardsFromHand();
                for (Card card : returnCards) {
                    this.deckDiscard.add(card);
                }
                this.incrementCounter();
                continue;
            }

            System.out.println("--- HRÁČ " + activePlayer.getName() + " ZAČÍNA KOLO ---");
            System.out.println("ŽIVOTY HRÁČA " + activePlayer.getName() + ":"  + activePlayer.getHealth());
            printCardsInFront(activePlayer);
            dynamiteCheck(activePlayer, lastPlayer);
            if(activePlayer.imprisoned){
                System.out.println("Hráč " + activePlayer.getName() + " je vo väzení!");
                tryBreakFree(activePlayer);
            }
            if(!activePlayer.imprisoned){
                for(int i = 0; i < 2; i++){
                    if(this.deck.size() <= 0){
                        swapDecks(deck, deckDiscard);
                    }
                   activePlayer.drawACard(deck);
                }
                while(!endTurn && this.getNumberOfActivePlayers() > 1 && activePlayer.cards.size() != 0){
                    this.makeTurn(activePlayer);
                }
                if(this.getNumberOfActivePlayers() > 1 && activePlayer.cards.size() != 0){
                    activePlayer.healthCards();
                }
                else if(this.getNumberOfActivePlayers() > 1){
                    System.out.println("Nemáš v rukách žiadne karty.");
                }
            }
            this.endTurn = false;
            this.incrementCounter();
            activePlayer.imprisoned  = false;
            lastPlayer = activePlayer;
            System.out.println("----------------------------------------------------------------");
        }
        System.out.println("--- HRA SKONČILA ---");
        System.out.println("*** VÝHERCA JE " + getWinner().getName() + " ***");
    }
    private void dynamiteCheck (Player activePlayer, Player lastPlayer){
        double randomNum = Math.random();
        if(activePlayer.dynamiteInFront){
            if(randomNum <= 0.125){
                activePlayer.removeHealth(3);
                System.out.println("Pred hráčom " + activePlayer.getName() + " vybuchuje dynamit!");
                System.out.println("Stráca tri životy a jeho životy sú: " + activePlayer.getHealth());
            }
            else{
                activePlayer.dynamiteInFront = false;
                lastPlayer.dynamiteInFront = true;
                System.out.println(activePlayer.getName() + " má šťastie, dynamit nevybuchol.");
                System.out.println("Dynamit sa preto posúva hráčovi " + lastPlayer.getName());
            }
        }
    }
    private void tryBreakFree (Player activePlayer){
        double randomNum = Math.random();
        if(randomNum <= 0.25){
            activePlayer.imprisoned = false;
            System.out.println("Hráčovi " + activePlayer.getName() + " sa podarilo újsť!");
        }
        else{
            System.out.println("Hráčovi " + activePlayer.getName() + " sa nepodarilo újsť!");
            System.out.println("Vynecháva toto kolo.");
        }
    }

    private void printCardsInFront(Player player){
        if(player.barrelInFront && player.dynamiteInFront){
            System.out.println("Má pred sebou Barrel a Dynamit.");
        }else if(player.barrelInFront){
            System.out.println("Má pred sebou Barrel.");
        }else if(player.dynamiteInFront){
            System.out.println("Má pred sebou Dynamit.");
        }else{
            System.out.println("Nemá pred sebou žiadne karty.");
        }
    }

    private int getNumberOfActivePlayers() {
        int count = 0;
        for (Player player : this.players) {
            if (player.isActive()) {
                count++;
            }
        }
        return count;
    }
    private void incrementCounter() {
        this.currentPlayer++;
        this.currentPlayer %= this.players.length;
    }
    private void makeTurn(Player activePlayer) {
        ArrayList<Card> playableCards = activePlayer.getPlayableCards();
        if (playableCards.size() != 0) {
            this.playCard(playableCards, activePlayer);
        }
    }

    private Player getWinner() {
        for (Player player : this.players) {
            if (player.isActive()) {
                return player;
            }
        }
        return null;
    }

    private void playCard(ArrayList<Card> playableCards, Player activePlayer) {
        System.out.println("--- TVOJE KARTY V RUKE ---");
        int numberCard = choosingCard(playableCards);
        if(numberCard == -1){
            this.endTurn = true;
        }else if(playableCards.get(numberCard).getName().equals("Bang")){
            System.out.println("List hráčov, na ktorých môžeš vystreliť:");
            for (int i = 0; i < players.length; i++){
                if(!players[i].getName().equals(activePlayer.getName()) && players[i].isActive()){
                    System.out.println((i+1) + ". " + players[i].getName());
                }
            }
            while (true) {
                int targetID = ZKlavesnice.readInt("VYBER HRÁČA NA KTORÉHO STRELÍŠ" + ": ") -1;
                if (targetID < 0 || targetID > players.length-1 || !players[targetID].isActive() ||
                    players[targetID] == activePlayer) {
                    System.out.println("!!! ZADAL SI NESPRÁVNE ČÍSLO HRÁČA !!!");
                } else {
                    Player targetPlayer = players[targetID];
                    playableCards.get(numberCard).playCard(activePlayer, targetPlayer, deckDiscard);
                    break;
                }
            }
            deckDiscard.add(playableCards.get(numberCard));
            activePlayer.removeUsedCard(playableCards.get(numberCard));
        }else if(playableCards.get(numberCard).getName().equals("Indians") || playableCards.get(numberCard).getName().equals("CatBalou")){
            playableCards.get(numberCard).playCard(activePlayer, players, deckDiscard);
            deckDiscard.add(playableCards.get(numberCard));
            activePlayer.removeUsedCard(playableCards.get(numberCard));
        }else if(playableCards.get(numberCard).getName().equals("Prison")){
            if(!allImprisoned(activePlayer, players)){
                playableCards.get(numberCard).playCard(activePlayer, players);
                deckDiscard.add(playableCards.get(numberCard));
                activePlayer.removeUsedCard(playableCards.get(numberCard));
            }
        }
        else if(playableCards.get(numberCard).getName().equals("Stagecoach")){
            for(int i = 0; i < 2; i++){
                if(this.deck.size() <= 0){
                    swapDecks(deck, deckDiscard);
                }
                playableCards.get(numberCard).playCard(activePlayer);
            }
            deckDiscard.add(playableCards.get(numberCard));
            activePlayer.removeUsedCard(playableCards.get(numberCard));
        }else{
            playableCards.get(numberCard).playCard(activePlayer);
            deckDiscard.add(playableCards.get(numberCard));
            activePlayer.removeUsedCard(playableCards.get(numberCard));
        }
    }

    private int choosingCard(ArrayList<Card> cards) {
        for (int i = 0; i < cards.size();i++) {
            System.out.println("KARTA " + (i+1) + ": " + cards.get(i).getName());
        }
        int numberCard = 0;
        while (true) {
            numberCard = ZKlavesnice.readInt("*** VYBER ČÍSLO KARTY KTORÚ CHCEŠ ZAHRAŤ ALEBO NAPÍŠ 0 PRE UKONČENIE KOLA ***") - 1;
            if (numberCard < -1 || numberCard > cards.size() - 1) {
                System.out.println("!!! ZADAL SI NESPRÁVNE ČÍSLO KARIET !!!");
            } else {
                break;
            }
        }
        return numberCard;
    }

    private boolean allImprisoned(Player activePlayer, Player[] target){
        int playerCount = 1;
        for(Player player : target){
            if((player != activePlayer) && player.imprisoned){
                playerCount++;
            }
        }
        if(playerCount == getNumberOfActivePlayers()){
            System.out.println("Všetci ostatní hráči už sú uväznení.");
            return true;
        }else{
            return false;
        }
    }
    private void swapDecks(ArrayList<Card> deck, ArrayList<Card> deckDiscard){
        deck.clear();
        deck.addAll(deckDiscard);
        deckDiscard.clear();
        Collections.shuffle(this.deck);
    }

    private void initializeCards(ArrayList<Card> deck){
        for (int i = 0; i < 30; i++) {
            deck.add(new BangC(deck));
        }
        for (int i = 0; i < 15; i++) {
            deck.add(new Missed(deck));
        }
        for (int i = 0; i < 8; i++) {
            deck.add(new Beer(deck));
        }
        for (int i = 0; i < 6; i++) {
            deck.add(new CatBalou(deck));
        }
        for (int i = 0; i < 4; i++) {
            deck.add(new Stagecoach(deck));
        }
        for (int i = 0; i < 2; i++) {
            deck.add(new Indians(deck));
        }
        for (int i = 0; i < 2; i++) {
            deck.add(new Barrel(deck));
        }
        for (int i = 0; i < 3; i++) {
            deck.add(new Prison(deck));
        }
        deck.add(new Dynamite(deck));
        Collections.shuffle(this.deck);
        for (Player player : players) {
            ArrayList<Card> newCards = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                newCards.add(deck.remove(0));
            }
            player.setCards(newCards);
        }
    }
}