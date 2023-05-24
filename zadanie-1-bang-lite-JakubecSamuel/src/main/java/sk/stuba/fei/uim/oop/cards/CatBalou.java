package sk.stuba.fei.uim.oop.cards;

import sk.stuba.fei.uim.oop.player.Player;
import sk.stuba.fei.uim.oop.utility.ZKlavesnice;

import java.lang.Math;
import java.util.ArrayList;
import java.util.Random;

public class CatBalou extends Card{
    private static final String NAME = "CatBalou";

    public CatBalou(ArrayList<Card> deck){
        super(NAME, deck);
    }

    @Override
    public void playCard(Player activePlayer, Player[] target, ArrayList<Card> deckDiscard) {
        int index = 0;
        int indexCard = 0;
        System.out.println("Zoznam hráčov, na ktorých môžeš zahrať kartu Cat Balou (majú nejaké karty): ");
        for(Player players : target){
            if((players != activePlayer) && players.isActive() &&(players.cards.size() > 0 || hasAnyCardInFront(players))){
                System.out.println((index+1) + ". " + players.getName());
            }
            index++;
        }
        while (true){
            index = ZKlavesnice.readInt("Vyber hráča: ") -1;
            if((index < 0 || index >= target.length) || !target[index].isActive() || (target[index] == activePlayer) || (target[index].cards.size() <= 0 && !hasAnyCardInFront(target[index]))){
                System.out.println("!!! ZADAL SI NEPLATNÉHO HRÁČA !!!");
            }else{
                if(hasAnyCardInFront(target[index]) && target[index].cards.size() > 0){
                    while (true){
                        indexCard = ZKlavesnice.readInt("Vyber či chceš vyhodiť hráčovi kartu z ruky (1) alebo kartu, ktorú ma pred sebou (2): ");
                        if(indexCard < 1 || indexCard > 2){
                            System.out.println("!!! ZADAL SI NEPLATNÉ ČÍSLO !!!");
                        }else{
                            if(indexCard == 1){
                                removeCardHand(target[index], deckDiscard);
                                deckDiscard.add(target[index].cards.get(index));
                            }
                            else{
                                removeCardFront(target[index]);
                            }
                            break;
                        }
                    }
                }
                else if(target[index].cards.size() > 0){
                    removeCardHand(target[index], deckDiscard);
                }
                else if(hasAnyCardInFront(target[index])){
                    removeCardFront(target[index]);
                }
                break;
            }
        }
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
    private void removeCardHand(Player target, ArrayList<Card> deckDiscard){
        Random rnd = new Random();
        int randomNum = rnd.nextInt(target.cards.size());
        System.out.println("Hráčovi " + target.getName() + " si odstránil kartu " + target.cards.get(randomNum).getName() + ".");
        deckDiscard.add(target.cards.get(randomNum));
        target.removeUsedCard(target.cards.get(randomNum));
    }

    private void removeCardFront(Player target){
        int numberCard = 0;
        int count = 0;
        System.out.println("Zoznam kariet, ktoré môžeš vyhodiť: ");
        if(target.barrelInFront){
            System.out.println("1. Barrel");
            count++;
        }
        if(target.dynamiteInFront){
            System.out.println("2. Dynamit");
            count++;
        }
        if(target.imprisoned){
            System.out.println("3. Prison");
            count++;
        }
        while(true) {
            numberCard = ZKlavesnice.readInt("VYBER ČÍSLO KARTY KTORÚ CHCEŠ HRÁČOVI VYHODIŤ ZO STOLA: ");
            if (numberCard > 3 || numberCard < 1) {
                System.out.println(" !!! ZADAL SI NESPRÁVNE ČÍSLO KARIET ! !!! ");
            }
            else if(numberCard == 1 && !target.barrelInFront){
                System.out.println(" !!! ZADAL SI NESPRÁVNE ČÍSLO KARIET ! !!! ");
            }
            else if(numberCard == 2 && !target.dynamiteInFront){
                System.out.println(" !!! ZADAL SI NESPRÁVNE ČÍSLO KARIET ! !!! ");
            }
            else if(numberCard == 3 && !target.imprisoned){
                System.out.println(" !!! ZADAL SI NESPRÁVNE ČÍSLO KARIET ! !!! ");
            }
            else{
                if(numberCard == 1){
                    target.barrelInFront = false;
                    System.out.println("Hráčovi " + target.getName() + " si vyhodil kartu Barrel.");
                }
                else if(numberCard == 2){
                    target.dynamiteInFront = false;
                    System.out.println("Hráčovi " + target.getName() + " si vyhodil kartu Dynamit.");
                }
                else if(numberCard == 3){
                    target.imprisoned = false;
                    System.out.println("Hráčovi " + target.getName() + " si vyhodil kartu Prison.");
                }
                break;
            }
        }
    }
    private int howManyCardsInFront(Player target){
        int count = 0;
        if(target.imprisoned){
            count++;
        }
        if(target.dynamiteInFront){
            count++;
        }
        if(target.barrelInFront){
            count++;
        }
        return count;
    }

    private boolean hasAnyCardInFront(Player target){
        if(target.imprisoned || target.dynamiteInFront || target.barrelInFront){
            return true;
        }
        else{
            return false;
        }
    }
}
