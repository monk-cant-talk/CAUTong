package tong.cau.com.cautong;


import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;

//찾은 정보들을 다 갖고있을 클래스
//단 하나만 존재해야 하므로 싱글턴패턴 사용
public class FoundInfoCollector {

    private ArrayList<WindowInfo> list;

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

    static final String TAG = "FoundInfoCollector";
    static final String CAU_URL = "https://www.cau.ac.kr:443";
    static final String SSO_URL = "https://sso2.cau.ac.kr/SSO/AuthWeb/NACookieManage.aspx";
    static final String CAU_NOTICE = "https://www.cau.ac.kr:443/04_ulife/causquare/notice/notice_list.php?bbsId=cau_notice";
    static final String ENCODE = "EUC-KR";

    private static String cookies = "";
    private static boolean session = false;

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
        URL url = null;
        try {
            url = new URL(SSO_URL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection con = null;
        try {
            con = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            con.setRequestMethod("POST");
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
        con.setConnectTimeout(5000);
        con.setReadTimeout(5000);
        con.setDoInput(true);
        con.setDoOutput(true);

        Map<String, String> parameters = new HashMap<>();
        parameters.put("retURL", CAU_NOTICE);
        parameters.put("ssosite", "www.cau.ac.kr");
        parameters.put("AUTHERR", "0");
        parameters.put("mode", "set");
        parameters.put("NCAUPOLICYNUM", "67");

        DataOutputStream out = null;
        try {
            out = new DataOutputStream(con.getOutputStream());
            out.writeBytes(getParamsString(parameters));
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String getUrl = "";
        BufferedReader in = null;
        try {
            int status = con.getResponseCode();
            in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            Log.d(TAG, content.toString());
            in.close();

            Document doc = Jsoup.parse(content.toString());
            String first = doc.head().children().first().html();
            Pattern pattern = Pattern.compile("'(.*?)'");
            Matcher matcher = pattern.matcher(first);
            if (matcher.find()) {
                Log.d(TAG, matcher.group(1));
                getUrl = matcher.group(1);
            } else {
                Log.d(TAG, "No URL.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        saveCookie(con);
        con.disconnect();

        try {
            Log.d(TAG, "Send start!");
//            sendGet(getUrl);
            sendGet(CAU_URL + "/ajax/bbs_list.php?isNoti=Y&pageSize=50&");
        } catch (Exception e) {
            e.printStackTrace();
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

    private static final String USER_AGENT = "Mozilla/5.0";

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
    private void sendPost() throws Exception {

        String url = "https://selfsolve.apple.com/wcResults.do";
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());
    }

    private static String unicodeParser(String str) {
//        return str.replaceAll("\\\\u.{4}", "$1");
        return str;
    }
}

