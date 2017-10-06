package yay.linda.dto;

import yay.linda.dto.enums.PlayerTeam;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Player object.
 */
public class Player {

    private String name;
    private double power;
    private List<Card> hand;
    private String team;

    public Player() {
    }

    public Player(String name) {
        this.name = name;
    }

    public Player(DTOPlayer dtoPlayer) {
        this.name = dtoPlayer.getName();
        this.team = dtoPlayer.getTeam();
        this.power = team.equals(PlayerTeam.TEAM1.name()) ? 1 : 2;
        this.hand = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPower() {
        return power;
    }

    public void setPower(double power) {
        this.power = power;
    }

    public List<Card> getHand() {
        return hand;
    }

    public void setHand(List<Card> hand) {
        this.hand = hand;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }
}
