package yay.linda.service;

import org.springframework.stereotype.Component;
import yay.linda.config.GameConfigurations;
import yay.linda.dto.Card;
import yay.linda.dto.Cell;
import yay.linda.dto.GameBoard;
import yay.linda.dto.Player;
import yay.linda.dto.enums.CellState;
import yay.linda.dto.enums.PlayerTeam;
import yay.linda.repo.PlayerGameSessionRepo;
import yay.linda.repo.PlayerRepo;

import javax.inject.Inject;
import java.util.UUID;

/**
 * Service to handle all functionality related to actual game play.
 */
@Component
public class GamePlayService {

    @Inject
    private GameConfigurations gameConfigurations;

    private GameBoard gameBoard;
    private PlayerGameSessionRepo playerGameSessionRepo = new PlayerGameSessionRepo();

    private Player player1;
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
        return playerRepo.getPlayerById(UUID.fromString(id));
    }

    public GameSession startGame(Player player1, Player player2) {
        player1.setOpponentId(player2.getId());
        GameSession gameSession = new GameSession(player1, player2, gameConfigurations);
        playerGameSessionRepo.assignGameSession(player1.getId(), gameSession);
        playerGameSessionRepo.assignGameSession(player2.getId(), gameSession);
        return gameSession;
    }

    public GameSession pollForGame(UUID id) {
        GameSession gameSession = playerGameSessionRepo.getGameSessionById(id);
        if (gameSession != null) {
            return gameSession;
        } else {
            return GameSession.none();
        }
    }

    public Card drawCard(UUID playerId) {
        return playerGameSessionRepo.getGameSessionById(playerId).drawCard();
    }

    public GameBoard placeCard(Card card, int row, int col) {
        card.setPlayed(true);

        PlayerTeam team = PlayerTeam.valueOf(card.getOwningTeam());

        if (team == PlayerTeam.TEAM2) {
            row = row % (gameConfigurations.getNumRows() - 1);
            col = col % (gameConfigurations.getNumCols() - 1);
        }

        Cell gameBoardCell = gameBoard.getCell(row, col);
        gameBoardCell.setCard(card);
        gameBoardCell.setState(CellState.OCCUPIED);

        return gameBoard;
    }

    public void updateBoard(GameBoard gameBoard) {
        // TODO
    }
}
