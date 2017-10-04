package yay.linda.dto;

import yay.linda.dto.enums.CardType;

public class Card {

    private String cardType;
    private int might;
    private int movement;
    private double cost;
    private String specialAbility;
    private Player owner;
    private boolean played;

    public Card() {}

    public Card(CardType cardType, int might, int movement, double cost) {
        this.cardType = cardType.name();
        this.might = might;
        this.movement = movement;
        this.cost = cost;
        this.specialAbility = "";
        this.owner = null;
        this.played = false;
    }

    public Card(Card other) {
        this.cardType = other.cardType;
        this.might = other.might;
        this.movement = other.movement;
        this.cost = other.cost;
        this.specialAbility = other.specialAbility;
        this.owner = other.owner;
        this.played = other.played;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
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

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public String getSpecialAbility() {
        return specialAbility;
    }

    public void setSpecialAbility(String specialAbility) {
        this.specialAbility = specialAbility;
    }

    public boolean isPlayed() {
        return played;
    }

    public void setPlayed(boolean played) {
        this.played = played;
    }
}
