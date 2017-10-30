
package tong.cau.com.cautong;


import android.app.Activity;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

//찾은 정보들을 다 갖고있을 클래스
//단 하나만 존재해야 하므로 싱글턴패턴 사용
public class FoundInfoCollector {
    private static FoundInfoCollector instance = null;

    public static FoundInfoCollector getInstance(Activity activity) {
        myActivity = activity;
        if (instance == null) {
            instance = new FoundInfoCollector();
            list = new ArrayList<>();
            for (int i = 0; i < INITIAL_WINDOW_SIZE; ++i) {
                list.add(new WindowInfo(activity));
            }
        }
        return instance;
    }

    private static Activity myActivity;

    public static final int INITIAL_WINDOW_SIZE = 50;
    private static ArrayList<WindowInfo> list;

    private final String TAG = "FoundInfoCollector";
    private final String CAU_URL = "https://www.cau.ac.kr:443";
    private final String SSO_URL = "https://sso2.cau.ac.kr/SSO/AuthWeb/NACookieManage.aspx";
    private final String CAU_NOTICE = "https://www.cau.ac.kr:443/04_ulife/causquare/notice/notice_list.php?bbsId=cau_notice";
    private final String CAU_NOTICE_BBS = "/ajax/bbs_list.php?isNoti=Y&pageSize=50&";
    private final String ENCODE = "EUC-KR";
    private final String USER_AGENT = "Mozilla/5.0";

    private String cookies = "";
    private boolean session = false;

    private FoundInfoCollector() {

    }

    public WindowInfo getInfo(int index) throws NullPointerException {
        if (index < getInfoSize())
            return list.get(index);
        else return null;
    }

    public int getInfoSize() {
        return list.size();
    }

    public void findInfo() {
        JsonArray dataList = getCAUNotice();

        if (dataList != null) {
            int minSize = Math.min(dataList.size(), getInfoSize());
            for (int i = 0; i < minSize; ++i) {
                getInfo(i).setTitle(myActivity, dataList.get(i).getAsJsonObject().get("TITLE").getAsString());
                getInfo(i).setAuthor(myActivity, dataList.get(i).getAsJsonObject().get("NAME").getAsString());
//                getInfo(i).setLink();
                Log.d(TAG, "REGDATE " + dataList.get(i).getAsJsonObject().get("REGDATE").getAsLong());
                //getInfo(i).setDate(myActivity, new MyDate(dataList.get(i).getAsJsonObject().get("REGDATE").getAsLong()));
            }
        }
    }

    public JsonArray getCAUNotice() {
        requestSSO(CAU_NOTICE);

        try {
            String response = sendGet(CAU_URL + CAU_NOTICE_BBS);
            return parseData(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void saveCookie(HttpURLConnection conn) {
        Map<String, List<String>> imap = conn.getHeaderFields();
        if (imap.containsKey("Set-Cookie")) {
            List<String> lString = imap.get("Set-Cookie");
            for (int i = 0; i < lString.size(); i++) {
                cookies += lString.get(i);
            }
            Log.e("zdg", cookies);
            session = true;
        } else {
            session = false;
        }
    }

    private void requestSSO(String url) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("retURL", url);
        parameters.put("ssosite", "www.cau.ac.kr");
        parameters.put("AUTHERR", "0");
        parameters.put("mode", "set");
        parameters.put("NCAUPOLICYNUM", "67");

        try {
            sendPost(SSO_URL, parameters, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // HTTP GET request
    private String sendGet(String url) throws Exception {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Cookie", cookies);

        int responseCode = con.getResponseCode();
        Log.d(TAG, "\nSending 'GET' request to URL : " + url);
        Log.d(TAG, "Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream(), "EUC-KR"));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result

//        Log.d(TAG, unicodeParser(response.toString()));
        return response.toString();
    }

    // HTTP POST request
    private void sendPost(String url, Map<String, String> parameters, boolean isSaveCookie) throws Exception {

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
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + parameters.toString());
        System.out.println("Response Code : " + responseCode);

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

    private String getParamsString(Map<String, String> params) throws UnsupportedEncodingException {
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

    private JsonArray parseData(String jsonString) {
        JsonParser parser = new JsonParser();
        JsonObject element = parser.parse(jsonString).getAsJsonObject();
        JsonArray data = element.get("data").getAsJsonArray();

        return data;
    }
}

