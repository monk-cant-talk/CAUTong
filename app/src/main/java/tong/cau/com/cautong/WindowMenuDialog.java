package tong.cau.com.cautong;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class WindowMenuDialog extends Dialog{

	private View.OnClickListener btn1, btn2, btn3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main_window_menu_dialog);

		LinearLayout layout1 = findViewById(R.id.main_window_menu_dialog_layout_1);

		Button button1 = findViewById(R.id.main_window_menu_dialog_button_1);
		Button button2 = findViewById(R.id.main_window_menu_dialog_button_2);
		Button button3 = findViewById(R.id.main_window_menu_dialog_button_3);
		RelativeLayout backButton = findViewById(R.id.main_window_menu_dialog_button_back);

		button1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				btn1.onClick(view);
				dismiss();
			}
		});

		button2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				btn2.onClick(view);
				dismiss();
			}
		});

		button3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				btn3.onClick(view);
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
	public WindowMenuDialog(Context context, View.OnClickListener btn1, View.OnClickListener btn2, View.OnClickListener btn3) {
		super(context, android.R.style.Theme_Translucent_NoTitleBar);
		this.btn1 = btn1;
		this.btn2 = btn2;
		this.btn3 = btn3;
	}
}
