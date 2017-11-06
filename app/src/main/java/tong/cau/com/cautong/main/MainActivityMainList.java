package tong.cau.com.cautong.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import tong.cau.com.cautong.R;
import tong.cau.com.cautong.WindowListDialog;
import tong.cau.com.cautong.model.Site;
import tong.cau.com.cautong.model.WindowInfo;
import tong.cau.com.cautong.utility.MapDataParser;

public class MainActivityMainList extends Fragment{
	public LinearLayout layout;

	public RelativeLayout search_button;
	public EditText searchText;
	public RelativeLayout list_button;


	public MainActivityMainList(){}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View cm = inflater.inflate(R.layout.main_activity_main_list, container, false);
		layout = cm.findViewById(R.id.view_pager_main);
		search_button = cm.findViewById(R.id.main_activity_search_button);
		searchText = cm.findViewById(R.id.main_activity_search_edit_text);
		list_button = cm.findViewById(R.id.main_activity_list_button);

		list_button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Log.d("fdsafdsa", "fdsfdas");
				Map<String, Site> siteMap = MapDataParser.getSiteMap();
				ArrayList<Site> siteList = new ArrayList<>();
				for (String key : siteMap.keySet()) {
					siteList.add(siteMap.get(key));
				}
				WindowListDialog ll = new WindowListDialog(MainActivity.instance, siteList);
				ll.show();
			}
		});

		search_button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				MainActivity.instance.keywordFilter(searchText.getText().toString());
			}
		});


		return cm;
	}
}
