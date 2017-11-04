package tong.cau.com.cautong.utility;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import tong.cau.com.cautong.MainActivity;
import tong.cau.com.cautong.R;
import tong.cau.com.cautong.model.ParseRule;
import tong.cau.com.cautong.model.Site;

/**
 * Created by Velmont on 2017-10-30.
 */

public class MapDataParser {
    private static final String TAG = "MapDataParser";

    private static Context context = MainActivity.instance;

    private static Gson gson = new Gson();
    private static JsonParser parser = new JsonParser();

    private static Map<String, Site> map = null;
    private static Map<String, ParseRule> siteRuleMap = null;

    public static void setContext(Context context) {
        MapDataParser.context = context;
    }

    public static Map<String, ParseRule> getSiteRuleMap() {
        if (siteRuleMap == null) {
            siteRuleMap = parseSiteRule();
        }
        return siteRuleMap;
    }

    public static Map<String, ParseRule> parseSiteRule() {
        InputStream is = context.getResources().openRawResource(R.raw.site_rule);
        String jsonString = streamToString(is);

        Type type = new TypeToken<Map<String, ParseRule>>() {
        }.getType();
        Map<String, ParseRule> ruleList = gson.fromJson(jsonString, type);
        return ruleList;
    }

    public static ParseRule getSiteRule(String siteName) {
        return getSiteRuleMap().get(siteName);
    }

    public static Site getSite(String siteName) {
        return getSiteMap().get(siteName);
    }

    public static Map<String, Site> getSiteMap() {
        if (map == null) {
            map = parseSiteMap();
        }
        return map;
    }

    private static Map<String, Site> parseSiteMap() {
        InputStream is = context.getResources().openRawResource(R.raw.site);
        String jsonString = streamToString(is);

        Site[] siteList = gson.fromJson(jsonString, Site[].class);

        map = new HashMap<>();
        for (Site site : siteList) {
            map.put(site.getName(), site);
        }

        return map;
    }

    private static FavoriteItem[] parseFavorite() {
        InputStream is = context.getResources().openRawResource(R.raw.favorite);

        String jsonString = streamToString(is);

        return gson.fromJson(jsonString, FavoriteItem[].class);
    }

    private static String streamToString(InputStream is) {
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return writer.toString();
    }

    private class FavoriteItem {
        String siteName;
        String cagegoryName;
    }
}
