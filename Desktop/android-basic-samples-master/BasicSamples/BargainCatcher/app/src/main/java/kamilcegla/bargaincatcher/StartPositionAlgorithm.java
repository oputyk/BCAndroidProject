package kamilcegla.bargaincatcher;

/**
 * Created by Oputyk on 22/07/2017.
 */

public class StartPositionAlgorithm {
    int lastStartPosition;
    int countOfArticles;

    public StartPositionAlgorithm(int lastStartPosition, int countOfArticles) {
        this.lastStartPosition = lastStartPosition;
        this.countOfArticles = countOfArticles;
    }

    public int getLastStartPosition() {
        return lastStartPosition;
    }

    public void setLastStartPosition(int lastStartPosition) {
        this.lastStartPosition = lastStartPosition;
    }

    public int getCountOfArticles() {
        return countOfArticles;
    }

    public void setCountOfArticles(int countOfArticles) {
        this.countOfArticles = countOfArticles;
    }

    public int computeStartPosition() {
        int startPosition = 0;

        startPosition = compute();

        if(startPosition < 0) {
            return 0;
        } else {
            return startPosition;
        }
    }

    private int compute() {
        return 0;
    }
}
