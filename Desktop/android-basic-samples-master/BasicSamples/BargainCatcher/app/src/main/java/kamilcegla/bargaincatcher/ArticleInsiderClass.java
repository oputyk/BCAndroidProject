package kamilcegla.bargaincatcher;

import java.io.IOException;
import java.util.Date;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

interface ArticleInsider {
	void makeArticleInsiderFromLink(String link) throws Exception;
	String getPhoneNumber();
	Date getPublishDate();
}

public class ArticleInsiderClass implements ArticleInsider {

	Document document;
	String phoneNumber = new String();
	Date publishDate;
	
	@Override
	public void makeArticleInsiderFromLink(String link) throws Exception {
		initializeDocument(link);
		parsePhoneNumber();
		parsePublishDate();
	}

	@Override
	public String getPhoneNumber() {
		return phoneNumber;
	}

	@Override
	public Date getPublishDate() {
		return publishDate;
	}
	
	private void initializeDocument(String link) throws IOException {
		document = Jsoup.connect(link).get();
	}

	private void parsePhoneNumber() throws Exception {
		String dataId = parseDataId();
		String jsonStringWithPhoneNumber = getJsonStringWithPhoneNumberByDataId(dataId);
		parsePhoneNumberFromJsonString(jsonStringWithPhoneNumber);
	}

	private void parsePublishDate() throws Exception {
		Element offerMetaValue = document.getElementsByClass("offer-meta__value").first();
		String dateText = offerMetaValue.text();
		publishDate = StringToDateConverter.stringToDate(dateText);
	}
	
	private String parseDataId() {
		Element sellerPhoneElement = document.getElementsByClass("seller-phones__button").first();
		String dataId = sellerPhoneElement.attr("data-id");
		return dataId;
	}

	private String getJsonStringWithPhoneNumberByDataId(String dataId) throws IOException {
		OkHttpClient client = new OkHttpClient();
		
		Request request = new Request.Builder().url("https://www.otomoto.pl/ajax/misc/contact/multi_phone/" + dataId + "/0").build();
		Response response = client.newCall(request).execute();
		
		String bodyString = response.body().string();
		return bodyString;
	}

	private void parsePhoneNumberFromJsonString(String bodyString) throws Exception {
		JSONObject jsonObject = new JSONObject(bodyString);
		phoneNumber = jsonObject.getString("value");
	}
	
}
