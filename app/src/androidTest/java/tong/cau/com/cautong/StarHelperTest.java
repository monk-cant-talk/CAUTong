package tong.cau.com.cautong;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import tong.cau.com.cautong.main.MainActivity;
import tong.cau.com.cautong.model.MyDate;
import tong.cau.com.cautong.model.WindowInfo;
import tong.cau.com.cautong.utility.StarHelper;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by Velmont on 2017-10-31.
 */
@RunWith(AndroidJUnit4.class)
public class StarHelperTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);
    public MainActivity activity;

    @Before
    public void setUp() {
        PlayerPrefs.getTestInstance(activity).removeKey("star");
        PlayerPrefs.getInstance().save();
        activity = mActivityRule.getActivity();
    }

    @Test
    public void initialStarWindowInfo() {
        WindowInfo sampleWindowInfo = new WindowInfo();
        sampleWindowInfo.init(WindowInfo.Logo.caucse, "유홍석 찐따", "준비중입니다", "www.naver.com", new MyDate("20171031"), "운영자");
        StarHelper.starWindowInfo(sampleWindowInfo);

        String starredWindowInfoListStr = PlayerPrefs.getInstance().getString("star");
        Log.d("StartHelperTest", starredWindowInfoListStr);


        List<WindowInfo> windowInfoList = new Gson().fromJson(starredWindowInfoListStr, new TypeToken<List<WindowInfo>>() {
        }.getType());

        assertThat(windowInfoList.get(0).getTitle(), is("유홍석 찐따"));
        assertThat(windowInfoList.get(0).getContent(), is("준비중입니다"));
    }
}
