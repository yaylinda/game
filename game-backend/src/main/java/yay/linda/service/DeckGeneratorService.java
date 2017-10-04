package yay.linda.service;

import org.springframework.stereotype.Component;
import yay.linda.config.GameConfigurations;
import yay.linda.dto.Card;
import yay.linda.dto.enums.CardType;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class DeckGeneratorService {

    @Inject
    private GameConfigurations gameConfigurations;

    private Random random;

    public DeckGeneratorService() {
        random = new Random();
    }

    public List<Card> generateDeck() {
        List<Card> deck = new ArrayList<>();
        deck.addAll(generateCards(CardType.TROOP, gameConfigurations.getNumTroopCards()));
        deck.addAll(generateCards(CardType.WALL, gameConfigurations.getNumWallCards()));
        return deck;
    }

    private List<Card> generateCards(CardType cardType, int num) {
        List<Card> cards = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            int might = this.calculateMightStat();
            int move = this.calculateMovementStat(cardType);
            double cost = this.calculateCostStat(might, move);
            Card card = new Card(cardType, might, move, cost);
            cards.add(card);
        }
        return cards;
    }

    private int calculateMightStat() {
        int might;
        float rand = random.nextFloat();

        if (rand >= 0 && rand <= gameConfigurations.getTroopMightStatTier1()) {
            might = this.getRandomNumberInRange(1,2);
        } else if (rand >= gameConfigurations.getTroopMightStatTier1() && rand <= gameConfigurations.getTroopMightStatTier2()) {
            might = this.getRandomNumberInRange(3,4);
        } else if (rand >= gameConfigurations.getTroopMightStatTier1() && rand <= gameConfigurations.getTroopMightStatTier2()) {
            might = this.getRandomNumberInRange(5,6);
        } else if (rand >= gameConfigurations.getTroopMightStatTier1() && rand <= gameConfigurations.getTroopMightStatTier2()) {
            might = this.getRandomNumberInRange(7,8);
        } else {
            might = this.getRandomNumberInRange(9,10);
        }

        return might;
    }

    private int calculateMovementStat(CardType cardType) {
        int movement = 0;
        if (cardType.equals(CardType.TROOP)) {
            movement = (random.nextFloat() > gameConfigurations.getTroopMoveExtraFreq()) ? 1 : 2;
        }
        return movement;
    }

    private double calculateCostStat(int might, int move) {
        // TODO future: add special ability chance to card, if card has special ability, round the cost up
        double cost = (might + move * 1.00) / 2;
        return cost;
    }

    private static int getRandomNumberInRange(int min, int max) {
        // copied from https://www.mkyong.com/java/java-generate-random-integers-in-a-range/
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

}
