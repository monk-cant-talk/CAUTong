package tong.cau.com.cautong.utility;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import tong.cau.com.cautong.SiteRequestController;
import tong.cau.com.cautong.model.Site;

/**
 * Created by Velmont on 2017-10-30.
 */

public class BoardMapper {

    private static final String TAG = "BoardMapper";

    public static JsonArray getArticleInfo(Site site, String boardName) {
        Log.d(TAG, "board: " + boardName);
        if (site.getSsoEnabled()) {

            SiteRequestController.requestSSO(site.getBaseUrl());
        }
        try {
            Log.d(TAG, site.getBoardUrl(boardName));
            Log.d(TAG, site.getEncodeType());
            String response = SiteRequestController.sendGet(site.getBoardUrl(boardName), site.getEncodeType());
            Log.d("BoardMapper", response);
            Log.d("BoardMapper", "FLAG");
            if (site.getParseType().equals("json"))
                return parseData(response);
            else {
                return htmlToJson(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static JsonArray htmlToJson(String response) {
        Log.d("BoardMapper", "htmlToJsonStart");
        Log.d("BoardMapper", response);
        Document doc = Jsoup.parse(response);
        Element content = doc.getElementById("cont_right");
        Element table = content.select("table").get(2);
        Element tbody = table.select("tbody").get(0);
        Elements tr = tbody.getElementsByAttributeValue("width", "718");
        tr = tr.select("tbody");
        tr = tr.select("tr");
        Elements tr2 = new Elements();
        int count = 0;
        for (int i = 0; i < tr.size(); i = i + 2) {
            tr2.add(tr.get(i));
            count++;
        }
        Log.d("whywhy", tr2.text());
        Log.d("wer", Integer.toString(count));
        JsonArray parentJsonObject = new JsonArray();
        for (Element row : tr2) {
            Log.d("wer", "rnenene");
            Log.d("wer", row.text());
            JsonObject jsonObject = new JsonObject();
            Elements tds = row.select("td");
            String number = tds.get(0).text();
            String title = tds.get(2).getElementsByTag("a").get(0).text();
            String author = tds.get(4).text();
            String date = tds.get(6).text();
            Log.d("whywhy", date);
            jsonObject.addProperty("number", number);
            jsonObject.addProperty("TITLE", title);
            jsonObject.addProperty("NAME", author);
            jsonObject.addProperty("REGDATE", 20171102);
            //jsonObject.addProperty("REGDATE",date);
            parentJsonObject.add(jsonObject);
            Gson gson = new Gson();
            String print = gson.toJson(parentJsonObject);
            Log.d("BoardMapper2", print);
        }

        return parentJsonObject;

    }

    private static JsonArray parseData(String jsonString) {
        JsonParser parser = new JsonParser();
        JsonObject element = parser.parse(jsonString).getAsJsonObject();
        JsonArray data = element.get("data").getAsJsonArray();

        return data;
    }

}
