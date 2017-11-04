
package tong.cau.com.cautong;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import tong.cau.com.cautong.model.MyDate;
import tong.cau.com.cautong.model.Site;
import tong.cau.com.cautong.model.WindowInfo;
import tong.cau.com.cautong.utility.BoardMapper;

// 웹에서 긁어온 raw 데이터를 WindowsInfo 형태로 변환해서 반환하는 클래스
// 여러 스레드에서 동시에 불릴 수 있으므로 "정보를 가지고 있지는 않는다."
public class FoundInfoCollector {
    public static final int INITIAL_WINDOW_SIZE = 50;
    private final String TAG = "FoundInfoCollector";

    private static FoundInfoCollector instance = null;

    public static FoundInfoCollector getInstance() {
        if (instance == null) {
            instance = new FoundInfoCollector();
        }
        return instance;
    }

    public ArrayList<WindowInfo> findInfo(Site site, String boardName) {
        JsonArray dataList = BoardMapper.getArticleInfo(site, boardName);
        ArrayList<WindowInfo> list = null;

        if (dataList != null) {
            list = new ArrayList<>(dataList.size());
            for (int i = 0; i < dataList.size(); ++i) {
                list.add(new WindowInfo());
            }

            for (int i = 0; i < dataList.size(); ++i) {
                JsonObject dataObject = dataList.get(i).getAsJsonObject();
                list.get(i).init(WindowInfo.Logo.caucse,
                        dataObject.get("TITLE").getAsString(),
                        "준비중입니다",
                        "https://www.cau.ac.kr",
                        new MyDate(dataObject.get("REGDATE").getAsLong()),
                        dataObject.get("NAME").getAsString());
            }
        }

        return list;
    }
}

