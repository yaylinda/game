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

import javax.inject.Inject;
import java.util.UUID;

/**
 * Service to handle all functionality related to actual game play.
 */
@Component
public class GamePlayService {

    @Inject
    private GameConfigurations gameConfigurations;

    // TODO v2: store games and look them up by ID to handle multiple games at once
    // for now, list should only have the one and only game session
//    private List<GameSession> gameSessionList = new ArrayList<>();
//    private List<GameBoard> gameBoardList = new ArrayList<>();

    private GameBoard gameBoard;
    private PlayerGameSessionRepo playerGameSessionRepo = new PlayerGameSessionRepo();

    public GameBoard startGame(Player player1, Player player2) {
        GameSession gameSession = new GameSession(player1, player2, gameConfigurations);
        playerGameSessionRepo.assignGameSession(player1.getId(), gameSession);
        playerGameSessionRepo.assignGameSession(player2.getId(), gameSession);
        return gameSession.getGameBoard();
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

    public GameBoard pollForGame(UUID id) {
        return playerGameSessionRepo.getGameSessionById(id).getGameBoard();
    }
}
