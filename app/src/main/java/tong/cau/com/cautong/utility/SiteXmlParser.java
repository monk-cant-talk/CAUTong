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

    public static Map<String, Site> parseSiteMap(Resources resources) {
        InputStream is = resources.openRawResource(R.raw.site);

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

        String jsonString = writer.toString();
        Gson gson = new Gson();
        Site[] siteList = gson.fromJson(jsonString, Site[].class);

        HashMap<String, Site> map = new HashMap<>();
        for (Site site : siteList) {
            map.put(site.getName(), site);
            for (Board board : site.getBoardList()) {
            }
        }

        return map;
    }
}
