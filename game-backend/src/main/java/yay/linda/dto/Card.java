package yay.linda.dto;

import yay.linda.dto.enums.CardType;

/**
 * Represents a Card object.
 */
public class Card {

    private String cardType;
    private int might;
    private int movement;
    private double cost;
//    private String owningTeam;
    private String owningPlayer;
    private boolean played;
    private String specialAbility; // TODO v2

    public Card() {}

    public Card(CardType cardType, int might, int movement, double cost) {
        this.cardType = cardType.name();
        this.might = might;
        this.movement = movement;
        this.cost = cost;
//        this.owningTeam = null;
        this.owningPlayer = null;
        this.played = false;
        this.specialAbility = "";
    }

    public Card(Card other) {
        this.cardType = other.cardType;
        this.might = other.might;
        this.movement = other.movement;
        this.cost = other.cost;
//        this.owningTeam = other.owningTeam;
        this.owningPlayer = other.owningPlayer;
        this.played = other.played;
        this.specialAbility = other.specialAbility;
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

//    public String getOwningTeam() {
//        return owningTeam;
//    }
//
//    public void setOwningTeam(String owningTeam) {
//        this.owningTeam = owningTeam;
//    }

    public String getOwningPlayer() {
        return owningPlayer;
    }

    public void setOwningPlayer(String owningPlayer) {
        this.owningPlayer = owningPlayer;
    }

    public boolean isPlayed() {
        return played;
    }

    public void setPlayed(boolean played) {
        this.played = played;
    }

    public String getSpecialAbility() {
        return specialAbility;
    }

    public void setSpecialAbility(String specialAbility) {
        this.specialAbility = specialAbility;
    }
}
