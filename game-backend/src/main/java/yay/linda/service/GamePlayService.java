package yay.linda.service;

import org.springframework.stereotype.Component;
import yay.linda.config.GameConfigurations;
import yay.linda.dto.*;
import yay.linda.dto.enums.*;
import yay.linda.game.GameSession;
import yay.linda.repo.PlayerGameSessionRepo;
import yay.linda.repo.PlayerRepo;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Service to handle all functionality related to actual game play.
 */
@Component
public class GamePlayService {

    private PlayerGameSessionRepo playerGameSessionRepo = new PlayerGameSessionRepo();

    // is this only used for matchmaking?
    private PlayerDTO player1;
    private PlayerRepo playerRepo = new PlayerRepo();

    @Inject
    private GameConfigurations gameConfigurations;

    @Inject
    private DeckGenerator deckGenerator;

    public PlayerDTO join(String name) {
//        System.out.printf("%s is joining the game\n", name);
        PlayerDTO player = new PlayerDTO();
        player.setMaxScore(gameConfigurations.getMaxScore());
        if (player1 == null) {
            player.setTeam(PlayerTeam.TEAM1.name());
            player.setPower(0);
            player1 = player;
        } else {
            player.setTeam(PlayerTeam.TEAM2.name());
            player.setPower(1);
            player.setOpponentId(player1.getId());
            player1 = null;
        }
        playerRepo.addPlayer(player);
        return player;
    }

    public PlayerDTO findPlayerById(String id) {
        return playerRepo.getPlayerById(id);
    }

    public GameSessionDTO startGame(PlayerDTO player1, PlayerDTO player2, String invokingPlayerId) {
        GameSession gameSession = new GameSession(player1, player2, this.deckGenerator,
                gameConfigurations.getNumRows(),
                gameConfigurations.getNumCols(),
                gameConfigurations.getHandSize());
        playerGameSessionRepo.assignGameSession(player1.getId(), gameSession);
        playerGameSessionRepo.assignGameSession(player2.getId(), gameSession);

        return new GameSessionDTO(
                this.playerGameSessionRepo.getGameSessionById(invokingPlayerId).getPlayers().get(invokingPlayerId),
                this.playerGameSessionRepo.getGameSessionById(invokingPlayerId).getPlayerGameboards().get(invokingPlayerId),
                this.playerGameSessionRepo.getGameSessionById(invokingPlayerId).getGameStates().get(invokingPlayerId).name(),
                this.playerGameSessionRepo.getGameSessionById(invokingPlayerId).getDeck().size(),
                false); // this gets sent to player 2
    }

    public GameSessionDTO pollForGame(String playerId) {
        GameSession gameSession = this.playerGameSessionRepo.getGameSessionById(playerId);

        if (gameSession != null && gameSession.getPlayerGameSessionStatuses().get(playerId) == GameSessionStatus.NEW) {
            PlayerDTO player = gameSession.getPlayers().get(playerId);

            List<List<Cell>> updatedGameboard = new ArrayList<>();
            for (List<Cell> row : gameSession.getPlayerGameboards().get(playerId).getBoard()) {
                List<Cell> copiedRow = new ArrayList<>();
                for (Cell cell : row) {
                    copiedRow.add(new Cell(cell));
                }
                updatedGameboard.add(copiedRow);
            }

            for (int rowNum = 0; rowNum < this.gameConfigurations.getNumRows(); rowNum++) {
                for (int colNum = 0; colNum < this.gameConfigurations.getNumCols(); colNum++) {
                    Cell cell = new Cell(updatedGameboard.get(rowNum).get(colNum));
                    if (CellState.valueOf(cell.getState()) == CellState.OCCUPIED &&
                            CardType.valueOf(cell.getType()) == CardType.TROOP &&
                            PlayerTeam.valueOf(cell.getTeam()) == PlayerTeam.valueOf(player.getTeam())) {
                        int newRowNum = rowNum - cell.getMove();
                        if (newRowNum >= 0) {
                            Cell otherCell = new Cell(updatedGameboard.get(newRowNum).get(colNum));
                            if (CellState.valueOf(otherCell.getState()) == CellState.OCCUPIED) {
                                if (PlayerTeam.valueOf(otherCell.getTeam()) == PlayerTeam.valueOf(player.getTeam())) {
                                    updatedGameboard.get(newRowNum).get(colNum).setMight(cell.getMight() + otherCell.getMight());
                                    updatedGameboard.get(rowNum).get(colNum).setState(CellState.EMPTY.toString());
                                } else {
                                    int mightDiff = cell.getMight() - otherCell.getMight();
                                    if (mightDiff > 0) {
                                        updatedGameboard.get(rowNum).get(colNum).setState(CellState.EMPTY.toString());
                                        updatedGameboard.get(newRowNum).get(colNum).setType(cell.getType());
                                        updatedGameboard.get(newRowNum).get(colNum).setMight(mightDiff);
                                        updatedGameboard.get(newRowNum).get(colNum).setMove(cell.getMove());
                                        updatedGameboard.get(newRowNum).get(colNum).setTeam(cell.getTeam());
                                    } else if (mightDiff < 0) {
                                        updatedGameboard.get(rowNum).get(colNum).setState(CellState.EMPTY.toString());
                                        updatedGameboard.get(newRowNum).get(colNum).setType(otherCell.getType());
                                        updatedGameboard.get(newRowNum).get(colNum).setMight(mightDiff * -1);
                                        updatedGameboard.get(newRowNum).get(colNum).setMove(otherCell.getMove());
                                        updatedGameboard.get(newRowNum).get(colNum).setTeam(otherCell.getTeam());
                                    } else {
                                        updatedGameboard.get(rowNum).get(colNum).setState(CellState.EMPTY.toString());
                                        updatedGameboard.get(newRowNum).get(colNum).setState(CellState.EMPTY.toString());
                                    }
                                }
                            } else {
                                updatedGameboard.get(rowNum).get(colNum).setState(CellState.EMPTY.toString());
                                updatedGameboard.get(newRowNum).get(colNum).setState(CellState.OCCUPIED.toString());
                                updatedGameboard.get(newRowNum).get(colNum).setType(cell.getType());
                                updatedGameboard.get(newRowNum).get(colNum).setMight(cell.getMight());
                                updatedGameboard.get(newRowNum).get(colNum).setMove(cell.getMove());
                                updatedGameboard.get(newRowNum).get(colNum).setTeam(cell.getTeam());
                            }
                        } else {
                            player.setScore(player.getScore() + cell.getMight());
                        }
                    }
                }
            }
            gameSession.getPlayerGameboards().get(playerId).setBoard(updatedGameboard);

            int furthestRow = gameConfigurations.getNumRows()-1;
            for (int row = gameConfigurations.getNumRows()-1; row >= 0; row--) {
                for (Cell cell : updatedGameboard.get(row)) {
                    if (CellState.valueOf(cell.getState()) == CellState.OCCUPIED
                            && PlayerTeam.valueOf(cell.getTeam()) == PlayerTeam.valueOf(player.getTeam())
                            && row < furthestRow) {
                        furthestRow = row;
                        break;
                    }
                }
            }
            player.setFurthestRow(furthestRow);

            if (player.getScore() >= player.getMaxScore()) {
                gameSession.getGameStates().put(playerId, GameState.WIN);
                gameSession.getPlayerGameboards().get(player.getOpponentId()).setBoard(updatedGameboard); // TODO reverse opponent's gameboard
                gameSession.getGameStates().put(player.getOpponentId(), GameState.LOSS);
                gameSession.getPlayerGameSessionStatuses().put(player.getOpponentId(), GameSessionStatus.NEW);
            }

            PlayerDTO originalPlayer = this.playerRepo.getPlayerById(playerId);
            originalPlayer.setPower(originalPlayer.getPower() + 1);
            player.setPower(originalPlayer.getPower());

            player.setOpponentScore(gameSession.getPlayers().get(player.getOpponentId()).getScore()); // TODO loser's score was not updated on winner's screen on last turn

            return new GameSessionDTO(
                    player,
                    gameSession.getPlayerGameboards().get(playerId),
                    gameSession.getGameStates().get(playerId).name(),
                    gameSession.getDeck().size(),
                    true);
        } else {
            return new GameSessionDTO(); // this gets sent to player 1 on first turn
        }
    }

    public Card drawCard(String playerId) {
        String team = this.playerRepo.getPlayerById(playerId).getTeam();
        return this.playerGameSessionRepo.getGameSessionById(playerId).drawCard(playerId, team);
    }

    public List<List<Cell>> processPutCard(MoveDTO move) {
        return this.playerGameSessionRepo.getGameSessionById(move.getPlayerId()).processPutCard(move);
    }

    public GameSessionDTO endTurn(String playerId, List<Card> hand) {
        GameSession gameSession = playerGameSessionRepo.getGameSessionById(playerId);
        gameSession.endTurn(playerId, hand);
        gameSession.getPlayerGameSessionStatuses().put(playerId, GameSessionStatus.OLD);
        return new GameSessionDTO(
                this.playerGameSessionRepo.getGameSessionById(playerId).getPlayers().get(playerId),
                gameSession.getPlayerGameboards().get(playerId),
                gameSession.getGameStates().get(playerId).name(),
                gameSession.getDeck().size(),
                false);
    }
}
