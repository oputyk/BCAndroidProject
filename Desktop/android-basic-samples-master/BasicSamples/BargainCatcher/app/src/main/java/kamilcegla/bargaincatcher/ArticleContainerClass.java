package kamilcegla.bargaincatcher;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

interface ArticleContainer {
	void setUrl(String url) throws IOException, JSONException;
	void searchNewNotPromotedArticles();
	List<Article> getNewNotPromotedArticles();
}

public class ArticleContainerClass implements ArticleContainer {
	List<Article> newNotPromotedArticles = new ArrayList<Article>();
	String lastNotPromotedArticleLink = new String("");
	PromotionBorderFinder promotionBorderFinder = new PromotionBorderFinderClass();
	private int newNotPromotedArticlesCountLimit = 100;

	public void setNewNotPromotedArticlesCountLimit(int newNotPromotedArticlesCountLimit) {
		this.newNotPromotedArticlesCountLimit = newNotPromotedArticlesCountLimit;
	}

	public int getNewNotPromotedArticlesCountLimit() {
		return newNotPromotedArticlesCountLimit;
	}

	@Override
	public void setUrl(String url) throws IOException, JSONException {
		promotionBorderFinder.setUrl(url);
	}

	@Override
	public List<Article> getNewNotPromotedArticles() {
		return newNotPromotedArticles;
	}

	@Override
	public void searchNewNotPromotedArticles() {
		try {
			setStartSearchPosition();
			promotionBorderFinder.searchFirstNotPromotedArticleIterator();
		} catch (Exception e) {
			e.printStackTrace();
		}
		ArticleIterator articleIterator = promotionBorderFinder.getFirstNotPromotedArticleIterator();
		addNewNotPromotedArticlesFromArticleIterator(articleIterator);
	}

	private void setStartSearchPosition() {
		int startPosition = computeStartPosition();
		promotionBorderFinder.setStartSearchPosition(startPosition);
	}

	private int computeStartPosition() {
		StartPositionAlgorithm startPositionAlgorithm = new StartPositionAlgorithm();
	}

	private void addNewNotPromotedArticlesFromArticleIterator(ArticleIterator articleIterator) {
		for(int i = 0; i < newNotPromotedArticlesCountLimit && articleIterator.isArticle(); i++) {
			if(!articleIterator.getArticle().getLink().equals(lastNotPromotedArticleLink)) {
				newNotPromotedArticles.add(articleIterator.getArticle());
			}
		}
	}

	private void saveSearcherCache() {
		SearcherCache searcherCache = new SearcherCache();

	}

}
