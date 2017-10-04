package yay.linda.dto;

import yay.linda.dto.enums.CellState;

/**
 * Represents a Cell object on the Game Board.
 */
public class Cell {

    private CellState state;
    private Card card;

    public Cell() {
        this.state = CellState.EMPTY;
        this.card = null;
    }

    public Cell(CellState state, Card card) {
        this.state = state;
        this.card = card;
    }


    public CellState getState() {
        return state;
    }

    public void setState(CellState state) {
        this.state = state;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }
}
