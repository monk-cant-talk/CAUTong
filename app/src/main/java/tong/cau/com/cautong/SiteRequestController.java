package tong.cau.com.cautong;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Velmont on 2017-10-30.
 */

public class SiteRequestController {
    private static final String TAG = "SiteRequestController";
    private static final String ENCODE = "EUC-KR";
    private static final String USER_AGENT = "Mozilla/5.0";
    private static String cookies = "";


    public static void requestSSO(String ssoUrl, String requestUrl) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("retURL", requestUrl);
        parameters.put("ssosite", "www.cau.ac.kr");
        parameters.put("AUTHERR", "0");
        parameters.put("mode", "set");

        try {
            sendPost(ssoUrl, parameters, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // HTTP GET request
    public static String sendGet(String url, String encodeType) throws Exception {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Cookie", cookies);
        con.addRequestProperty("Connection", "keep-alive");
        con.setRequestProperty("Keep-Alive", "header");

        int responseCode = con.getResponseCode();

        if (url == null) {
            throw new NullPointerException("url is null");
        }
        if (encodeType == null) {
            throw new NullPointerException("encode Type is null : " + url);
        }
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream(), encodeType));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }

        in.close();

        //print result
        return response.toString();
    }

    // HTTP POST request
    private static void sendPost(String url, Map<String, String> parameters, boolean isSaveCookie) throws Exception {

        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        // set timeout
        con.setConnectTimeout(5000);
        con.setReadTimeout(5000);

        // add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);

        // Send post request
        con.setDoOutput(true);
        DataOutputStream out = new DataOutputStream(con.getOutputStream());
        out.writeBytes(getParamsString(parameters));
        out.flush();
        out.close();

        int responseCode = con.getResponseCode();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        if (isSaveCookie) {
            saveCookie(con);
        }
    }

    private static void saveCookie(HttpURLConnection conn) {
        Map<String, List<String>> imap = conn.getHeaderFields();
        if (imap.containsKey("Set-Cookie")) {
            List<String> lString = imap.get("Set-Cookie");
            for (int i = 0; i < lString.size(); i++) {
                cookies += lString.get(i);
            }
        }
    }

    private static String getParamsString(Map<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            result.append(URLEncoder.encode(entry.getKey(), ENCODE));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), ENCODE));
            result.append("&");
        }

        String resultString = result.toString();
        return resultString.length() > 0 ? resultString.substring(0, resultString.length() - 1) : resultString;
    }

    private String unicodeParser(String str) {
//        return str.replaceAll("\\\\u.{4}", "$1");
        return str;
    }
}
