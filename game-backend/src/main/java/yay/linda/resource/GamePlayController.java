package yay.linda.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yay.linda.dto.*;
import yay.linda.service.GameSession;
import yay.linda.service.GamePlayService;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

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
    public ResponseEntity<GameBoard> startGame(@RequestBody List<Player> players) {
        GameBoard gameBoard = gamePlayService.startGame(players.get(0), players.get(1));
        if (gameBoard != null) {
            return ResponseEntity.ok(gameBoard);
        }
        return ResponseEntity.badRequest().build();
    }

    @RequestMapping(value = "/card", method = RequestMethod.GET)
    public ResponseEntity<Card> drawCard() {
        Card nextCard = gamePlayService.drawCard(UUID.randomUUID()); // TODO get playerId from frontend
        return ResponseEntity.ok(nextCard);
    }

    @RequestMapping(value = "/card", method = RequestMethod.PUT)
    public ResponseEntity<GameBoard> placeCard(Card card, int x, int y) {
        GameBoard gameBoard = gamePlayService.placeCard(card, x, y);
        return ResponseEntity.ok(gameBoard);
    }

    @RequestMapping(value = "/board", method = RequestMethod.PUT)
    public ResponseEntity<GameBoard> updateBoard(GameBoard gameBoard) {
        gamePlayService.updateBoard(gameBoard);
        return ResponseEntity.ok(gameBoard);
    }

    @RequestMapping(value = "/poll/{id}", method = RequestMethod.GET)
    public ResponseEntity<GameBoard> pollForGame(@PathVariable String id) {
        GameBoard gameBoard = gamePlayService.pollForGame(UUID.fromString(id));
        return ResponseEntity.ok(gameBoard);
    }
}
