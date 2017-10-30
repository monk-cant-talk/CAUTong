package tong.cau.com.cautong;

import android.content.res.Resources;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tong.cau.com.cautong.model.Site;

/**
 * Created by Velmont on 2017-10-30.
 */

public class SiteXmlParser {
    public static Map<String, Site> parseSiteMap(Resources resources) {
        InputStream is = resources.openRawResource(R.raw.site);

        Map<String, Site> siteMap = new HashMap<>();
        // xmlPullParser
        Log.d("parserTest", "testStart");
        try {

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new InputStreamReader(is, "UTF-8"));
            int eventType = parser.getEventType();
            Site site = null;
            String siteName = null;
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        String startTag = parser.getName();
                        if (startTag.equals("site")) {
                            site = new Site();
                            siteName = parser.getAttributeValue(0);
                            Log.d("parserTest", siteName);
                        }
                        if(startTag.equals("site_url")){
                            site.setSiteUrl(parser.nextText());
                        }
                        if (startTag.equals("sso_url")) {
                            site.setSsoUrl(parser.nextText());
                        }
                        if (startTag.equals("notice_url")) {
                            site.setNoticeUrl(parser.nextText());
                        }
                        if (startTag.equals("notice_bbs_url")) {
                            site.setNoticeBbsUrl(parser.nextText());
                        }
                        if(startTag.equals("page")){
                            site.getBbsInfo().setPage(parser.nextText());
                        }
                        if(startTag.equals("author")){
                            site.getBbsInfo().setAuthor(parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        String endTag = parser.getName();
                        if (endTag.equals("site")) {
                            siteMap.put(siteName, site);
                        }
                        break;
                }
                eventType = parser.next();
            }


        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return siteMap;
    }
}
