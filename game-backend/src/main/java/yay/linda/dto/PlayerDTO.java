package yay.linda.dto;

import yay.linda.dto.enums.PlayerTeam;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Represents a Player object.
 */
public class PlayerDTO {

    private String id;
    private String opponentId;
    private String name;
    private String opponentName;
    private String team;
    private double power;
    private List<Card> hand;
    private int score;
    private int opponentScore;
    private int maxScore;
    private int furthestRow;

    public PlayerDTO() {
        this.id = UUID.randomUUID().toString();
        this.score = 0;
        this.opponentScore = 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOpponentId() {
        return opponentId;
    }

    public void setOpponentId(String opponentId) {
        this.opponentId = opponentId;
    }

    public String getOpponentName() {
        return opponentName;
    }

    public void setOpponentName(String opponentName) {
        this.opponentName = opponentName;
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }

    public int getOpponentScore() {
        return opponentScore;
    }

    public void setOpponentScore(int opponentScore) {
        this.opponentScore = opponentScore;
    }

    public int getFurthestRow() {
        return furthestRow;
    }

    public void setFurthestRow(int furthestRow) {
        this.furthestRow = furthestRow;
    }
}
