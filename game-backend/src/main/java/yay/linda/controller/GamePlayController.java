package yay.linda.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import yay.linda.dto.Player;
import yay.linda.dto.GameSession;
import yay.linda.service.GamePlayService;

import javax.inject.Inject;
import java.util.List;

/**
 * Controller to handle all requests related to actual game play
 */
@RestController
@RequestMapping("/gameplay")
public class GamePlayController {

    @Inject
    private GamePlayService gamePlayService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<GameSession> startGame(@RequestBody List<Player> players) {
        GameSession gameSession = gamePlayService.startGame(players);
        if (gameSession != null) {
            return ResponseEntity.ok(gameSession);
        }
        return ResponseEntity.badRequest().build();
    }
}
