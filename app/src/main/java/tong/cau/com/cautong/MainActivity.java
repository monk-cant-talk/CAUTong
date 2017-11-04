package tong.cau.com.cautong;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import tong.cau.com.cautong.alarm.AlarmService;
import tong.cau.com.cautong.model.Board;
import tong.cau.com.cautong.model.Site;
import tong.cau.com.cautong.model.WindowInfo;
import tong.cau.com.cautong.start.StartActivity;
import tong.cau.com.cautong.utility.SiteParser;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    LinearLayout main_layout;
    RelativeLayout button;
    RelativeLayout testbutton;
    public static MainActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        instance = this;

        main_layout = (LinearLayout) findViewById(R.id.main_linear_layout);
        button = (RelativeLayout) findViewById(R.id.requestButton);
        testbutton = (RelativeLayout) findViewById(R.id.test_button);

        startActivity(StartActivity.search_key);

    }

    //이전 액티비티(StartActivity) 에서 검색키워드를 넣게 되면 자동으로 이 액티비티로 넘어오면서 이 함수가 실행된다.
    private void startActivity(String search_key) {

        Map<String, Site> siteMap = SiteParser.getSiteMap(getResources());
        ArrayList<Site> siteList = new ArrayList<>();
        for (String key : siteMap.keySet()) {
            siteList.add(siteMap.get(key));
        }

        for (Site site : siteList) {
            getRequestSite(site);
        }

        testbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                Notification notifi = new Notification.Builder(getApplicationContext())
                        .setContentTitle("Content Title")
                        .setContentText("Content Text")
                        .setSmallIcon(R.drawable.c)
                        .setTicker("알림!!!")
                        //.setContentIntent(intent)
                        .build();

                nm.notify(10, notifi);
                Toast.makeText(MainActivity.this, "fdsa", Toast.LENGTH_SHORT).show();
            }
        });

        startAlarmService();
    }

    private void startAlarmService() {
        Intent intent = new Intent(MainActivity.this, AlarmService.class);
        startService(intent);

    }

    private void getRequestSite(Site site) {
        for (Board board : site.getBoardList()) {
            new Thread(new FavoriteRunnable(site, board.getName())).start();
        }
    }

    private void getFavoriteSite() {
        //TODO 유저의 즐겨찾기 리스트에 등록되어 있는 사이트 리스트를 받아옴
//        Map<String, Site> siteMap = SiteParser.parseSiteMap(getResources());

    }

    private class FavoriteRunnable implements Runnable {
        Site site;
        String boardName;

        public FavoriteRunnable(Site site, String boardName) {
            this.site = site;
            this.boardName = boardName;
        }

        @Override
        public void run() {
            ArrayList<WindowInfo> foundList = FoundInfoCollector.getInstance().findInfo(site, boardName);
            if (foundList != null) {
                for (WindowInfo wf : foundList) {
                    MainActivity.instance.addWindow(wf);
                }
            }
        }
    }

    public void addWindow(WindowInfo info) {
        runOnUiThread(new AddWindowInfo(info));
    }

    private class AddWindowInfo implements Runnable {
        WindowInfo info;

        public AddWindowInfo(WindowInfo info) {
            this.info = info;
        }

        @Override
        public void run() {
            main_layout.addView(info.getLayout(MainActivity.instance));
        }
    }
}
