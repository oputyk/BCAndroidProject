package kamilcegla.bargaincatcher;

/**
 * Created by Oputyk on 22/07/2017.
 */

public class MinPriceArticleFilter implements ArticleFilter {

    int minPrice = 0;
    boolean moreOrEqualToMinPrice = true;

    public MinPriceArticleFilter(int maxPrice) {
        this.minPrice = maxPrice;
    }

    public void setMaxPrice(int maxPrice) {
        this.minPrice = maxPrice;
    }

    public double getMaxPrice() {
        return minPrice;
    }

    @Override
    public boolean filterArticle(Article article) {
        if(moreOrEqualToMinPrice) {
            return article.getBruttoMoney() >= minPrice;
        } else {
            return article.getBruttoMoney() < minPrice;
        }
    }

    @Override
    public void setPositive() {
        moreOrEqualToMinPrice = true;
    }

    @Override
    public void setNegative() {
        moreOrEqualToMinPrice = false;
    }
}
