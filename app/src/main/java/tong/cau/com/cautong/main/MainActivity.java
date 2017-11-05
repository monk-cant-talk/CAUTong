package tong.cau.com.cautong.main;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import tong.cau.com.cautong.FoundInfoCollector;
import tong.cau.com.cautong.R;
import tong.cau.com.cautong.alarm.AlarmService;
import tong.cau.com.cautong.model.Board;
import tong.cau.com.cautong.model.Site;
import tong.cau.com.cautong.model.WindowInfo;
import tong.cau.com.cautong.start.StartActivity;
import tong.cau.com.cautong.utility.MapDataParser;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    ViewPager viewPager;
    MainViewAdapter adapter;
    RelativeLayout button;
    RelativeLayout testbutton;
    public static MainActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        instance = this;


        adapter = new MainViewAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.main_activity_view_pager);
        viewPager.setAdapter(adapter);

        //button = (RelativeLayout) findViewById(R.id.requestButton);
        //testbutton = (RelativeLayout) findViewById(R.id.test_button);

        startActivity(StartActivity.search_key);
    }

    //이전 액티비티(StartActivity) 에서 검색키워드를 넣게 되면 자동으로 이 액티비티로 넘어오면서 이 함수가 실행된다.
    private void startActivity(String searchKey) {

        // 크롤링 할 정보 로드
        Map<String, Site> siteMap = MapDataParser.getSiteMap();
        ArrayList<Site> siteList = new ArrayList<>();
        for (String key : siteMap.keySet()) {
            siteList.add(siteMap.get(key));
        }

        // 크롤링 시작
        for (Site site : siteList) {
            if (site.isEnabled()) {
                getRequestSite(site);
            }
        }

        // 링크 주소를 기준으로 중복 제거
//        Map<String, WindowInfo> map = new LinkedHashMap<>();
//        for (WindowInfo wf : finalList) {
//            map.put(wf.getLink(), wf);
//        }
//        finalList.clear();
//        finalList.addAll(map.values());

        // 게시물 시간 순으로 내림차순 정렬
        Collections.sort(finalList, new Comparator<WindowInfo>() {
            @Override
            public int compare(WindowInfo o1, WindowInfo o2) {
                return Long.compare(o1.getDateValue(), o2.getDateValue());
            }
        });
        Collections.reverse(finalList);
        Log.d(TAG, "number: " + finalList.size());

        // 키워드로 필터링
        if (searchKey.equals("")) {
            for (WindowInfo wf : finalList) {
                MainActivity.instance.addWindow(wf);
            }
        } else {
            for (WindowInfo wf : finalList) {
                if (wf.getTitle().toLowerCase().contains(searchKey.toLowerCase())) {
                    MainActivity.instance.addWindow(wf);
                }
            }
        }

        /*
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
        */
        startAlarmService();
    }

    private void startAlarmService() {
        Intent intent = new Intent(MainActivity.this, AlarmService.class);
        startService(intent);

    }

    private void getRequestSite(Site site) {
        List<Thread> threadList = new ArrayList<>();
        for (Board board : site.getBoardList()) {
            threadList.add(new Thread(new FavoriteRunnable(site, board.getName())));
            threadList.get(threadList.size() - 1).start();
        }

        // 모든 스레드가 끝날 때까지 기달린다.
        for (Thread thread : threadList) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void getFavoriteSite() {
        //TODO 유저의 즐겨찾기 리스트에 등록되어 있는 사이트 리스트를 받아옴
//        Map<String, Site> siteMap = MapDataParser.parseSiteMap(getResources());

    }

    final List<WindowInfo> finalList = Collections.synchronizedList(new ArrayList<WindowInfo>());

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
                finalList.addAll(foundList);
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
            adapter.main.layout.addView(info.getLayout(MainActivity.instance));
        }
    }
}
