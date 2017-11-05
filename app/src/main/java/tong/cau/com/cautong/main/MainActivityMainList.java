package tong.cau.com.cautong.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import tong.cau.com.cautong.R;

public class MainActivityMainList extends Fragment{
	public LinearLayout layout;


	public MainActivityMainList(){}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View cm = inflater.inflate(R.layout.main_activity_main_list, container, false);
		layout = cm.findViewById(R.id.view_pager_main);
		return cm;
	}
}
