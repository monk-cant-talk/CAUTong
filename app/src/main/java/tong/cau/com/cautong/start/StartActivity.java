package tong.cau.com.cautong.start;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import tong.cau.com.cautong.MainActivity;
import tong.cau.com.cautong.R;

public class StartActivity extends AppCompatActivity{

	public static String search_key = "";

	private static StartActivity instance;

	public EditText editText;

	protected int anim_start_enter = R.anim.start_enter;
	protected int anim_start_exit = R.anim.start_exit;
	protected int anim_finish_enter = R.anim.finish_enter;
	protected int anim_finish_exit = R.anim.finish_exit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		this.overridePendingTransition(anim_start_enter, anim_start_exit);
		instance = this;
		editText = (EditText)findViewById(R.id.edit_text_search);

		setEditTextExit(editText, new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				search_key = StartActivity.instance.editText.getText().toString();
				startActivity(new Intent(StartActivity.this, MainActivity.class));
			}
		});
	}

	@Override
	public void finish() {
		super.finish();
		this.overridePendingTransition(anim_finish_enter, anim_finish_exit);
	}

	public void setEditTextExit(EditText curr, View.OnClickListener listener){
		curr.setOnEditorActionListener(new onEditorActionListener(curr, true, listener));
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
					InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
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
