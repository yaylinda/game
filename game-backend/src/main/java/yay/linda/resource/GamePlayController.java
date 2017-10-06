package yay.linda.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yay.linda.dto.*;
import yay.linda.service.GameSession;
import yay.linda.service.GamePlayService;

import javax.inject.Inject;
import java.util.List;

/**
 * Controller to handle all requests related to actual game play.
 */
@RestController
@RequestMapping("/game")
@CrossOrigin("*")
public class GamePlayController {

    @Inject
    private GamePlayService gamePlayService;

    @RequestMapping(value = "/start", method = RequestMethod.POST)
    public ResponseEntity<DTOGameSession> startGame(@RequestBody List<DTOPlayer> players) {
        GameSession gameSession = gamePlayService.startGame(new Player(players.get(0)), new Player(players.get(1)));
        if (gameSession != null) {
            return ResponseEntity.ok(new DTOGameSession(gameSession));
        }
        return ResponseEntity.badRequest().build();
    }

    @RequestMapping(value = "/card", method = RequestMethod.GET)
    public ResponseEntity<Card> drawCard() {
        Card nextCard = gamePlayService.drawCard();
        return ResponseEntity.ok(nextCard);
    }

    @RequestMapping(value = "/card", method = RequestMethod.PUT)
    public ResponseEntity<GameBoard> placeCard(Card card, int x, int y) {
        GameBoard gameBoard = gamePlayService.placeCard(card, x, y);
        return ResponseEntity.ok(gameBoard);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<GameBoard> update(GameBoard gameBoard) {
        gamePlayService.update(gameBoard);
        return ResponseEntity.ok(gameBoard);
    }
}
