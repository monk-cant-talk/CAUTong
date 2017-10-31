package tong.cau.com.cautong;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

//전체 뷰 중에서 기사를 하나 찾게 되면 기사에 관련한 윈도우를 띄워야 하는데 그 정보를 아래에 채워 넣는다.
public class WindowInfo {
    //윈도우에 띄울 로고 이미지
    private Logo logo;

    //윈도우에 띄울 제목
    private String title;

    //윈도우에 띄울 컨텐츠
    private String content;

    //소스정보 (ex. https://www.cau.ac.kr/)
    private String link;

    //날짜
    private MyDate date;

    //작성자
    private String author;



    public void rePrint(Activity activity){
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                info_logo.setBackgroundResource(getLogoImage());
                info_title.setText(WindowInfo.this.title);
                info_content.setText(WindowInfo.this.content);
                info_date.setText(WindowInfo.this.date.toString());
                info_writer.setText(WindowInfo.this.author);
            }
        });
    }


    public void setLogo(Activity activity, Logo logo) {
        this.logo = logo;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                info_logo.setBackgroundResource(getLogoImage());
            }
        });
    }

    public void setTitle(Activity activity, String title) {
        this.title = title;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                info_title.setText(WindowInfo.this.title);
            }
        });
    }

    public void setContent(Activity activity, String content) {
        this.content = content;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                info_content.setText(WindowInfo.this.content);
            }
        });
    }

    public void setDate(Activity activity, MyDate date) {
        this.date = date;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                info_date.setText(WindowInfo.this.date.toString());
            }
        });
    }

    public void setAuthor(Activity activity, String author) {
        this.author = author;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                info_writer.setText(WindowInfo.this.author);
            }
        });
    }

    LinearLayout info_window;
    RelativeLayout info_logo;
    RelativeLayout info_title_board;
    TextView info_content;
    TextView info_writer;
    TextView info_title;
    TextView info_date;
    Button info_menu;
    LinearLayout ret;

    public void init(Logo logo, String title, String content, String link, MyDate date, String author) {
        this.logo = logo;
        this.title = title;
        this.content = content;
        this.link = link;
        this.date = date;
        this.author = author;
    }

    public WindowInfo() {
        logo = Logo.ict;
        title = "no title";
        content = "no content";
        link = "https://www.cau.ac.kr";
        date = new MyDate(new SimpleDateFormat("yyyy-MM-dd-HH-mm"), "2017-03-09-09-40");
        author = "cauTong";
    }

    //변수값이 변했으면 적용한다.
    public void print() {
        info_title.setText(title);
        info_content.setText(content);
        info_writer.setText(author);
        info_date.setText(date.toString());
        info_logo.setBackgroundResource(getLogoImage());
        info_title_board.setBackgroundResource(getLogoColor());
    }


    public LinearLayout getLayout() {

        ret = (LinearLayout) MainActivity.instance.getLayoutInflater().inflate(R.layout.main_window_info, null);
        info_window = ret.findViewById(R.id.window_info_window);
        info_logo = ret.findViewById(R.id.window_info_logo);
        info_title_board = ret.findViewById(R.id.window_info_title_board);
        info_content = ret.findViewById(R.id.window_info_content);
        info_writer = ret.findViewById(R.id.window_info_writer);
        info_title = ret.findViewById(R.id.window_info_title);
        info_date = ret.findViewById(R.id.window_info_date);
        info_menu = ret.findViewById(R.id.window_info_menu);

        info_window.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(WindowInfo.this.link != null) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(WindowInfo.this.link));
                    MainActivity.instance.startActivity(intent);
                }
            }
        });

        info_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WindowMenuDialog dialog = new WindowMenuDialog(MainActivity.instance,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(MainActivity.instance, "첫번째 버튼 터치", Toast.LENGTH_SHORT).show();
                            }
                        }, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainActivity.instance, "두번째 버튼 터치", Toast.LENGTH_SHORT).show();
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainActivity.instance, "세번째 버튼 터치", Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.show();
            }
        });

        print();
        return ret;
    }

    public enum Logo {
        notice, unknown, social, caucse, ict, cauie
    }

    private int getLogoImage() {
        switch (logo) {
            case notice:
                return R.drawable.logo_cau;
            case unknown:
                return R.drawable.c;
            case social:
                return R.drawable.logo_social;
            case caucse:
                return R.drawable.logo_cse;
            case cauie:
                return R.drawable.logo_cauie;
            case ict:
                return R.drawable.logo_ict;
        }
        return R.drawable.c;
    }

    private int getLogoColor() {
        switch (logo) {
            case notice:
                return R.drawable.window_board_title_caunotice;
            case unknown:
                return R.drawable.window_board_title_unknown;
            case social:
                return R.drawable.window_board_title_social;
            case caucse:
                return R.drawable.window_board_title_caucse;
            case cauie:
                return R.drawable.window_board_title_cauie;
            case ict:
                return R.drawable.window_board_title_ict;
        }
        return R.drawable.c;
    }
}
