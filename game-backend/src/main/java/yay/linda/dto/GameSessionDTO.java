package yay.linda.dto;

public class GameSessionDTO {
    private Player player;
    private GameBoard gameboard;
    private boolean myTurn;

    public GameSessionDTO() {
    }

    public GameSessionDTO(Player player, GameBoard gameboard, boolean myTurn) {
        this.player = player;
        this.gameboard = gameboard;
        this.myTurn = myTurn;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public GameBoard getGameboard() {
        return gameboard;
    }

    public void setGameboard(GameBoard gameboard) {
        this.gameboard = gameboard;
    }

    public boolean isMyTurn() {
        return myTurn;
    }

    public void setMyTurn(boolean myTurn) {
        this.myTurn = myTurn;
    }
}
