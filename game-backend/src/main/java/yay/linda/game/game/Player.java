package yay.linda.game.game;

import java.util.List;

public class Player {

    private List<Card> hand;
    private Team team;

    public Player(List<Card> hand, Team team) {
        this.hand = hand;
        this.team = team;
    }

    public List<Card> getHand() {
        return hand;
    }

    public void setHand(List<Card> hand) {
        this.hand = hand;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
