package yay.linda.game.game;

public abstract class Card {

    protected int cost;
    protected int might;
    protected int movement;

    public Card(int cost, int might, int movement) {
        this.cost = cost;
        this.might = might;
        this.movement = movement;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getMight() {
        return might;
    }

    public void setMight(int might) {
        this.might = might;
    }

    public int getMovement() {
        return movement;
    }

    public void setMovement(int movement) {
        this.movement = movement;
    }
}
