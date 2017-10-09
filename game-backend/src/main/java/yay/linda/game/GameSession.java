package yay.linda.game;

import yay.linda.dto.Card;
import yay.linda.dto.GameBoard;
import yay.linda.dto.GameSessionDTO;
import yay.linda.dto.Player;
import yay.linda.dto.enums.GameSessionStatus;
import yay.linda.service.DeckGenerator;

import javax.inject.Inject;
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


    public GameSession() { }

    public GameSession(Player player1, Player player2, DeckGenerator deckGenerator, int numRows, int numCols, int handSize) {
        this.players = new HashMap<>();
        this.playerGameboards = new HashMap<>();
        this.playerGameSessionStatuses = new HashMap<>();
        this.playerTurns = new HashMap<>();
        this.deckGenerator = deckGenerator;
        this.deck = this.deckGenerator.generateDeck();
        this.handSize = handSize;

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
        this.players.get(playerId).setPower(players.get(playerId).getPower() + 1.0);
        this.players.get(playerId).setHand(gameSession.getPlayer().getHand());
        this.playerGameboards.get(playerId).setBoard(gameSession.getGameboard());
        this.playerGameSessionStatuses.put(playerId, GameSessionStatus.OLD);
        this.playerTurns.put(playerId, false);

        // get other players to update stats as well
        for (String id : players.keySet()) {
            if (!id.equals(playerId)) {
                // TODO logic to update other player's board correctly
                this.playerGameboards.get(id).setBoard(gameSession.getGameboard());

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
