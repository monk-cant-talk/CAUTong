package tong.cau.com.cautong;


import android.app.Activity;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Date;

//전체 뷰 중에서 기사를 하나 찾게 되면 기사에 관련한 윈도우를 띄워야 하는데 그 정보를 아래에 채워 넣는다.
public class WindowInfo {

    private static final String TAG = "WindowInfo";
    //윈도우에 띄울 로고 이미지
    private Logo logo;

    //윈도우에 띄울 제목
    private String title;

    //윈도우에 띄울 컨텐츠
    private String content;

    // 작성자
    private String author;

    // 작성 날짜
    private Date date;

    //소스정보 (ex. https://www.cau.ac.kr/)
    private String link;

    public enum Logo {
        main, unknown
    }

    public WindowInfo() {
        logo = Logo.unknown;
        title = "no title";
        content = "no content";
        link = "https://www.cau.ac.kr";
    }

    LinearLayout layout = null;
    Button info_menu = null;
    RelativeLayout info_logo = null;
    LinearLayout info_window = null;
    TextView info_title = null;
    TextView info_content = null;

    public void refresh() {
        info_title.setText(title);
        info_content.setText(content);
        info_logo.setBackgroundResource(getLogoImage());
    }

    public LinearLayout getLayout(Activity activity) {
        if (layout == null) {
            Log.d(TAG, "inflate layout");
            layout = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.main_window_info, null);
        }
        info_menu = layout.findViewById(R.id.window_info_menu);
        info_logo = layout.findViewById(R.id.window_info_logo);
        info_window = layout.findViewById(R.id.window_info_window);
        info_title = layout.findViewById(R.id.window_info_title);
        info_content = layout.findViewById(R.id.window_info_content);

        refresh();

        return layout;
    }

    private int getLogoImage() {
        switch (logo) {
            case main:
                return R.drawable.cau;
            case unknown:
                return R.drawable.cau;
        }
        return R.drawable.cau;
    }

    public Logo getLogo() {
        return logo;
    }

    public void setLogo(Logo logo) {
        this.logo = logo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String newTitle) {
        title = newTitle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
