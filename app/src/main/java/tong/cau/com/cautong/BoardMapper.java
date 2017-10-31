package tong.cau.com.cautong;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URLDecoder;

import tong.cau.com.cautong.model.Site;

/**
 * Created by Velmont on 2017-10-30.
 */

public class BoardMapper {

    private static final String TAG = "BoardMapper";

    public static JsonArray getArticleInfo(Site site) {
        SiteRequestController.requestSSO(site.getBbsBaseUrl());

        try {
            final int testBoardId = 1;
            String response = SiteRequestController.sendGet(site.getBoardUrl(testBoardId));
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
