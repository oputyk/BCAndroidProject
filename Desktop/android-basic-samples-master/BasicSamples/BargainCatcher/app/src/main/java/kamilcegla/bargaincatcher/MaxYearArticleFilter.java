package kamilcegla.bargaincatcher;

/**
 * Created by Oputyk on 22/07/2017.
 */

public class MaxYearArticleFilter implements ArticleFilter {

    int maxYear = 0;
    boolean lessOrEqualToMaxYear = true;

    public MaxYearArticleFilter(int maxYear) {
        this.maxYear = maxYear;
    }

    public void setMaxYear(int maxYear) {
        this.maxYear = maxYear;
    }

    public double getMaxYear() {
        return maxYear;
    }

    @Override
    public boolean filterArticle(Article article) {
        if(lessOrEqualToMaxYear) {
            return article.getYear() <= maxYear;
        } else {
            return article.getYear() > maxYear;
        }
    }

    @Override
    public void setPositive() {
        lessOrEqualToMaxYear = true;
    }

    @Override
    public void setNegative() {
        lessOrEqualToMaxYear = false;
    }
}
