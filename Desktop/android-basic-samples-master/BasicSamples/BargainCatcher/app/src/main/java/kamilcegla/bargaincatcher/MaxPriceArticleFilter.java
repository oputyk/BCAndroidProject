package kamilcegla.bargaincatcher;

/**
 * Created by Oputyk on 22/07/2017.
 */

public class MaxPriceArticleFilter implements ArticleFilter {

    int maxPrice = 0;
    boolean lessOrEqualToMaxPrice = true;

    public MaxPriceArticleFilter(int maxPrice) {
        this.maxPrice = maxPrice;
    }

    public void setMaxPrice(int maxPrice) {
        this.maxPrice = maxPrice;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    @Override
    public boolean filterArticle(Article article) {
        if(lessOrEqualToMaxPrice) {
            return article.getBruttoMoney() <= maxPrice;
        } else {
            return article.getBruttoMoney() > maxPrice;
        }
    }

    @Override
    public void setPositive() {
        lessOrEqualToMaxPrice = true;
    }

    @Override
    public void setNegative() {
        lessOrEqualToMaxPrice = false;
    }
}
