package tong.cau.com.cautong;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.google.gson.JsonArray;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.net.URLDecoder;
import java.util.Map;

import tong.cau.com.cautong.model.Site;
import tong.cau.com.cautong.utility.BoardMapper;
import tong.cau.com.cautong.utility.SiteXmlParser;

import static android.app.PendingIntent.getActivity;
import static junit.framework.Assert.assertNotNull;

/**
 * Created by Velmont on 2017-10-30.
 */
@RunWith(AndroidJUnit4.class)
public class FindInfoCollectorTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Test
    public void testFindInfo() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertNotNull(appContext.getResources());

        Map<String, Site> siteMap = SiteXmlParser.parseSiteMap(appContext.getResources());

        Site site = siteMap.get("CAU");
        JsonArray dataList = BoardMapper.getArticleInfo(site);
        if (dataList != null) {
            int minSize = dataList.size();
            for (int i = 0; i < minSize; ++i) {
                Log.d("ficTest", dataList.get(i).getAsJsonObject().get("TITLE").getAsString());
                Log.d("ficTest", dataList.get(i).getAsJsonObject().get("NAME").getAsString());
                Log.d("ficTest", String.valueOf(dataList.get(i).getAsJsonObject().get("REGDATE").getAsLong()));

            }
        }
    }
}
