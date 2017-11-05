package tong.cau.com.cautong.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import tong.cau.com.cautong.R;

public class MainActivityStarList extends Fragment{
	public LinearLayout layout;


	public MainActivityStarList(){}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View cm = inflater.inflate(R.layout.main_activity_star_list, container, false);
		layout = cm.findViewById(R.id.view_pager_star);
		return cm;
	}
}
