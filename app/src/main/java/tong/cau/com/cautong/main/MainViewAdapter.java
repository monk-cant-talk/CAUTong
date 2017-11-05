package tong.cau.com.cautong.main;


import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import tong.cau.com.cautong.R;
import tong.cau.com.cautong.WindowMenuDialog;
import tong.cau.com.cautong.model.WindowInfo;

public class MainViewAdapter extends PagerAdapter {

	MainActivity activity;
	public LinearLayout main;
	public LinearLayout star;
	private boolean init = false;

	public MainViewAdapter(MainActivity activity) {
		this.activity = activity;
		main = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.main_activity_main_list, null);
		star = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.main_activity_main_list, null);
	}

	@Override
	public int getCount() {
		return 2;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return false;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		if(!init){
			Toast.makeText(activity, "fdsa", Toast.LENGTH_SHORT).show();
			((ViewPager) container).addView(main, 0);
			((ViewPager) container).addView(star, 0);
			init = true;
		}
		if(position == 0)
			return main;
		else
			return star;

	}

}
