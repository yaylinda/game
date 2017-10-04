package yay.linda.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:deck-generator.properties")
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

    @Value("${troop.move.extra.freq}")
    private float troopMoveExtraFreq;

    @Value("${wall.might.min}")
    private int wallMightMax;

    @Value("${wall.might.max}")
    private int wallMightMin;

    @Value("${card.ability.freq}")
    private float cardAbilityFreq;

    @Value("${troop.might.numTiers}")
    private int troopMightStatNumTiers;

    @Value("${troop.might.tier1.freq}")
    private float troopMightStatTier1;

    @Value("${troop.might.tier2.freq}")
    private float troopMightStatTier2;

    @Value("${troop.might.tier3.freq}")
    private float troopMightStatTier3;

    @Value("${troop.might.tier4.freq}")
    private float troopMightStatTier4;

    @Value("${troop.might.tier5.freq}")
    private float troopMightStatTier5;

    @Value("${hand.size}")
    private int handSize;

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

    public float getTroopMoveExtraFreq() {
        return troopMoveExtraFreq;
    }

    public int getWallMightMax() {
        return wallMightMax;
    }

    public int getWallMightMin() {
        return wallMightMin;
    }

    public float getCardAbilityFreq() {
        return cardAbilityFreq;
    }

    public int getTroopMightStatNumTiers() {
        return troopMightStatNumTiers;
    }

    public float getTroopMightStatTier1() {
        return troopMightStatTier1;
    }

    public float getTroopMightStatTier2() {
        return troopMightStatTier2;
    }

    public float getTroopMightStatTier3() {
        return troopMightStatTier3;
    }

    public float getTroopMightStatTier4() {
        return troopMightStatTier4;
    }

    public float getTroopMightStatTier5() {
        return troopMightStatTier5;
    }

    public int getHandSize() {
        return handSize;
    }
}
