package yay.linda.dto;

public class DeckGeneratorRequest {

    private int numTotal;
    private int numTroops;
    private int numWalls;

    public DeckGeneratorRequest() {
//        numTotal = 60;
//        numTroops = 40;
//        numWalls = 20;
    }

    public DeckGeneratorRequest(int numTotal, int numTroops, int numWalls) {
        this.numTotal = numTotal;
        this.numTroops = numTroops;
        this.numWalls = numWalls;
    }

    public int getNumTotal() {
        return numTotal;
    }

    public void setNumTotal(int numTotal) {
        this.numTotal = numTotal;
    }

    public int getNumTroops() {
        return numTroops;
    }

    public void setNumTroops(int numTroops) {
        this.numTroops = numTroops;
    }

    public int getNumWalls() {
        return numWalls;
    }

    public void setNumWalls(int numWalls) {
        this.numWalls = numWalls;
    }
}
