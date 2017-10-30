package tong.cau.com.cautong;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import tong.cau.com.cautong.model.Site;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Created by Velmont on 2017-10-30.
 */
@RunWith(AndroidJUnit4.class)
public class XmlParseTest {
    private static final String CAU_URL = "https://www.cau.ac.kr:443";
    private static final String SSO_URL = "https://sso2.cau.ac.kr/SSO/AuthWeb/NACookieManage.aspx";
    private static final String CAU_NOTICE = "https://www.cau.ac.kr:443/04_ulife/causquare/notice/notice_list.php?bbsId=cau_notice";
    private static final String CAU_NOTICE_BBS = "%2fajax%2fbbs_list.php%3fisNoti%3dY%26pageSize%3d50%26";
    @Test
    public void parseTest(){
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertNotNull(appContext.getResources());

        Map<String, Site> siteMap = SiteXmlParser.parseSiteMap(appContext.getResources());
        Site site = siteMap.get("CAU");

        assertThat(site.getSiteUrl(), is(CAU_URL));
        assertThat(site.getSsoUrl(), is(SSO_URL));
        assertThat(site.getNoticeUrl(), is(CAU_NOTICE));
        assertThat(site.getNoticeBbsUrl(), is(CAU_NOTICE_BBS));
    }
}
