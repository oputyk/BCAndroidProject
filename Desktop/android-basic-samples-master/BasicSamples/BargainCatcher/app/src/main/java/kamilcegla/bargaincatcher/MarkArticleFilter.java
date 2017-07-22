package kamilcegla.bargaincatcher;

/**
 * Created by Oputyk on 22/07/2017.
 */
public class MarkArticleFilter implements ArticleFilter {

    private boolean positive = true;
    private String mark;

    public MarkArticleFilter(String mark) {
        this.mark = mark;
    }

    @Override
    public boolean filterArticle(Article article) {
        return article.getMark().equals(mark);
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
