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

    public static JsonArray getArticleInfo(Site site, String boardName, int pageNum) {
        if (site.getSsoEnabled()) {
            SiteRequestController.requestSSO(site.getSsoUrl(), site.getBaseUrl());
        }

        String url = site.getBoardUrl(boardName, pageNum);

        String response = SiteRequestController.sendGet(url, site.getEncodeType());
        if (response == null) {
            Log.e(TAG, "Error get " + url);
            return null;
        } else if (site.getParseType().equals("json"))
            return parseData(response);
        else if (site.getParseType().equals("html")) {
            return parseHtml(url, site.getId(), response);
        }
         return null;
    }

    private static JsonArray parseData(String jsonString) {
        JsonParser parser = new JsonParser();
        JsonObject element = parser.parse(jsonString).getAsJsonObject();
        JsonArray data = element.get("data").getAsJsonArray();

        return data;
    }

    private static JsonArray parseHtml(String url, String siteName, String response) {
        Document doc = Jsoup.parse(response);
        JsonArray parentJsonObject = new JsonArray();

        ParseRule rule = MapDataParser.getSiteRule(siteName);
        Site site = MapDataParser.getSite(siteName);

        if (rule == null) {
            Log.e(TAG, siteName + " rule is not exists");
        }

        Elements tableCandidates = doc.getElementsByTag(rule.getTag());
        Elements found = null;
        for (String key : rule.getTableAttrs().keySet()) {
            found = tableCandidates.select(rule.getTableQuery(key));
        }

        if (found != null && found.size() != 0) {
            Element targetTable = found.get(rule.getTableIndex());
            Element tableBody = targetTable.getElementsByTag("tbody").first();
            if (tableBody == null || tableBody.children().size() == 0) {
//                Log.d(TAG, siteName);
            } else {
                Elements rows = getFilteredRow(tableBody, rule);

                for (Element row : rows) {
                    JsonObject jsonObject = new JsonObject();

                    // 제목
                    String title = parseMeta(row, rule.getTitleMeta());
                    if (title == null) {
                        Log.e(TAG,String.format("Parsing %s  failed. (%s)\n%s", siteName, url, tableBody.toString()));

                        break;
                    }
                    jsonObject.addProperty("TITLE", title);

                    // 작성자
                    String author = (rule.getAuthorMeta() == null) ? site.getName() : parseMeta(row, rule.getAuthorMeta());
                    if (author == null){
                        Log.e(TAG,String.format("Parsing %s  failed. (%s)\n%s", siteName, url, tableBody.toString()));
                        break;
                    }
                    jsonObject.addProperty("NAME", site.getName() + " - " + author);

                    long val = 0;
                    if (rule.getDateMeta() != null) {
                        // 날짜
                        try {
                            SimpleDateFormat sdf = new SimpleDateFormat(rule.getDateMeta().getEtc());
                            val = sdf.parse(parseMeta(row, rule.getDateMeta())).getTime();
                        } catch (ParseException e) {
//                            e.printStackTrace();
                            break;
                        }
                    }
                    jsonObject.addProperty("REGDATE", val);

                    // 게시물 링크
                    String link;
                    String parsedLink = parseLink(row, rule.getTitleMeta());
                    if (parsedLink == null) break;
                    if (isRequired(parsedLink)) {
                        link = makeLinkForContent(url) + parsedLink;
                    } else {
                        link = url + parsedLink;
                    }
                    jsonObject.addProperty("LINK", link);

                    // 게시물 내용
//                    String content = extractContent(site, link, rule);
                    jsonObject.addProperty("CONTENT", "게시물 내용은 읽어오지 않습니다");

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
        if (meta.getTdIndex() >= row.children().size()) {
            Log.e(TAG, String.format("Table Data number is smaller than index(%d/%d):\n%s", meta.getTdIndex(), row.children().size(), row.toString()));
            return null;
        }
        Element td = row.child(meta.getTdIndex());
        for (String childTag : meta.getHierarchy()) {
            td = td.tagName(childTag);
        }
        return td.text();
    }

    private static String parseLink(Element row, ParseRule.Meta meta) {
        if (meta.getTdIndex() >= row.children().size()) {
            Log.e(TAG, String.format("Table Data number is smaller than index(%d/%d):\n%s", meta.getTdIndex(), row.children().size(), row.toString()));
            return null;
        }
        Element td = row.child(meta.getTdIndex());
        for (String childTag : meta.getHierarchy()) {
            td = td.tagName(childTag);
        }

        for (Element el : td.children()) {
            if (el.hasAttr("href")) {
                return el.attr("href");
            }
        }
        return null;
    }

    private static boolean isRequired(String parsedLink) {
        return parsedLink.getBytes()[0] == '/';
    }

    private static String makeLinkForContent(String parentLink) {
        String result = "";
        String[] splitList = parentLink.split("/");
        for (int i = 0; i < splitList.length - 1; ++i) {
            result += splitList[i] + "/";
        }
        return result;
    }

    final static String NOT_READY_MSG = "게시물 내용 불러오기 실패.";

    public static String extractContent(Site site, String url, ParseRule rule) {
        if (site.getSsoEnabled()) {
            SiteRequestController.requestSSO(site.getBaseUrl(), site.getEncodeType());
        }
        try {
            String response = SiteRequestController.sendGet(url, site.getEncodeType());
            Document doc = Jsoup.parse(response);

            Elements tdCandidates = doc.getElementsByTag(rule.getContentTag());
            Elements tds = null;
            for (String key : rule.getProperties().keySet()) {
                tds = tdCandidates.select(rule.getContentQuery(key, rule.getContentParseType()));
            }

            if (tds.size() == 1) {
                return tds.first().text();
            } else {
                Log.e(TAG, String.format("Content parsing error:  %d / %d tds (%s)", tds.size(), tdCandidates.size(), url));
                for (Element td : tds) {
                    Log.e(TAG, td.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return NOT_READY_MSG;
    }
}
