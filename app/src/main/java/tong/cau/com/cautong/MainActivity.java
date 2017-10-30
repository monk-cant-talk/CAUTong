package tong.cau.com.cautong;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

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

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread crawler = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        FoundInfoCollector.getInstance(_this).findInfo();
                    }
                });
                crawler.start();
            }
        });

        button.callOnClick();
    }
}
