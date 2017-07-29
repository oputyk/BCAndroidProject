package kamilcegla.bargaincatcher;

/**
 * Created by Oputyk on 22/07/2017.
 */

public class StartPositionAlgorithm {
    SearcherCache searcherCache = new SearcherCache();

    public StartPositionAlgorithm(SearcherCache searcherCache) {
        this.searcherCache = searcherCache;
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
