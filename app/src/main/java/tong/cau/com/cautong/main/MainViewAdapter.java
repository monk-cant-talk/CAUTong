package tong.cau.com.cautong.main;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import tong.cau.com.cautong.R;

public class MainViewAdapter extends FragmentPagerAdapter {

	public MainActivityMainList main;
	public MainActivityStarList star;

	public MainViewAdapter(FragmentManager fm) {
		super(fm);
		main = new MainActivityMainList();
		star = new MainActivityStarList();
	}

	@Override
	public int getCount() {
		return 2;
	}


	@Override
	public Fragment getItem(int position) {
		if(position == 0)
			return main;
		else
			return star;
	}

}