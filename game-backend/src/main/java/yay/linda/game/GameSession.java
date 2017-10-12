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

    public void updateGameData(String playerId, GameSessionDTO gameSession) {

        for (int rowNum = 0; rowNum < this.numRows; rowNum++) {
            for (int colNum = 0; colNum < this.numCols; colNum++) {
                Cell cell = gameSession.getGameboard().get(rowNum).get(colNum);
                if (CardType.valueOf(cell.getType()) == CardType.TROOP
                        && CellState.valueOf(cell.getState()) == CellState.OCCUPIED
                        && PlayerTeam.valueOf(cell.getTeam()) == PlayerTeam.valueOf(gameSession.getPlayer().getTeam())) {
                    int newRowNum = rowNum - cell.getMove();
                    if (newRowNum >= 0) {
                        Cell otherCell = gameSession.getGameboard().get(newRowNum).get(colNum);
                        if (CellState.valueOf(otherCell.getState()) == CellState.OCCUPIED) {
                            if (PlayerTeam.valueOf(otherCell.getTeam()) == PlayerTeam.valueOf(gameSession.getPlayer().getTeam())) {
                                gameSession.getGameboard().get(newRowNum).get(colNum).setMight(cell.getMight() + otherCell.getMight());
                            } else {
                                int mightDiff = cell.getMight() - otherCell.getMight();
                                if (mightDiff > 0) {
                                    gameSession.getGameboard().get(rowNum).get(colNum).setState(CellState.EMPTY.toString());
                                    gameSession.getGameboard().get(newRowNum).get(colNum).setType(cell.getType());
                                    gameSession.getGameboard().get(newRowNum).get(colNum).setMight(mightDiff);
                                    gameSession.getGameboard().get(newRowNum).get(colNum).setMove(cell.getMove());
                                    gameSession.getGameboard().get(newRowNum).get(colNum).setTeam(cell.getTeam());
                                } else if (mightDiff < 0) {
                                    gameSession.getGameboard().get(rowNum).get(colNum).setState(CellState.EMPTY.toString());
                                    gameSession.getGameboard().get(newRowNum).get(colNum).setType(otherCell.getType());
                                    gameSession.getGameboard().get(newRowNum).get(colNum).setMight(mightDiff);
                                    gameSession.getGameboard().get(newRowNum).get(colNum).setMove(otherCell.getMove());
                                    gameSession.getGameboard().get(newRowNum).get(colNum).setTeam(otherCell.getTeam());
                                } else {
                                    gameSession.getGameboard().get(rowNum).get(colNum).setState(CellState.EMPTY.toString());
                                    gameSession.getGameboard().get(newRowNum).get(colNum).setState(CellState.EMPTY.toString());
                                }
                            }
                        } else {
                            gameSession.getGameboard().get(rowNum).get(colNum).setState(CellState.EMPTY.toString());
                            gameSession.getGameboard().get(newRowNum).get(colNum).setState(CellState.OCCUPIED.toString());
                            gameSession.getGameboard().get(newRowNum).get(colNum).setType(cell.getType());
                            gameSession.getGameboard().get(newRowNum).get(colNum).setMight(cell.getMight());
                            gameSession.getGameboard().get(newRowNum).get(colNum).setMove(cell.getMove());
                            gameSession.getGameboard().get(newRowNum).get(colNum).setTeam(cell.getTeam());
                        }
                    } else {
                        this.players.get(playerId).setScore(this.players.get(playerId).getScore() + 1);
                        if (this.players.get(playerId).getScore() == this.players.get(playerId).getMaxScore()) {
                            // TODO win state for this player; lose state for other player
                        }
                    }
                }
            }
        }
        this.playerGameboards.get(playerId).setBoard(gameSession.getGameboard());
        this.playerGameSessionStatuses.put(playerId, GameSessionStatus.OLD);
        this.playerTurns.put(playerId, false);
        this.players.get(playerId).setPower(players.get(playerId).getPower() + 1.0);
        this.players.get(playerId).setHand(gameSession.getPlayer().getHand());

        // get other players to update stats as well
        for (String id : players.keySet()) {
            if (!id.equals(playerId)) {
                List<List<Cell>> opponentGameBoard = new ArrayList<>();
                for (int i = gameSession.getGameboard().size()-1; i >=0; i--) {
                    opponentGameBoard.add(gameSession.getGameboard().get(i));
                }
                this.playerGameboards.get(id).setBoard(opponentGameBoard);

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

    public List<Card> getDeck() {
        return deck;
    }

    public void setDeck(List<Card> deck) {
        this.deck = deck;
    }
}
