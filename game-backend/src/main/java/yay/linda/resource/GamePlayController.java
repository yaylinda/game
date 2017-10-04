package yay.linda.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import yay.linda.dto.Card;
import yay.linda.dto.DTOGameSession;
import yay.linda.dto.DTOPlayer;
import yay.linda.dto.Player;
import yay.linda.service.GameSession;
import yay.linda.service.GamePlayService;

import javax.inject.Inject;
import java.util.List;

/**
 * Controller to handle all requests related to actual game play.
 */
@RestController
@RequestMapping("/gameplay")
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

    @RequestMapping(value = "/card", method = RequestMethod.PUT)
    public ResponseEntity<DTOGameSession> placeCard(Card card, int x, int y) {
        return null;
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<DTOGameSession> update(DTOGameSession newSession) {
        return null;
    }
}
