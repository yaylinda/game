package yay.linda.dto;

import yay.linda.dto.enums.CellState;

/**
 * Represents a Cell object on the Game Board.
 */
public class Cell {

    private String state;
    private String type;
    private int might;
    private int move;
    private String team;

    public Cell() {
        this.state = CellState.EMPTY.toString();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMight() {
        return might;
    }

    public void setMight(int might) {
        this.might = might;
    }

    public int getMove() {
        return move;
    }

    public void setMove(int move) {
        this.move = move;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }
}
