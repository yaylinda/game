package yay.linda.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yay.linda.dto.*;
import yay.linda.service.GameSession;
import yay.linda.service.GamePlayService;
import yay.linda.service.PlayerService;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

/**
 * Controller to handle all requests related to actual game play.
 */
@RestController
@RequestMapping("/")
@CrossOrigin("*")
public class GamePlayController {

    @Inject
    private GamePlayService gamePlayService;

//    @Inject
//    private PlayerService playerService;

    /**
     *
     * @param name
     * @return
     */
    @RequestMapping(value = "player/join/{name}", method = RequestMethod.POST)
    public ResponseEntity<Player> join(@PathVariable String name) {
        Player player = gamePlayService.join(name);
        return ResponseEntity.ok(player);
    }

    /**
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "player/{id}", method = RequestMethod.GET)
    public ResponseEntity<Player> findPlayerById(@PathVariable String id) {
        Player player = gamePlayService.findPlayerById(id);
        return ResponseEntity.ok(player);
    }

    /**
     *
     * @param players
     * @return
     */
    @RequestMapping(value = "game/start", method = RequestMethod.POST)
    public ResponseEntity<GameSession> startGame(@RequestBody List<Player> players) {
        GameSession gameSession = gamePlayService.startGame(players.get(0), players.get(1));
        if (gameSession != null) {
            return ResponseEntity.ok(gameSession);
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "game/poll/{id}", method = RequestMethod.GET)
    public ResponseEntity<GameSession> pollForGame(@PathVariable String id) {
        GameSession gameSession = gamePlayService.pollForGame(UUID.fromString(id));
        if (gameSession != null) {
            return ResponseEntity.ok(gameSession);
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(value = "game/card/{id}", method = RequestMethod.GET)
    public ResponseEntity<Card> drawCard(@PathVariable String id) {
        Card nextCard = gamePlayService.drawCard(UUID.fromString(id));
        return ResponseEntity.ok(nextCard);
    }

    @RequestMapping(value = "game/card", method = RequestMethod.PUT)
    public ResponseEntity<GameBoard> placeCard(Card card, int x, int y) {
        GameBoard gameBoard = gamePlayService.placeCard(card, x, y);
        return ResponseEntity.ok(gameBoard);
    }

    @RequestMapping(value = "game/board", method = RequestMethod.PUT)
    public ResponseEntity<GameBoard> updateBoard(GameBoard gameBoard) {
        gamePlayService.updateBoard(gameBoard);
        return ResponseEntity.ok(gameBoard);
    }


}
