package kamilcegla.bargaincatcher;

/**
 * Created by Oputyk on 22/07/2017.
 */
public class ModelArticleFilter implements ArticleFilter {

    private boolean positive = true;
    private String model;

    public ModelArticleFilter(String model) {
        this.model = model;
    }

    @Override
    public boolean filterArticle(Article article) {
        return article.getModel().equals(model);
    }

    @Override
    public void setPositive() {
        positive = true;
    }

    @Override
    public void setNegative() {
        positive = false;
    }
}
