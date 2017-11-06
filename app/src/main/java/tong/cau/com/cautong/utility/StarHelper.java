package tong.cau.com.cautong.utility;

import android.app.Activity;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import tong.cau.com.cautong.PlayerPrefs;
import tong.cau.com.cautong.model.WindowInfo;

public class StarHelper {
    // 별표하기
    public static void starWindowInfo(WindowInfo windowInfo){
        Gson gson = new Gson();
        WindowInfo[] idList;
        if(PlayerPrefs.getInstance().hasKey("star")) {
            String starredWindowInfoListStr = PlayerPrefs.getInstance().getString("star");
            WindowInfo[] buf = gson.fromJson(starredWindowInfoListStr, WindowInfo[].class);
            idList = new WindowInfo[buf.length + 1];
            for(int i = 0 ; i < buf.length ; i ++){
                idList[i] = buf[i];
            }
            idList[buf.length] = windowInfo;
        }else{
            idList = new WindowInfo[1];
            idList[0] = windowInfo;
        }

        String str = gson.toJson(idList);
        PlayerPrefs.getInstance().setString("star", str);
        PlayerPrefs.getInstance().save();
    }

    // 별표된 것 가져오기
    public static List<WindowInfo> getStarredWindowInfo(){
        String starredWindowInfoList = PlayerPrefs.getInstance().getString("star");
        List<WindowInfo> windowInfoList = new Gson().fromJson(starredWindowInfoList, new TypeToken<List<WindowInfo>>(){}.getType());
        return windowInfoList;
    }

    public static void removedStarredWindowInfo(WindowInfo windowInfo){
        List<WindowInfo> windowInfoList = getStarredWindowInfo();
        for(WindowInfo searchedWindowInfo : windowInfoList){
            Log.d("StarHelper", windowInfo.getTitle());
            Log.d("StarHelper", searchedWindowInfo.getTitle());

            Log.d("StarHelper", windowInfo.getDate().toString());
            Log.d("StarHelper", searchedWindowInfo.getDate().toString());
            if(windowInfo.getTitle().equals(searchedWindowInfo.getTitle()) && windowInfo.getDate().toString().equals(searchedWindowInfo.getDate().toString())){
                windowInfoList.remove(searchedWindowInfo);
                break;
            }
        }
        String str = new Gson().toJson(windowInfoList);
        PlayerPrefs.getInstance().setString("star", str);
        PlayerPrefs.getInstance().save();
    }

}
