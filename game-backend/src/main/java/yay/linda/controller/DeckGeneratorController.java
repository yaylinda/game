package yay.linda.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import yay.linda.dto.Card;
import yay.linda.service.DeckGeneratorService;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping("/deck")
public class DeckGeneratorController {

    @Inject
    private DeckGeneratorService deckGeneratorService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Card>> generateDeck() {
        List<Card> deck = deckGeneratorService.generateDeck();
        if (deck.size() > 0) {
            return ResponseEntity.ok(deck);
        }
        return ResponseEntity.badRequest().build();
    }


}
