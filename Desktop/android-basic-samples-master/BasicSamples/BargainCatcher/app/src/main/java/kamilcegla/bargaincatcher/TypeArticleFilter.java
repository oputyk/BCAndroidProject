package kamilcegla.bargaincatcher;

/**
 * Created by Oputyk on 22/07/2017.
 */
public class TypeArticleFilter implements ArticleFilter {

    private boolean positive = true;
    private String type;

    public TypeArticleFilter(String type) {
        this.type = type;
    }

    @Override
    public boolean filterArticle(Article article) {
        return article.getType().equals(type);
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
