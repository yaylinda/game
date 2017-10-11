package yay.linda.service;

import org.springframework.stereotype.Component;
import yay.linda.config.GameConfigurations;
import yay.linda.dto.Card;
import yay.linda.dto.GameSessionDTO;
import yay.linda.dto.Player;
import yay.linda.dto.enums.GameSessionStatus;
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

    private PlayerGameSessionRepo playerGameSessionRepo = new PlayerGameSessionRepo();

    // is this only used for matchmaking?
    private Player player1;
    private PlayerRepo playerRepo = new PlayerRepo();

    @Inject
    private GameConfigurations gameConfigurations;

    @Inject
    private DeckGenerator deckGenerator;

    public Player join(String name) {
        System.out.printf("%s is joining the game\n", name);
        Player player = new Player(name);
        player.setMaxScore(gameConfigurations.getMaxScore());
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

    public GameSessionDTO startGame(Player player1, Player player2, String invokingPlayerId) {
        GameSession gameSession = new GameSession(player1, player2, this.deckGenerator,
                gameConfigurations.getNumRows(),
                gameConfigurations.getNumCols(),
                gameConfigurations.getHandSize());
        playerGameSessionRepo.assignGameSession(player1.getId(), gameSession);
        playerGameSessionRepo.assignGameSession(player2.getId(), gameSession);

        return new GameSessionDTO(
                this.playerGameSessionRepo.getGameSessionById(invokingPlayerId).
                        getPlayers().get(invokingPlayerId), false,
                this.playerGameSessionRepo.getGameSessionById(invokingPlayerId).
                        getPlayerGameboards().get(invokingPlayerId)); // this gets sent to player 2
    }

    public GameSessionDTO pollForGame(String playerId) {
        GameSession gameSession = playerGameSessionRepo.getGameSessionById(playerId);
        if (gameSession != null && gameSession.getPlayerGameSessionStatuses().get(playerId) == GameSessionStatus.NEW) {
            return new GameSessionDTO(
                    this.playerGameSessionRepo.getGameSessionById(playerId).
                            getPlayers().get(playerId), true,
                    this.playerGameSessionRepo.getGameSessionById(playerId).
                            getPlayerGameboards().get(playerId)); // this gets sent to player 1
        } else {
            return new GameSessionDTO();
        }
    }

    public Card drawCard(String playerId) {
        String team = playerRepo.getPlayerById(playerId).getTeam();
        return playerGameSessionRepo.getGameSessionById(playerId).drawCard(playerId, team);
    }

    public void updateGameData(GameSessionDTO gameSession) {
        String playerId = gameSession.getPlayer().getId();
        this.playerGameSessionRepo.getGameSessionById(playerId).updateGameData(playerId, gameSession);
    }
}
