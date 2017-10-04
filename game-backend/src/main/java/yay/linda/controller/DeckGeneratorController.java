package yay.linda.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yay.linda.dto.Card;
import yay.linda.dto.DeckGeneratorRequest;
import yay.linda.service.DeckGeneratorService;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/deck")
public class DeckGeneratorController {

    @Inject
    private DeckGeneratorService deckGeneratorService;

    public ResponseEntity<List<Card>> generateDeck(@RequestBody DeckGeneratorRequest request) {
        List<Card> deck = deckGeneratorService.generateDeck(request);
        if (deck.size() == request.getNumTotal()) {
            return ResponseEntity.ok(deck);
        }
        return ResponseEntity.badRequest().build();
    }


}
