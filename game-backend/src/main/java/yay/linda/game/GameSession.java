package yay.linda.game;

import yay.linda.dto.*;
import yay.linda.dto.enums.*;
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

    private Map<String, GameState> gameStates;

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
        this.gameStates = new HashMap<>();
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

        this.gameStates.put(player1.getId(), GameState.ONGOING);
        this.gameStates.put(player2.getId(), GameState.ONGOING);
    }

    public Card drawCard(String owningPlayerId, String owningTeam) {
        Card toReturn;
        if (this.deck.size() > 0) {
            int index = deckGenerator.getRandomNumberInRange(0, deck.size() - 1);
            toReturn = deck.get(index);
            toReturn.setOwningPlayer(owningPlayerId);
            toReturn.setOwningTeam(owningTeam);
            Card last = new Card(deck.get(deck.size() - 1));
            deck.set(index, last);
            deck.remove(deck.size() - 1);
            return toReturn;
        } else {
            toReturn= new Card();
            toReturn.setCardType(CardType.BLANK.toString());
        }
        return toReturn;
    }

    public List<List<Cell>> processPutCard(MoveDTO move) {
        GameBoard gameBoard = this.playerGameboards.get(move.getPlayerId());
        gameBoard.getBoard().get(move.getRow()).get(move.getCol()).setState(CellState.OCCUPIED.toString());
        gameBoard.getBoard().get(move.getRow()).get(move.getCol()).setTeam(move.getCell().getTeam());
        gameBoard.getBoard().get(move.getRow()).get(move.getCol()).setType(move.getCell().getType());
        gameBoard.getBoard().get(move.getRow()).get(move.getCol()).setMove(move.getCell().getMove());
        gameBoard.getBoard().get(move.getRow()).get(move.getCol()).setMight(move.getCell().getMight());
        return gameBoard.getBoard();
    }

    public void endTurn(String playerId, List<Card> hand) {
        this.players.get(playerId).setHand(hand);

        // update opponent's gameboard with player's moves
        List<List<Cell>> gameboard = this.playerGameboards.get(playerId).getBoard();
        for (String id : players.keySet()) {
                if (!id.equals(playerId)) {
                List<List<Cell>> opponentGameboard = new ArrayList<>();
                for (int i = gameboard.size()-1; i >=0; i--) {
                    List<Cell> originalRow = gameboard.get(i);
                    List<Cell> newRow = new ArrayList<>();
                    for (Cell originalCell : originalRow) {
                        newRow.add(new Cell(originalCell));
                    }
                    opponentGameboard.add(newRow);
                }
                this.playerGameboards.get(id).setBoard(opponentGameboard);
                this.playerGameSessionStatuses.put(id, GameSessionStatus.NEW);
                this.playerTurns.put(id, true);
            }
        }
    }

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

    public Map<String, Boolean> getPlayerTurns() {
        return playerTurns;
    }

    public void setPlayerTurns(Map<String, Boolean> playerTurns) {
        this.playerTurns = playerTurns;
    }

    public Map<String, GameState> getGameStates() {
        return gameStates;
    }

    public void setGameStates(Map<String, GameState> gameStates) {
        this.gameStates = gameStates;
    }

    public int getHandSize() {
        return handSize;
    }

    public void setHandSize(int handSize) {
        this.handSize = handSize;
    }

    public int getNumRows() {
        return numRows;
    }

    public void setNumRows(int numRows) {
        this.numRows = numRows;
    }

    public int getNumCols() {
        return numCols;
    }

    public void setNumCols(int numCols) {
        this.numCols = numCols;
    }

    public List<Card> getDeck() {
        return deck;
    }

    public void setDeck(List<Card> deck) {
        this.deck = deck;
    }
}
