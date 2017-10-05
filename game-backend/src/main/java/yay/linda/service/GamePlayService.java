package yay.linda.service;

import org.springframework.stereotype.Component;
import yay.linda.config.GameConfigurations;
import yay.linda.dto.Card;
import yay.linda.dto.Cell;
import yay.linda.dto.GameBoard;
import yay.linda.dto.Player;
import yay.linda.dto.enums.CellState;
import yay.linda.dto.enums.PlayerTeam;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

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

    private GameSession gameSession;
    private GameBoard gameBoard;

    public GameSession startGame(Player player1, Player player2) {
        GameSession gameSession = new GameSession(player1, player2, gameConfigurations);
        this.gameBoard = gameSession.getGameBoard();
        return gameSession;
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

    public void update(GameBoard gameBoard) {
        // TODO
    }
}
