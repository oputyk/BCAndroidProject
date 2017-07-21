package kamilcegla.bargaincatcher;

import java.util.ArrayList;
import java.util.List;

interface ArticleContainerFilter {
	List<Article> getFilteredArticles(List<Article> articles);
	void addArticleFilter(ArticleFilter articleFilter);
}

public class ArticleContainerFilterClass implements ArticleContainerFilter {

	List<ArticleFilter> articleFilters = new ArrayList<ArticleFilter>();
	
	@Override
	public void addArticleFilter(ArticleFilter articleFilter) {
		articleFilters.add(articleFilter);
	}
	
	@Override
	public List<Article> getFilteredArticles(List<Article> articles) {
		List<Article> filteredArticles = new ArrayList<Article>();
		
		for(Article article : articles) {
			if(isArticleOk(article))
				filteredArticles.add(article);
		}
		
		return filteredArticles;
	}

	private boolean isArticleOk(Article article) {
		boolean allConditionsAreMet = true;
		
		for(ArticleFilter articleFilter : articleFilters) {
			if(!articleFilter.filterArticle(article)) {
				allConditionsAreMet = false;
				break;
			}
		}
		
		return allConditionsAreMet;
	}

}
