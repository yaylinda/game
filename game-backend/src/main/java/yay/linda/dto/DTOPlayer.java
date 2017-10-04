package yay.linda.dto;

/**
 * DTO of a Player object.
 */
public class DTOPlayer {

    private String name;
    private String team;

    public DTOPlayer() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }
}
