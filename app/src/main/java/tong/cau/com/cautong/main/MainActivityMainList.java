package tong.cau.com.cautong.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import tong.cau.com.cautong.R;
import tong.cau.com.cautong.WindowListDialog;
import tong.cau.com.cautong.model.Site;
import tong.cau.com.cautong.model.WindowInfo;
import tong.cau.com.cautong.start.StartActivity;
import tong.cau.com.cautong.utility.MapDataParser;

import static android.content.Context.INPUT_METHOD_SERVICE;

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

		setEditTextExit(searchText, new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Toast.makeText(MainActivity.instance, "fdsafdsa", Toast.LENGTH_SHORT);
				MainActivity.instance.keywordFilter(searchText.getText().toString());
			}
		});



		return cm;
	}

	public void setEditTextExit(EditText curr, View.OnClickListener listener){
		curr.setOnEditorActionListener(new MainActivityMainList.onEditorActionListener(curr, true, listener));
		//curr.setOnTouchListener(new onEditorTouchListener(curr, listener));
	}

	protected class onEditorActionListener implements TextView.OnEditorActionListener{
		private EditText next;
		private boolean exit;
		View.OnClickListener listener;
		public onEditorActionListener(EditText next, boolean exit, View.OnClickListener listener){
			this.next = next;
			this.exit = exit;
			this.listener = listener;
		}
		@Override
		public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
			if (actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_DONE) {
				if (!exit) {
					next.setText("");
					next.setOnTouchListener(null);
				} else {
					InputMethodManager imm = (InputMethodManager) MainActivity.instance.getSystemService(INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(next.getWindowToken(), 0);
				}
				if(listener != null){
					listener.onClick(v);
				}
			}
			return false;
		}
	}
}
