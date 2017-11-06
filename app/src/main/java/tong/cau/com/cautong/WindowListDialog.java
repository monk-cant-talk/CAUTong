package tong.cau.com.cautong;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import tong.cau.com.cautong.main.MainActivity;
import tong.cau.com.cautong.model.Site;

public class WindowListDialog extends Dialog{

	private static final String TAG = "WindowListDialog";
	private View.OnClickListener btn1, btn2, btn3;
	private LinearLayout scrollView;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main_window_site_list_dialog);

		LinearLayout layout1 = findViewById(R.id.main_window_menu_dialog_layout_1);


		scrollView = (LinearLayout) this.findViewById(R.id.list_items);
		RelativeLayout okButton = findViewById(R.id.main_window_menu_dialog_button_back);


		okButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				MainActivity.instance.refreshWindowInfo();
				dismiss();
			}
		});
		layout1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});

		for(String key : MainActivity.siteMap.keySet()){
			AddList(MainActivity.siteMap.get(key));
		}
	}

	// 클릭버튼이 하나일때 생성자 함수로 클릭이벤트를 받는다.
	public WindowListDialog(Context context, List<Site> list) {
		super(context, android.R.style.Theme_Translucent_NoTitleBar);
	}

	public void AddList(Site site){
		LinearLayout cm = (LinearLayout) MainActivity.instance.getLayoutInflater().inflate(R.layout.main_window_list, null);
		RelativeLayout rl = (RelativeLayout) cm.findViewById(R.id.window_list_board);
		View view = cm.findViewById(R.id.window_list_checkbox);
		if(site.isEnabled()){
			view.setBackgroundResource(R.drawable.check_box_full);
		}else{
			view.setBackgroundResource(R.drawable.check_box_empty);
		}
		TextView tv = cm.findViewById(R.id.window_list_title);
		tv.setText(site.getName());
		rl.setOnClickListener(new OneList(site, view));
		scrollView.addView(cm);
	}

	private class OneList implements View.OnClickListener {

		Site site;
		View checkbox;

		public OneList(Site site, View checkbox){
			this.site = site;
			this.checkbox = checkbox;
		}

		@Override
		public void onClick(View view) {
			site.setEnabled(!site.isEnabled());
			if(site.isEnabled()){
				checkbox.setBackgroundResource(R.drawable.check_box_full);
			}else{
				checkbox.setBackgroundResource(R.drawable.check_box_empty);
			}
		}
	}
}
