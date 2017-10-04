package yay.linda.util;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class RandomCardUtils {

    public int getRandomNumberInRange(int min, int max) {
        // copied from https://www.mkyong.com/java/java-generate-random-integers-in-a-range/
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
}
