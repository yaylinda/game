package yay.linda.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GameConfigurations {

    @Value("${num.board.rows}")
    private int numRows;

    @Value("${num.board.cols}")
    private int numCols;

    @Value("${num.card.total}")
    private int numTotalCards;

    @Value("${num.card.troops}")
    private int numTroopCards;

    @Value("${num.card.walls}")
    private int numWallCards;

    @Value("${troop.might.min}")
    private int troopMightMin;

    @Value("${troop.might.max}")
    private int troopMightMax;

    @Value("${troop.move.min}")
    private int troopMoveMin;

    @Value("${troop.move.max}")
    private int troopMoveMax;

    @Value("${wall.might.min}")
    private int wallMightMax;

    @Value("${wall.might.max}")
    private int wallMightMin;

    public int getNumRows() {
        return numRows;
    }

    public int getNumCols() {
        return numCols;
    }

    public int getNumTotalCards() {
        return numTotalCards;
    }

    public int getNumTroopCards() {
        return numTroopCards;
    }

    public int getNumWallCards() {
        return numWallCards;
    }

    public int getTroopMightMin() {
        return troopMightMin;
    }

    public int getTroopMightMax() {
        return troopMightMax;
    }

    public int getTroopMoveMin() {
        return troopMoveMin;
    }

    public int getTroopMoveMax() {
        return troopMoveMax;
    }

    public int getWallMightMax() {
        return wallMightMax;
    }

    public int getWallMightMin() {
        return wallMightMin;
    }
}
