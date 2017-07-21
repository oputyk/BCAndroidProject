package kamilcegla.bargaincatcher;

import android.util.Log;

import org.json.JSONException;

import java.io.IOException;

interface ArticleIterator {
	void setFirstPageByUrl(String url) throws IOException, JSONException;
	void setPosition(int newPosition);
	int getPosition();
	boolean isArticle();
	void goToNext();
	void goToPrevious();
	Article getArticle();
}

public class ArticleIteratorClass implements ArticleIterator {
	
	interface ArticlePosition {
		void setAbsolutePosition(int absolutePosition);
		int getAbsolutePosition();
		void setPosition(int pagePosition, int articleOnPagePosition);
		void goToNext();
		void goToPrevious();
		int getPagePosition();
		int getArticleOnPagePosition();
		String toString();
		boolean isPageToReload();
	}
	
	private class ArticlePositionClass implements ArticlePosition {
		private int pagePosition = 1; // First is 1 (not 0)
		private int articleOnPagePosition = 0;
		private boolean pageNumberWasChanged = false;
		
		public void setAbsolutePosition(int absolutePosition) {
			int newPagePosition = absolutePosition / 32 + 1;
			if(pagePosition != newPagePosition) {
				pageNumberWasChanged = true;
			}
			else {
				pageNumberWasChanged = false;
			}
			pagePosition = newPagePosition;
			articleOnPagePosition = absolutePosition % 32; 
		}
		
		@Override
		public String toString() {
			String string = Integer.toString(pagePosition) + ", "
					+ Integer.toString(articleOnPagePosition);
			return string;
		}
		
		public int getAbsolutePosition() {
			int articlesOnOnePage = 32;
			int previousPages = pagePosition - 1;
			
			int articlesOnPreviousPages = articlesOnOnePage * previousPages;
			
			int absolutePosition = articlesOnPreviousPages + articleOnPagePosition;
			
			return absolutePosition;
		}
		
		public void goToNext() {
			setPosition(pagePosition, articleOnPagePosition + 1);
		}
		
		public void goToPrevious() {
			setPosition(pagePosition, articleOnPagePosition - 1);
		}

		public void setPosition(int pagePosition, int articleOnPagePosition) {
			pagePosition += articleOnPagePosition / 32;
			articleOnPagePosition %= 32;
			if(articleOnPagePosition < 0) {
				articleOnPagePosition += 32;
				pagePosition--;
			}
			if(pagePosition != this.pagePosition) {
				pageNumberWasChanged = true;
			} else {
				pageNumberWasChanged = false;
			}
			
			this.pagePosition = pagePosition;
			this.articleOnPagePosition = articleOnPagePosition;
		}
		
		public int getPagePosition() {
			return pagePosition;
		}
		
		public int getArticleOnPagePosition() {
			return articleOnPagePosition;
		}

		@Override
		public boolean isPageToReload() {
			return pageNumberWasChanged;
		}
	}
	
	String baseUrl = new String();
	PageArticleContainer pageArticleContainer = new PageArticleContainerClass();
	ArticlePosition articlePosition = new ArticlePositionClass();
	Article currentArticle;

	@Override
	public Article getArticle() {
		return currentArticle;
	}
	
	@Override
	public void setFirstPageByUrl(String baseUrl) throws IOException, JSONException {
		this.baseUrl = baseUrl;
		initializeFirstPage();
	}
	
	private void initializeFirstPage() throws JSONException {
		pageArticleContainer.loadPageByUrl(baseUrl);
	}
	
	@Override
	public void setPosition(int newPosition) {
		articlePosition.setAbsolutePosition(newPosition);
		updateArticleByCurrentPosition();
	}

	@Override
	public int getPosition() {
		return articlePosition.getAbsolutePosition();
	}

	
	@Override
	public boolean isArticle() {
		return currentArticle != null;
	}

	
	@Override
	public void goToNext() {
		articlePosition.goToNext();
		updateArticleByCurrentPosition();
	}
	

	@Override
	public void goToPrevious() {
		articlePosition.goToPrevious();
		updateArticleByCurrentPosition();
	}

	private void updateArticleByCurrentPosition() {
		try {
			if(articlePosition.isPageToReload()) {
				goToNthPage(articlePosition.getPagePosition());
			}
			goToNthArticle(articlePosition.getArticleOnPagePosition());
		} catch(Exception e) {
			articleNotFound();
		}
	}
	
	private void articleNotFound() {
		currentArticle = null;
	}
	
	private void goToNthPage(int n) throws JSONException {
		String nthPageUrl = getUrlOfNthPage(n);		
		pageArticleContainer.loadPageByUrl(nthPageUrl);
	}
	
	private void goToNthArticle(int articleOnPagePosition) {
		currentArticle = pageArticleContainer.getArticles().get(articleOnPagePosition);
	}
	
	private String getUrlOfNthPage(int n) {
		String pageAdditionText = "?page=" + Integer.toString(n);
		String urlOfNthPage = baseUrl.concat(pageAdditionText);
		return urlOfNthPage;
	}
	
}