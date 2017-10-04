package yay.linda.service;

import org.springframework.stereotype.Component;
import yay.linda.dto.GameSession;
import yay.linda.dto.Player;

import java.util.List;

@Component
public class GamePlayService {

    public GameSession startGame(List<Player> players) {
        GameSession gameSession = null;
        if (players.size() == 2) {
            gameSession = new GameSession(players.get(0), players.get(1));
        }
        return gameSession;
    }
}
