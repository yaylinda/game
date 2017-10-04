package yay.linda.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.commons.lang3.RandomStringUtils;
import yay.linda.config.GameConfigurations;
import yay.linda.dto.Card;
import yay.linda.dto.enums.CardType;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DeckGenerator {

    private GameConfigurations gameConfigurations;
    private Random random;

    public DeckGenerator(GameConfigurations gameConfigurations) {
        this.gameConfigurations = gameConfigurations;
        this.random = new Random();
    }

    public List<Card> generateDeck() {
        List<Card> deck = new ArrayList<>();
        deck.addAll(generateCards(CardType.TROOP, this.gameConfigurations.getNumTroopCards()));
        deck.addAll(generateCards(CardType.WALL, this.gameConfigurations.getNumWallCards()));
        this.serializeDeck(deck); // TODO refactor this to user another thread to be faster
        return deck;
    }

    public List<Card> loadDeck() {
        // TODO future: option to load deck from file, rather than generate random
        List<Card> deck = new ArrayList<>();
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
        System.out.println(rand);
        if (rand >= 0 && rand < gameConfigurations.getTroopMightStatTier1()) {
            might = this.getRandomNumberInRange(1,2);
        } else if (rand >= gameConfigurations.getTroopMightStatTier1() && rand < gameConfigurations.getTroopMightStatTier2()) {
            might = this.getRandomNumberInRange(3,4);
        } else if (rand >= gameConfigurations.getTroopMightStatTier2() && rand < gameConfigurations.getTroopMightStatTier3()) {
            might = this.getRandomNumberInRange(5,6);
        } else if (rand >= gameConfigurations.getTroopMightStatTier3() && rand < gameConfigurations.getTroopMightStatTier4()) {
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

    public int getRandomNumberInRange(int min, int max) {
        // copied from https://www.mkyong.com/java/java-generate-random-integers-in-a-range/
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    private void serializeDeck(List<Card> deck) {
        String chaos = RandomStringUtils.randomAlphabetic(6);
        String filename = "generated-decks/deck_" + chaos + ".json";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.writeValue(bw, deck);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
