package kamilcegla.bargaincatcher;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oputyk on 21/07/2017.
 */
public class SearcherSettings {
    private int minPrice = 0;
    private int maxPrice = Integer.MAX_VALUE;
    private String type;
    private String mark;
    private String model;
    private int minYear;
    private int maxYear;

    public void setMinYear(int minYear) {
        this.minYear = minYear;
    }

    public void setMaxYear(int maxYear) {
        this.maxYear = maxYear;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public void setMaxPrice(int maxPrice) {
        this.maxPrice = maxPrice;
    }

    public void setMinPrice(int minPrice) {
        this.minPrice = minPrice;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMaxPrice() {
        return maxPrice;
    }

    public int getMinPrice() {
        return minPrice;
    }

    public String getMark() {
        return mark;
    }

    public String getModel() {
        return model;
    }

    public String getType() {
        return type;
    }

    public int getMinYear() {
        return minYear;
    }

    public int getMaxYear() {
        return maxYear;
    }

    public List<ArticleFilter> convertToArticleFilters() {
        List<ArticleFilter> articleFilters = new ArrayList<>();

        addMachineBaseInfoFiltersToArticleFilters(articleFilters);
        addPriceFiltersToArticleFilters(articleFilters);
        addYearFiltersToArticleFilters(articleFilters);

        return articleFilters;
    }

    private void addMachineBaseInfoFiltersToArticleFilters(List<ArticleFilter> articleFilters) {
        articleFilters.add(new TypeArticleFilter(type));
        articleFilters.add(new MarkArticleFilter(mark));
        articleFilters.add(new ModelArticleFilter(model));
    }

    private void addPriceFiltersToArticleFilters(List<ArticleFilter> articleFilters) {
        articleFilters.add(new MinPriceArticleFilter(minPrice));
        articleFilters.add(new MaxPriceArticleFilter(maxPrice));
    }

    private void addYearFiltersToArticleFilters(List<ArticleFilter> articleFilters) {
        articleFilters.add(new MinYearArticleFilter(minYear));
        articleFilters.add(new MaxYearArticleFilter(maxYear));
    }
}
