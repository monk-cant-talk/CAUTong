package tong.cau.com.cautong;


import android.app.Activity;
import android.content.Context;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

//전체 뷰 중에서 기사를 하나 찾게 되면 기사에 관련한 윈도우를 띄워야 하는데 그 정보를 아래에 채워 넣는다.
public class WindowInfo {

	//윈도우에 띄울 로고 이미지
	public Logo logo;

	//윈도우에 띄울 제목
	public String title;

	//윈도우에 띄울 컨텐츠
	public String content;

	//소스정보 (ex. https://www.cau.ac.kr/)
	public String link;

	public enum Logo {
		main, unknown
	}

	public WindowInfo(){
		logo = Logo.unknown;
		title = "no title";
		content = "no content";
		link = "https://www.cau.ac.kr";
	}

	public LinearLayout getLayout(Activity activity){
		LinearLayout ret = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.main_window_info, null);
		Button info_menu = (Button)ret.findViewById(R.id.window_info_menu);
		RelativeLayout info_logo = (RelativeLayout) ret.findViewById(R.id.window_info_logo);
		LinearLayout info_window = (LinearLayout) ret.findViewById(R.id.window_info_window);
		TextView info_title = (TextView)ret.findViewById(R.id.window_info_title);
		TextView info_content = (TextView) ret.findViewById(R.id.window_info_content);

		info_title.setText(title);
		info_content.setText(content);
		info_logo.setBackgroundResource(getLogoImage());

		return ret;
	}

	private int getLogoImage(){
		switch (logo){
			case main:
				return R.drawable.cau;
			case unknown:
				return R.drawable.cau;
		}
		return R.drawable.cau;
	}
}
