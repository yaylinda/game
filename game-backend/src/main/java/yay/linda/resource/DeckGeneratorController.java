//package yay.linda.resource;
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//import yay.linda.dto.Card;
//import yay.linda.service.DeckGenerator;
//
//import javax.inject.Inject;
//import java.util.List;
//
//@RestController
//@RequestMapping("/deck")
//public class DeckGeneratorController {
//
//    @Inject
//    private DeckGenerator deckGeneratorService;
//
//    // TODO this is for debugging. remove after UI is built or tests are written
//    @RequestMapping(method = RequestMethod.GET)
//    public ResponseEntity<List<Card>> generateDeck() {
////        List<Card> deck = deckGeneratorService.generateDeck();
////        if (deck.size() > 0) {
////            return ResponseEntity.ok(deck);
////        }
//        return ResponseEntity.badRequest().build();
//    }
//
//
//}
