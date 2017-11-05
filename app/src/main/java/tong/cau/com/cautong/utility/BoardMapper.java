package tong.cau.com.cautong.utility;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import tong.cau.com.cautong.SiteRequestController;
import tong.cau.com.cautong.model.ParseRule;
import tong.cau.com.cautong.model.Site;

/**
 * Created by Velmont on 2017-10-30.
 */

public class BoardMapper {

    private static final String TAG = "BoardMapper";
    private static final int MAXIMUM_GET_RETRY = 30;

    public static JsonArray getArticleInfo(Site site, String boardName, int pageNum) {
        if (site.getSsoEnabled()) {
            SiteRequestController.requestSSO(site.getSsoUrl(), site.getBaseUrl() + site.getBbsListParams());
        }

        String response = SiteRequestController.sendGet(site.getBoardUrl(boardName, pageNum), site.getEncodeType());
        if (response == null) {
            Log.e(TAG, "Error get " + site.getBoardUrl(boardName, pageNum));
            return null;
        }
        else if (site.getParseType().equals("json"))
            return parseData(response);
        else {
            return parseHtml(site.getId(), response);
        }
    }

    private static JsonArray parseData(String jsonString) {
        JsonParser parser = new JsonParser();
        JsonObject element = parser.parse(jsonString).getAsJsonObject();
        JsonArray data = element.get("data").getAsJsonArray();

        return data;
    }

    private static JsonArray parseHtml(String siteName, String response) {
        Document doc = Jsoup.parse(response);
        JsonArray parentJsonObject = new JsonArray();

        ParseRule rule = MapDataParser.getSiteRule(siteName);
        Site site = MapDataParser.getSite(siteName);

        Elements tableCandidates = doc.getElementsByTag(rule.getTag());
        Elements found = null;
        for (String key : rule.getTableAttrs().keySet()) {
            found = tableCandidates.select(rule.getTableQuery(key));
        }

        if (found != null && found.size() != 0) {
            Element targetTable = found.first();
            Element tableBody = targetTable.getElementsByTag("tbody").first();
            if (tableBody == null || tableBody.children().size() == 0) {
//                Log.d(TAG, siteName);
            } else {
                Elements rows = getFilteredRow(tableBody, rule);

                for (Element row : rows) {
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("TITLE", parseMeta(row, rule.getTitleMeta()));
                    String author = (rule.getAuthorMeta() == null) ? site.getName() : parseMeta(row, rule.getAuthorMeta());
                    jsonObject.addProperty("NAME", site.getName() + " - " + author);
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat(rule.getDateMeta().getEtc());
                        long val = sdf.parse(parseMeta(row, rule.getDateMeta())).getTime();
                        jsonObject.addProperty("REGDATE", val);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    jsonObject.addProperty("LINK", parseLink(row, rule.getTitleMeta()));
                    final String testLink = "http://ie.cau.ac.kr/20141101/sub07/sub03_view.php?bbsIdx=41&searchBbsCategoryNo=&searchKind=&searchStr=?bbsIdx=41&gotoPage=1&searchBbsCategoryNo=&searchKind=&searchStr=";
                    jsonObject.addProperty("CONTENT", extractContent(site, testLink, rule));

                    parentJsonObject.add(jsonObject);
                }
            }
        }

        return parentJsonObject;
    }

    private static Elements getFilteredRow(Element tableBody, ParseRule rule) {
        Elements rows = new Elements();

        int adder = (rule.isRowSpaced()) ? 2 : 1;
        for (int i = rule.getFirstRowIndex(); i < tableBody.children().size(); i += adder) {
            rows.add(tableBody.child(i));
        }

        return rows;
    }

    private static String parseMeta(Element row, ParseRule.Meta meta) {
        Element td = row.child(meta.getTdIndex());
        for (String childTag : meta.getHierarchy()) {
            td = td.tagName(childTag);
        }
        return td.text();
    }

    private static String parseLink(Element row, ParseRule.Meta meta) {
        Element td = row.child(meta.getTdIndex());
        for (String childTag : meta.getHierarchy()) {
            td = td.tagName(childTag);
        }
        return td.attr("href");
    }

    final static String NOT_READY_MSG = "준비중입니다.";

    public static String extractContent(Site site, String url, ParseRule rule) {
        if (site.getSsoEnabled()) {
            SiteRequestController.requestSSO(site.getBaseUrl(), site.getEncodeType());
        }
        try {
            String response = SiteRequestController.sendGet(url, site.getEncodeType());
            if (site.getParseType().equals("json"))
                return null;
            else {
                Document doc = Jsoup.parse(response);
                Element content = doc.getElementById(rule.getContentId());
                return (content != null) ? content.text() : NOT_READY_MSG;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return NOT_READY_MSG;
    }
}
