package tong.cau.com.cautong.utility;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import tong.cau.com.cautong.SiteRequestController;
import tong.cau.com.cautong.model.Site;

/**
 * Created by Velmont on 2017-10-30.
 */

public class BoardMapper {

    private static final String TAG = "BoardMapper";

    public static JsonArray getArticleInfo(Site site, String boardName) {
        SiteRequestController.requestSSO(site.getBbsBaseUrl());

        try {
            String response = SiteRequestController.sendGet(site.getBoardUrl(boardName));
            return parseData(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static JsonArray parseData(String jsonString) {
        JsonParser parser = new JsonParser();
        JsonObject element = parser.parse(jsonString).getAsJsonObject();
        JsonArray data = element.get("data").getAsJsonArray();

        return data;
    }

}
