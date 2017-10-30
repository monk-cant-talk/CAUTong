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

	private Button button1;
	private Button button2;
	private Button button3;
	private RelativeLayout backButton;

	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main_window_menu_dialog);

		LinearLayout layout1 = findViewById(R.id.main_window_menu_dialog_layout_1);


		button1 = findViewById(R.id.main_window_menu_dialog_button_1);
		button2 = findViewById(R.id.main_window_menu_dialog_button_2);
		button3 = findViewById(R.id.main_window_menu_dialog_button_3);
		backButton = findViewById(R.id.main_window_menu_dialog_button_back);

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
	public WindowMenuDialog(Context context) {
		super(context, android.R.style.Theme_Translucent_NoTitleBar);
		this.context = context;
	}
}
