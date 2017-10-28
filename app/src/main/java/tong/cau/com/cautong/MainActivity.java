package tong.cau.com.cautong;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    LinearLayout main_layout;
    RelativeLayout button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        main_layout = (LinearLayout) findViewById(R.id.main_linear_layout);
        button = (RelativeLayout) findViewById(R.id.requestButton);

        for (int i = 0; i < 2000; i++) {
            main_layout.addView(FoundInfoCollector.getInstance().getInfo(i).getLayout(this));
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        FoundInfoCollector.getCAUNotice();
                    }
                }).start();
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                FoundInfoCollector.getCAUNotice();
            }
        }).start();


    }
}
