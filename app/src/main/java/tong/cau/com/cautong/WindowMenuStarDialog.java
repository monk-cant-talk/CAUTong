package tong.cau.com.cautong;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class WindowMenuStarDialog extends Dialog{

	private View.OnClickListener btn1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main_window_menu_star_dialog);

		LinearLayout layout1 = findViewById(R.id.main_window_menu_dialog_layout_1);

		Button button1 = findViewById(R.id.main_window_menu_dialog_button_1);
		RelativeLayout backButton = findViewById(R.id.main_window_menu_dialog_button_back);

		button1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				btn1.onClick(view);
				dismiss();
			}
		});

		backButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
		layout1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});

	}

	// 클릭버튼이 하나일때 생성자 함수로 클릭이벤트를 받는다.
	public WindowMenuStarDialog(Context context, View.OnClickListener btn1) {
		super(context, android.R.style.Theme_Translucent_NoTitleBar);
		this.btn1 = btn1;
	}
}
