
package tong.cau.com.cautong;


import android.app.Activity;

import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.List;

import tong.cau.com.cautong.model.MyDate;
import tong.cau.com.cautong.model.Site;
import tong.cau.com.cautong.model.WindowInfo;
import tong.cau.com.cautong.utility.BoardMapper;

//찾은 정보들을 다 갖고있을 클래스
//단 하나만 존재해야 하므로 싱글턴패턴 사용
public class FoundInfoCollector {
    public static final int INITIAL_WINDOW_SIZE = 50;
    private static ArrayList<WindowInfo> list;
    private final String TAG = "FoundInfoCollector";
    private static FoundInfoCollector instance = null;

    public static FoundInfoCollector getInstance() {
        if (instance == null) {
            instance = new FoundInfoCollector();
            list = new ArrayList<>();
            for (int i = 0; i < INITIAL_WINDOW_SIZE; ++i) {
                list.add(new WindowInfo());
            }
        }
        return instance;
    }

    private FoundInfoCollector() {

    }

    public WindowInfo getInfo(int index) throws NullPointerException {
        if (index < getInfoSize())
            return list.get(index);
        else return null;
    }

    public void findInfo(Site site, String boardName) {
        JsonArray dataList = BoardMapper.getArticleInfo(site, boardName);

        if (dataList != null) {
            int minSize = Math.min(dataList.size(), getInfoSize());
            minSize = 50; //버퍼 오버플로우 문제로 임시 설정
            for (int i = 0; i < minSize; ++i) {
                WindowInfo wf = new WindowInfo();
                wf.init(WindowInfo.Logo.caucse,
                        dataList.get(i).getAsJsonObject().get("title").getAsString(),
                        dataList.get(i).getAsJsonObject().get("content").getAsString(),
                        "https://www.cau.ac.kr",
                        new MyDate(dataList.get(i).getAsJsonObject().get("regdate").getAsLong()),
                        dataList.get(i).getAsJsonObject().get("name").getAsString());
                list.add(wf);
                MainActivity.instance.addWindow(wf);
            }
        }
    }


    public int getInfoSize() {
        return list.size();
    }

}

