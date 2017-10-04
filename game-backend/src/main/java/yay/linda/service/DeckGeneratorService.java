package yay.linda.service;

import org.springframework.stereotype.Component;
import yay.linda.dto.Card;
import yay.linda.dto.DeckGeneratorRequest;

import java.util.ArrayList;
import java.util.List;

@Component
public class DeckGeneratorService {

    public List<Card> generateDeck(DeckGeneratorRequest request) {
        List<Card> deck = new ArrayList<>();
        deck.addAll(generateTroopCards(request.getNumTroops()));
        deck.addAll(generateWallCards(request.getNumWalls()));
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
