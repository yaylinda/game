package yay.linda.service;

import org.springframework.stereotype.Component;
import yay.linda.config.GameConfigurations;
import yay.linda.dto.Card;
import yay.linda.dto.DeckGeneratorRequest;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Component
public class DeckGeneratorService {

    @Inject
    private GameConfigurations gameConfigurations;

    public List<Card> generateDeck() {
        List<Card> deck = new ArrayList<>();
        deck.addAll(generateTroopCards(gameConfigurations.getNumTroopCards()));
        deck.addAll(generateWallCards(gameConfigurations.getNumWallCards()));
        return deck;
    }

    public List<Card> generateTroopCards(int num) {
        List<Card> troops = new ArrayList<>();
        return troops;
    }

    public List<Card> generateWallCards(int num) {
        List<Card> walls = new ArrayList<>();
        return walls;
    }
}
