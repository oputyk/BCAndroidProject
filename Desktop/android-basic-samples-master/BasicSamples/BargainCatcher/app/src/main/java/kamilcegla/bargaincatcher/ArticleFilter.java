package kamilcegla.bargaincatcher;

public interface ArticleFilter {
	boolean filterArticle(Article article);
	void setPositive();
	void setNegative();
}
