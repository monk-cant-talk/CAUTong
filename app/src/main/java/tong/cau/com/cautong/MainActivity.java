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

import tong.cau.com.cautong.Alarm.BroadcastAlarm;
import tong.cau.com.cautong.Alarm.AlarmService;
import tong.cau.com.cautong.model.Site;

public class MainActivity extends AppCompatActivity {

    LinearLayout main_layout;
    RelativeLayout button;
    public static MainActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        instance = this;

        main_layout = (LinearLayout) findViewById(R.id.main_linear_layout);
        button = (RelativeLayout) findViewById(R.id.requestButton);
        RelativeLayout testbutton = (RelativeLayout) findViewById(R.id.test_button);


        getRequestSite();


        testbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationManager nm = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
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

    private void startAlarmService(){
        Intent intent = new Intent(MainActivity.this, AlarmService.class);
        startService(intent);

    }

    private void getRequestSite(){
        //MainActivity Context의 리소스를 이용하여 xml로 파싱한 Site 정보 리스트를 가져옴
        Map<String, Site> siteMap = SiteXmlParser.parseSiteMap(getResources());

        //TODO 유저의 즐겨찾기 리스트에 등록되어 있는 사이트 리스트를 받아옴
        ArrayList<Site> favoriteList = new ArrayList<>();
        for(String key : siteMap.keySet())
            favoriteList.add(siteMap.get(key));

        new Thread(new FavoriteRunnable(favoriteList)).start();
    }

    private class FavoriteRunnable implements Runnable{
        List favoriteList;
        public FavoriteRunnable(List favoriteList){this.favoriteList = favoriteList;}
        @Override
        public void run(){
            FoundInfoCollector.getInstance(MainActivity.this).findInfo(favoriteList);
        }
    }

    public void addWindow(WindowInfo info){
        runOnUiThread(new AddWindowInfo(info));
    }

    private class AddWindowInfo implements Runnable{
        WindowInfo info;
        public AddWindowInfo(WindowInfo info){this.info = info;}
        @Override
        public void run(){
            main_layout.addView(info.getLayout());
        }
    }
}
