package tong.cau.com.cautong.utility;

import android.content.res.Resources;
import android.util.Log;

import com.google.gson.Gson;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tong.cau.com.cautong.R;
import tong.cau.com.cautong.model.Board;
import tong.cau.com.cautong.model.Site;

/**
 * Created by Velmont on 2017-10-30.
 */

public class SiteXmlParser {
    private static final String TAG = "SiteParser";

    private static Gson gson = new Gson();
    private static Map<String, Site> map = null;

    public static Map<String, Site> getSiteMap(Resources resources) {
        if (map == null) {
            map = parseSiteMap(resources);
        }

        return map;
    }

    public static Map<String, Site> parseSiteMap(Resources resources) {
        InputStream is = resources.openRawResource(R.raw.site);

        String jsonString = streamToString(is);


        Site[] siteList = gson.fromJson(jsonString, Site[].class);

        map = new HashMap<>();
        for (Site site : siteList) {
            map.put(site.getName(), site);
        }

        return map;
    }

    public static FavoriteItem[] parseFavorite(Resources resources) {
        InputStream is = resources.openRawResource(R.raw.favorite);

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
