package yay.linda.game;

import yay.linda.dto.*;
import yay.linda.dto.enums.CardType;
import yay.linda.dto.enums.CellState;
import yay.linda.dto.enums.GameSessionStatus;
import yay.linda.dto.enums.PlayerTeam;
import yay.linda.service.DeckGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a Game Session object, with logic to handle all internal states.
 */
public class GameSession {

    private Map<String, Player> players;

    private Map<String, GameBoard> playerGameboards;

    private Map<String, GameSessionStatus> playerGameSessionStatuses;

    private Map<String, Boolean> playerTurns;

    private DeckGenerator deckGenerator;

    private List<Card> deck;

    private int handSize;

    private int numRows;
    private int numCols;

    public GameSession() { }

    public GameSession(Player player1, Player player2, DeckGenerator deckGenerator, int numRows, int numCols, int handSize) {
        this.players = new HashMap<>();
        this.playerGameboards = new HashMap<>();
        this.playerGameSessionStatuses = new HashMap<>();
        this.playerTurns = new HashMap<>();
        this.deckGenerator = deckGenerator;
        this.deck = this.deckGenerator.generateDeck();
        this.handSize = handSize;
        this.numRows = numRows;
        this.numCols = numCols;

        player1.setHand(this.pickStartingCards(player1.getId(), player1.getTeam()));
        player2.setHand(this.pickStartingCards(player2.getId(), player2.getTeam()));

        player1.setOpponentName(player2.getName());
        player2.setOpponentName(player1.getName());

        player1.setOpponentId(player2.getId());
        player2.setOpponentId(player1.getId());

        this.players.put(player1.getId(), player1);
        this.players.put(player2.getId(), player2);

        this.playerGameboards.put(player1.getId(), new GameBoard(numRows, numCols));
        this.playerGameboards.put(player2.getId(), new GameBoard(numRows, numCols));

        this.playerGameSessionStatuses.put(player1.getId(), GameSessionStatus.NEW);
        this.playerGameSessionStatuses.put(player2.getId(), GameSessionStatus.OLD);
    }

    public Card drawCard(String owningPlayerId, String owningTeam) {
        int index = deckGenerator.getRandomNumberInRange(0, deck.size()-1);
        Card toReturn = deck.get(index);
        toReturn.setOwningPlayer(owningPlayerId);
        toReturn.setOwningTeam(owningTeam);
        Card last = new Card(deck.get(deck.size()-1));
        deck.set(index, last);
        deck.remove(deck.size()-1);

        System.out.println("Deck now has " + deck.size() + " remaining.");

        return toReturn;
    }

//    public void updateGameData(String playerId, GameSessionDTO gameSession) {
//
//        List<List<Cell>> gameboard = new ArrayList<>();
//        for (List<Cell> row : gameSession.getGameboard()) {
//            List<Cell> copiedRow = new ArrayList<>();
//            for (Cell cell : row) {
//                copiedRow.add(new Cell(cell));
//            }
//            gameboard.add(copiedRow);
//        }
//
//        for (int rowNum = 0; rowNum < this.numRows; rowNum++) {
//            for (int colNum = 0; colNum < this.numCols; colNum++) {
//                Cell cell = new Cell(gameboard.get(rowNum).get(colNum));
//                if (CellState.valueOf(cell.getState()) == CellState.OCCUPIED &&
//                        CardType.valueOf(cell.getType()) == CardType.TROOP &&
//                        PlayerTeam.valueOf(cell.getTeam()) == PlayerTeam.valueOf(gameSession.getPlayer().getTeam())) {
//                    int newRowNum = rowNum - cell.getMove();
//                    if (newRowNum >= 0) {
//                        Cell otherCell = new Cell(gameboard.get(newRowNum).get(colNum));
//                        if (CellState.valueOf(otherCell.getState()) == CellState.OCCUPIED) {
//                            if (PlayerTeam.valueOf(otherCell.getTeam()) == PlayerTeam.valueOf(gameSession.getPlayer().getTeam())) {
//                                gameboard.get(newRowNum).get(colNum).setMight(cell.getMight() + otherCell.getMight());
//                            } else {
//                                int mightDiff = cell.getMight() - otherCell.getMight();
//                                if (mightDiff > 0) {
//                                    gameboard.get(rowNum).get(colNum).setState(CellState.EMPTY.toString());
//                                    gameboard.get(newRowNum).get(colNum).setType(cell.getType());
//                                    gameboard.get(newRowNum).get(colNum).setMight(mightDiff);
//                                    gameboard.get(newRowNum).get(colNum).setMove(cell.getMove());
//                                    gameboard.get(newRowNum).get(colNum).setTeam(cell.getTeam());
//                                } else if (mightDiff < 0) {
//                                    gameboard.get(rowNum).get(colNum).setState(CellState.EMPTY.toString());
//                                    gameboard.get(newRowNum).get(colNum).setType(otherCell.getType());
//                                    gameboard.get(newRowNum).get(colNum).setMight(mightDiff);
//                                    gameboard.get(newRowNum).get(colNum).setMove(otherCell.getMove());
//                                    gameboard.get(newRowNum).get(colNum).setTeam(otherCell.getTeam());
//                                } else {
//                                    gameboard.get(rowNum).get(colNum).setState(CellState.EMPTY.toString());
//                                    gameboard.get(newRowNum).get(colNum).setState(CellState.EMPTY.toString());
//                                }
//                            }
//                        } else {
//                            gameboard.get(rowNum).get(colNum).setState(CellState.EMPTY.toString());
//                            gameboard.get(newRowNum).get(colNum).setState(CellState.OCCUPIED.toString());
//                            gameboard.get(newRowNum).get(colNum).setType(cell.getType());
//                            gameboard.get(newRowNum).get(colNum).setMight(cell.getMight());
//                            gameboard.get(newRowNum).get(colNum).setMove(cell.getMove());
//                            gameboard.get(newRowNum).get(colNum).setTeam(cell.getTeam());
//                        }
//                    } else {
//                        this.players.get(playerId).setScore(this.players.get(playerId).getScore() + 1);
//                        if (this.players.get(playerId).getScore() == this.players.get(playerId).getMaxScore()) {
//                            // TODO win state for this player; lose state for other player
//                        }
//                    }
//                }
//            }
//        }
//        this.playerGameboards.get(playerId).setBoard(gameboard);
//        this.playerGameSessionStatuses.put(playerId, GameSessionStatus.OLD);
//        this.playerTurns.put(playerId, false);
//        this.players.get(playerId).setPower(players.get(playerId).getPower() + 1.0);
//        this.players.get(playerId).setHand(gameSession.getPlayer().getHand());
//
//        // get other players to update stats as well
//        for (String id : players.keySet()) {
//            if (!id.equals(playerId)) {
//                List<List<Cell>> opponentGameBoard = new ArrayList<>();
//                for (int i = gameSession.getGameboard().size()-1; i >=0; i--) {
//                    opponentGameBoard.add(gameSession.getGameboard().get(i));
//                }
//                this.playerGameboards.get(id).setBoard(opponentGameBoard);
//
//                this.playerGameSessionStatuses.put(id, GameSessionStatus.NEW);
//                this.playerTurns.put(id, true);
//            }
//        }
//    }



    private List<Card> pickStartingCards(String owningPlayerId, String owningTeam) {
        List<Card> hand = new ArrayList<>();
        for (int i = 0; i < handSize; i++) {
            Card card = this.drawCard(owningPlayerId, owningTeam);
            card.setOwningPlayer(owningPlayerId);
            hand.add(card);
        }
        return hand;
    }

    public Map<String, Player> getPlayers() {
        return players;
    }

    public void setPlayers(Map<String, Player> players) {
        this.players = players;
    }

    public Map<String, GameBoard> getPlayerGameboards() {
        return playerGameboards;
    }

    public void setPlayerGameboards(Map<String, GameBoard> playerGameboards) {
        this.playerGameboards = playerGameboards;
    }

    public Map<String, GameSessionStatus> getPlayerGameSessionStatuses() {
        return playerGameSessionStatuses;
    }

    public void setPlayerGameSessionStatuses(Map<String, GameSessionStatus> playerGameSessionStatuses) {
        this.playerGameSessionStatuses = playerGameSessionStatuses;
    }

    public List<Card> getDeck() {
        return deck;
    }

    public void setDeck(List<Card> deck) {
        this.deck = deck;
    }

    public GameBoard processPutCard(MoveDTO move) {
        GameBoard gameBoard = this.playerGameboards.get(move.getPlayerId());
        this.players.get(move.getPlayerId()).setHand(move.getHand());

        gameBoard.getBoard().get(move.getRow()).get(move.getCol()).setState(CellState.OCCUPIED.toString());
        gameBoard.getBoard().get(move.getRow()).get(move.getCol()).setTeam(move.getCell().getTeam());
        gameBoard.getBoard().get(move.getRow()).get(move.getCol()).setType(move.getCell().getType());
        gameBoard.getBoard().get(move.getRow()).get(move.getCol()).setMove(move.getCell().getMove());
        gameBoard.getBoard().get(move.getRow()).get(move.getCol()).setMight(move.getCell().getMight());

        // TODO put card on other board on opposite side
        return gameBoard;
    }
}
