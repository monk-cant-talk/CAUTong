package tong.cau.com.cautong;

import android.app.Activity;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

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
    public Activity activity;

    @Before
    public void setUp(){
        PlayerPrefs.getInstance(mActivityRule.getActivity()).removeKey("star");
        activity = (Activity)mActivityRule.getActivity();
    }

    @Test
    public void initialStarWindowInfo(){
        WindowInfo sampleWindowInfo = new WindowInfo();
        sampleWindowInfo.init(WindowInfo.Logo.caucse, "유홍석 찐따", "준비중입니다", "www.naver.com", new MyDate("20171031"), "운영자");
        StarHelper.starWindowInfo((Activity)activity, sampleWindowInfo);

        Gson gson = new Gson();
        String starredWindowInfoList = PlayerPrefs.getInstance(activity).getString("star");
        List<WindowInfo> windowInfoList = gson.fromJson(starredWindowInfoList, new TypeToken<List<WindowInfo>>(){}.getType());

        assertThat(windowInfoList.get(0).getTitle(), is("유홍석 찐따"));
        assertThat(windowInfoList.get(0).getContent(), is("준비중입니다"));
    }
}
