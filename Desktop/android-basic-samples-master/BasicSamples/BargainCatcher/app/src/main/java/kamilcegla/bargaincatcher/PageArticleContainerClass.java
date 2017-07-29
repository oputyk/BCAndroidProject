package kamilcegla.bargaincatcher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

interface PageArticleContainer {
    void loadPageByUrl(String url) throws JSONException;
    String toString();
    List<Article> getArticles();
    boolean isFoundPage();
    int getCountOfAllArticles();
}

public class PageArticleContainerClass implements PageArticleContainer {
    private List<Article> articles = new ArrayList<Article>();
    private Document document;
    private boolean foundPage = true;
    private int countOfAllArticles;

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Ogloszenia:\n\n");
        for (Article article : articles) {
            stringBuilder.append(article.toString() + "\n\n");
        }

        return stringBuilder.toString();
    }

    @Override
    public List<Article> getArticles() {
        return articles;
    }

    @Override
    public boolean isFoundPage() {
        return foundPage;
    }

    @Override
    public int getCountOfAllArticles() {
        return countOfAllArticles;
    }

    @Override
    public void loadPageByUrl(String url) throws JSONException {
        getDocument(url);
        parseCountOfAllArticles();
        loadArticles();
    }

    private void parseCountOfAllArticles() {
        String countOfAllArticlesString = document.getElementsByClass("fleft tab selected").first().getElementsByClass("counter").first().html();
        countOfAllArticlesString = countOfAllArticlesString.replace(" ", "");
        countOfAllArticlesString = countOfAllArticlesString.replace("(", "");
        countOfAllArticlesString = countOfAllArticlesString.replace(")", "");
        countOfAllArticles = Integer.valueOf(countOfAllArticlesString);
    }

    private void getDocument(String url) {
        try {
            document = Jsoup.connect(url).get();
            foundPage = true;
        } catch (IOException e) {
            foundPage = false;
        }
    }

    private void loadArticles() throws JSONException {
        deleteArticles();
        Elements elements = getArticlesElements();
        for (Element element : elements) {
            Article newArticle = new ArticleClass();
            newArticle.makeArticleFromElement(element);
            addNewArticle(newArticle);
        }
    }

    private void deleteArticles() {
        articles.clear();
    }

    private Elements getArticlesElements() {
        return document.getElementsByTag("article");
    }

    private void addNewArticle(Article newArticle) {
        if (newArticle.isOk())
            articles.add(newArticle);
    }
}
