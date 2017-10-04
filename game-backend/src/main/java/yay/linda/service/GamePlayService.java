package yay.linda.service;

import org.springframework.stereotype.Component;
import yay.linda.config.GameConfigurations;
import yay.linda.dto.DTOPlayer;
import yay.linda.dto.Player;

import javax.inject.Inject;
import java.util.List;

@Component
public class GamePlayService {

    @Inject
    private GameConfigurations gameConfigurations;

    public GameSession startGame(Player player1, Player player2) {
        GameSession gameSession = new GameSession(player1, player2, gameConfigurations);

        return gameSession;
    }
}
