package tong.cau.com.cautong;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import tong.cau.com.cautong.model.Site;
import tong.cau.com.cautong.utility.SiteXmlParser;

public class MainActivity extends AppCompatActivity {

    LinearLayout main_layout;
    RelativeLayout button;
    Activity _this = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        main_layout = (LinearLayout) findViewById(R.id.main_linear_layout);
        button = (RelativeLayout) findViewById(R.id.requestButton);


        for (int i = 0; i < FoundInfoCollector.INITIAL_WINDOW_SIZE; i++) {
            main_layout.addView(FoundInfoCollector.getInstance(_this).getInfo(i).getLayout());
        }


        //MainActivity Context의 리소스를 이용하여 xml로 파싱한 Site 정보 리스트를 가져옴
        Map<String, Site> siteMap = SiteXmlParser.parseSiteMap(getResources());

        //TODO 유저의 즐겨찾기 리스트에 등록되어 있는 사이트 리스트를 받아옴
        final List favoriteList = new ArrayList();
        for(String key : siteMap.keySet()){
            favoriteList.add(siteMap.get(key));
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread crawler = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        FoundInfoCollector.getInstance(_this).findInfo(favoriteList);
                    }
                });
                crawler.start();
            }
        });

        button.callOnClick();
    }
}
