package yay.linda.game.game;

public class Cell {

    private CellState state;
    private Card card;

    public Cell(CellState state, Card card) {
        this.state = state;
        this.card = card;
    }

    public Cell() {
        this.state = CellState.EMPTY;
        this.card = null;
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
