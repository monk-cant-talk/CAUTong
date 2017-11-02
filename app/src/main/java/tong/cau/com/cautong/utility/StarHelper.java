package tong.cau.com.cautong.utility;

import android.app.Activity;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import tong.cau.com.cautong.PlayerPrefs;
import tong.cau.com.cautong.model.WindowInfo;

/**
 * Created by Velmont on 2017-10-31.
 */

public class StarHelper {
    // 별표하기
    public static void starWindowInfo(Activity activity, WindowInfo windowInfo){
        Gson gson = new Gson();
        List<String> idList;
        if(PlayerPrefs.getInstance(activity).hasKey("star")) {
            String starredWindowInfoList = PlayerPrefs.getInstance(activity).getString("star");
            idList = gson.fromJson(starredWindowInfoList, new TypeToken<List<String>>() {
            }.getType());
        }else{
            idList = new ArrayList<>();
        }
        idList.add(gson.toJson(windowInfo));
        PlayerPrefs.getInstance(activity).setString("star", gson.toJson(idList));
        PlayerPrefs.getInstance(activity).save();
    }

    // 별표된 것 가져오기
    public static List<WindowInfo> getStarredWindowInfo(Activity activity){
        String starredWindowInfoList = PlayerPrefs.getInstance(activity).getString("star");
        Gson gson = new Gson();
        List<WindowInfo> windowInfoList = gson.fromJson(starredWindowInfoList, new TypeToken<List<WindowInfo>>(){}.getType());
        return windowInfoList;
    }
}
