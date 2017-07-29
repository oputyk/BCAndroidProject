package kamilcegla.bargaincatcher;

import org.json.JSONException;
import org.jsoup.nodes.Element;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;

/**
 * Created by Oputyk on 10/06/2017.
 */

interface Article {
    void makeArticleFromElement(Element element) throws JSONException;
    double getMoney();
    int getBruttoMoney();
    String getModel();
    String getMark();
    String getType();
    int getSizeOfAllArticles();
    String toString();
    int getYear();
    String getLink();
    boolean isBrutto();
    String getName();
    String getCurrencyName();
    boolean getPromotion();
    boolean isOk();
    boolean equals(Article article);
    ArticleInsider getArticleInsider() throws Exception;
}

public class ArticleClass implements Article {

    private double money;
    private int year;
    private String link;
    private String currencyName;
    private boolean brutto;
    private double exchangeRatio;
    private boolean anyPromotion;
    private ArticleInsider articleInsider = null;
    
    private Element element;
    private String mark;
    private String model;
    private String type;

    @Override
    public String toString() {
        return "Nazwa: " + getName() + "\n"
        		+ "Rok: " + String.valueOf(getYear()) + "\n"
        		+ "Cena: " + getFormattedStringBruttoMoney() + "\n"
        		+ "Link: " + getLink() + "\n"
        		+ "Promowane ogloszenie: " + (anyPromotion ? "Tak" : "Nie") + "\n"
        		+ "exchangeRatio: " + String.valueOf(exchangeRatio) + "\n"
        		+ "currencyName: " + currencyName + "\n"
        		+ "brutto: " + Boolean.toString(brutto) + "\n";
    }

    @Override
    public String getName() {
        return mark + " " + model;
    }

    @Override 
    public boolean equals(Article article) {
    	return link.equals(article.getLink());
    }
    
	@Override
	public boolean getPromotion() {
		return anyPromotion;
	}
    
    @Override
    public double getMoney() {
        return money;
    }

    @Override
    public String getModel() { return model; }

    @Override
    public int getYear() {
        return year;
    }

    @Override
    public String getLink() {
    	return link;
    }
    
    @Override
    public boolean isBrutto() {
    	return brutto;
    }
    
    @Override
    public String getCurrencyName() {
		return currencyName;
	}
    
    @Override
    public ArticleInsider getArticleInsider() throws Exception {
    	initializeArticleInsiderIfNecessary();
    	return articleInsider;
    }

    @Override
    public String getMark() {
        return mark;
    }

    @Override
    public String getType() {
        return type;
    }

    private void getMarkAndTypeFromName(String name) {
        mark = name.substring(0, name.indexOf(' '));
        findFullMarkName();
        model = name.substring(mark.length() + 1);
    }

    private void findFullMarkName() {
        List<String> markNames = ; // To use Android resources..
    }

    private void initializeArticleInsiderIfNecessary() throws Exception {
		if(articleInsider == null) {
    		articleInsider = new ArticleInsiderClass();
    		articleInsider.makeArticleInsiderFromLink(link);
    	}
	}
    
    private String getFormattedStringOriginal() {
        Locale locale = new Locale(currencyName);
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
        String moneyFormattedString = numberFormat.format(money);
        moneyFormattedString = moneyFormattedString.replace(",00", "");
        return moneyFormattedString;
    }
    
    private String getFormattedStringBruttoMoney() {
        Locale polish = new Locale("PL", "pl");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(polish);
        String moneyFormattedString = numberFormat.format(getBruttoMoney());
        moneyFormattedString = moneyFormattedString.replace(",00", "");
        return moneyFormattedString;
    }

    @Override
    public int getBruttoMoney() {
    	double moneyBrutto = getBrutto();
    	int moneyBruttoPLN = (int)(moneyBrutto * exchangeRatio + 0.5);
    	return moneyBruttoPLN;
    }
    
    private double getBrutto() {
    	if(isBrutto()) {
    		return money;
    	}
    	else {
    		return (double)money * 1.23;
    	}
    }
    
    @Override
    public void makeArticleFromElement(Element element) throws JSONException {
    	this.element = element;
        parseTypeFromElement();
        parseMarkAndTypeFromElement();
        parseMoneyFromElement();
        parseCurrencyFromElement();
        parseBruttoFromElement();
        parseYearFromElement();
        parseLinkFromElement();
        parseAnyPromotion();
        initExchangeRatio();
    }

    private void parseLinkFromElement() {
		link = element.getElementsByClass("offer-title__link").first().attr("href");
	}

	private void parseYearFromElement() {
        try {
            year = Integer.parseInt(element.getElementsByAttributeValue("data-code", "year").first().text());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parseMarkAndTypeFromElement() {
        String name = element.getElementsByClass("offer-title__link").first().html();
        getMarkAndTypeFromName(name);
    }

    private void parseTypeFromElement() {
        type = element.getElementsByClass("category-choose").attr("title").toLowerCase();
    }

    private void parseMoneyFromElement() {
        try {
            String stringWithoutWhiteSpaces = getStringWithoutWhiteSpaces(element.getElementsByClass("offer-price__number").first().html().split("<")[0]);
            stringToDouble(stringWithoutWhiteSpaces);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	private void stringToDouble(String stringWithoutWhiteSpaces) throws ParseException {
		NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
		Number number = format.parse(stringWithoutWhiteSpaces);
		money = number.doubleValue();
	}
  
    private void parseCurrencyFromElement() {
    	currencyName = element.getElementsByClass("offer-price__currency").first().html();
    }
    
    private void parseBruttoFromElement() {
    	String offerPriceDetails = element.getElementsByClass("offer-price__details").first().html();
    	brutto = offerPriceDetails.contains("Brutto");
    }
    
    private void parseAnyPromotion() {
    	String articleClass = element.className();
    	anyPromotion = articleClass.contains("promoted");
    }
    
    private void initExchangeRatio() throws JSONException {
    	ExchangeRates exchangeRates = ExchangeRatesClass.getInstance();
    	exchangeRatio = exchangeRates.getExchangeRateToPLNFrom(currencyName);
    }
    
    @Override
    public boolean isOk() {
    	return exchangeRatio != 0;
    }
    
    private String getStringWithoutWhiteSpaces(String str) {
        str = str.replaceAll("\\s+","");
        return str;
    }
}
