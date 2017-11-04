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

import java.sql.Date;
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

    public static JsonArray getArticleInfo(Site site, String boardName) {
        if (site.getSsoEnabled()) {

            SiteRequestController.requestSSO(site.getBaseUrl());
        }
        try {
            String response = SiteRequestController.sendGet(site.getBoardUrl(boardName), site.getEncodeType());
            if (site.getParseType().equals("json"))
                return parseData(response);
            else {
                return parseHtml(site.getName(), response);
            }
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
            Elements rows = getFilteredRow(tableBody, rule);

            for (Element row : rows) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("TITLE", parseMeta(row, rule.getTitleMeta()));
                jsonObject.addProperty("NAME", parseMeta(row, rule.getAuthorMeta()));
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat(rule.getDateMeta().getEtc());
                    long val = sdf.parse(parseMeta(row, rule.getDateMeta())).getTime();
                    jsonObject.addProperty("REGDATE", val);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                parentJsonObject.add(jsonObject);
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
}
