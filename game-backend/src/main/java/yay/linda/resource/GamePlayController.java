package yay.linda.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yay.linda.dto.*;
import yay.linda.service.GamePlayService;

import javax.inject.Inject;
import java.util.List;

/**
 * Controller to handle all requests related to actual game play.
 */
@RestController
@RequestMapping("/")
@CrossOrigin("*")
public class GamePlayController {

    @Inject
    private GamePlayService gamePlayService;

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
    @RequestMapping(value = "game/start/{id}", method = RequestMethod.POST)
    public ResponseEntity<GameSessionDTO> startGame(@PathVariable String id, @RequestBody List<Player> players) {
        GameSessionDTO gameSession = gamePlayService.startGame(players.get(0), players.get(1), id);
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
    public ResponseEntity<GameSessionDTO> pollForGame(@PathVariable String id) {
        GameSessionDTO gameSession = gamePlayService.pollForGame(id);
        if (gameSession != null) {
            return ResponseEntity.ok(gameSession);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "game/card/{id}", method = RequestMethod.GET)
    public ResponseEntity<Card> drawCard(@PathVariable String id) {
        Card nextCard = gamePlayService.drawCard(id);
        return ResponseEntity.ok(nextCard);
    }

    /**
     *
     * @param move
     * @return
     */
    @RequestMapping(value = "game/card", method = RequestMethod.PUT)
    public ResponseEntity<GameBoard> processPutCard(@RequestBody MoveDTO move) {
        GameBoard updatedGameboard = gamePlayService.processPutCard(move);
        return ResponseEntity.ok(updatedGameboard);
    }

    /**
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "game/endTurn/{id}", method = RequestMethod.GET)
    public ResponseEntity<GameSessionDTO> endTurn(@PathVariable String id, @RequestBody List<Card> hand) {
        GameSessionDTO gameSessionDTO = gamePlayService.endTurn(id, hand);
        return ResponseEntity.ok(gameSessionDTO);
    }
}
