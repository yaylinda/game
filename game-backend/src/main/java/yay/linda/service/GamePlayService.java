package yay.linda.service;

import org.springframework.stereotype.Component;
import yay.linda.config.GameConfigurations;
import yay.linda.dto.Card;
import yay.linda.dto.GameBoard;
import yay.linda.dto.Player;
import yay.linda.dto.enums.PlayerTeam;
import yay.linda.game.GameSession;
import yay.linda.repo.PlayerGameSessionRepo;
import yay.linda.repo.PlayerRepo;

import javax.inject.Inject;

/**
 * Service to handle all functionality related to actual game play.
 */
@Component
public class GamePlayService {

    @Inject
    private GameConfigurations gameConfigurations;

    private Player player1;

    private PlayerGameSessionRepo playerGameSessionRepo = new PlayerGameSessionRepo();

    private PlayerRepo playerRepo = new PlayerRepo();

    public Player join(String name) {
        System.out.printf("%s is joining the game\n", name);
        Player player = new Player(name);
        if (player1 == null) {
            player.setTeam(PlayerTeam.TEAM1.name());
            player.setPower(1);
            player1 = player;
        } else {
            player.setTeam(PlayerTeam.TEAM2.name());
            player.setPower(2);
            player.setOpponentId(player1.getId());
            player1 = null;
        }
        playerRepo.addPlayer(player);
        return player;
    }

    public Player findPlayerById(String id) {
        return playerRepo.getPlayerById(id);
    }

    public GameSession startGame(Player player1, Player player2) {
        player1.setOpponentId(player2.getId());
        player2.setOpponentId(player1.getId());
        GameSession gameSession = new GameSession(player1, player2,
                gameConfigurations.getNumRows(),
                gameConfigurations.getNumCols(),
                gameConfigurations.getHandSize());
        playerGameSessionRepo.assignGameSession(player1.getId(), gameSession);
        playerGameSessionRepo.assignGameSession(player2.getId(), gameSession);
        return gameSession;
    }

    public GameSession pollForGame(String playerId) {
        GameSession gameSession = playerGameSessionRepo.getGameSessionById(playerId);
        if (gameSession != null) {
            return gameSession;
        } else {
            return GameSession.none();
        }
    }

    public Card drawCard(String playerId) {
        return playerGameSessionRepo.getGameSessionById(playerId).drawCard(playerId);
    }

    public void updateBoard(String playerId, GameBoard gameboard) {
        playerGameSessionRepo.getGameSessionById(playerId).updateBoard(playerId, gameboard);
    }
}
