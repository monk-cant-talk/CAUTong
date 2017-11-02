package tong.cau.com.cautong;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Map;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import tong.cau.com.cautong.model.Site;
import tong.cau.com.cautong.utility.SiteXmlParser;

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

        assertThat(site.getBaseUrl(), is(CAU_URL));
        assertThat(site.getSsoUrl(), is(SSO_URL));
        assertThat(site.getBbsBaseUrl(), is(CAU_NOTICE));
        assertThat(site.getBbsListParams(), is(CAU_NOTICE_BBS));
    }
}
