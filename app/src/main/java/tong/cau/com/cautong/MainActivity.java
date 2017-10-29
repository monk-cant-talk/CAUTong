package tong.cau.com.cautong;

import android.content.SharedPreferences;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

	LinearLayout main_layout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);

		main_layout = (LinearLayout) findViewById(R.id.main_linear_layout);
		FoundInfoCollector.findInfo(this);
		for (int i = 0; i < 200; i++) {
			main_layout.addView(FoundInfoCollector.getInfo(i).getLayout());
		}
		
	}


}
