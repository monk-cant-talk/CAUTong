package tong.cau.com.cautong;


import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

//찾은 정보들을 다 갖고있을 클래스
//단 하나만 존재해야 하므로 싱글턴패턴 사용
public class FoundInfoCollector {

    private ArrayList<WindowInfo> list;

    private static final String TAG = "FoundInfoCollector";
    private static final String CAU_URL = "https://www.cau.ac.kr:443";
    private static final String SSO_URL = "https://sso2.cau.ac.kr/SSO/AuthWeb/NACookieManage.aspx";
    private static final String CAU_NOTICE = "https://www.cau.ac.kr:443/04_ulife/causquare/notice/notice_list.php?bbsId=cau_notice";
    private static final String CAU_NOTICE_BBS = "/ajax/bbs_list.php?isNoti=Y&pageSize=50&";
    private static final String ENCODE = "EUC-KR";
    private static final String USER_AGENT = "Mozilla/5.0";

    private static String cookies = "";
    private static boolean session = false;

    private static FoundInfoCollector instance = null;

    public static FoundInfoCollector getInstance() {
        if (instance == null) {
            instance = new FoundInfoCollector();
        }
        return instance;
    }

    private FoundInfoCollector() {
        list = new ArrayList<>();
        findInfo();
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
        for (int i = 0; i < 2000; i++) {
            list.add(new WindowInfo());
        }
        list.add(new WindowInfo());
        list.add(new WindowInfo());
        list.add(new WindowInfo());
        list.add(new WindowInfo());
        // TODO: 2017-10-25 여기에서 list 에 정보들을 채워 넣는다.
    }

    private static void saveCookie(HttpURLConnection conn) {
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

    public static void getCAUNotice() {
        requestSSOCookies(CAU_NOTICE);

        try {
            sendGet(CAU_URL + CAU_NOTICE_BBS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void requestSSOCookies(String url) {

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
    private static void sendGet(String url) throws Exception {
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

        Log.d(TAG, unicodeParser(response.toString()));

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

        con.setDoInput(true);
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

        //print result
        System.out.println(response.toString());
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

    private static String unicodeParser(String str) {
//        return str.replaceAll("\\\\u.{4}", "$1");
        return str;
    }
}

