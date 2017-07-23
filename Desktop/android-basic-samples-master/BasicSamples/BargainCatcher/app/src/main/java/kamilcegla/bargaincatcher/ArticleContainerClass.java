package kamilcegla.bargaincatcher;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

interface ArticleContainer {
	void setUrl(String url) throws IOException, JSONException;
	List<Article> getNewPromotedArticles();
	List<Article> getNewNotPromotedArticles();
	void update() throws Exception;
	boolean isNewPromotedArticle();
	boolean isNewNotPromotedArticle();
}

public class ArticleContainerClass implements ArticleContainer {
	PageArticleContainer pageArticleContainer = new PageArticleContainerClass();
	List<Article> promotedArticles = new ArrayList<Article>(); 
	List<Article> notPromotedArticles = new ArrayList<Article>();
	List<Article> lastPromotedArticles = new ArrayList<Article>(); 
	List<Article> lastNotPromotedArticles = new ArrayList<Article>();
	PromotionBorderFinder promotionBorderFinder = new PromotionBorderFinderClass();
	String url;
	
	@Override
	public void setUrl(String url) throws IOException, JSONException {
		this.url = url;
		promotionBorderFinder.setUrl(url);
	}

	@Override
	public void update() throws Exception {
		saveArticles();
		promotionBorderFinder.searchFirstNotPromotedArticleIterator();
		promotedArticles = promotionBorderFinder.getPromotedArticles();
		ArticleIterator articleIterator = promotionBorderFinder.getFirstNotPromotedArticleIterator();
		notPromotedArticles.clear();
		for(int i = 0; i < 4 && articleIterator.isArticle(); i++, articleIterator.goToNext()) {
			notPromotedArticles.add(articleIterator.getArticle());
		}
	}



	private void saveArticles() {
		lastPromotedArticles = new ArrayList<Article>(promotedArticles);
		lastNotPromotedArticles = new ArrayList<Article>(notPromotedArticles);
	}
	
	@Override
	public List<Article> getNewPromotedArticles() {
		List<Article> newArticles = new ArrayList<Article>();
		if(isNewPromotedArticle()) 
			newArticles = getNewArticles(lastPromotedArticles, promotedArticles);
		return newArticles;
	}

	@Override
	public List<Article> getNewNotPromotedArticles() {
		List<Article> newArticles = new ArrayList<Article>();
		if(isNewNotPromotedArticle()) 
			newArticles = getNewArticles(lastNotPromotedArticles, notPromotedArticles);
		return newArticles;
	}

	@Override
	public boolean isNewPromotedArticle() {
		return !lastPromotedArticles.equals(promotedArticles);
	}

	@Override
	public boolean isNewNotPromotedArticle() {
		return !lastNotPromotedArticles.equals(notPromotedArticles);
	}

	private List<Article> getNewArticles(List<Article> lastArticles, List<Article> articles) {
		List<Article> newArticles = new ArrayList<Article>();
		for(int i = 0; i < articles.size(); i++) {
			if(lastArticles.isEmpty() || !articles.get(i).equals(lastArticles.get(0)))
				newArticles.add(articles.get(i));
			else
				break;
		}
		return newArticles;
	}

}
