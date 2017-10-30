
package tong.cau.com.cautong;


import android.app.Activity;
import android.util.Log;

import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.List;

import tong.cau.com.cautong.model.Site;

//찾은 정보들을 다 갖고있을 클래스
//단 하나만 존재해야 하므로 싱글턴패턴 사용
public class FoundInfoCollector {
    private static Activity myActivity;
    public static final int INITIAL_WINDOW_SIZE = 50;
    private static ArrayList<WindowInfo> list;
    private final String TAG = "FoundInfoCollector";
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
    private FoundInfoCollector() {

    }

    public WindowInfo getInfo(int index) throws NullPointerException {
        if (index < getInfoSize())
            return list.get(index);
        else return null;
    }

    public void findInfo(List<Site> siteList) {
        for(Site site : siteList){
            JsonArray dataList = BoardMapper.getArticleInfo(site);

            if (dataList != null) {
                int minSize = Math.min(dataList.size(), getInfoSize());
                for (int i = 0; i < minSize; ++i) {
                    getInfo(i).setTitle(myActivity, dataList.get(i).getAsJsonObject().get("TITLE").getAsString());
                    getInfo(i).setAuthor(myActivity, dataList.get(i).getAsJsonObject().get("NAME").getAsString());
//                getInfo(i).setLink();
                    Log.d(TAG, "REGDATE " + dataList.get(i).getAsJsonObject().get("REGDATE").getAsLong());
                    getInfo(i).setDate(myActivity, new MyDate(dataList.get(i).getAsJsonObject().get("REGDATE").getAsLong()));
                }
            }
        }
    }


    public int getInfoSize() {
        return list.size();
    }

}

