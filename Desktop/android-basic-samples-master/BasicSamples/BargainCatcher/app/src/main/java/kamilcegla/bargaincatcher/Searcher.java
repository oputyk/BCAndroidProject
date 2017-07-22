package kamilcegla.bargaincatcher;

import android.support.annotation.NonNull;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

/**
 * Created by Oputyk on 21/07/2017.
 */

public class Searcher {
    ArticleContainer articleContainer = new ArticleContainerClass();
    ArticleContainerFilter articleContainerFilter = new ArticleContainerFilterClass();
    SearcherSettings searcherSettings = new SearcherSettings();

    public void setSearcherSettings(SearcherSettings searcherSettings) {
        this.searcherSettings = searcherSettings;
        List<ArticleFilter> articleFilters = searcherSettings.convertToArticleFilters();
        articleContainerFilter.addArticleFilters(articleFilters);
        try {
            articleContainer.setUrl(getUrl());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public List<Article> searchArticles() {
        List<Article> newArticles = articleContainer.getNewNotPromotedArticles();
        return newArticles;
    }

    @NonNull
    private String getUrl() {
        return "http://www.otomoto.pl/" + searcherSettings.getType() + "/" + searcherSettings.getMark();
    }
}
