package kamilcegla.bargaincatcher;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

interface PromotionBorderFinder {
	ArticleIterator searchFirstNotPromotedArticleIterator() throws Exception;
	List<Article> getPromotedArticles();
	void setUrl(String url) throws IOException, JSONException;
	ArticleIterator getFirstNotPromotedArticleIterator();
	void setStartSearchPosition(int startSearchPosition);
	int getStartSearchPosition();
}

public class PromotionBorderFinderClass implements PromotionBorderFinder {

	String url = new String();
	ArticleIterator articleIterator = new ArticleIteratorClass();
	List<Article> promotedArticles = new ArrayList<Article>();
	Article currentArticle;
	Article lastArticle;
	int startSearchPosition = 0;
	boolean isFinishedFirstChecking;
	int startSearchPositionStep = 32;
	boolean initialized = false;

	@Override
	public List<Article> getPromotedArticles() {
		return promotedArticles;
	}
	
	@Override
	public void setUrl(String url) throws IOException, JSONException {
		this.url = url;
	}

	public void setStartSearchPosition(int startSearchPosition) {
		this.startSearchPosition = startSearchPosition;
	}

	@Override
	public int getStartSearchPosition() {
		return startSearchPosition;
	}

	@Override
	public ArticleIterator getFirstNotPromotedArticleIterator() {
		return articleIterator;
	}
	
	@Override
	public ArticleIterator searchFirstNotPromotedArticleIterator() throws Exception {
		if(!initialized) {
			articleIterator.setFirstPageByUrlAndStartPosition(url, startSearchPosition);
		}
		prepareForSearch();
		if(!checkFirstTwoArticlesAndIsFinished()) {
			checkNextArticles();
		}
		
		return articleIterator;
	}

	
	private void prepareForSearch() {
		articleIterator.setPosition(startSearchPosition);
		promotedArticles.clear();
		isFinishedFirstChecking = true;
		currentArticle = new ArticleClass();
		lastArticle = new ArticleClass();
	}
	
	private boolean checkFirstTwoArticlesAndIsFinished() throws Exception {
		if(articleIterator.isArticle()) {
			currentArticle = articleIterator.getArticle();
			articleIterator.goToNext();
			if(articleIterator.isArticle()) {
				checkTheFirstTwoArticles();
			}
			else {
				checkTheOnlyOneArticle();
			}
		}
		return isFinishedFirstChecking;
	}

	private void checkNextArticles() {
		articleIterator.goToNext();
		while(articleIterator.isArticle()) {
			currentArticle = articleIterator.getArticle();
			if(!currentArticle.getPromotion()) {
				try {
					if(isCurrentArticleNewerThanLastArticle(currentArticle, lastArticle)) {
						break;
					}
					else {
						goToNextArticleAndSavePromotedArticle();
					}
				} catch (Exception e) { }
			}
			else {
				goToNextArticleAndSavePromotedArticle();
			}
		}
	}
	
	
	private void checkTheFirstTwoArticles() throws Exception {
		lastArticle = currentArticle;
		currentArticle = articleIterator.getArticle();
		boolean firstPromotion = lastArticle.getPromotion();
		boolean secondPromotion = currentArticle.getPromotion();
		if(!firstPromotion && !secondPromotion) {
			setArticleIteratorOnFirstPosition();
		}
		else if(firstPromotion && secondPromotion) {
			thereIsNoBorder();
		}
		else if(firstPromotion && !secondPromotion) {
			if(isCurrentArticleNewerThanLastArticle(currentArticle, lastArticle)) {
				promotedArticles.add(lastArticle);
			} else {
				thereIsNoBorder();
			}
		}
		else if(firstPromotion && !secondPromotion) {
			thereIsNoBorder();
		}
	}
	
	private void checkTheOnlyOneArticle() {
		if(!currentArticle.getPromotion()) {
			setArticleIteratorOnFirstPosition();
		}
		else {
			promotedArticles.add(currentArticle);
		}
	}
	
	
	private void thereIsNoBorder() {
		promotedArticles.add(lastArticle);
		promotedArticles.add(currentArticle);
		isFinishedFirstChecking = false;
	}
	
	private void setArticleIteratorOnFirstPosition() {
		if(!currentArticle.getPromotion()) {
			articleIterator.setPosition(startSearchPosition);
		}
	}
	
	private void goToNextArticleAndSavePromotedArticle() {
		promotedArticles.add(currentArticle);
		lastArticle = currentArticle;
		articleIterator.goToNext();
	}

	private boolean isCurrentArticleNewerThanLastArticle(Article currentArticle, Article lastArticle) throws Exception {
		Date currentArticlePublishDate = currentArticle.getArticleInsider().getPublishDate();
		Date lastArticlePublishDate = lastArticle.getArticleInsider().getPublishDate();
		return currentArticlePublishDate.after(lastArticlePublishDate);
	}


}
