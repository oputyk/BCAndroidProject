package kamilcegla.bargaincatcher;

/**
 * Created by Oputyk on 22/07/2017.
 */

public class MinYearArticleFilter implements ArticleFilter {

    int minYear = 0;
    boolean moreOrEqualToMinYear = true;

    public MinYearArticleFilter(int minYear) {
        this.minYear = minYear;
    }

    public void setMinYear(int minYear) {
        this.minYear = minYear;
    }

    public double getMinYear() {
        return minYear;
    }

    @Override
    public boolean filterArticle(Article article) {
        if(moreOrEqualToMinYear) {
            return article.getYear() >= minYear;
        } else {
            return article.getYear() < minYear;
        }
    }

    @Override
    public void setPositive() {
        moreOrEqualToMinYear = true;
    }

    @Override
    public void setNegative() {
        moreOrEqualToMinYear = false;
    }
}
